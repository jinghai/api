package com.ipet.client.api;

import java.util.List;

import com.ipet.client.api.domain.IpetPhoto;

/**
 * 发现API
 * 
 * @author xiaojinghai
 */
public interface DiscoverApi {

	/**
	 * 分页获取在某时间点之后发布的图片
	 * 
	 * @param date
	 *            时间字符串"yyyy-MM-dd HH:mm:ss"
	 */
	public List<IpetPhoto> listPage(String date, String pageNumber, String pageSize);

}
