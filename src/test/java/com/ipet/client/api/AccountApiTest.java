package com.ipet.client.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import com.ipet.client.api.base.APIException;
import com.ipet.client.api.domain.IpetUser;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class AccountApiTest extends BaseTest {

	private AccountApi api = IpetApiImpl.getInstance("1", "1").getAccountApi();

	@Test
	public void login() {
		IpetUser ret = api.login("admin", "admin");
		logger.debug(ToStringBuilder.reflectionToString(ret));
		assertEquals("1", ret.getId());
	}

	@Test
	public void logout() {
		api.logout();
	}

	@Test
	public void register() {
		String loginName = UUID.randomUUID().toString().replaceAll("-", "");
		IpetUser ret = api.register(loginName, "test");
		logger.debug(ToStringBuilder.reflectionToString(ret));
		assertEquals(loginName, ret.getLoginName());
		try {
			ret = api.register("测试", "test");
		} catch (Exception e) {
			assertTrue(e instanceof APIException);
			assertTrue(e.getMessage().contains("用户名重复"));
			logger.debug(e.getLocalizedMessage());
		}
	}

	@Test
	public void checkLoginName() {
		Boolean ret = api.checkLoginName("neverused");
		assertEquals(true, ret);
		ret = api.checkLoginName("admin");
		assertEquals(false, ret);
	}

	@Test
	public void checkPhone() {
		Boolean ret = api.checkPhone("9999");
		logger.debug(ret.toString());
		assertEquals(true, ret);
	}

	@Test
	public void checkEmail() {
		Boolean ret = api.checkEmail("test@test.com");
		logger.debug(ret.toString());
		assertEquals(true, ret);
	}

	@Test
	public void changePassword() {
		api.login("admin", "admin");
		Boolean ret = api.changePassword("admin", "admin");
		logger.debug(ret.toString());
		assertEquals(true, ret);
	}

}
