package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ipet.server.domain.IdEntity;

/**
 * 用户关注关系表
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "user_friend", uniqueConstraints = { @UniqueConstraint(columnNames = { "userIdA", "userIdB" }) })
public class FriendRelation extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -6059220926549987276L;

	private String userIdA;
	private String userIdB;

	public String getUserIdA() {
		return userIdA;
	}

	public void setUserIdA(String userIdA) {
		this.userIdA = userIdA;
	}

	public String getUserIdB() {
		return userIdB;
	}

	public void setUserIdB(String userIdB) {
		this.userIdB = userIdB;
	}

}
