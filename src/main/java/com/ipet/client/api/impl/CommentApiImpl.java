package com.ipet.client.api.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ipet.client.api.CommentApi;
import com.ipet.client.api.base.ApiBase;
import com.ipet.client.api.base.ApiContext;
import com.ipet.client.api.domain.IpetComment;

/**
 * 
 * @author xiaojinghai
 */
public class CommentApiImpl extends ApiBase implements CommentApi {

	public CommentApiImpl(ApiContext context) {
		super(context);
	}

	@Override
	public IpetComment comment(String photoId, String text) {
		requireAuthorization();
		URI uri = buildUri("comment/create");
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("uid", context.getCurrUserId());
		body.add("photoId", photoId);
		body.add("text", text);
		IpetComment ret = context.getRestTemplate().postForObject(uri, body, IpetComment.class);
		return ret;
	}

	@Override
	public List<IpetComment> listPage(String photoId, String pageNumber, String pageSize) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("photoId", photoId);
		parameters.set("pageNumber", pageNumber);
		parameters.set("pageSize", pageSize);

		URI uri = buildUri("comment/listPage", parameters);
		IpetComment[] ret = context.getRestTemplate().getForObject(uri, IpetComment[].class);
		List<IpetComment> list = Arrays.asList(ret);
		return list;
	}

}
