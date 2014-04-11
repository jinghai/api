package com.ipet.server.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.ipet.server.util.ProjectUtil;

/**
 * 应用配置
 * 
 * @author xiaojinghai
 */
@Component
public class AppConfig {

	@Resource
	private WebApplicationContext webApplicationContext;

	// 网站根目录下上传文件的基础目录
	private final String uploadDir = "files/";
	// 网站根目录下临时文件的基础目录
	private final String tempDir = "temp/";

	private String uploadDirRealPath;
	private String tempDirRealPath;
	private String webContextRealPath;

	/**
	 * WEB容器根目录
	 */
	public String getWebContextRealPath() {
		if (StringUtils.isEmpty(webContextRealPath)) {
			webContextRealPath = webApplicationContext.getServletContext().getRealPath("/");
		}
		return webContextRealPath;
	}

	/**
	 * 上传目录真实地址
	 */
	public String getUploadDirRealPath() {
		if (StringUtils.isEmpty(uploadDirRealPath)) {
			uploadDirRealPath = getWebContextRealPath() + uploadDir;
			ProjectUtil.checkAndCreateIfNotExists(uploadDirRealPath, true);
		}
		return uploadDirRealPath;
	}

	/**
	 * 临时文件真实地址
	 */
	public String getTempDirRealPath() {
		if (StringUtils.isEmpty(tempDirRealPath)) {
			tempDirRealPath = getWebContextRealPath() + tempDir;
			ProjectUtil.checkAndCreateIfNotExists(tempDirRealPath, true);
		}
		return tempDirRealPath;
	}

	/**
	 * 上传目录相对地址
	 */
	public String getUploadDir() {
		return uploadDir;
	}

	/**
	 * 临时文件目录相对地址
	 */
	public String getTempDir() {
		return tempDir;
	}

}
