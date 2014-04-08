package com.ipet.client.api;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import com.ipet.client.api.domain.IpetAppUpdate;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

public class AppApiTest extends BaseTest {

	private final AppApi apApi = IpetApiImpl.getInstance("ipet", "ipet").getAppApi();

	@Test
	public void testCheckAppVersion() {
		IpetAppUpdate ret = apApi.checkAppVersion("ipet");
		logger.debug(ToStringBuilder.reflectionToString(ret));
	}

}
