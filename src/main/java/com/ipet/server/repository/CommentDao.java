package com.ipet.server.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.Comment;

public interface CommentDao extends JpaRepository<Comment, String> {

	public Page<Comment> findByPhotoId(String photoId, Pageable pageRequest);

	public List<Comment> findByPhotoIdInOrderByCreateAtAsc(Collection<String> photoIds);

}
