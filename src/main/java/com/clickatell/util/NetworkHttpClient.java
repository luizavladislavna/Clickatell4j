package com.clickatell.util;

import com.clickatell.exxeption.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NetworkHttpClient {

	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SOCKET_TIMEOUT = 30500;
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	/**
	 * @var The URL to use for the base of the REST API.
	 */
	private static final String CLICKATELL_REST_BASE_URL = "https://api.clickatell.com/rest/";
	/**
	 * @var The three private variables to use for authentication.
	 */
	private static String AUTHORIZATION_KEY;
	private final org.apache.http.client.HttpClient client;

	/**
	 * Create a new HTTP Client.
	 */
	private NetworkHttpClient (String apiKey) {
		this.AUTHORIZATION_KEY = apiKey;

		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(CONNECTION_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT)
				.build();

		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
		headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + AUTHORIZATION_KEY));
		headers.add(new BasicHeader("X-Version", "1"));
		headers.add(new BasicHeader(HttpHeaders.CONTENT_LANGUAGE, "en-US"));
		headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));

		client = HttpClientBuilder.create()
				.setConnectionManager(new PoolingHttpClientConnectionManager())
				.setDefaultRequestConfig(config)
				.setDefaultHeaders(headers)
				.setMaxConnPerRoute(10)
				.build();
	}

	public static NetworkHttpClient of (String apiKey) {return new NetworkHttpClient(apiKey);}

	/**
	 * Make a request.
	 *
	 * @param resource       request to make
	 * @param method         method to make request
	 * @param jsonDataPacket dataPacket to make
	 * @return Response of the HTTP request
	 */
	public String makeRequest (String resource, HttpMethod method, String jsonDataPacket) {

		RequestBuilder builder = RequestBuilder.create(method.toString())
				.setUri(CLICKATELL_REST_BASE_URL + resource)
				.setVersion(HttpVersion.HTTP_1_1)
				.setCharset(DEFAULT_CHARSET);

		if (jsonDataPacket != null) {
			String l = Integer.toString(jsonDataPacket.getBytes().length);
			log.debug("jsonDataPacket: {}", jsonDataPacket);
			builder.setEntity(new StringEntity(jsonDataPacket, DEFAULT_CHARSET));
		} else {
			builder.setHeader(new BasicHeader(HttpHeaders.CONTENT_LENGTH,"0"));
		}

		try {
			HttpResponse response = client.execute(builder.build());
			return handleResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}

	}

	public String handleResponse (HttpResponse response) throws IOException {
		return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
	}

}
