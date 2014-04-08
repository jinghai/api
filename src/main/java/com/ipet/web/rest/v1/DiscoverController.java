package com.ipet.web.rest.v1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipet.server.domain.entity.Photo;
import com.ipet.server.service.DiscoverService;

/**
 * 人脉rest
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/discover")
public class DiscoverController extends BaseController {

	@Resource
	private DiscoverService discoverService;

	@RequestMapping(value = "listPage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Photo> getFollowForPage(String date, String pageNumber, String pageSize) throws IOException,
			ParseException {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(pageNumber) || StringUtils.isEmpty(pageSize)) {
			throw new RuntimeException("非法参数");
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = format.parse(date);
		Integer page = Integer.valueOf(pageNumber);
		Integer size = Integer.valueOf(pageSize);
		return discoverService.listNewerPhotosForPage(datetime, page, size);
	}

}
