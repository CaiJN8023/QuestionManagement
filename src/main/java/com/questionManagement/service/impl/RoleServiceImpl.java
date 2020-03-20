package com.questionManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.entity.Role;
import com.questionManagement.repository.IRoleRepository;
import com.questionManagement.service.IRoleService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: RoleServiceImpl 
 * Description: Role实体对应业务接口实现类
 */
@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public Role getById(Integer id) {
		return roleRepository.getOne(id);
	}

	@Override
	public Integer getIdByName(String name) throws NullPointerException{
		return roleRepository.findByNameLike(name).getId();
	}

	@Override
	public List<Role> listAll() {
		
		return roleRepository.findAll();
	}

}
