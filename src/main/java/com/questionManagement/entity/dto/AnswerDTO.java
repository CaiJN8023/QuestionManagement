package com.questionManagement.entity.dto;

import com.questionManagement.entity.Answer;

/**
 * 
 * @author CJN
 * @date 2019年3月28日
 * Title: AnswerDTO 
 * Description: Answer 实体相关的数据传输对象类，用于显示学生作答了的试卷信息
 */
public class AnswerDTO {

	private String answerName;
	
	private String paperName;
	
	private Answer answer;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(String answerName, String paperName, Answer answer) {
		super();
		this.answerName = answerName;
		this.paperName = paperName;
		this.answer = answer;
	}

	public String getAnswerName() {
		return answerName;
	}

	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "AnswerDTO [answerName=" + answerName + ", paperName=" + paperName + ", answer=" + answer + "]";
	}
	
}
