package com.ipet.client.api.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ipet.client.api.DiscoverApi;
import com.ipet.client.api.base.ApiBase;
import com.ipet.client.api.base.ApiContext;
import com.ipet.client.api.domain.IpetPhoto;

/**
 * 
 * @author xiaojinghai
 */
public class DiscoverApiImpl extends ApiBase implements DiscoverApi {

	public DiscoverApiImpl(ApiContext context) {
		super(context);
	}

	@Override
	public List<IpetPhoto> listPage(String date, String pageNumber, String pageSize) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("date", date);
		parameters.set("pageNumber", pageNumber);
		parameters.set("pageSize", pageSize);

		URI uri = buildUri("discover/listPage", parameters);
		IpetPhoto[] users = context.getRestTemplate().getForObject(uri, IpetPhoto[].class);
		List<IpetPhoto> list = Arrays.asList(users);
		return list;
	}

}
