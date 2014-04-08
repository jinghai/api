package com.ipet.client.api;

import java.io.File;
import java.util.List;

import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.domain.IpetUser;
import com.ipet.client.api.domain.IpetUserUpdate;

/**
 * 
 * @author xiaojinghai
 */
public interface UserApi {

	/**
	 * 获取单个用户信息
	 */
	public IpetUser getUser(String userId);

	/**
	 * 获取多个用户信息
	 */
	public List<IpetUser> getUsers(String ids);

	/**
	 * 更新用户基本信息
	 */
	public IpetUser updateUserInfo(IpetUserUpdate update);

	/**
	 * 更新用户头像
	 */
	public IpetUser updateAvatar(FileSystemResource avatarFile);

	/**
	 * 更新用户头像
	 * 
	 * @param avatarFile
	 */
	public IpetUser updateAvatar(File avatarFile);

	/**
	 * 更新用户头像
	 * 
	 * @param avatarFilePath
	 *            文件完整路径
	 */
	public IpetUser updateAvatar(String avatarFilePath);

}
