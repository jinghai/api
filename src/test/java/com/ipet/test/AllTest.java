package com.ipet.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.ipet.client.api.AccountApiTest;
import com.ipet.client.api.AppApiTest;
import com.ipet.client.api.CommentApiTest;
import com.ipet.client.api.ContactApiTest;
import com.ipet.client.api.DescoverApiTest;
import com.ipet.client.api.FavorApiTest;
import com.ipet.client.api.FeedbackApiTest;
import com.ipet.client.api.PhotoApiTest;
import com.ipet.client.api.ShortUUIDForMultiThreadedTest;
import com.ipet.client.api.ShortUUIDTest;
import com.ipet.client.api.UserApiTest;
import com.ipet.jetty.JettyServer;
import com.ipet.server.web.rest.base.FilePathTest;
import com.ipet.server.web.rest.v1.AccountControllerTest;
import com.ipet.server.web.rest.v1.UserControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AppApiTest.class, FeedbackApiTest.class, AccountApiTest.class, CommentApiTest.class,
		ContactApiTest.class, DescoverApiTest.class, FavorApiTest.class, PhotoApiTest.class, ShortUUIDTest.class,
		ShortUUIDForMultiThreadedTest.class, UserApiTest.class, FilePathTest.class, AccountControllerTest.class,
		UserControllerTest.class })
public class AllTest extends BaseTestWithDBUnit {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseTestWithDBUnit.setUpBeforeClass();
		JettyServer.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JettyServer.stop();
	}

}
