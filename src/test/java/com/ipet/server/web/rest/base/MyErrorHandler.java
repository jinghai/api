package com.ipet.server.web.rest.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * 
 * @author xiaojinghai
 */
public class MyErrorHandler implements ResponseErrorHandler {

	// private final ResponseErrorHandler errorHandler = new
	// DefaultResponseErrorHandler();
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return hasError(response.getStatusCode());
		// return errorHandler.hasError(response);
	}

	protected boolean hasError(HttpStatus statusCode) {
		return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR || statusCode.series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// String theString = IOUtils.toString(response.getBody(), "UTF-8");
		HttpStatus statusCode = response.getStatusCode();
		MediaType contentType = response.getHeaders().getContentType();
		Charset charset = contentType != null ? contentType.getCharSet() : null;
		byte[] body = getResponseBody(response);
		String str = new String(body, charset);
		/*
		 * switch (statusCode.series()) { case CLIENT_ERROR: throw new
		 * HttpClientErrorException(statusCode, response.getStatusText(), body,
		 * charset); case SERVER_ERROR: throw new
		 * HttpServerErrorException(statusCode, response.getStatusText(), body,
		 * charset); default: throw new
		 * RestClientException("Unknown status code [" + statusCode + "]"); }
		 */
		throw new RuntimeException("Error:" + str);
	}

	private byte[] getResponseBody(ClientHttpResponse response) {
		try {
			InputStream responseBody = response.getBody();
			if (responseBody != null) {
				return FileCopyUtils.copyToByteArray(responseBody);
			}
		} catch (IOException ex) {
			// ignore
		}
		return new byte[0];
	}

}
