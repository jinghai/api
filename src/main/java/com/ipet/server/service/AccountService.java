package com.ipet.server.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.UserRole;
import com.ipet.server.domain.UserState;
import com.ipet.server.domain.entity.User;
import com.ipet.server.repository.UserDao;
import com.ipet.server.util.Digests;
import com.ipet.server.util.Encodes;

/**
 * 用户管理类.
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends BaseService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Resource
	private UserDao userDao;

	/**
	 * 获取所有用户
	 */
	public List<User> listAll() {
		return (List<User>) getUserDao().findAll();
	}

	/**
	 * 按ID获取用户
	 */
	public User getById(String id) {
		return getUserDao().findOne(id);
	}

	/**
	 * 用户名是否已存在
	 */
	public Boolean validUsername(String loginName) {
		String userId = getUserDao().getUserIdByLoginNameAndUserState(loginName, UserState.ENABLE);
		return userId == null;
	}

	/**
	 * 电话号码是否已存在
	 */
	public Boolean validPhone(String phone) {
		String userId = getUserDao().getUserIdByPhoneAndUserState(phone, UserState.ENABLE);
		return userId == null;
	}

	/**
	 * 验证Email是否已存在
	 */
	public Boolean validEmail(String email) {
		String userId = getUserDao().getUserIdByEmailAndUserState(email, UserState.ENABLE);
		return userId == null;
	}

	/**
	 * 是否第一次使用系统
	 */
	public Boolean isNewUser(String userId) {
		Long t = getUserDao().getLoginNumByIdAndUserState(userId, UserState.ENABLE);
		if (null == t) {
			return false;
		}
		return 0l == t;
	}

	/**
	 * 注册终端用户
	 */
	@Transactional(readOnly = false)
	public void register(User user) {
		logger.debug("registerUser:" + user.getLoginName());
		if (!validUsername(user.getLoginName())) {
			throw new RuntimeException("用户名重复");
		}
		entryptPassword(user);
		user.setUserState(UserState.ENABLE);
		user.setRoles(UserRole.ENDUSER.name());
		user.setLoginNum(0l);
		getUserDao().save(user);
	}

	/**
	 * 验证用户凭证
	 */
	@Transactional(readOnly = false)
	public User verifyUserCertificate(String loginName, String password) {
		logger.debug("verifyUserCertificate:" + loginName + ":" + password);
		User user = this.getUserDao().findByLoginNameAndUserState(loginName, UserState.ENABLE);
		if (user == null) {
			throw new RuntimeException("登录名错误");
		}

		Boolean validOldPassword = this.verifyPassword(password, user.getPassword(), user.getSalt());
		if (!validOldPassword) {
			throw new RuntimeException("密码错误");
		}
		user.setLoginNum(user.getLoginNum() + 1);
		getUserDao().save(user);
		return user;
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 *            旧的明文密码
	 * @param newPassword
	 *            新的明文密码
	 */
	@Transactional(readOnly = false)
	public void changePassword(String userId, String oldPassword, String newPassword) {
		User user = getById(userId);
		if (null == user) {
			throw new RuntimeException("无效参数");
		}

		String salt = user.getSalt();
		String password = user.getPassword();
		// 验证旧密码是否正确
		Boolean validOldPassword = this.verifyPassword(oldPassword, password, salt);
		if (!validOldPassword) {
			throw new RuntimeException("旧密码错误");
		}

		user.setPlainPassword(newPassword);
		this.entryptPassword(user);
		this.getUserDao().save(user);
	}

	/**
	 * 更新用户信息
	 */
	@Transactional(readOnly = false)
	public void update(User user) {
		getUserDao().save(user);
	}

	/**
	 * 物理删除用户
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		getUserDao().delete(id);
	}

	/**
	 * 验证密码正确性
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param passord
	 *            密文密码
	 * @param salt
	 */
	private Boolean verifyPassword(String plainPassword, String passord, String salt) {
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS);
		String p = Encodes.encodeHex(hashPassword);
		return p.equals(passord);
	}

	/**
	 * 加密密码，成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
