package com.questionManagement.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_user database table.
 * 
 */
@Entity
@Table(name="tb_user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	private Integer age;
	
	private String sex;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="idx_name")
	private String name;

	private String password;
	
	@Column(name="role_id")
	private Integer roleId;

	private String phone;

	private String status;

	private String text;

	@Column(name="uk_account")
	private String account;

	@Temporal(TemporalType.DATE)
	@Column(name="update_time")
	private Date updateTime;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return this.sex;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", sex=" + sex + ", createTime=" + createTime + ", name=" + name
				+ ", password=" + password + ", roleId=" + roleId + ", phone=" + phone + ", status=" + status
				+ ", text=" + text + ", account=" + account + ", updateTime=" + updateTime + "]";
	}


}