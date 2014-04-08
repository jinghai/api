package com.ipet.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ipet.server.app.AppConfig;
import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.Favor;
import com.ipet.server.domain.entity.FollowRelation;
import com.ipet.server.domain.entity.Photo;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.FavorDao;
import com.ipet.server.repository.FollowRelationDao;
import com.ipet.server.repository.PhotoDao;
import com.ipet.server.repository.UserDao;
import com.ipet.server.util.ProjectUtil;

/**
 * 图片发布.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class PhotoService extends BaseService {

	@Resource
	private UserDao userDao;

	@Resource
	private FollowRelationDao followRelationDao;

	@Resource
	private PhotoDao photoDao;

	@Resource
	private FavorDao favorDao;

	@Resource
	private AppConfig appConfig;

	private final int smallWith = 480;
	private final int smallHeigth = 480;

	private final int originalWith = 800;
	private final int originalHeigth = 600;

	// 允许上传的头像文件扩展名
	private static final List<String> allowPrefix = new ArrayList<String>();

	static {
		allowPrefix.add("jpg");
		allowPrefix.add("gif");
		allowPrefix.add("png");
	}

	/**
	 * 按时间和关注分页获取图片
	 */
	public List<Photo> getPhotosByTimeAndFollowForPage(String uid, Date date, Integer pageNumber, Integer pageSize) {

		PageRequest pageR = ProjectUtil.buildPageRequest(pageNumber, pageSize, Sort.Direction.DESC, "createAt");
		// 寻找关注关系，含自己
		List<FollowRelation> idsR = getFollowRelationDao().findByUserIdA(uid);
		List<String> followIds = new ArrayList<String>();
		followIds.add(uid);
		for (FollowRelation r : idsR) {
			followIds.add(r.getUserIdB());
		}
		// 取出所有图片
		Page<Photo> ret = getPhotoDao().findByCreateAtBeforeAndUserIdIn(date, followIds, pageR);

		// 填充图片发布者信息
		List<Photo> photos = ret.getContent();
		Map<String, Photo> photoIdMap = new HashMap<String, Photo>(photos.size());
		for (Photo photo : photos) {
			photoIdMap.put(photo.getId(), photo);
		}

		List<Favor> favors = this.getFavorDao().findByPhotoIdInAndUserId(photoIdMap.keySet(), uid);
		List<String> favoredPhotoIds = new ArrayList<String>(favors.size());
		for (Favor favor : favors) {
			favoredPhotoIds.add(favor.getPhotoId());
		}

		for (Map.Entry<String, Photo> photoItem : photoIdMap.entrySet()) {
			if (favoredPhotoIds.contains(photoItem.getKey())) {
				photoItem.getValue().setFavored(true);
			}
		}

		favors.clear();
		favoredPhotoIds.clear();
		photoIdMap.clear();

		return photos;
	}

	/**
	 * 根据图片Id获取图片
	 */
	public Photo findById(String photoId) {
		return getPhotoDao().findById(photoId);
	}

	/**
	 * 发布图片
	 */
	@Transactional(readOnly = false)
	public Photo publishPhoto(String uid, String context, MultipartFile file) throws IOException {
		User user = this.getUserDao().findByIdAndUserState(uid, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}
		checkFilePrefix(file);
		// 临时文件
		File tempFile = new File(appConfig.getTempDirRealPath() + generateTempFileName(file));
		String originalRelativeFile = getPhotoRelativePath(generatePhotoFileName(uid, file));
		String originalRealFile = getPhotoRealPath(originalRelativeFile);
		String smallRelativeFile = getPhotoRelativePath(generatePhotoFileName(uid, file));
		String smallRealFile = getPhotoRealPath(smallRelativeFile);
		File originalFile = new File(originalRealFile);
		File smallFile = new File(smallRealFile);
		// 文件冲突检查,避免shorUUID可能存在重复的bug
		if (originalFile.exists() || smallFile.exists()) {
			throw new RuntimeException("文件重复");
		}
		try {
			// 上传至临时文件
			file.transferTo(tempFile);
			/*
			 * 若图片横比with小，高比heigth小，不变 若图片横比with小，高比heigth大，高缩小到heigth，图片比例不变
			 * 若图片横比with大，高比heigth小，横缩小到with，图片比例不变
			 * 若图片横比with大，高比heigth大，图片按比例缩小，横为with或高为heigth
			 */
			Thumbnails.of(tempFile).size(originalWith, originalHeigth).toFile(originalRealFile);
			Thumbnails.of(tempFile).size(smallWith, smallHeigth).toFile(smallRealFile);

			Photo photo = new Photo();
			photo.setText(context);
			photo.setOriginalURL(originalRelativeFile);
			photo.setSmallURL(smallRelativeFile);
			photo.setUserId(uid);
			photo.setAvatar48(user.getAvatar48());
			photo.setUserName(user.getDisplayName());
			// 更新统计
			user.setPhotoCount(user.getPhotoCount() + 1);

			this.getPhotoDao().save(photo);
			this.getUserDao().save(user);

			return photo;
		} catch (Exception e) {
			if (originalFile != null && originalFile.exists()) {
				originalFile.delete();
			}
			if (smallFile != null && smallFile.exists()) {
				smallFile.delete();
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (tempFile != null && tempFile.exists()) {
				tempFile.delete();
			}
		}
	}

	/** 为了解决发布文字乱码问题而临时提供的一个接口 */
	@Deprecated
	@Transactional(readOnly = false)
	public Photo publishText(String uid, String id, String context) throws IOException {
		User user = this.getUserDao().findByIdAndUserState(uid, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}

		Photo photo = this.getPhotoDao().findById(id);
		photo.setText(context);
		return this.getPhotoDao().save(photo);
	}

	// 生成临时文件相对路径
	private String generateTempFileName(MultipartFile file) {
		String prefix = ProjectUtil.getPrefix(file.getOriginalFilename());
		return ProjectUtil.generateShortUUID() + "." + prefix;
	}

	// 检查文件扩展名合法性
	private void checkFilePrefix(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		String prefix = ProjectUtil.getPrefix(originalFilename);
		if (StringUtils.isEmpty(prefix) || !allowPrefix.contains(prefix)) {
			throw new RuntimeException("被禁止的文件类型");
		}
	}

	// 得到相对路径
	private String getPhotoRelativePath(String avatarFileName) {
		String path = appConfig.getUploadDir() + avatarFileName;
		return path;
	}

	// 生成相对路径
	private String generatePhotoFileName(String uid, MultipartFile file) {
		String prefix = ProjectUtil.getPrefix(file.getOriginalFilename());
		return uid + "/" + ProjectUtil.generateShortUUID() + "." + prefix;
	}

	// 得到绝对地址
	private String getPhotoRealPath(String relativePath) {
		String path = appConfig.getWebContextRealPath() + relativePath;
		ProjectUtil.checkDirAndCreateIfNotExists(path);
		return path;
	}

	public PhotoDao getPhotoDao() {
		return photoDao;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
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

	public FavorDao getFavorDao() {
		return favorDao;
	}

	public void setFavorDao(FavorDao favorDao) {
		this.favorDao = favorDao;
	}

}
