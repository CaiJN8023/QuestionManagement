package com.questionManagement.entity.dto;

import java.io.Serializable;

import com.questionManagement.entity.Answer;
import com.questionManagement.entity.Question;

/**
 * 
 * @author CJN
 * @date 2019年3月28日
 * Title: AnswerDTO 
 * Description: 试题和答案数据传输对象类，包括当前试题对象以及作答该试题的答案对象、
 * 				用来回显学生作答的信息给老师批改
 */
public class AnswerQuestionDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Question question;
	
	private Answer answer;
	
	public AnswerQuestionDTO() {
		super();
	}
	
	public AnswerQuestionDTO(Question question, Answer answer) {
		this.question = question;
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "AnswerDTO [question=" + question + ", answer=" + answer + "]";
	}
	
}
