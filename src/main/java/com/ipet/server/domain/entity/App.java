package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.ipet.server.domain.AppType;
import com.ipet.server.domain.IdEntity;

/**
 * 应用
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "app", indexes = { @Index(name = "ipet_apps_userId", columnList = "userId") })
public class App extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1594512026639172536L;

	// 应用名称
	private String name;

	@Enumerated(EnumType.ORDINAL)
	private AppType type;

	@Column(unique = true)
	private String appKey;

	private String appSecret;

	private String userId;

	private String userName;

	// 数值版本号,用于版本更新比较
	private Integer versionCode;

	// 字符串版本号，呈现给用户界面
	private String versionName;

	// app下载地址（完整路径）
	@Column(length = 500)
	private String appDownloadUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppType getType() {
		return type;
	}

	public void setType(AppType type) {
		this.type = type;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}

}
