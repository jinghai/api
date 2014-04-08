package com.ipet.client.api;

import com.ipet.client.api.domain.IpetAppUpdate;

/**
 * 应用API
 * 
 * @author xiaojinghai
 */
public interface AppApi {

	/**
	 * 检查应用版本信息
	 */
	public IpetAppUpdate checkAppVersion(String appKey);

}
