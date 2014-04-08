package com.ipet.server.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipet.server.domain.IdEntity;
import com.ipet.server.domain.UserState;

@Entity
// @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "users", indexes = { @Index(name = "ipet_users_createAt", columnList = "createAt"),
		@Index(name = "ipet_users_updateAt", columnList = "updateAt"),
		@Index(name = "ipet_users_userProfileId", columnList = "userProfileId"),
		@Index(name = "ipet_users_userSettingId", columnList = "userSettingId"),
		@Index(name = "ipet_users_lastLocationId", columnList = "lastLocationId") })
public class User extends IdEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8976503015944322713L;

	// @JsonUnwrapped
	// private Avatar avatar;
	// 登录名称
	// JSR303 BeanValidator的校验规则
	@Column(unique = true, length = 50)
	private String loginName;

	@Column(length = 50)
	private String displayName;

	// 登录密码
	@JsonIgnore
	@Column(length = 50)
	private String password;

	@Column(unique = true, length = 50)
	private String email;

	@Column(unique = true, length = 15)
	private String phone;

	// 盐,加密算法时使用
	@JsonIgnore
	@Column(length = 50)
	private String salt;

	@Column()
	@JsonIgnore
	private String roles;

	@JsonIgnore
	@Column()
	@Enumerated(EnumType.ORDINAL)
	private UserState userState;

	// 登录次数
	@Column(columnDefinition = "bigint default 0")
	private Long loginNum = 0l;

	@Column
	private String avatar32;

	@Column
	private String avatar48;

	// 明文密码,瞬时属性,加密时使用,不持久化
	@JsonIgnore
	@Transient
	private String plainPassword;

	@Column
	private Long userProfileId;

	@Column
	private Long userSettingId;

	@Column
	private Long lastLocationId;

	// 商铺数量
	@Column(columnDefinition = "int default 0")
	private Integer shopCount = 0;

	// 应用数量
	@Column(columnDefinition = "int default 0")
	private Integer appCount = 0;

	// 关注数量
	@Column(columnDefinition = "int default 0")
	private Integer followCount = 0;

	// 粉丝数量
	@Column(columnDefinition = "int default 0")
	private Integer followerCount = 0;

	// 朋友数量
	@Column(columnDefinition = "int default 0")
	private Integer friendCount = 0;

	@Column(columnDefinition = "int default 0")
	private Integer photoCount = 0;

	@Column(columnDefinition = "int default 0")
	private Integer favorCount = 0;

	@Column(columnDefinition = "int default 0")
	private Integer commentCount = 0;

	public User() {
	}

	public User(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public Long getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Long loginNum) {
		this.loginNum = loginNum;
	}

	public String getAvatar32() {
		return avatar32;
	}

	public void setAvatar32(String avatar32) {
		this.avatar32 = avatar32;
	}

	public String getAvatar48() {
		return avatar48;
	}

	public void setAvatar48(String avatar48) {
		this.avatar48 = avatar48;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getShopCount() {
		return shopCount;
	}

	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}

	public Integer getAppCount() {
		return appCount;
	}

	public void setAppCount(Integer appCount) {
		this.appCount = appCount;
	}

	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	public Integer getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}

	public Integer getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}

	public Integer getFavorCount() {
		return favorCount;
	}

	public void setFavorCount(Integer favorCount) {
		this.favorCount = favorCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Long getUserSettingId() {
		return userSettingId;
	}

	public void setUserSettingId(Long userSettingId) {
		this.userSettingId = userSettingId;
	}

	public Long getLastLocationId() {
		return lastLocationId;
	}

	public void setLastLocationId(Long lastLocationId) {
		this.lastLocationId = lastLocationId;
	}

}
