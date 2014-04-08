package com.ipet.server.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * 位置
 * 
 * @author xiaojinghai
 * 
 */
@Embeddable
public class Location implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 176908795583707170L;

	/** 精度 */
	private Long longitude;

	/** 纬度 */
	private Long latitude;

	/** GEO哈希 */
	private String geoHash;

	/** 文本地址 */
	private String address;

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
