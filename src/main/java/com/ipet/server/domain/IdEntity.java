package com.ipet.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity {

	@Id
	@GeneratedValue(generator = "myGenerator")
	@GenericGenerator(name = "myGenerator", strategy = "uuid2")
	// @JsonSerialize(as = String.class)
	// @JsonDeserialize(as = Long.class)
	@Column(length = 40)
	protected String id;

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date createAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date updateAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@PrePersist
	public void onCreate() {
		createAt = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		updateAt = new Date();
	}

}
