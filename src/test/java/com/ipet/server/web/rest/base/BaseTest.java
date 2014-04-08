package com.ipet.server.web.rest.base;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author xiaojinghai
 */
public class BaseTest extends com.ipet.test.BaseTest {

	public RestTemplate restTemplate;

	public BaseTest() {
		Charset charset = Charset.forName("UTF-8");

		restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter(charset));
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		// messageConverters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);

		restTemplate.setErrorHandler(new MyErrorHandler());

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new MyHttpRequestInterceptor("a", "a"));
		restTemplate.setInterceptors(interceptors);
	}

}
