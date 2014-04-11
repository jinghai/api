package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.ipet.server.domain.IdEntity;

/**
 * 图片评论
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "comment", indexes = { @Index(name = "ipet_comments_userId", columnList = "userId"),
		@Index(name = "ipet_comments_photoId", columnList = "photoId") })
public class Comment extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -3121944297979914043L;

	// 评论内容
	private String text;

	// 谁评论的
	private String userId;

	// 谁评论的
	private String userName;

	// 被评论的图片
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
