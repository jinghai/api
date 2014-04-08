package com.ipet.test;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础测试类，提供基础功能，如Logger
 * 
 * @author luocanfeng
 * @date 2014年3月27日
 */
public abstract class BaseTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected String getTestPhotoPath() throws UnsupportedEncodingException {
		return getPhotoPath("test.jpg");
	}

	protected String getTest5MPhotoPath() throws UnsupportedEncodingException {
		return getPhotoPath("5M.JPG");
	}

	protected String getPhotoPath(String photoFilename) throws UnsupportedEncodingException {
		URL url = ClassLoader.getSystemResource(photoFilename);
		logger.debug("--photoPathURL=", url);
		String photoPath = URLDecoder.decode(url.getPath(), "UTF-8");
		logger.debug("--photoPath=", photoPath);
		return photoPath;
	}

}
