package com.questionManagement.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_role_authority database table.
 * 
 */
@Entity
@Table(name="tb_role_authority")
@NamedQuery(name="RoleAuthority.findAll", query="SELECT r FROM RoleAuthority r")
public class RoleAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_id")
	private Integer id;

	@Column(name="authority_id")
	private Integer authorityId;

	@Column(name="role_id")
	private Integer roleId;

	public RoleAuthority() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "RoleAuthority [id=" + id + ", authorityId=" + authorityId + ", roleId=" + roleId + "]";
	}
	

}