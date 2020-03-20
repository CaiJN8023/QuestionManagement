package com.questionManagement.entity.dto;

import com.questionManagement.entity.User;

public class UserDTO {

	private User user;
	
	private String roleName;
	
	public UserDTO() {
		
	}

	public UserDTO(User user, String roleName) {
		super();
		this.user = user;
		this.roleName = roleName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserDTO [user=" + user + ", roleName=" + roleName + "]";
	}
	
}
