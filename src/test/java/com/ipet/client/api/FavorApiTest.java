package com.ipet.client.api;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.domain.IpetFavor;
import com.ipet.client.api.domain.IpetPhoto;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class FavorApiTest extends BaseTest {

	private final AccountApi accountApi = IpetApiImpl.getInstance("1", "1").getAccountApi();
	private final PhotoApi photoApi = IpetApiImpl.getInstance("1", "1").getPhotoApi();
	private final FavorApi favorApi = IpetApiImpl.getInstance("1", "1").getFavorApi();

	@Test()
	public void testFavor() throws UnsupportedEncodingException {
		accountApi.login("admin", "admin");
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetPhoto photo = photoApi.publish("测试", fsr);
		favorApi.favor(photo.getId(), "喜欢");
		List<IpetFavor> comm = favorApi.list(photo.getId());
		assertEquals("喜欢", comm.get(0).getText());
	}

	@Test()
	public void testUnfavor() throws UnsupportedEncodingException {
		accountApi.login("admin", "admin");
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetPhoto photo = photoApi.publish("测试", fsr);
		photo = favorApi.favor(photo.getId(), "喜欢");
		assertEquals("1", photo.getFavorCount());
		assertEquals(true, photo.isFavored());

		photo = favorApi.unfavor(photo.getId());
		assertEquals("0", photo.getFavorCount());
		assertEquals(false, photo.isFavored());
	}

}
