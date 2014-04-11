package com.ipet.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.repository.FavorDao;
import com.ipet.server.repository.PhotoDao;

/**
 * 图片发布.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class DiscoverService extends BaseService {

	@Resource
	private PhotoDao photoDao;

	@Resource
	private FavorDao favorDao;

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

	public FavorDao getFavorDao() {
		return favorDao;
	}

	public void setFavorDao(FavorDao favorDao) {
		this.favorDao = favorDao;
	}

}
