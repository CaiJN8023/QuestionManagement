package com.questionManagement.contorller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.service.IRoleAuthorityService;

/**
 * 
 * @author CJN
 * @date 2019年4月26日
 * Title: RoleAuthorityController 
 * Description: RoleAuthority 实体对应控制器类
 */
@Controller
public class RoleAuthorityController {

	@Autowired
	private IRoleAuthorityService roleAuthorityService;
	
	/**
	 * 
	 * Title: roleAssignAuth
	 * Description: 提交分配的权限
	 * @param roleId
	 * @param req
	 * @return
	 * String
	 */
	@RequestMapping(value = "/roleAssignAuth")
	public String roleAssignAuth(@RequestParam(value = "roleId") Integer roleId, HttpServletRequest req) {
		List<RoleAuthority> roleAuthoritys = new ArrayList<>();
		String[] authIdArr = req.getParameterValues("authId");
		for(String authIdStr : authIdArr) {
			RoleAuthority roleAuthority = new RoleAuthority();
			Integer authId = Integer.parseInt(authIdStr);
			roleAuthority.setRoleId(roleId); 
			roleAuthority.setAuthorityId(authId);
			roleAuthoritys.add(roleAuthority);
		}
		roleAuthorityService.deleteByRoleId(roleId);
		roleAuthorityService.saveAll(roleAuthoritys);
		return "redirect:/userMana/rolesInfo";
	}
}
