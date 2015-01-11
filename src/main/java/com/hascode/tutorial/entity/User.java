package com.hascode.tutorial.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "users")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class User {
	@Id
	@Column(name = "userid")
	private String userid;

	@Column(name = "password")
	private String password;

	@ElementCollection
	@CollectionTable(name = "users_groups", joinColumns = @JoinColumn(name = "userid"))
	private List<UserGroup> groups = new ArrayList<UserGroup>();

	public User() {
	}

	public User(final String userId) {
		userid = userId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(final List<UserGroup> groups) {
		this.groups = groups;
	}

}
