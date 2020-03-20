package com.questionManagement.service;

import java.util.List;

import com.questionManagement.entity.RoleAuthority;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IRoleAuthorityService 
 * Description: RoleAuthority实体对应业务接口
 */
public interface IRoleAuthorityService {

	/**
	 * 
	 * Title: listByRoleId
	 * Description: 通过角色Id查找权限
	 * @param roleId
	 * @return
	 * List<Authority>
	 */
	List<RoleAuthority> listByRoleId(Integer roleId);
	
	void saveAll(List<RoleAuthority> roleAuthoritys);
	
	void deleteByRoleId(Integer roleId);
	
}
