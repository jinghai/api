package com.ipet.web.rest.v1;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipet.server.domain.entity.User;
import com.ipet.server.service.AccountService;

/**
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/account")
public class AccountController extends BaseController {

	@Resource
	private AccountService accountService;

	/**
	 * 用户验证(临时,非安全接口)
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User login(String username, String password) {
		logger.debug("login:" + username + ":" + password);
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			// IllegalArgumentException
			throw new RuntimeException("非法参数");
		}
		User user = accountService.verifyUserCertificate(username, password);
		return user;
	}

	/**
	 * 注册用户
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(String username, String password) {
		logger.debug("create:" + username + ":" + password);
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new RuntimeException("非法参数");
		}

		User newUser = new User();
		newUser.setLoginName(username);
		newUser.setDisplayName(username);
		newUser.setPlainPassword(password);
		// 注册
		accountService.register(newUser);
		return new ResponseEntity(newUser, HttpStatus.CREATED);
	}

	/**
	 * 检查用户名是否可用
	 */
	@RequestMapping(value = "/availableUsername", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> availableUsername(String username) {
		logger.debug("username:" + username);
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("无效参数");
		}
		return new ResponseEntity(accountService.validUsername(username), HttpStatus.OK);
	}

	/**
	 * 检查手机是否可用
	 */
	@RequestMapping(value = "/availablePhone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> availablePhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			throw new RuntimeException("无效参数");
		}
		return new ResponseEntity(accountService.validPhone(phone), HttpStatus.OK);
	}

	/**
	 * 检查邮箱是否可用
	 */
	@RequestMapping(value = "/availableEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> availableEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new RuntimeException("无效参数");
		}
		return new ResponseEntity(accountService.validEmail(email), HttpStatus.OK);
	}

	/**
	 * 检查用户是否是第一次使用系统
	 */
	@RequestMapping(value = "/isNewUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> isNewUser(String userId) {
		if (null == userId) {
			throw new RuntimeException("无效参数");
		}
		return new ResponseEntity(accountService.isNewUser(userId), HttpStatus.OK);
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> changePassword(String userId, String oldPassword, String newPassword) {
		if (null == userId || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
			throw new RuntimeException("无效参数");
		}
		accountService.changePassword(userId, oldPassword, newPassword);

		return new ResponseEntity(true, HttpStatus.OK);
	}

	/*
	 * //for test
	 * 
	 * @RequestMapping(value = "/body", method = RequestMethod.POST, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public ResponseEntity<?> createRequestParam(@RequestBody
	 * String body) { logger.debug("body"); return new ResponseEntity(body,
	 * HttpStatus.OK); }
	 * 
	 * @RequestMapping(value = "/create.json", method = RequestMethod.POST,
	 * produces = MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public ResponseEntity<?> create(@RequestBody User newUser)
	 * { logger.debug("createRequestBody"); //注册
	 * accountService.registerUser(newUser);
	 * 
	 * return new ResponseEntity(newUser, HttpStatus.CREATED); }
	 * 
	 * @RequestMapping(value = "/create.json", method = RequestMethod.POST,
	 * produces = MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public ResponseEntity<?>
	 * createRequestParam(@RequestParam("username") String username,
	 * 
	 * @RequestParam("password") String password) {
	 * logger.debug("createRequestParam");
	 * 
	 * User newUser = new User(); newUser.setLoginName(username);
	 * newUser.setPlainPassword(password); //注册
	 * accountService.registerUser(newUser);
	 * 
	 * return new ResponseEntity(newUser, HttpStatus.CREATED); }
	 * 
	 * @RequestMapping(value = "/createValid", method = RequestMethod.POST,
	 * produces = MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public ResponseEntity<?> createValid(@Valid User newUser) {
	 * logger.debug("createValid"); //注册 accountService.registerUser(newUser);
	 * 
	 * return new ResponseEntity(newUser, HttpStatus.CREATED);
	 * //http://johnathanmarksmith
	 * .com/spring/java/javaconfig/programming/spring%
	 * 20java%20configuration/spring
	 * %20mvc/web/rest/resttemplate/2013/06/18/how-to
	 * -use-spring-resttemplate-to-post-data-to-a-web-service/ }
	 */
}
