package com.questionManagement.contorller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.questionManagement.constant.fieldEnum.AnswerStageEnum;
import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.entity.Answer;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.AnswerDTO;
import com.questionManagement.entity.dto.PaperDTO;
import com.questionManagement.entity.dto.SubjectDTO;
import com.questionManagement.service.IAnswerService;
import com.questionManagement.service.IPaperService;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.ISubjectService;
import com.questionManagement.util.VerifyUtils;
import com.questionManagement.util.GA.GA;
import com.questionManagement.util.GA.Paper;
import com.questionManagement.util.GA.Rule;

/**
 * 
 * @author CJN
 * @date 2019年3月24日
 * Title: PaperController 
 * Description: Paper 实体类对应业务操作控制器类
 */
@Controller
public class PaperController {

	@Autowired
	private IQuestionService questionService;
	
	@Autowired
	private ISubjectService subjectService;
	
	@Autowired
	private IPaperService paperService; 
	
	@Autowired
	private IAnswerService answerService;
	
	private static List<BaseStatusEnum> statuses = new ArrayList<BaseStatusEnum>();
	
	static {
		statuses.add(BaseStatusEnum.NORMAL);
		statuses.add(BaseStatusEnum.DESTORY);
	}
	
	/**
	 * 
	 * Title: infoMana
	 * Description: 展示该教职工创建的所有试卷
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/paperMana/infoMana")
	public String infoMana(Map<Object, Object> map, HttpSession session) {
		String roleName = (String) session.getAttribute("roleName");
		List<PaperDTO> paperDTOs = new ArrayList<PaperDTO>();
		if(roleName.contains("管理")) {
			paperDTOs = paperService.getDTOListByList(paperService.listAll());
		}else if(roleName.contains("教职工")) {
			int creatorId = ((User) session.getAttribute("user")).getId();
			paperDTOs = paperService.getDTOListByList(paperService.getListByCreatorId(creatorId));
		}
		map.put("paperDTOs", paperDTOs);
		return "manager/paperMana/papers_info";
	}
	
	/**
	 * 
	 * Title: toCreatePaper
	 * Description: 去到可以为哪些学科生成试卷页面
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/paperMana/subjInfo")
	public String toCreatePaper(Map<Object, Object> map, HttpSession session) {
		String roleName = (String)session.getAttribute("roleName");
		User user = (User) session.getAttribute("user");
		Integer creatorId = user.getId();
		
		List<Subject> subjects = new ArrayList<Subject>();
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		
		if(roleName.contains("管理")) {
			subjectDTOs = subjectService.listAllSubjectDTO();
		}else if(roleName.contains("教职工")) {
			subjects = subjectService.listByCreatorId(creatorId);
			subjectDTOs = subjectService.getDTOListByList(subjects);
		}else {
			
		}
		map.put("subjectDTOs", subjectDTOs);
		return "manager/paperMana/toSubject_info";
	}
	
	/**
	 * 
	 * Title: toCreatePaper
	 * Description: 去到设置组卷规则页面
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toCreatePaper")
	public String toCreatePaper(@RequestParam(value = "id")Integer id, Map<Object, Object> map) {
		SubjectDTO subjectDTO = subjectService.getOneById(id);
		List<QuestionType> types = subjectService.getTypesById(id);
		Integer maxChapter = subjectDTO.getSubject().getMaxChapter();
		List<Integer> chapters = new ArrayList<Integer>();
		for(int i = 1; i <= maxChapter; i ++) {
			chapters.add(i);
		}
		map.put("subjectDTO", subjectDTO);
		map.put("types", types);
		map.put("chapters", chapters);
		map.put("statuses", statuses);
		return "manager/paperMana/setting_rule";
	}
	
	/**
	 * 
	 * Title: createPaper
	 * Description: 根据规则自动组卷
	 * @param rule
	 * @param diff
	 * @param paper
	 * @param req
	 * @return
	 * String
	 */
	@RequestMapping(value = "/createPaper")
	public String createPaper(Rule rule, @RequestParam double diff, 
			com.questionManagement.entity.Paper paper, HttpServletRequest req) {
		// 遗传算法组卷
		Paper resultPaper = GA.geneticAlgorithm(rule, diff, req);
		
		// 组装Paper Entity对象存入数据库
		String ids = questionService.getIdsByList(resultPaper.getQuestionList());
		
		Date date = new Date(new java.util.Date().getTime());
		paper.setContent(ids);
		paper.setDifficulty((int)(Math.floor(resultPaper.getDifficulty() * 5)));
		paper.setChapterStart(rule.getMinChapter());
		paper.setChapterEnd(rule.getMaxChapter());
		paper.setTotalScore(rule.getTotalMark());
		paper.setCreateTime(date);
		paper.setUpdateTime(date);
		System.out.println(paper.toString());
		paperService.save(paper);
		
		return "redirect:/paperMana/infoMana";
	}

