package com.ipet.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.FollowRelation;

public interface FollowRelationDao extends JpaRepository<FollowRelation, String> {

	/**
	 * 查询A与B的关注关系
	 */
	public FollowRelation findByUserIdAAndUserIdB(String userIdA, String userIdB);

	public List<FollowRelation> findByUserIdA(String userId);

	public List<FollowRelation> findByUserIdB(String userId);

	/**
	 * A是否关注了B
	 */
	// @Query("select fr.id from FollowRelation fr where fr.userIdA=?1 and fr.userIdB=?2")
	// public String isFollowd(long userIdA, long userIdB);

	/**
	 * A是否是B的粉丝
	 */
	// @Query("select fr.id from FollowRelation fr where fr.userIdB=?1 and fr.userIdA=?2")
	// public String isFollower(long userIdA, long userIdB);

}
