package com.questionManagement.service;

import java.util.List;

import com.questionManagement.entity.Role;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IRoleService 
 * Description: Role实体对应业务接口
 */
public interface IRoleService {

	Role getById(Integer id);
	
	Integer getIdByName(String name) throws NullPointerException;
	
	List<Role> listAll();
	
}
