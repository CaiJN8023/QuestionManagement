package com.questionManagement.contorller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.questionManagement.constant.MyPage;
import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.SubjectDTO;
import com.questionManagement.service.IQuestionTypeService;
import com.questionManagement.service.ISubjectService;
import com.questionManagement.service.IUserService;

/**
 * 
 * @author CJN
 * @date 2019年3月9日
 * Title: SubjectController 
 * Description: Subject 实体对应控制器类
 */
@Controller
public class SubjectController {

	@Autowired
	private ISubjectService subjectService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IQuestionTypeService questionTypeService;
	
	private static List<BaseStatusEnum> statuses = new ArrayList<BaseStatusEnum>();
	
	static {
		statuses.add(BaseStatusEnum.NORMAL);
		statuses.add(BaseStatusEnum.DESTORY);
	}
	
	
	/**
	 * 
	 * Title: getSubjectPage
	 * Description: 获取subject 分页数据，默认每页五条
	 * @param page
	 * @return
	 * Page<Subject>
	 */
	@RequestMapping(value = "/getSubjectPage")
	@ResponseBody
	public MyPage<SubjectDTO> getSubjectPage(@RequestParam(value = "page") String page,
						@RequestParam(value = "cretieriaName") String cretieriaName, HttpSession session){
		MyPage<SubjectDTO> subjectDTOPage = null;
		
		// 若session中有值，且参数无值，则采用session中的值， 否则直接用参数值
		String sessionVal = (String) session.getAttribute("cretieriaName");
		if(cretieriaName == "" && sessionVal != null) {
			cretieriaName = sessionVal;
		}
		// 如果查询名称为空，则全部查出
//		if(cretieriaName == null || "".equals(cretieriaName)) {
//			Page<Subject> subjectPage = subjectService.getSubjectPage(Integer.parseInt(page), MyPage.PAGELIMIT);
//			subjectDTOPage = subjectService.getSubjectDTOPage(subjectPage, subjectPage.getTotalPages(), (int)subjectPage.getTotalElements());
//		}else {
			// 否则查出部分
			subjectDTOPage = subjectService.getSubjectDTOPageByNameLike(cretieriaName, Integer.parseInt(page), MyPage.PAGELIMIT);
//			session.setAttribute("cretieriaName", cretieriaName);
//		}
		session.setAttribute("totalPages", subjectDTOPage.getTotalPages());
		session.setAttribute("totalElements", subjectDTOPage.getTotalElements());
		return subjectDTOPage;
	}
	
	/**
	 * 
	 * Title: subjectMana
	 * Description: 显示所有学科部分信息，并提供CRUD操作按钮
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/subjectMana")
	public String subjectMana(Map<Object, Object> map, HttpSession session) {
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		
		User user = (User)session.getAttribute("user");
		String roleName = (String)session.getAttribute("roleName");
		if(roleName.contains("管理员")) {
			subjectDTOs = subjectService.getDTOListByList(subjectService.listAll());
		}else if(roleName.contains("教职工")) {
			subjectDTOs = subjectService.getDTOListByList(subjectService.listByCreatorId(user.getId()));
		}
		
		
		map.put("subjectDTOs", subjectDTOs);
		return "manager/subjectsMana/subjects_info";
	}
	
	/**
	 * 
	 * Title: subjectInfo
	 * Description: 查看subject 详细信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/subjectInfo")
	public String subjectInfo(@RequestParam(value = "id") Integer id, Map<Object, Object> map, HttpSession session) {
		SubjectDTO subjectDTO = subjectService.getOneById(id);
		List<Integer> typeIds = subjectService.getTypeIdsById(id);
		List<QuestionType> types = new ArrayList<QuestionType>();
		
		types = questionTypeService.listAll();
		
		map.put("subjectDTO", subjectDTO);
		map.put("typeIds", typeIds);
		map.put("types", types);
		map.put("statuses", statuses);
		return "manager/subjectsMana/subject_info";
	}
	
	/**
	 * 
	 * Title: subjectEdit
	 * Description: 编辑subject 信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/subjectEdit")
	public String subjectEdit(@RequestParam(value = "id") Integer id, Map<Object, Object> map, HttpSession session) {
		SubjectDTO subjectDTO = subjectService.getOneById(id);
		
		List<Integer> typeIds = subjectService.getTypeIdsById(id);
		List<User> users = userService.listAll();
		
		List<QuestionType> types = new ArrayList<QuestionType>();
		
		types = questionTypeService.listAll();
		
		map.put("subjectDTO", subjectDTO);
		map.put("statuses", statuses);
		map.put("typeIds", typeIds);
		map.put("types", types);
		map.put("users", users);
		return "manager/subjectsMana/subject_edit";
	}
	
	/**
	 * 
	 * Title: subjectUpdate
	 * Description: 更新提交的subject 数据
	 * @param subject
	 * @return
	 * String
	 */
	@RequestMapping(value = "/subjectUpdate")
	public String subjectUpdate(Subject subject) {
		Date date = new Date(new java.util.Date().getTime());
		subject.setUpdateTime(date);
		subjectService.save(subject);
		return "redirect:/subjectMana";
	}
	
	/**
	 * 
	 * Title: toSubjectInsert
	 * Description: 去到新增subject 页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toSubjectInsert")
	public String toSubjectInsert(Map<Object, Object> map, HttpSession session) {
		List<QuestionType> types = new ArrayList<QuestionType>();
		
		types = questionTypeService.listAll();
		
		List<User> users = userService.listAll();
		map.put("statuses", statuses);
		map.put("types", types);
		map.put("users", users);
		return "manager/subjectsMana/subject_insert";
	}
	
	/**
	 * 
	 * Title: saveSubject
	 * Description: 保存新增学科信息
	 * @param subject
	 * @return
	 * String
	 */
	@RequestMapping(value = "/subjectSave")
	public String saveSubject(Subject subject) {
		Date date = new Date(new java.util.Date().getTime());
		subject.setCreateTime(date);
		subject.setUpdateTime(date);
		System.out.println(subject);
		subjectService.save(subject);
		return "redirect:/subjectMana";
	}
	
	/**
	 * 
	 * Title: deleteSubject
	 * Description: 删除学科，需要获取ID
	 * @param id
	 * @return
	 * boolean
	 */
	@RequestMapping(value = "/subjectDelete")
	@ResponseBody
	public boolean deleteSubject(@RequestParam(value = "id") Integer id) {
		return subjectService.delete(id);
	}
}
