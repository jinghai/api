package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ipet.server.domain.IdEntity;
import com.ipet.server.domain.Location;

/**
 * 店铺
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "shop", indexes = { @Index(name = "ipet_shops_userId", columnList = "userId") })
public class Shop extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1885870057214599215L;

	// 店铺名称
	private String name;

	// 位置
	@JsonUnwrapped
	private Location location;

	private String userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
