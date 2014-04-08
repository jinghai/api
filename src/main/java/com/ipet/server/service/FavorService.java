package com.ipet.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.Favor;
import com.ipet.server.domain.entity.Photo;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.FavorDao;
import com.ipet.server.repository.PhotoDao;
import com.ipet.server.repository.UserDao;

/**
 * 赞服务.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class FavorService extends BaseService {

	@Resource
	private UserDao userDao;
	@Resource
	private FavorDao favorDao;
	@Resource
	private PhotoDao photoDao;

	public List<Favor> listFavors(String photoId) {
		return getFavorDao().findByPhotoId(photoId);
	}

	@Transactional(readOnly = false)
	public Photo favor(String photoId, String userId, String text) {
		User user = getUserDao().findByIdAndUserState(userId, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}

		Favor favor = new Favor();
		favor.setText(text);
		favor.setPhotoId(photoId);
		favor.setUserName(user.getDisplayName());
		favor.setUserId(userId);
		getFavorDao().save(favor);

		user.setFavorCount(user.getFavorCount() + 1);
		getUserDao().save(user);

		Photo photo = getPhotoDao().findOne(photoId);
		photo.setFavorCount(photo.getFavorCount() + 1);
		photo = getPhotoDao().save(photo);

		photo.setFavored(true);
		return photo;
	}

	@Transactional(readOnly = false)
	public Photo unfavor(String photoId, String userId) {
		User user = getUserDao().findByIdAndUserState(userId, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}

		Favor favor = getFavorDao().findByPhotoIdAndUserId(photoId, userId);
		getFavorDao().delete(favor);

		user.setFavorCount(user.getFavorCount() - 1);
		getUserDao().save(user);

		Photo photo = getPhotoDao().findOne(photoId);
		photo.setFavorCount(photo.getFavorCount() - 1);
		photo = getPhotoDao().save(photo);

		photo.setFavored(false);
		return photo;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public FavorDao getFavorDao() {
		return favorDao;
	}

	public void setFavorDao(FavorDao favorDao) {
		this.favorDao = favorDao;
	}

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

}
