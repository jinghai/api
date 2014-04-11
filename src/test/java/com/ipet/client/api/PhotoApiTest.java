package com.ipet.client.api;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.domain.IpetPhoto;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class PhotoApiTest extends BaseTest {

	private final PhotoApi api = IpetApiImpl.getInstance("1", "1").getPhotoApi();

	public void publishPhoto() throws UnsupportedEncodingException {
		// IpetApiImpl.getInstance("1", "1").getAccountApi().login("admin",
		// "admin");
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetPhoto ret = api.publish("测试", fsr);
		logger.debug(ToStringBuilder.reflectionToString(ret));
	}

	@Test
	public void listFollow() throws InterruptedException, UnsupportedEncodingException {
		IpetApiImpl.getInstance("1", "1").getAccountApi().login("admin", "admin");
		for (int i = 0; i < 9; i++) {
			publishPhoto();
		}
		Thread.sleep(1000);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateformat.format(new Date());
		List<IpetPhoto> ret = api.listFollowd(dateStr, "0", "4");
		assertEquals(4, ret.size());
	}

}
