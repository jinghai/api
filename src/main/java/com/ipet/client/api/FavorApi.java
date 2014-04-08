package com.ipet.client.api;

import java.util.List;

import com.ipet.client.api.domain.IpetFavor;
import com.ipet.client.api.domain.IpetPhoto;

/**
 * 赞API
 * 
 * @author xiaojinghai
 */
public interface FavorApi {

	public IpetPhoto favor(String photoId, String text);

	public IpetPhoto unfavor(String photoId);

	public List<IpetFavor> list(String photoId);

}
