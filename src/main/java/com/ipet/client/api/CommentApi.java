package com.ipet.client.api;

import java.util.List;

import com.ipet.client.api.domain.IpetComment;

/**
 * 评论API
 * 
 * @author xiaojinghai
 */
public interface CommentApi {

	/**
	 * 发表评论
	 */
	public IpetComment comment(String photoId, String text);

	/**
	 * 分页获取评论列表
	 */
	public List<IpetComment> listPage(String photoId, String pageNumber, String pageSize);

}
