package com.ipet.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.Photo;

public interface PhotoDao extends JpaRepository<Photo, String> {

	public Photo findById(String id);

	// 查找发布日期之后的图片
	public Page<Photo> findByCreateAtBefore(Date date, Pageable pageRequest);

	// 查找某用户某时间点之后的图片
	public Page<Photo> findByCreateAtBeforeAndUserId(Date date, String userId, Pageable pageRequest);

	// 查找某发布时间点之后并且限定发布用户范围
	public Page<Photo> findByCreateAtBeforeAndUserIdIn(Date date, List<String> ids, Pageable pageRequest);

}
