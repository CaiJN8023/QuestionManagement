package com.questionManagement.contorller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.questionManagement.entity.Authority;
import com.questionManagement.entity.Role;
import com.questionManagement.service.IAuthorityService;
import com.questionManagement.service.IRoleService;

/**
 * 
 * @author CJN
 * @date 2019年4月26日
 * Title: AuthorityController 
 * Description: Authority 实体对应控制器类
 */
@Controller
public class AuthorityController {

	@Autowired
	private IAuthorityService authorityService;
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 
	 * Title: assignAuth
	 * Description: 去到分配权限页面
	 * @param roleId
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/assignAuth")
	public String assignAuth(@RequestParam(value = "roleId") Integer roleId, Map<Object, Object> map) {
		List<Authority> allAuths = authorityService.listAll();
		List<Authority> roleAuths = authorityService.ListByRoleId(roleId);
		Role role = roleService.getById(roleId);
		map.put("allAuths", allAuths);
		map.put("roleAuths", roleAuths);
		map.put("role", role);
		return "manager/authorityMana/role_assignAuth";
	}
	
}
