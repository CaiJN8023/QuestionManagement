package com.questionManagement.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the tb_question_type database table.
 * 
 */
@Entity
@Table(name="tb_question_type")
@NamedQuery(name="QuestionType.findAll", query="SELECT q FROM QuestionType q")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class QuestionType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="idx_creator_id")
	private Integer creatorId;

	private String status;

	private String text;

	@Column(name="idx_name")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;

	public QuestionType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "QuestionType [id=" + id + ", createTime=" + createTime + ", creatorId=" + creatorId + ", status="
				+ status + ", text=" + text + ", name=" + name + ", updateTime=" + updateTime + "]";
	}

}