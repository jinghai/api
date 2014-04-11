package com.ipet.server.web.rest.v1;

import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ipet.server.domain.entity.User;
import com.ipet.server.web.rest.base.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class AccountControllerTest extends BaseTest {

	public static final String baseUrl = "http://localhost:8080/server/api/v1/account";
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void create() {
		// 发送MultiValueMap参数
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		String loginName = UUID.randomUUID().toString().replaceAll("-", "");
		request.add("username", loginName);
		request.add("password", "test");
		User r = restTemplate.postForObject(baseUrl + "/create", request, User.class);
		logger.debug(r.toString());
	}

	@Test
	public void availablePhone() {
		Boolean r = restTemplate.getForObject(baseUrl + "/availablePhone?phone=1111", Boolean.class);
		logger.debug(r.toString());
	}

	@Test
	public void availableEmail() {
		Boolean r = restTemplate.getForObject(baseUrl + "/availableEmail?email=admin@gmail.com", Boolean.class);
		logger.debug(r.toString());
	}

	@Test
	public void availableUsername() {
		Boolean r = restTemplate.getForObject(baseUrl + "/availableUsername?username=amdin", Boolean.class);
		logger.debug(r.toString());
	}

	@Test
	public void isNewUser() {
		Boolean r = restTemplate.getForObject(baseUrl + "/isNewUser?userId=1", Boolean.class);
		logger.debug(r.toString());
	}

	@Test
	public void changePassword() {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("userId", "1");
		body.add("oldPassword", "admin");
		body.add("newPassword", "admin");
		String url = baseUrl + "/changePassword";
		Boolean r = restTemplate.postForObject(url, body, Boolean.class);
		logger.debug(r.toString());
	}

}
