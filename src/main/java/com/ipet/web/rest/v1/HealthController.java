package com.ipet.web.rest.v1;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author xiaojinghai
 */
@Controller
@RequestMapping(value = "/v1/health")
public class HealthController extends BaseController {

	/**
	 * 监控检查
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void health() {
	}

}
