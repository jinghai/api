package com.ipet.client.api;

import org.junit.Test;

import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

public class FeedbackApiTest extends BaseTest {

	private final AccountApi accountApi = IpetApiImpl.getInstance("1", "1").getAccountApi();
	private final FeedbackApi feedbackApi = IpetApiImpl.getInstance("1", "1").getFeedbackApi();

	@Test
	public void testFeedback() {
		accountApi.login("admin", "admin");
		feedbackApi.feedback("", "测试意见反馈 by admin", "");
	}

	@Test
	public void testFeedbackAnonymous() {
		accountApi.logout();
		feedbackApi.feedback("", "测试意见反馈", "");
	}

}
