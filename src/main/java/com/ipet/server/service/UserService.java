package com.ipet.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ipet.server.app.AppConfig;
import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.UserDao;
import com.ipet.server.util.ProjectUtil;

/**
 * 用户管理类.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService {

	@Resource
	private UserDao userDao;

	@Resource
	private AppConfig appConfig;

	// 允许上传的头像文件扩展名
	private static final List<String> allowPrefix = new ArrayList<String>();

	static {
		allowPrefix.add("jpg");
		allowPrefix.add("gif");
		allowPrefix.add("png");
	}

	/**
	 * 获取所有用户，仅限测试使用
	 */
	public List<User> getAllUser() {
		return (List<User>) getUserDao().findAll();
	}

	/**
	 * 按ID获取用户信息
	 */
	public User getUserById(String userId) {
		User user = getUserDao().findByIdAndUserState(userId, UserState.ENABLE);
		return user;
	}

	/**
	 * 批量获取用户
	 */
	public List<User> getUserByIds(List<String> ids) {
		List<User> users = getUserDao().findByIdInAndUserState(ids, UserState.ENABLE);
		return users;
	}

	/**
	 * 更新头像
	 */
	@Transactional(readOnly = false)
	public User updateAvatar(String userId, MultipartFile file) throws IOException {
		User user = this.getUserDao().findByIdAndUserState(userId, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("无效用户");
		}
		// 旧头像
		String old32 = user.getAvatar32();
		String old48 = user.getAvatar48();
		checkFilePrefix(file);
		// 原始文件
		File originalFile = new File(appConfig.getTempDirRealPath() + generateTempFileName(file));
		// 32*32相对地址
		String avatar32RelativeFile = getAvatarrelativePath(generateAvatarFileName(userId, file));
		// 32*32绝对地址
		String avatar32RealFile = getAvatarRealPath(avatar32RelativeFile);
		// 48*48相对地址
		String avatar48RelativeFile = getAvatarrelativePath(generateAvatarFileName(userId, file));
		// 48*48绝对地址
		String avatar48RealFile = getAvatarRealPath(avatar48RelativeFile);

		File avatar32File = new File(avatar32RealFile);
		File avatar48File = new File(avatar48RealFile);
		try {
			// 上传原始文件
			file.transferTo(originalFile);
			// 文件冲突检查,避免shorUUID可能存在重复的bug
			if (avatar32File.exists() || avatar48File.exists()) {
				throw new RuntimeException("文件重复");
			}

			/*
			 * 若图片横比32小，高比32小，不变 若图片横比32小，高比32大，高缩小到32，图片比例不变
			 * 若图片横比32大，高比32小，横缩小到32，图片比例不变 若图片横比32大，高比32大，图片按比例缩小，横为32或高为32
			 */
			Thumbnails.of(originalFile).size(32, 32).toFile(avatar32RealFile);
			Thumbnails.of(originalFile).size(48, 48).toFile(avatar48RealFile);

			// 更新记录
			user.setAvatar32(avatar32RelativeFile);
			user.setAvatar48(avatar48RelativeFile);
			this.getUserDao().save(user);

			// 清理，保留以前上传过的头像
			// deleteOldAvatarFile(old32);
			// deleteOldAvatarFile(old48);
			return user;

		} catch (Exception e) {
			if (avatar32File != null && avatar32File.exists()) {
				avatar32File.delete();
			}
			if (avatar48File != null && avatar48File.exists()) {
				avatar48File.delete();
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (originalFile != null && originalFile.exists()) {
				originalFile.delete();
			}
		}
	}

	// 检查文件扩展名合法性
	private void checkFilePrefix(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		String prefix = ProjectUtil.getPrefix(originalFilename);
		if (StringUtils.isEmpty(prefix) || !allowPrefix.contains(prefix)) {
			throw new RuntimeException("被禁止的文件类型");
		}
	}

	// 生成头像文件相对路径
	private String generateAvatarFileName(String uid, MultipartFile file) {
		String prefix = ProjectUtil.getPrefix(file.getOriginalFilename());
		return uid + "/" + ProjectUtil.generateShortUUID() + "." + prefix;
	}

	// 生成临时文件相对路径
	private String generateTempFileName(MultipartFile file) {
		String prefix = ProjectUtil.getPrefix(file.getOriginalFilename());
		return ProjectUtil.generateShortUUID() + "." + prefix;
	}

	// 得到头像相对相对路径
	private String getAvatarrelativePath(String avatarFileName) {
		String path = appConfig.getUploadDir() + avatarFileName;
		return path;
	}

	// 得到头像绝对地址
	private String getAvatarRealPath(String relativePath) {
		String path = appConfig.getWebContextRealPath() + relativePath;
		ProjectUtil.checkDirAndCreateIfNotExists(path);
		return path;
	}

	// 删除旧头像
	private void deleteOldAvatarFile(String relativePath) {
		if (!StringUtils.isEmpty(relativePath)) {
			File f = new File(getAvatarRealPath(relativePath));
			f.delete();
		}
	}

	/**
	 * 更新用户基本信息
	 */
	@Transactional(readOnly = false)
	public User updateUserInfo(User userUpdate) {
		User user = getUserById(userUpdate.getId());
		user.setDisplayName(userUpdate.getDisplayName());
		user.setEmail(userUpdate.getEmail());
		user.setPhone(userUpdate.getPhone());
		getUserDao().save(user);
		return user;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
