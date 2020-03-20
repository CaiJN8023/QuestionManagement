package com.questionManagement.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The persistent class for the tb_paper database table.
 * 
 */
@Entity
@Table(name="tb_paper")
@NamedQuery(name="Paper.findAll", query="SELECT p FROM Paper p")
public class Paper implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	@Column(name="chapter_end")
	private Integer chapterEnd;

	@Column(name="chapter_start")
	private Integer chapterStart;

	private String content;
	
	@Column(name="idx_subject_id")
	private Integer subjectId;
	
	@Column(name="exam_time")
	private Integer examTime;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="total_score")
	private Integer totalScore;

	@Column(name="difficulty")
	private Integer difficulty;

	@Column(name="idx_creator_id")
	private Integer creatorId;

	@Column(name="idx_name")
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="start_time")
	private Date startTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="invite_code")
	private String inviteCode;

	private String status;

	private String text;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;

	public Paper() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChapterEnd() {
		return this.chapterEnd;
	}

	public void setChapterEnd(Integer chapterEnd) {
		this.chapterEnd = chapterEnd;
	}

	public Integer getChapterStart() {
		return this.chapterStart;
	}

	public void setChapterStart(Integer chapterStart) {
		this.chapterStart = chapterStart;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getExamTime() {
		return examTime;
	}

	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
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

	@Override
	public String toString() {
		return "Paper [id=" + id + ", chapterEnd=" + chapterEnd + ", chapterStart=" + chapterStart + ", content="
				+ content + ", subjectId=" + subjectId + ", examTime=" + examTime + ", createTime=" + createTime
				+ ", totalScore=" + totalScore + ", difficulty=" + difficulty + ", creatorId=" + creatorId + ", name="
				+ name + ", startTime=" + startTime + ", endTime=" + endTime + ", inviteCode=" + inviteCode
				+ ", status=" + status + ", text=" + text + ", updateTime=" + updateTime + "]";
	}

}