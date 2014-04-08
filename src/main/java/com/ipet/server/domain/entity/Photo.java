package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.ipet.server.domain.IdEntity;
import com.ipet.server.domain.Location;

/**
 * 图片
 * 
 * @author xiaojinghai
 */
@Entity
@Table(name = "photo", indexes = { @Index(name = "ipet_photos_createAt", columnList = "createAt"),
		@Index(name = "ipet_photos_updateAt", columnList = "updateAt"),
		@Index(name = "ipet_photos_forwordFromId", columnList = "forwordFromId"),
		@Index(name = "ipet_photos_userId", columnList = "userId") })
public class Photo extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -4218596589941666668L;

	private String originalURL;

	private String smallURL;

	private String text;

	// 位置
	@JsonUnwrapped
	private Location location;

	private String forwordFromId;

	@Column(columnDefinition = "int default 0")
	private Integer commentCount = 0;

	@Column(columnDefinition = "int default 0")
	private Integer favorCount = 0;

	private String userId;

	// @Transient
	private String userName;

	// @Transient
	private String avatar48;

	// 我是否赞过
	@Transient
	private boolean favored;

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getSmallURL() {
		return smallURL;
	}

	public void setSmallURL(String smallURL) {
		this.smallURL = smallURL;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getFavorCount() {
		return favorCount;
	}

	public void setFavorCount(Integer favorCount) {
		this.favorCount = favorCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getForwordFromId() {
		return forwordFromId;
	}

	public void setForwordFromId(String forwordFromId) {
		this.forwordFromId = forwordFromId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar48() {
		return avatar48;
	}

	public void setAvatar48(String avatar48) {
		this.avatar48 = avatar48;
	}

	public boolean isFavored() {
		return favored;
	}

	public void setFavored(boolean favored) {
		this.favored = favored;
	}

}
