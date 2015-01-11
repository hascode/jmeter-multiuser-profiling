package com.hascode.tutorial.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
public class UserGroup {

	@Column(name = "groupid")
	private String groupid;

	public UserGroup() {
	}

	public UserGroup(final String groupid) {
		this.groupid = groupid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(final String groupid) {
		this.groupid = groupid;
	}

	@Override
	public String toString() {
		return "UserGroup [groupid=" + groupid + "]";
	}
}
