package com.ipet.client.api;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.client.api.domain.IpetComment;
import com.ipet.client.api.domain.IpetPhoto;
import com.ipet.client.api.impl.IpetApiImpl;
import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class CommentApiTest extends BaseTest {

	private final AccountApi accountApi = IpetApiImpl.getInstance("1", "1").getAccountApi();
	private final PhotoApi photoApi = IpetApiImpl.getInstance("1", "1").getPhotoApi();
	private final CommentApi commentApi = IpetApiImpl.getInstance("1", "1").getCommentApi();

	@Test
	public void comment() throws UnsupportedEncodingException {
		accountApi.login("admin", "admin");
		String filePath = super.getTestPhotoPath();
		FileSystemResource fsr = new FileSystemResource(filePath);
		IpetPhoto photo = photoApi.publish("测试", fsr);
		commentApi.comment(photo.getId(), "真好啊");
		List<IpetComment> comm = commentApi.listPage(photo.getId(), "0", "20");
		assertEquals("真好啊", comm.get(0).getText());
	}

}
