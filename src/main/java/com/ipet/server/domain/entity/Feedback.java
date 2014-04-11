package com.ipet.server.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 意见反馈
 * 
 * @author luocanfeng
 */
@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 7647784943930313535L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 主键

	// private String type; // 意见类型

	@Column(name = "title", length = 50)
	private String title; // 主题

	@Column(name = "content", columnDefinition = "text")
	private String content; // 反馈内容

	@Column(name = "contact", length = 200)
	private String contact; // 联系方式，非必填，用户未登录的情况下反馈意见时可留下联系方式

	@Column(name = "created_by", length = 40)
	private String createdBy; // 意见反馈人，用户登录后反馈意见时才有此记录

	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	private Date createdAt; // 反馈时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
