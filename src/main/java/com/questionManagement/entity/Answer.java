package com.questionManagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The persistent class for the tb_answer database table.
 * 
 */
/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: Answer 
 * Description:Answer表对应实体类，其他实体类同该类，均由JPA自动生成
 */
@Entity
@Table(name="tb_answer")
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	private String content;

	@Column(name="idx_answerer_id")
	private Integer answererId;

	@Column(name="idx_paper_id")
	private Integer paperId;

	@Column(name="idx_question_id")
	private Integer questionId;

	private Integer score;
	
	private String options;

	private String status;
	
	private String stage;
	
	@Transient
	private List<String> answers = new ArrayList<>();
	
	@Transient
	private List<String> contents = new ArrayList<>();
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="start_time")
	private Date startTime;

	private String text;

	public Answer() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAnswererId() {
		return this.answererId;
	}

	public void setAnswererId(Integer answererId) {
		this.answererId = answererId;
	}

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = Arrays.asList(answers.replaceAll(" ", "").split(","));
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", content=" + content + ", answererId=" + answererId + ", paperId=" + paperId
				+ ", questionId=" + questionId + ", score=" + score + ", options=" + options + ", status=" + status
				+ ", stage=" + stage + ", startTime=" + startTime + ", text=" + text + "]";
	}

}