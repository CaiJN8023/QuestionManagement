package com.questionManagement.contorller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.questionManagement.entity.Role;
import com.questionManagement.service.IRoleService;

/**
 * 
 * @author CJN
 * @date 2019年4月26日
 * Title: RoleController 
 * Description: Role 实体对应控制器类
 */
@Controller
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	/**
	 * 
	 * Title: rolesInfo
	 * Description: 显示系统所有角色
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userMana/rolesInfo")
	public String rolesInfo(Map<Object, Object> map) {
		List<Role> roles = roleService.listAll();
		map.put("roles", roles);
		return "manager/authorityMana/roles_info";
	}
	
}
