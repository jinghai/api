package com.ipet.server.repository;

import com.ipet.server.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, String> {

    public Page<Comment> findByPhotoId(String photoId, Pageable pageRequest);

}
