package com.questionManagement.contorller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.questionManagement.constant.fieldEnum.AnswerStageEnum;
import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.constant.fieldEnum.QuestionTypeEnum;
import com.questionManagement.entity.Answer;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.AnswerDTO;
import com.questionManagement.entity.dto.AnswerQuestionDTO;
import com.questionManagement.service.IAnswerService;
import com.questionManagement.service.IPaperService;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.IUserService;
import com.questionManagement.util.StringUtils;


/**
 * 
 * @author CJN
 * @date 2019年3月28日
 * Title: AnswerController 
 * Description: Answer 实体对应业务控制器类
 */
@Controller
public class AnswerController {

	@Autowired
	private IAnswerService answerService;
	
	@Autowired
	private IPaperService paperService;
	
	@Autowired
	private IQuestionService questionService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 
	 * Title: answerSubmit
	 * Description: 保存学生做题答案
	 * @param req
	 * @param answer
	 * @return
	 * String
	 */
	@RequestMapping(value = "/answerSubmit")
	public String answerSubmit(HttpServletRequest req, Answer answer) {
		Integer paperId = answer.getPaperId();
		Integer answererId = answer.getAnswererId();
		String ids = paperService.getById(paperId).getContent();
		List<Question> questions = questionService.getListByIds(ids);
		List<Answer> answers = new ArrayList<>();
		
		for(Question q : questions) {
			Answer a = new Answer();
			String content = req.getParameter("题目" + q.getId());
			
			// 保存单选选项顺序
			if(questionService.getTypeNameById(q.getId()).equals(QuestionTypeEnum.SINGLE.getValue())) {
				String options = req.getParameter("options" + q.getId());
				options = options.substring(1, options.length() - 1);
				a.setOptions(options);
			}
			
			// 多选题获取答案
			if(questionService.getTypeNameById(q.getId()).equals(QuestionTypeEnum.MULTIPLE.getValue())) {
				String[] ansArr = req.getParameterValues("题目" + q.getId());
				if(ansArr != null) {
					content = StringUtils.join(ansArr, ",").replace(" ", "");
				}else {
					content = "";
				}
			}
			a.setAnswererId(answererId);
			a.setPaperId(paperId);
			a.setQuestionId(q.getId());
			a.setContent(content);
			if(content != null && q.getAnswer().equals(content)) {
				a.setScore(q.getScore());
			}
			a.setStage(AnswerStageEnum.UNMARK.getValue());
			a.setStartTime(answer.getStartTime());
			a.setStatus(BaseStatusEnum.NORMAL.getValue());
			answers.add(a);
		}
		answerService.saveAll(answers);
		return "redirect:/stuTest/onlineTest";
	}
	
	/**
	 * 
	 * Title: doMark
	 * Description: 将学生做题答案回显，老师开始评分
	 * @param answererId
	 * @param paperId
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/doMark")
	public String doMark(@RequestParam Integer answererId, @RequestParam Integer paperId, 
												Map<Object, Object> map) {
		answerService.listKindsAnswerQuestionByAnswererIdAndPaperId(answererId, paperId, map);
		List<Answer> answers = answerService.listByAnswererIdAndPaperId(answererId, paperId);
		String answererName = userService.getById(answererId).getName();
		map.put("answers", answers);
		map.put("answererName", answererName);
		return "staff/paper_mark";
	}
	
	/**
	 * 
	 * Title: markSubmit
	 * Description: 老师将评分提交
	 * @param answererId
	 * @param paperId
	 * @param req
	 * @return
	 * String
	 */
	@RequestMapping(value = "/markSubmit")
	public String markSubmit(@RequestParam Integer answererId, @RequestParam Integer paperId, 
												HttpServletRequest req) {
		List<Answer> answers = answerService.listByAnswererIdAndPaperId(answererId, paperId);
		for(Answer answer : answers) {
			// 从请求中获取指定题号的答案保存
			String scoreStr = req.getParameter("得分" + answer.getQuestionId());
			answer.setScore(Integer.parseInt(scoreStr));
			answer.setStage(AnswerStageEnum.MARKED.getValue());
		}
		answerService.saveAll(answers);
		String url = "redirect:/showPaperAnswers?answererId=" + answererId + "&paperId=" + paperId;
		return url;
	}
	
	/**
	 * 
	 * Title: gradeEval
	 * Description: 学生查看所有测试的试卷
	 * @param session
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/stuTest/gradeEval")
	public String gradeEval(HttpSession session, Map<Object, Object> map) {
		Integer answererId = ((User) session.getAttribute("user")).getId();
		List<Answer> answers = answerService.findByAnswererIdAndGroupByPaperId(answererId);
//		for(Answer answer : answers) {
//			Integer totalScore = answerService.calculateTotalScoreByAnswererIdAndPaperId
//					(answer.getAnswererId(), answer.getPaperId());
//			answer.setScore(totalScore);
//		}
		List<AnswerDTO> answerDTOs = answerService.getDTOListByList(answers);
		map.put("answerDTOs", answerDTOs);
		return "studentTest/gradeEval/tested_papers_info";
	}
	
	/**
	 * 
	 * Title: showGradeEval
	 * Description: 显示某个试卷成绩情况
	 * @param answererId
	 * @param paperId
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/showGradeEval")
	public String showGradeEval(@RequestParam Integer answererId, @RequestParam Integer paperId,
												Map<Object, Object> map) {
		Integer totalScore = answerService.calculateTotalScoreByAnswererIdAndPaperId(answererId, paperId);
		// 回显学生做题答案
		answerService.listKindsAnswerQuestionByAnswererIdAndPaperId(answererId, paperId, map);
		List<Answer> answers = answerService.listByAnswererIdAndPaperId(answererId, paperId);
		
		String answererName = userService.getById(answererId).getName();
		map.put("answers", answers);
		map.put("answererName", answererName);
		map.put("totalScore", totalScore);
		
		return "studentTest/gradeEval/paper_gradeEval";
	}
	
	/**
	 * 
	 * Title: getBarChartInfo
	 * Description: 为显示图表传递数据
	 * @param answererId
	 * @param paperId
	 * @return
	 * Map<Object,Object>
	 */
	@RequestMapping(value = "/getBarChartInfo")
	@ResponseBody
	public Map<Object, Object> getBarChartInfo(@RequestParam Integer answererId, @RequestParam Integer paperId){
		List<AnswerQuestionDTO> anQuDTOs = answerService.listAnswerQuestionDTOByAnswererIdAndPaperId(answererId, paperId);
		
		TreeSet<Integer> chapters = new TreeSet<>();
		for(int i = 0; i < anQuDTOs.size(); i++) {
			chapters.add(anQuDTOs.get(i).getQuestion().getChapter());
		}
		
		Map<Object, Object> map = new HashMap<>();
		map.put("anQuDTOs", anQuDTOs);
		map.put("chapters", chapters);
		return map;
	}
}
