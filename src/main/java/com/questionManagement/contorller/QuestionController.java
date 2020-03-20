package com.questionManagement.contorller;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.questionManagement.constant.WebFile;
import com.questionManagement.constant.fieldEnum.AnswerEnum;
import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.constant.fieldEnum.DifficultyEnum;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.QuestionDTO;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.ISubjectService;
import com.questionManagement.service.IUserService;
import com.questionManagement.util.FileUtil;

/**
 * 
 * @author CJN
 * @date 2019年3月16日
 * Title: QuestionController 
 * Description: Question 试题对应控制器类
 */
@Controller
public class QuestionController {

	@Autowired
	private IQuestionService questionService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISubjectService subjectService;
	
	@javax.annotation.Resource
	private ResourceLoader resourceLoader;
	
	private static List<DifficultyEnum> difficulties = new ArrayList<DifficultyEnum>();
	private static List<BaseStatusEnum> statuses = new ArrayList<BaseStatusEnum>();
	private static List<AnswerEnum> choices = new ArrayList<AnswerEnum>();
	private static List<AnswerEnum> judges = new ArrayList<AnswerEnum>();
	
	static {
		difficulties.add(DifficultyEnum.LEVEL1);
		difficulties.add(DifficultyEnum.LEVEL2);
		difficulties.add(DifficultyEnum.LEVEL3);
		difficulties.add(DifficultyEnum.LEVEL4);
		difficulties.add(DifficultyEnum.LEVEL5);
		statuses.add(BaseStatusEnum.NORMAL);
		statuses.add(BaseStatusEnum.DESTORY);
		choices.add(AnswerEnum.A);
		choices.add(AnswerEnum.B);
		choices.add(AnswerEnum.C);
		choices.add(AnswerEnum.D);
		judges.add(AnswerEnum.TRUE);
		judges.add(AnswerEnum.FALSE);
	}
	
	/**
	 * 
	 * Title: questionMana
	 * Description: 去到管理试题信息页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionMana")
	public String questionMana(Map<Object, Object> map, HttpSession session) {
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		
		User user = (User)session.getAttribute("user");
		String roleName = (String)session.getAttribute("roleName");
		if(roleName.contains("管理员")) {
			questionDTOs = questionService.getDTOListByList(questionService.listAll());
		}else if(roleName.contains("教职工")) {
			questionDTOs = questionService.getDTOsByCreatorId(user.getId());
		}
		
		map.put("questionDTOs", questionDTOs);
		return "manager/questionMana/questions_info";
	}
	
	/**
	 * 
	 * Title: toQuestionInsert
	 * Description: 去到添加试题页面
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toQuestionInsert")
	public String toQuestionInsert(Map<Object, Object> map, HttpSession session) {
		List<User> users = userService.listAll();
		User user = (User) session.getAttribute("user");
		List<Subject> subjects = subjectService.listByCreatorId(user.getId());
		map.put("users", users);
		map.put("subjects", subjects);
		map.put("difficulties", difficulties);
		map.put("statuses", statuses);
		map.put("choices", choices);
		map.put("judges", judges);
		return "manager/questionMana/question_insert";
	}
	
	/**
	 * 
	 * Title: toBatchImport
	 * Description: 去到批量添加试题页面
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/batchImport")
	public String toBatchImport(Map<Object, Object> map, HttpSession session) {
		List<User> users = userService.listAll();
		User user = (User) session.getAttribute("user");
		List<Subject> subjects = subjectService.listByCreatorId(user.getId());
		map.put("users", users);
		map.put("subjects", subjects);
		return "manager/questionMana/question_batch_import";
	}
	
	/**
	 * 
	 * Title: downloadTemplate
	 * Description: 下载试题模板
	 * @param fileName
	 * @param req
	 * @param res
	 * @return
	 * String
	 */
	@RequestMapping(value = "/downloadTemplate")
	public String downloadTemplate(@RequestParam(value = "fileName") String fileName, HttpServletResponse res) {
		
		// 下载模板
		FileUtil.downloadFile(fileName, res);
		
		return null;
	}
	
