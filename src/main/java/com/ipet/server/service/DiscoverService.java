package com.ipet.server.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.entity.Photo;
import com.ipet.server.repository.PhotoDao;
import com.ipet.server.util.ProjectUtil;

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

	/**
	 * 分页获取时间点之后最新的图片（发现）
	 */
	public List<Photo> listNewerPhotosForPage(Date date, Integer pageNumber, Integer pageSize) {
		PageRequest pageR = ProjectUtil.buildPageRequest(pageNumber, pageSize, Sort.Direction.DESC, "createAt");
		Page<Photo> ret = getPhotoDao().findByCreateAtBefore(date, pageR);
		return ret.getContent();
	}

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

}
