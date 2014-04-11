package com.ipet.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.Comment;
import com.ipet.server.domain.entity.Photo;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.CommentDao;
import com.ipet.server.repository.PhotoDao;
import com.ipet.server.repository.UserDao;
import com.ipet.server.util.ProjectUtil;

/**
 * 赞服务.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends BaseService {

	@Resource
	private UserDao userDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private PhotoDao photoDao;

	public List<Comment> getCommentList(String photoId, Integer pageNumber, Integer pageSize) {
		PageRequest pageR = ProjectUtil.buildPageRequest(pageNumber, pageSize, Sort.Direction.ASC, "createAt");
		Page<Comment> ret = getCommentDao().findByPhotoId(photoId, pageR);
		return ret.getContent();
	}

	@Transactional(readOnly = false)
	public Comment comment(String photoId, String userId, String text) {
		User user = getUserDao().findByIdAndUserState(userId, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}

		Comment comment = new Comment();
		comment.setText(text);
		comment.setPhotoId(photoId);
		comment.setUserName(user.getDisplayName());
		comment.setUserId(userId);
		getCommentDao().save(comment);

		Photo photo = getPhotoDao().findOne(photoId);
		photo.setCommentCount(photo.getCommentCount() + 1);
		getPhotoDao().save(photo);

		user.setCommentCount(user.getCommentCount() + 1);
		getUserDao().save(user);
		return comment;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

}
