package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.ipet.server.domain.IdEntity;

/**
 * 图片赞
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "favor", indexes = { @Index(name = "ipet_favors_userId", columnList = "userId"),
		@Index(name = "ipet_favors_photoId", columnList = "photoId") })
public class Favor extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -372177302425835413L;

	// 赞的话
	private String text;
	// 谁赞的
	private String userId;
	// 谁赞的
	private String userName;
	// 赞什么
	private String photoId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
