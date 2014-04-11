package com.ipet.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.FollowRelation;
import com.ipet.server.domain.entity.FriendRelation;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.FollowRelationDao;
import com.ipet.server.repository.FriendRelationDao;
import com.ipet.server.repository.UserDao;

/**
 * 人脉管理.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class ContactService extends BaseService {

	@Resource
	private UserDao userDao;

	@Resource
	private FollowRelationDao followRelationDao;

	@Resource
	private FriendRelationDao friendRelationDao;

	/**
	 * 获取关注用户列表
	 */
	public List<User> listGuys(String userId) {
		List<FollowRelation> frlist = getFollowRelationDao().findByUserIdA(userId);
		List<String> ids = new ArrayList<String>(frlist.size());
		if (frlist.size() > 0) {
			for (FollowRelation fr : frlist) {
				ids.add(fr.getUserIdB());
			}
			return getUserDao().findByIdInAndUserState(ids, UserState.ENABLE);
		} else {
			return new ArrayList<User>(0);
		}
	}

	/**
	 * 获取粉丝列表
	 */
	public List<User> listFans(String userId) {
		List<FollowRelation> frlist = getFollowRelationDao().findByUserIdB(userId);
		List<String> ids = new ArrayList<String>(frlist.size());
		if (frlist.size() > 0) {
			for (FollowRelation fr : frlist) {
				ids.add(fr.getUserIdA());
			}
			return getUserDao().findByIdInAndUserState(ids, UserState.ENABLE);
		} else {
			return new ArrayList<User>(0);
		}
	}

	/**
	 * 获取朋友列表
	 */
	public List<User> listFriends(String userId) {
		List<FriendRelation> frlist = getFriendRelationDao().findByUserIdA(userId);
		List<String> ids = new ArrayList<String>(frlist.size());
		if (frlist.size() > 0) {
			for (FriendRelation fr : frlist) {
				ids.add(fr.getUserIdB());
			}
			return getUserDao().findByIdInAndUserState(ids, UserState.ENABLE);
		} else {
			return new ArrayList<User>(0);
		}
	}

	/**
	 * A关注B
	 */
	@Transactional(readOnly = false)
	public void follow(String userIdA, String userIdB) {
		User userA = this.getUserDao().findByIdAndUserState(userIdA, UserState.ENABLE);
		User userB = this.getUserDao().findByIdAndUserState(userIdB, UserState.ENABLE);
		if (userA == null || userB == null) {
			throw new RuntimeException("无效用户");
		}
		int followCountA = userA.getFollowCount() == null ? 0 : userA.getFollowCount();
		int friendCountA = userA.getFriendCount() == null ? 0 : userA.getFriendCount();
		int followerCountB = userB.getFollowerCount() == null ? 0 : userB.getFollowerCount();
		int friendCountB = userB.getFriendCount() == null ? 0 : userB.getFriendCount();

		// A是否关注过B
		boolean aHasFollowdB = getFollowRelationDao().findByUserIdAAndUserIdB(userIdA, userIdB) != null;
		if (aHasFollowdB) {
			logger.warn("增加一个已存在的关注关系");
			return;
		}
		// 建立关注关系
		FollowRelation follow = new FollowRelation();
		follow.setUserIdA(userIdA);
		follow.setUserIdB(userIdB);
		getFollowRelationDao().save(follow);
		// 统计
		followCountA += 1;
		userA.setFollowCount(followCountA);
		followerCountB += 1;
		userB.setFollowerCount(followerCountB);

		// B是否关注过A
		boolean bHasFollowdA = getFollowRelationDao().findByUserIdAAndUserIdB(userIdB, userIdA) != null;
		// 如果B关注了A，则建立朋友关系
		if (bHasFollowdA) {
			FriendRelation friendRA = new FriendRelation();
			friendRA.setUserIdA(userIdA);
			friendRA.setUserIdB(userIdB);
			FriendRelation friendRB = new FriendRelation();
			friendRB.setUserIdA(userIdB);
			friendRB.setUserIdB(userIdA);
			getFriendRelationDao().save(friendRA);
			getFriendRelationDao().save(friendRB);
			// 统计
			friendCountA += 1;
			userA.setFriendCount(friendCountA);
			friendCountB += 1;
			userB.setFriendCount(friendCountB);
		}
		getUserDao().save(userA);
		getUserDao().save(userB);
	}

	/**
	 * 反关注
	 */
	@Transactional(readOnly = false)
	public void unfollow(String userIdA, String userIdB) {
		User userA = this.getUserDao().findByIdAndUserState(userIdA, UserState.ENABLE);
		User userB = this.getUserDao().findByIdAndUserState(userIdB, UserState.ENABLE);
		if (userA == null || userB == null) {
			throw new RuntimeException("无效用户");
		}
		int followCountA = userA.getFollowCount() == null ? 0 : userA.getFollowCount();
		int friendCountA = userA.getFriendCount() == null ? 0 : userA.getFriendCount();
		int followerCountB = userB.getFollowerCount() == null ? 0 : userB.getFollowerCount();
		int friendCountB = userB.getFriendCount() == null ? 0 : userB.getFriendCount();

		// A与B的关注关系
		FollowRelation followAB = getFollowRelationDao().findByUserIdAAndUserIdB(userIdA, userIdB);
		if (followAB == null) {
			logger.warn("删除一个不存在的关注关系");
			return;
		}
		// 解除关注关系
		getFollowRelationDao().delete(followAB);
		// 统计
		followCountA -= 1;
		userA.setFollowCount(followCountA);
		followerCountB -= 1;
		userB.setFollowerCount(followerCountB);

		FriendRelation friendRA = getFriendRelationDao().findByUserIdAAndUserIdB(userIdA, userIdB);
		FriendRelation friendRB = getFriendRelationDao().findByUserIdAAndUserIdB(userIdB, userIdA);

		// 解除朋友关系
		if (friendRA != null) {
			getFriendRelationDao().delete(friendRA);
			friendCountA -= 1;
			userA.setFriendCount(friendCountA);
		}
		if (friendRB != null) {
			getFriendRelationDao().delete(friendRB);
			friendCountB -= 1;
			userB.setFriendCount(friendCountB);
		}
		getUserDao().save(userA);
		getUserDao().save(userB);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public FollowRelationDao getFollowRelationDao() {
		return followRelationDao;
	}

	public void setFollowRelationDao(FollowRelationDao followRelationDao) {
		this.followRelationDao = followRelationDao;
	}

	public FriendRelationDao getFriendRelationDao() {
		return friendRelationDao;
	}

	public void setFriendRelationDao(FriendRelationDao friendRelationDao) {
		this.friendRelationDao = friendRelationDao;
	}

}
