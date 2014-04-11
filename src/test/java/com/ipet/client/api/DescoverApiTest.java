package com.ipet.client.api;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.domain.IpetPhoto;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class DescoverApiTest extends BaseTest {

	private final AccountApi accountApi = IpetApiImpl.getInstance("1", "1").getAccountApi();
	private final PhotoApi photoApi = IpetApiImpl.getInstance("1", "1").getPhotoApi();
	private final DiscoverApi discoverApi = IpetApiImpl.getInstance("1", "1").getDiscoverApi();

	@Test
	public void comment() throws InterruptedException, UnsupportedEncodingException {
		accountApi.login("admin", "admin");
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetPhoto photo = photoApi.publish("测试", fsr);
		photo = photoApi.publishText(photo.getId(), "测试");
		logger.debug(photo.getText());
		Thread.sleep(1000);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		logger.debug(dateStr);
		List<IpetPhoto> list = discoverApi.listPage(dateStr, "0", "20");
		Assert.assertEquals("测试", list.get(0).getText());
	}

}
