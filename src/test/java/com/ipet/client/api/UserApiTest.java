package com.ipet.client.api;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.base.APIException;
import com.ipet.client.api.domain.IpetUser;
import com.ipet.client.api.domain.IpetUserUpdate;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class UserApiTest extends BaseTest {

	private UserApi api = IpetApiImpl.getInstance("1", "1").getUserApi();

	@Test
	public void getUser() {
		IpetUser ret = api.getUser("1");
		logger.debug(ToStringBuilder.reflectionToString(ret));
	}

	@Test
	public void getUsers() {
		List<IpetUser> ret = api.getUsers("1,2");
		logger.debug(ToStringBuilder.reflectionToString(ret));
	}

	@Test
	public void updateUserInfo() {
		IpetApiImpl.getInstance("1", "1").getAccountApi().login("admin", "admin");
		IpetUserUpdate update = new IpetUserUpdate();
		update.setDisplayName("admin->测试");
		update.setEmail("neverused@test.com");
		update.setPhone("1234");
		IpetUser ret = api.updateUserInfo(update);
		logger.debug(ToStringBuilder.reflectionToString(ret));
	}

	@Test
	public void updateAvatar() throws UnsupportedEncodingException {
		IpetApiImpl.getInstance("1", "1").getAccountApi().login("admin", "admin");
		String filePath = super.getTestPhotoPath();
		File f = new File(filePath);
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetUser ret = api.updateAvatar(fsr);
		logger.debug(ToStringBuilder.reflectionToString(ret));

		ret = api.updateAvatar(f);
		logger.debug(ToStringBuilder.reflectionToString(ret));

		ret = api.updateAvatar(filePath);
		logger.debug(ToStringBuilder.reflectionToString(ret));
		logger.debug(ret.getAvatar32());
		logger.debug(ret.getAvatar48());
	}

	@Test
	public void updateAvatarException() throws UnsupportedEncodingException {
		IpetApiImpl.getInstance("1", "1").getAccountApi().login("admin", "admin");
		String fs = super.getTest5MPhotoPath();
		File f = new File(fs);
		FileSystemResource fsr = new FileSystemResource(fs);
		try {
			IpetUser ret = api.updateAvatar(fsr);
			logger.debug(ToStringBuilder.reflectionToString(ret));

			ret = api.updateAvatar(f);
			logger.debug(ToStringBuilder.reflectionToString(ret));

			ret = api.updateAvatar(fs);
			logger.debug(ToStringBuilder.reflectionToString(ret));
		} catch (Exception e) {
			assertTrue(e instanceof APIException);
			assertTrue(e.getMessage().contains("文件过大"));
			logger.debug(e.getLocalizedMessage());
		}
	}

}
