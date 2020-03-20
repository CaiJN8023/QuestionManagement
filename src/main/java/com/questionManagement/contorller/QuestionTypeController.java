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

import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.QuestionTypeDTO;
import com.questionManagement.service.IQuestionTypeService;
import com.questionManagement.service.IUserService;

/**
 * 
 * @author CJN
 * @date 2019年3月11日
 * Title: QuestionTypeController 
 * Description: QuestionType 实体类对应控制器
 */
@Controller
public class QuestionTypeController {

	@Autowired
	private IQuestionTypeService questionTypeService;
	
	@Autowired
	private IUserService userService;
	
	private static List<BaseStatusEnum> statuses = new ArrayList<BaseStatusEnum>();
	
	static {
		statuses.add(BaseStatusEnum.NORMAL);
		statuses.add(BaseStatusEnum.DESTORY);
	}
	
	/**
	 * 
	 * Title: questionTypeMana
	 * Description: 去到试题类型信息查询页面
	 * @param map
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/questionTypeMana")
	public String questionTypeMana(Map<Object, Object> map, HttpSession session) {
		List<QuestionType> types = new ArrayList<QuestionType>();
		
		types = questionTypeService.listAll();
		
		List<QuestionTypeDTO> typeDTOs = questionTypeService.getDTOListByList(types);
		map.put("typeDTOs", typeDTOs);
		return "manager/questionTypeMana/types_info";
	}
	
	/**
	 * 
	 * Title: typeInfo
	 * Description: 查看题型信息，需要获取ID
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "typeInfo")
	public String typeInfo(@RequestParam(value = "id")Integer id, Map<Object, Object> map) {
		QuestionTypeDTO typeDTO = questionTypeService.getDTOById(id);
		List<User> users = userService.listAll();
		map.put("typeDTO", typeDTO);
		map.put("users", users);
		map.put("statuses", statuses);
		return "manager/questionTypeMana/type_info";
	}
	
	/**
	 * 
	 * Title: typeEdit
	 * Description: 去到编辑题型页面，需要获取ID
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/typeEdit")
	public String typeEdit(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		QuestionTypeDTO typeDTO = questionTypeService.getDTOById(id);
		List<User> users = userService.listAll();
		map.put("typeDTO", typeDTO);
		map.put("users", users);
		map.put("statuses", statuses);
		
		return "manager/questionTypeMana/type_edit";
	}
	
	/**
	 * 
	 * Title: typeUpdate
	 * Description: 更新当前题型信息
	 * @param type
	 * @return
	 * String
	 */
	@RequestMapping(value = "typeUpdate")
	public String typeUpdate(QuestionType type) {
		Date date = new Date(new java.util.Date().getTime());
		type.setUpdateTime(date);
		questionTypeService.save(type);
		return "redirect:/questionTypeMana";
	}
	
	/**
	 * 
	 * Title: typeInsert
	 * Description: 去到添加题型页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/typeInsert")
	public String typeInsert(Map<Object, Object> map) {
		List<User> users = userService.listAll();
		map.put("users", users);
		map.put("statuses", statuses);
		return "manager/questionTypeMana/type_insert";
	}
	
	/**
	 * 
	 * Title: typeSave
	 * Description: 保存当前题型信息
	 * @param type
	 * @return
	 * String
	 */
	@RequestMapping(value = "/typeSave")
	public String typeSave(QuestionType type) {
		Date date = new Date(new java.util.Date().getTime());
		type.setCreateTime(date);
		type.setUpdateTime(date);
		questionTypeService.save(type);
		return "redirect:/questionTypeMana";
	}
	
	@RequestMapping(value = "/typeDelete")
	@ResponseBody
	public boolean typeDelete(@RequestParam Integer id) {
		
		return questionTypeService.deleteById(id);
	}
}