	/**
	 * 
	 * Title: paperInfo
	 * Description: 教师查看试卷信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/paperInfo")
	public String paperInfo(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		paperService.getKindsQuestionsById(id, map);
		return "manager/paperMana/paper_info";
	}
	
	/**
	 * 
	 * Title: toTestPapers
	 * Description: 去到学生输入邀请码查询试卷页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/stuTest/onlineTest")
	public String toTestPapers(Map<Object, Object> map) {
//		List<PaperDTO> paperDTOs = paperService.getDTOListByList(paperService.listAll());
//		map.put("paperDTOs", paperDTOs);
		return "studentTest/paper/papers_info";
	}
	
	/**
	 * 
	 * Title: getPapersByInviteCode
	 * Description: 学生输入邀请码查询试卷
	 * @param inviteCode
	 * @return
	 * List<com.questionManagement.entity.Paper>
	 */
	@RequestMapping(value = "/getPapersByInviteCode")
	@ResponseBody
	public List<PaperDTO> getPapersByInviteCode
								(@RequestParam(value = "inviteCode") String inviteCode){
		List<PaperDTO> paperDTOs = paperService.getDTOListByList(paperService.listByInviteCode(inviteCode));
		return paperDTOs;
	}
	
	/**
	 * 
	 * Title: idTested
	 * Description: 判断该学生考试时间是否合法及是否参加过该试卷的测评
	 * @param paperId
	 * @param session
	 * @return
	 * boolean
	 */
	@RequestMapping(value = "/isTimeOutOrTested")
	@ResponseBody
	public String idTested(@RequestParam(value = "paperId") Integer paperId, HttpSession session) {
		// 先判断考试时间是否合法
		long now = new Date().getTime();
		Date startTime = paperService.getById(paperId).getStartTime();
		Date endTime = paperService.getById(paperId).getEndTime();
		if(now < startTime.getTime() || now > endTime.getTime()) {
			return "该试卷允许进入测评时间段为" + startTime + "至" + endTime
					+ "，请选择正确时间进入测评！";
		}
		// 在判断是否参加过测评
		Integer answererId = ((User)session.getAttribute("user")).getId();
		String unMark = AnswerStageEnum.UNMARK.getValue();
		String marked = AnswerStageEnum.MARKED.getValue();
		
		boolean res = paperService.isTestedPaper(answererId, paperId, unMark, marked);
		if(res) {
			return "您已参加过该试卷的测评，不能再次参加！";
		}
		return "true";
	}
	
