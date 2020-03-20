package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.constant.MyPage;
import com.questionManagement.entity.Role;
import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.UserDTO;
import com.questionManagement.repository.IRoleAuthorityRepository;
import com.questionManagement.repository.IRoleRepository;
import com.questionManagement.repository.ISubjectRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IAnswerService;
import com.questionManagement.service.ISubjectService;
import com.questionManagement.service.IUserService;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: UserServiceImpl 
 * Description: User实体类对应业务接口实现类
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository; 
	
	@Autowired
	private IAnswerService answerService;
	
	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Autowired
	private ISubjectService subjectService;
	
	@Autowired
	private IRoleAuthorityRepository roleAuthorityRepository; 
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public User getById(Integer id) {
		return userRepository.getOne(id);
	}
	
	@Override
	public User login(User user) {
		User u = userRepository.findByAccountAndPassword(user.getAccount(), user.getPassword());
		return u;
	}

	@Override
	public List<RoleAuthority> checkAuthority(User user) {
		Integer roleId = user.getRoleId();
		List<RoleAuthority> roleAuthorities = roleAuthorityRepository.findByRoleId(roleId);
		return roleAuthorities;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> listAll() {
		return userRepository.findAll();
	}

	@Override
	public String deleteById(Integer id) {
		List<Subject> subjects = subjectRepository.findAllByCreatorId(id);
		if(subjects.size() != 0) {
			return "error";
		}else {
			userRepository.deleteById(id);
			return "success";
		}
	}
	
	@Override
	public int countAll() {
		return (int) userRepository.count();
	}

	@Override
	public List<UserDTO> getDTOListByList(List<User> users) {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for(User user : users) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUser(user);
			userDTO.setRoleName(roleRepository.getOne(user.getRoleId()).getName());
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	public MyPage<UserDTO> getUserDTOPageByNameLike(String name, int pageNum, int pageLimit) {
		int start = (pageNum - 1) * pageLimit;
		int count = pageLimit;
		List<User> users = userRepository.findByNameLike(name, start, count);
		List<UserDTO> userDTOs = this.getDTOListByList(users);
		int totalElements = users.size();
		int totalPages = 0;
		if(totalElements % pageLimit == 0) {
			totalPages = totalElements / pageLimit;
		}else {
			totalPages = totalElements / pageLimit + 1;
		}
		return new MyPage<UserDTO>(userDTOs, totalElements, totalPages);
	}

	@Override
	public UserDTO getUserDTOById(Integer id) {
		User user = userRepository.getOne(id);
		Role role = roleRepository.getOne(user.getRoleId());
		return new UserDTO(user, role.getName());
	}

	@Override
	public boolean delete(Integer id) {
		long subjectCount = subjectService.countByCreatorId(id);
		long answerCount = answerService.countByAnswererId(id);
		if(subjectCount > 0 || answerCount > 0) {
			return false;
		}else {
			userRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public boolean isAccountExsit(String account) {
		long count = userRepository.countByAccount(account);
		
		return (count >= 1);
	}

	@Override
	public void saveList(List<User> users) {
		userRepository.saveAll(users);
	}

	
}
