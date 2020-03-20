package com.questionManagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the tb_question database table.
 * 
 */
@Entity
@Table(name="tb_question")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	private String answer;

	private String content;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="idx_creator_id")
	private Integer creatorId;

	@Column(name="idx_type_id")
	private Integer typeId;

	@Column(name="idx_subject_id")
	private Integer subjectId;
	
	private String image;
	
	public Integer difficulty;
	
	public Integer chapter;

	@Column(name="option_a")
	private String optionA;

	@Column(name="option_b")
	private String optionB;

	@Column(name="option_c")
	private String optionC;

	@Column(name="option_d")
	private String optionD;

	private Integer score;

	private String status;

	private String text;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;
	
	@Transient
	private List<String> options = new ArrayList<>();
	
	@Transient
	private List<String> answers = new ArrayList<>();

	public Question() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public String getOptionA() {
		return this.optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return this.optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return this.optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return this.optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setOptions() {
//		System.out.println("SetOptions");
		options.clear();
		// 只限制选择题
		if(getOptionA() != null && getOptionB() != null && getOptionC()
				!= null && getOptionD() != null && answer.length() == 1) {
			options.add(getOptionA());
			options.add(getOptionB());
			options.add(getOptionC());
			options.add(getOptionD());
		}
		Collections.shuffle(options);
	}
	
	public List<String> getOptions(){
		return options;
	}
	
	public void setAnswers() {
//		System.out.println("SetAnswers");
		answers.clear();
		if(options.size() != 0) {
			for(String option : options) {
				if(option.equals(getOptionA())) {
					answers.add("A");
				}else if(option.equals(getOptionB())) {
					answers.add("B");
				}else if(option.equals(getOptionC())) {
					answers.add("C");
				}else if(option.equals(getOptionD())){
					answers.add("D");
				}else {
					
				}
			}
		}
	}
	
	public List<String> getAnswers(){
//		System.out.println(answers.toString());
		return answers;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", creatorId=" + creatorId + ", typeId=" + typeId 
				+ ", difficulty=" + difficulty + ", chapter=" + chapter + ", score=" + score + "]";
	}

}