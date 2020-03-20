package com.questionManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.repository.IRoleAuthorityRepository;
import com.questionManagement.service.IRoleAuthorityService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: RoleAuthorityServiceImpl 
 * Description: RoleAuthority 实体对应业务接口实现类
 */

@Transactional
@Service
public class RoleAuthorityServiceImpl implements IRoleAuthorityService {
	
	@Autowired
	private IRoleAuthorityRepository roleAuthorityRepository;

	@Override
	public List<RoleAuthority> listByRoleId(Integer roleId) {
		return roleAuthorityRepository.findByRoleId(roleId);
	}

	@Override
	public void saveAll(List<RoleAuthority> roleAuthoritys) {
		roleAuthorityRepository.saveAll(roleAuthoritys);
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		roleAuthorityRepository.deleteByRoleId(roleId);
	}


}