	/**
	 * 
	 * Title: toTestPaper
	 * Description: 学生选择试卷去到测评页面
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toTestPaper")
	public String toTestPaper(@RequestParam(value = "id") Integer id, Map<Object, Object> map, HttpSession session) {
		paperService.getKindsQuestionsById(id, map);
		Integer userId = ((User) session.getAttribute("user")).getId();
		String startTimeStr = "startTime" + userId + id;
		// 刷新浏览器保持倒计时
		if(session.getAttribute(startTimeStr) != null) {
			map.put("startTime", session.getAttribute(startTimeStr));
		}else {
			map.put("startTime", "");
		}
		return "studentTest/paper/paper_test";
	}
	
	/**
	 * 
	 * Title: getStartTime
	 * Description: 记录学生点击开始测评按钮的时间以生成倒计时
	 * @param answererId
	 * @param paperId
	 * @param session
	 * @return
	 * @throws ParseException
	 * Map<Object,Object>
	 */
	@RequestMapping(value = "/getStartTime")
	@ResponseBody
	public Map<Object, Object> getStartTime(@RequestParam(value = "answererId") Integer answererId, 
									@RequestParam(value = "paperId") Integer paperId, HttpSession session) throws ParseException {
		String startTimeStr = "startTime" + answererId + paperId;
		Map<Object, Object> map = new HashMap<>();
		// 第一次进来则保存当前开始时间，以后从session中获取
		if(session.getAttribute(startTimeStr) == null) {
			Date startTime = new Date();
			// 转换为24进制时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			session.setAttribute(startTimeStr, sdf.format(startTime));
			// ajax 返回设定input中的值
			map.put(startTimeStr, sdf.format(startTime));
		}
		return map;
	}
	
	/**
	 * 
	 * Title: paperDelete
	 * Description: 删除一个试卷信息
	 * @param id
	 * @return
	 * boolean
	 */
	@RequestMapping(value = "/paperDelete")
	@ResponseBody
	public boolean paperDelete(@RequestParam Integer id) {
		return paperService.deleteById(id);
	}
	
	/**
	 * 
	 * Title: showPapers
	 * Description: 展示该教师所有试卷，进而查询答卷信息
	 * @param session
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/paperMana/onlineMarking")
	public String showPapers(HttpSession session, Map<Object, Object> map) {
		User user = (User) session.getAttribute("user");
		Integer creatorId = user.getId();
		List<PaperDTO> paperDTOs = paperService.getDTOListByList(paperService.getListByCreatorId(creatorId));
		map.put("paperDTOs", paperDTOs);
		return "staff/subjects_paper_info";
	}
	
	/**
	 * 
	 * Title: showPaperAnswers
	 * Description: 查看某张试卷的所有答题情况
	 * @param paperId
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/showPaperAnswers")
	public String showPaperAnswers(@RequestParam(value = "paperId") Integer paperId, Map<Object, Object> map) {
		List<Answer> answers = answerService.listByPaperIdAndGroupByAnswerId(paperId);
		List<AnswerDTO> answerDTOs = answerService.getDTOListByList(answers);
		map.put("answerDTOs", answerDTOs);
		return "staff/paper_answers_info";
	}
	
	/**
	 * 
	 * Title: inviteMana
	 * Description: 去到教师选择试卷生成邀请码和考试时间段
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/paperMana/inviteMana")
	public String inviteMana(Map<Object, Object> map, HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<PaperDTO> paperDTOs = paperService.getDTOListByList(paperService.getListByCreatorId(user.getId()));
		map.put("paperDTOs", paperDTOs);
		return "manager/paperMana/invites_info";
	}
	
	/**
	 * 
	 * Title: toGetInviteCode
	 * Description: 去到生成邀请码和选择考试是简单页面
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toGetInviteCode")
	public String toGetInviteCode(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		com.questionManagement.entity.Paper paper = paperService.getById(id);
		String inviteCode = VerifyUtils.getInviteCode();
		map.put("paper", paper);
		map.put("inviteCode", inviteCode);
		return "manager/paperMana/getInvite_time";
	}
	
	/**
	 * 
	 * Title: getInviteCode
	 * Description: 保存邀请码和考试时间段
	 * @param id
	 * @return
	 * String
	 */
	@RequestMapping(value = "/getInviteCode")
	public String getInviteCode(com.questionManagement.entity.Paper p) {
		com.questionManagement.entity.Paper paper = paperService.getById(p.getId());
		paper.setInviteCode(p.getInviteCode());
		paper.setStartTime(p.getStartTime());
		paper.setEndTime(p.getEndTime());
		paperService.save(paper);
		
		return "redirect:/paperMana/inviteMana";
	}
	
	
}
