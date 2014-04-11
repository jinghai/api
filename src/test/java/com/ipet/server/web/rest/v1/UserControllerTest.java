package com.ipet.server.web.rest.v1;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ipet.server.domain.entity.User;
import com.ipet.server.web.rest.base.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class UserControllerTest extends BaseTest {

	public static final String baseUrl = "http://localhost:8080/server/api/v1/user";
	private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@Test
	public void getOneUser() {
		// 使用URI模板方式,参数也可使用Map<String, Object> urlVariables = new HashMap<
		// String, Object>();
		User user = restTemplate.getForObject(baseUrl + "/{id}", User.class, "1");
		logger.info(user.toString());
	}

	@Test
	public void getUsers() {
		// URL参数需要构建在URL字符串中
		User[] users = restTemplate.getForObject(baseUrl + "/listByIds?ids=1,2", User[].class);
		List<User> list = Arrays.asList(users);
		logger.info(list.toString());
	}

	@Test
	public void getUsersForOne() {
		// URL参数需要构建在URL字符串中
		User[] users = restTemplate.getForObject(baseUrl + "/listByIds?ids=1", User[].class);
		List<User> list = Arrays.asList(users);
		logger.info(list.toString());
	}

	@Test
	public void uploadForHttpEntity() throws UnsupportedEncodingException {
		String url = baseUrl + "/uploadAvatar";

		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);

		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("userId", "1");
		body.add("file", fsr);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body,
				requestHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		logger.info(response.toString());
		// restTemplate.postForObject(url, body, String.class);
	}

	@Test
	public void uploadAvatar() throws UnsupportedEncodingException {
		logger.debug(ClassLoader.getSystemResource("test.jpg").getPath());
		String url = baseUrl + "/uploadAvatar";
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);

		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("userId", "1");
		body.add("file", fsr);
		String r = restTemplate.postForObject(url, body, String.class);
		logger.info(r);
	}

	@Test
	// 测试超过5M的文件
	public void uploadFor5M() {
		String url = baseUrl + "/uploadAvatar";
		FileSystemResource fsr = new FileSystemResource(ClassLoader.getSystemResource("5M.JPG").getPath());

		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("userId", "1");
		body.add("file", fsr);
		try {
			String r = restTemplate.postForObject(url, body, String.class);
			logger.info(r);
		} catch (Exception e) {
			// assertTrue(e instanceof MyErrorHandler);
			logger.debug(e.getLocalizedMessage());
		}
	}

	// @Test
	// 测试100次上单线程上传性能
	public void uploadFile100WTime() throws UnsupportedEncodingException {
		// long times = 1000000;//100W
		long times = 3;
		long start = System.currentTimeMillis();
		for (long i = 0; i < times; i++) {
			uploadAvatar();
		}
		long end = System.currentTimeMillis();
		Long useAV = (end - start) / times;
		logger.info("平均耗时:" + useAV + " 毫秒");
	}

}
