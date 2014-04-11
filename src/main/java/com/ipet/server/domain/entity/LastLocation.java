package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ipet.server.domain.IdEntity;
import com.ipet.server.domain.Location;

/**
 * 用户最后位置
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "user_last_location", indexes = { @Index(name = "ipet_last_locations_userId", columnList = "userId") })
public class LastLocation extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1540071327348511327L;

	// 位置
	@JsonUnwrapped
	private Location location;

	private String userId;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
