package com.questionManagement.entity.dto;

import com.questionManagement.entity.Question;

public class QuestionDTO {

	private Question question;
	
	private String creatorName;
	
	private String subjectName;
	
	private String typeName;
	
	public QuestionDTO() {
		
	}
	
	public QuestionDTO(Question question, String creatorName,
			String subjectName, String typeName) {
		super();
		this.question = question;
		this.creatorName = creatorName;
		this.subjectName = subjectName;
		this.typeName = typeName;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "QuestionDTO [question=" + question + ", creatorName=" + creatorName + ", subjectName=" + subjectName
				+ ", typeName=" + typeName + "]";
	}
	
}
