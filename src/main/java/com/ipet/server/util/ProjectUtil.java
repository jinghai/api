package com.ipet.server.util;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 
 * @author xiaojinghai
 */
public class ProjectUtil {

	/**
	 * 构建分页对象
	 */
	public static PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, Direction direction,
			String... fields) {
		Direction d = Sort.Direction.ASC;
		int number = 0;
		int size = 20;
		if (pageNumber != null) {
			number = pageNumber;
		}
		if (pageSize != null) {
			size = pageSize;
		}
		if (direction != null) {
			d = direction;
		}
		Sort sort = new Sort(d, fields);
		return new PageRequest(number, size, sort);
	}

	/**
	 * 生成13位短UUID (13 characters)
	 */
	public static String generateShortUUID() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}

	/**
	 * 获取项目路径
	 */
	public static String getApplicationPath() {
		Resource resource = new ClassPathResource("./");
		String filePath = "";
		try {
			filePath = resource.getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		filePath = filePath.substring(0, filePath.indexOf("target"));
		return filePath;
	}

	/**
	 * 检查一个文件路径是否存在，若不存在则创建其目录结构
	 */
	public static void checkAndCreateIfNotExists(String filePath, boolean isDirectory) {
		File folder = new File(filePath);
		if (!isDirectory) {
			folder = new File(folder.getParent());
		}

		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	/**
	 * 得到文件扩展名（返回小写）
	 */
	public static String getPrefix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	public static void LocaleResourceTest() {
		LocalizedResourceHelper lrHalper = new LocalizedResourceHelper();
		// ① 获取对应美国的本地化文件资源
		Resource msg_us = lrHalper.findLocalizedResource("i18n/message", ".properties", Locale.US);
		// ② 获取对应中国大陆的本地化文件资源
		Resource msg_cn = lrHalper.findLocalizedResource("i18n/message", ".properties", Locale.CHINA);
		System.out.println("fileName(us):" + msg_us.getFilename());
		System.out.println("fileName(cn):" + msg_cn.getFilename());

		Resource res = new ClassPathResource("conf/file1.txt");
		// ① 指定文件资源对应的编码格式（UTF-8）
		EncodedResource encRes = new EncodedResource(res, "UTF-8");
		// ② 这样才能正确读取文件的内容，而不会出现乱码
	}

}
