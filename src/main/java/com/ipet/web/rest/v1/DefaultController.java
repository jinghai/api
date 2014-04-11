package com.ipet.web.rest.v1;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipet.web.rest.exceptions.UnknownResourceException;

/**
 * 
 * @author xiaojinghai
 */
@Controller
public class DefaultController {

	@RequestMapping("/**")
	public void unmappedRequest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		throw new UnknownResourceException("There is no resource for path " + uri);
	}

}
