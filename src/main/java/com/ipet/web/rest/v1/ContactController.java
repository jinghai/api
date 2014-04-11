package com.ipet.web.rest.v1;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipet.server.domain.entity.User;
import com.ipet.server.service.ContactService;

/**
 * 人脉rest
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/contact")
public class ContactController extends BaseController {

	@Resource
	private ContactService contactService;

	/**
	 * 获取关注列表
	 */
	@RequestMapping(value = "listFollows", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> followList(String uid) {
		if (StringUtils.isEmpty(uid)) {
			throw new RuntimeException("无效参数");
		}

		return contactService.listGuys(uid);
	}

	/**
	 * 获取粉丝列表
	 */
	@RequestMapping(value = "listFollowers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> followerList(String uid) {
		if (StringUtils.isEmpty(uid)) {
			throw new RuntimeException("无效参数");
		}

		return contactService.listFans(uid);
	}

	/**
	 * 获取好友列表
	 */
	@RequestMapping(value = "listfriends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> friendList(String uid) {
		if (StringUtils.isEmpty(uid)) {
			throw new RuntimeException("无效参数");
		}

		return contactService.listFriends(uid);
	}

	/**
	 * A关注B
	 */
	@RequestMapping(value = "follow", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean follow(String userIdA, String userIdB) {
		if (StringUtils.isEmpty(userIdA) || StringUtils.isEmpty(userIdB)) {
			throw new RuntimeException("无效参数");
		}

		contactService.follow(userIdA, userIdB);
		return true;
	}

	/**
	 * A反关注B
	 */
	@RequestMapping(value = "unfollow", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Boolean unfollow(String userIdA, String userIdB) {
		if (StringUtils.isEmpty(userIdA) || StringUtils.isEmpty(userIdB)) {
			throw new RuntimeException("无效参数");
		}

		contactService.unfollow(userIdA, userIdB);
		return true;
	}

}
