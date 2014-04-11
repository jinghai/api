package com.ipet.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipet.server.domain.entity.App;
import com.ipet.server.repository.AppDao;

/**
 * 应用服务类
 * 
 * @author xiaojinghai
 */
@Service
@Transactional(readOnly = true)
public class AppService extends BaseService {

	@Resource
	private AppDao AppDao;

	/**
	 * 新增一个版本
	 */
	@Transactional(readOnly = false)
	public void newVersion(String appKey, Integer versionCode, String versionName, String downloadUrl) {
		App app = getAppDao().findByAppKey(appKey);
		if (app == null) {
			app = new App();
			app.setAppKey(appKey);
			// throw new RuntimeException("无效AppKey");
		}
		app.setVersionCode(versionCode);
		app.setVersionName(versionName);
		app.setAppDownloadUrl(downloadUrl);
		getAppDao().save(app);
	}

	/**
	 * 检查更新
	 */
	public App checkForUpdate(String appKey) {
		App app = getAppDao().findByAppKey(appKey);
		if (app == null) {
			throw new RuntimeException("无效AppKey");
		}
		return app;
	}

	public AppDao getAppDao() {
		return AppDao;
	}

	public void setAppDao(AppDao AppDao) {
		this.AppDao = AppDao;
	}

}
