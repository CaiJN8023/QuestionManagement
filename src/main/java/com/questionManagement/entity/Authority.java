package com.questionManagement.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_authority database table.
 * 
 */
@Entity
@Table(name="tb_authority")
@NamedQuery(name="Authority.findAll", query="SELECT a FROM Authority a")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	private String description;

	@Column(name="parent_id")
	private Integer parentId;

	private String status;

	private String text;

	@Column(name="idx_name")
	private String name;

	private String url;

	public Authority() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", description=" + description + ", parentId=" + parentId + ", status=" + status
				+ ", text=" + text + ", name=" + name + ", url=" + url + "]";
	}
	

}