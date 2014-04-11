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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ipet.server.domain.entity.Photo;
import com.ipet.server.service.PhotoService;

/**
 * 图片分享
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/photo")
public class PhotoController extends BaseController {

	@Resource
	private PhotoService photoService;

	/**
	 * 发布图片
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Photo publish(String uid, @RequestParam(required = false) String context, MultipartFile file)
			throws IOException {
		if (StringUtils.isEmpty(uid)) {
			throw new RuntimeException("非法参数");
		}
		/*
		 * String s1 = new String(context.getBytes("UTF-8"), "ISO-8859-1");
		 * String s2 = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
		 * logger.debug("s1:" + s1); logger.debug("s2:" + s2);
		 * 
		 * String s3 = new String(context.getBytes("UTF-8"), "UTF-8"); String s4
		 * = new String(s3.getBytes("ISO-8859-1"), "UTF-8"); logger.debug("s1:"
		 * + s1); logger.debug("s2:" + s2);
		 * 
		 * String s5 = new String(context.getBytes("UTF-8"), "UTF-8"); String s6
		 * = new String(s5.getBytes("UTF-8"), "ISO-8859-1"); logger.debug("s1:"
		 * + s1); logger.debug("s2:" + s2);
		 * 
		 * logger.debug("context:" + context); logger.debug("context:" +
		 * java.net.URLDecoder.decode(context));
		 * logger.debug("context()-->UTF-8：" + new String(context.getBytes(),
		 * "UTF-8")); logger.debug("context(ISO-8859-1)-->UTF-8：" + new
		 * String(context.getBytes("ISO-8859-1"), "UTF-8"));
		 * logger.debug("context(UTF-8)-->UTF-8：" + new
		 * String(context.getBytes("UTF-8"), "UTF-8"));
		 * logger.debug("context(GBK)-->UTF-8：" + new
		 * String(context.getBytes("GBK"), "UTF-8"));
		 * logger.debug("context(GB2312)-->UTF-8：" + new
		 * String(context.getBytes("GB2312"), "UTF-8"));
		 * logger.debug("context()-->ISO-8859-1：" + new
		 * String(context.getBytes(), "ISO-8859-1"));
		 * logger.debug("context()-->GBK：" + new String(context.getBytes(),
		 * "GBK")); logger.debug("context()-->GB2312：" + new
		 * String(context.getBytes(), "GB2312"));
		 * logger.debug("context()-->ISO-8859-1：" + new
		 * String(context.getBytes(), "ISO-8859-1")); String fname =
		 * file.getOriginalFilename(); logger.debug("fname:" + fname);
		 * logger.debug("fname:" + java.net.URLDecoder.decode(fname));
		 * logger.debug("fname()-->UTF-8：" + new String(fname.getBytes(),
		 * "UTF-8")); logger.debug("fname(ISO-8859-1)-->UTF-8：" + new
		 * String(fname.getBytes("ISO-8859-1"), "UTF-8"));
		 * logger.debug("fname(UTF-8)-->UTF-8：" + new
		 * String(fname.getBytes("UTF-8"), "UTF-8"));
		 * logger.debug("fname(GBK)-->UTF-8：" + new
		 * String(fname.getBytes("GBK"), "UTF-8"));
		 * logger.debug("fname(GB2312)-->UTF-8：" + new
		 * String(fname.getBytes("GB2312"), "UTF-8"));
		 * logger.debug("fname()-->ISO-8859-1：" + new String(fname.getBytes(),
		 * "ISO-8859-1")); logger.debug("fname()-->GBK：" + new
		 * String(fname.getBytes(), "GBK")); logger.debug("fname()-->GB2312：" +
		 * new String(fname.getBytes(), "GB2312"));
		 * logger.debug("fname()-->ISO-8859-1：" + new String(fname.getBytes(),
		 * "ISO-8859-1"));
		 */
		// String text = new String(context.getBytes(), "UTF-8");
		// String text = new String(context.getBytes("ISO-8859-1"), "UTF-8");
		// String text = new String(context.getBytes("UTF-8"), "UTF-8");
		// String text = new String(context.getBytes("GBK"), "UTF-8");
		// String text = new String(context.getBytes("GB2312"), "UTF-8");

		return photoService.publishPhoto(uid, context, file);
	}

	/** 为了解决发布文字乱码问题而临时提供的一个接口 */
	@Deprecated
	@RequestMapping(value = "publishText", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Photo publishText(String uid, String id, String context) throws IOException {
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(id)) {
			throw new RuntimeException("非法参数");
		}

		return photoService.publishText(uid, id, context);
	}

	@RequestMapping(value = "listFollow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Photo> getFollowForPage(String uid, String date, String pageNumber, String pageSize)
			throws IOException, ParseException {
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(date) || StringUtils.isEmpty(pageNumber)
				|| StringUtils.isEmpty(pageSize)) {
			throw new RuntimeException("非法参数");
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = format.parse(date);
		Integer page = Integer.valueOf(pageNumber);
		Integer size = Integer.valueOf(pageSize);
		return photoService.listPhotosByDatetimeAndFollowForPage(uid, datetime, page, size);
	}

}
