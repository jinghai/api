package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.ipet.server.domain.IdEntity;

/**
 * 用户档案
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "user_profile", indexes = { @Index(name = "ipet_user_profiles_userId", columnList = "userId") })
public class UserProfile extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2654726171487650697L;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
