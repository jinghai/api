package com.ipet.server.web.rest.base;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 暂时不用
 * 
 * Client request interceptor that does preemptive HTTP Basic authentication by
 * ensuring that an Authorization header with HTTP Basic credentials is always
 * included in the request headers.
 * 
 * @author xiaojinghai
 */
class MyHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MyHttpRequestInterceptor.class);

	private static final Base64 base64 = new Base64();

	private final String username;

	private final String password;

	private final Charset charset;

	public MyHttpRequestInterceptor(String username, String password) {
		this(username, password, Charset.forName("UTF-8"));
	}

	public MyHttpRequestInterceptor(String username, String password, Charset charset) {
		this.username = username;
		this.password = password;
		this.charset = charset;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().set("Authorization",
				"Basic " + new String(base64.encode((username + ":" + password).getBytes(charset)), charset));
		logger.debug(request.getHeaders().toString());
		logger.debug(body.toString());
		return execution.execute(request, body);
	}

}
