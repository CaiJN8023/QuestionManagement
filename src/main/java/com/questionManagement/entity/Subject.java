package com.questionManagement.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_subject database table.
 * 
 */
@Entity
@Table(name="tb_subject")
@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s")
public class Subject implements Serializable {
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
	
	@Column(name="idx_type_ids")
	private String typeIds;
	
	@Column(name="max_chapter")
	private Integer maxChapter;

	private String status;

	private String text;

	@Column(name="idx_name")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;

	public Subject() {
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

	public String getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}

	public Integer getMaxChapter() {
		return maxChapter;
	}

	public void setMaxChapter(Integer maxChapter) {
		this.maxChapter = maxChapter;
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
		return "Subject [id=" + id + ", createTime=" + createTime + ", creatorId=" + creatorId + ", typeIds=" + typeIds
				+ ", maxChapter=" + maxChapter + ", status=" + status + ", text=" + text + ", name=" + name
				+ ", updateTime=" + updateTime + "]";
	}
}