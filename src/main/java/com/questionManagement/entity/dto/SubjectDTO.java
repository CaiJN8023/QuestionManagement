package com.questionManagement.entity.dto;

import com.questionManagement.entity.Subject;

public class SubjectDTO {

	private Subject subject;
	
	private String username;
	
	private String typeNames;
	
	public SubjectDTO(Subject subject, String username, String typeNames) {
		super();
		this.subject = subject;
		this.username = username;
		this.typeNames = typeNames;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTypeNames() {
		return typeNames;
	}

	public void setTypeNames(String typeNames) {
		this.typeNames = typeNames;
	}

	@Override
	public String toString() {
		return "SubjectDTO [subject=" + subject + ", username=" + username + ", typeNames=" + typeNames + "]";
	}

}
