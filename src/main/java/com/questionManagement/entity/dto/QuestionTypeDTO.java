package com.questionManagement.entity.dto;

import com.questionManagement.entity.QuestionType;

public class QuestionTypeDTO {

	private QuestionType questionType;
	
	private String creatorName;
	
	public QuestionTypeDTO() {
		
	}

	public QuestionTypeDTO(QuestionType questionType, String creatorName) {
		super();
		this.questionType = questionType;
		this.creatorName = creatorName;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Override
	public String toString() {
		return "QuestionTypeDTO [questionType=" + questionType + ", creatorName=" + creatorName + "]";
	}

}