	/**
	 * 
	 * Title: batchInsertQuestion
	 * Description: 教师上传试题文件，保存并读取文件内容批量保存试题
	 * @param file
	 * @param creatorId
	 * @param subjectId
	 * @param req
	 * @return
	 * String
	 */
	@RequestMapping(value = "/batchInsertQuestion")
	@ResponseBody
	public String batchInsertQuestion(@RequestParam(value = "excelFile")MultipartFile file, 
						@RequestParam(value = "creatorId") Integer creatorId,
						@RequestParam(value = "subjectId") Integer subjectId, HttpServletRequest req) {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件扩展名
		String extention = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if(!extention.equalsIgnoreCase("xls")) {
			return "文件格式不对，请重新上传xls格式文件！";
		}
		// 创建文件夹
		File f = new File(WebFile.uploadDir);
		if(!f.exists()) {
			f.mkdir();
		}
		// 传输文件
		String fileDir = WebFile.uploadDir +File.separator + fileName;
		try {
			file.transferTo(new File(fileDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 从文件中读取试题
		List<Question> questions = FileUtil.getQuestionsFromFile(fileDir, creatorId, subjectId);
		if(questions.size() <= 0) {
			return "请按照正确格式填写试题模板！";
		}
		questionService.saveList(questions);
		
		return "批量保存试题成功！";
	}
	
	
	/**
	 * 
	 * Title: getInfosById
	 * Description: 实现Subject ， QuestionType， chapter的二级联动，ajax请求，
	 * 				需要根据选择的Subject 返回对应的 types 和 maxChapter
	 * @param id
	 * @return
	 * Map<Object,Object>
	 */
	@RequestMapping(value = "/getInfosById")
	@ResponseBody
	public Map<Object, Object> getInfosById(@RequestParam(value = "id")Integer id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Subject subject = subjectService.getOneById(id).getSubject();
		List<QuestionType> types = subjectService.getTypesById(id);
		Integer maxChapter = subject.getMaxChapter();
		map.put("types", types);
		map.put("maxChapter", maxChapter);
		return map;
		
	}
	
	/**
	 * 
	 * Title: validateSimilarity
	 * Description: 根据这些参数查找试题库中是否存在非常类似的题目
	 * 				若有，则提示不需要再次添加
	 * @param content
	 * @param subjectId
	 * @param typeId
	 * @param chapter
	 * @param difficulty
	 * @return
	 * boolean
	 */
	@RequestMapping(value = "validateSimilarity")
	@ResponseBody
	public boolean validateSimilarity(@RequestParam(value = "content") String content, 
					@RequestParam(value = "subjectId") Integer subjectId, @RequestParam(value = "typeId") Integer typeId,
					@RequestParam(value = "chapter") Integer chapter, @RequestParam(value = "difficulty") Integer difficulty) {
		
		return questionService.validateSimilarity(content, subjectId, typeId, chapter, difficulty);
	}
	
	/**
	 * 
	 * Title: questionSave
	 * Description: 保存试题
	 * @param question
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionSave")
	public String questionSave(Question question) {
		Date date = new Date(new java.util.Date().getTime());
		question.setCreateTime(date);
		question.setUpdateTime(date);
		questionService.save(question);
		return "redirect:/questionMana";
	}
	
	/**
	 * 
	 * Title: questionEdit
	 * Description: 去到编辑试题页面，试题所属学科不可被编辑
	 * @param map
	 * @param id
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionEdit")
	public String questionEdit(Map<Object, Object> map, @RequestParam Integer id, HttpSession session) {
		Question question = questionService.getById(id);
		Integer subjectId = question.getSubjectId();
		
		
		List<User> users = userService.listAll();
		List<Subject> subjects = subjectService.listByCreatorId(question.getCreatorId());
		
		List<QuestionType> types = subjectService.getTypesById(subjectId);
		
		Integer maxChapter = subjectService.getOneById(subjectId).getSubject().getMaxChapter();
		List<Integer> chapters = new ArrayList<Integer>();
		for(int i = 1; i <= maxChapter; i ++) {
			chapters.add(i);
		}
		map.put("question", question);
		map.put("users", users);
		map.put("subjects", subjects);
		map.put("difficulties", difficulties);
		map.put("statuses", statuses);
		map.put("types", types);
		map.put("chapters", chapters);
		map.put("choices", choices);
		map.put("judges", judges);
		return "manager/questionMana/question_edit";
	}
	
	/**
	 * 
	 * Title: questionUpdate
	 * Description: 更新当前试题
	 * @param question
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionUpdate")
	public String questionUpdate(Question question) {
		Date date = new Date(new java.util.Date().getTime());
		question.setUpdateTime(date);
		questionService.save(question);
		return "redirect:/questionMana";
	}
	
	/**
	 * 
	 * Title: questionInfo
	 * Description: 查看某个试题信息，需要获取ID
	 * @param id
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionInfo")
	public String questionInfo(@RequestParam Integer id, Map<Object, Object> map, HttpSession session) {
		Question question = questionService.getById(id);
		Integer subjectId = question.getSubjectId();
		
		
		List<User> users = userService.listAll();
		List<Subject> subjects = subjectService.listByCreatorId(question.getCreatorId());
		
		List<QuestionType> types = subjectService.getTypesById(subjectId);
		
		Integer maxChapter = subjectService.getOneById(subjectId).getSubject().getMaxChapter();
		List<Integer> chapters = new ArrayList<Integer>();
		for(int i = 1; i <= maxChapter; i ++) {
			chapters.add(i);
		}
		map.put("question", question);
		map.put("users", users);
		map.put("subjects", subjects);
		map.put("difficulties", difficulties);
		map.put("statuses", statuses);
		map.put("types", types);
		map.put("chapters", chapters);
		map.put("choices", choices);
		map.put("judges", judges);
		return "manager/questionMana/question_info";
	}
	
	/**
	 * 
	 * Title: questionDelete
	 * Description: 删除试题，需要获取ID
	 * @param id
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionDelete")
	@ResponseBody
	public boolean questionDelete(@RequestParam Integer id) {
		
		return questionService.deleteById(id);
	}
	
}
