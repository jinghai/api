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
@Table(name = "user_setting", indexes = { @Index(name = "ipet_user_settings_userId", columnList = "userId") })
public class UserSetting extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2246972647734766782L;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
