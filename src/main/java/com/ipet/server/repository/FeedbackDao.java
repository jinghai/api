package com.ipet.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.Feedback;

/**
 * 意见反馈Dao
 * 
 * @author luocanfeng
 */
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {

}
