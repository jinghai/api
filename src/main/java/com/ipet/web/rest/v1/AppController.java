package com.ipet.web.rest.v1;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipet.server.domain.entity.App;
import com.ipet.server.service.AppService;

/**
 * 赞
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/app")
public class AppController extends BaseController {

	@Resource
	private AppService appService;

	/**
	 * 更新软件版本
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void create(String appKey, String versionCode, String versionName, String downloadUrl) throws IOException {
		if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(versionName) || StringUtils.isEmpty(versionCode)
				|| StringUtils.isEmpty(downloadUrl)) {
			throw new RuntimeException("非法参数");
		}
		Integer vc = Integer.valueOf(versionCode);
		appService.newVersion(appKey, vc, versionName, downloadUrl);
	}

	@RequestMapping(value = "checkUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public App check(String appKey) throws IOException {
		if (StringUtils.isEmpty(appKey)) {
			throw new RuntimeException("非法参数");
		}
		return appService.checkForUpdate(appKey);
	}

}
