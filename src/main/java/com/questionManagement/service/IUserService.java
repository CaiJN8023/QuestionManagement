package com.questionManagement.service;

import java.util.List;

import com.questionManagement.constant.MyPage;
import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.UserDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: IUserService 
 * Description: User实体类对应业务接口类
 */
public interface IUserService {
	
	User getById(Integer id);

	User login(User user);
	
	List<RoleAuthority> checkAuthority(User user);
	
	User save(User user);
	
	List<User> listAll();
	
	String deleteById(Integer id);
	
	int countAll();
	
	List<UserDTO> getDTOListByList(List<User> users); 
	
	MyPage<UserDTO> getUserDTOPageByNameLike(String name, int pageNum, int pageLimit);
	
	UserDTO getUserDTOById(Integer id);
	
	boolean delete(Integer id);
	
	boolean isAccountExsit(String account);
	
	void saveList(List<User> users);
}
