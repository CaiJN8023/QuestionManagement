package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.entity.Authority;
import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.repository.IAuthorityRepository;
import com.questionManagement.repository.IRoleAuthorityRepository;
import com.questionManagement.service.IAuthorityService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: AuthorityServiceImpl 
 * Description: Authority实体业务接口实现类
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {

	@Autowired
	private IAuthorityRepository authorityRepository;
	
	@Autowired
	private IRoleAuthorityRepository roleAuthorityRepository;
	
	@Override
	public List<Authority> listFatherNode() {
		List<Authority> fatherNodes = authorityRepository.findByParentId(-1);
		return fatherNodes;
	}

	@Override
	public List<Authority> listChilNode() {
		return null;
	}

	@Override
	public List<Authority> listByParentId(Integer parentId) {
		return authorityRepository.findByParentId(parentId);
	}

	@Override
	public List<Authority> listAll() {
		return authorityRepository.findAll();
	}

	@Override
	public List<Authority> ListByRoleId(Integer roleId) {
		List<RoleAuthority> roleAuthoritys = roleAuthorityRepository.findByRoleId(roleId);
		List<Authority> auths = new ArrayList<Authority>();
		for(RoleAuthority roleAuthority : roleAuthoritys) {
			auths.add(authorityRepository.getOne(roleAuthority.getAuthorityId()));
		}
		return auths;
	}

}
