package fr.polytech.rfid.restful;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.polytech.rfid.serializers.Serializer;

public abstract class AbstractRestfulRequester implements RestfulRequester {

	private final String baseUrl;

	private final RestTemplate restTemplate;

	private final Serializer<String> serializer;

	public AbstractRestfulRequester(String baseUrl, Serializer<String> serializer) {
		this.baseUrl = baseUrl;
		this.restTemplate = new RestTemplate();
		this.serializer = serializer;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	@Override
	public <T> T get(String resourcePath, Class<T> responseType) {
		return execute(resourcePath, HttpMethod.GET, responseType).getBody();
	}

	@Override
	public <T> T post(String resourcePath, Object object, Class<T> responseType) {
		return execute(resourcePath, HttpMethod.POST, this.serializer.to(object), responseType).getBody();
	}

	@Override
	public <T> T put(String resourcePath, Object object, Class<T> responseType) {
		return execute(resourcePath, HttpMethod.PUT, this.serializer.to(object), responseType).getBody();
	}

	@Override
	public <T> T delete(String resourcePath, Class<T> responseType) {
		return execute(resourcePath, HttpMethod.DELETE, responseType).getBody();
	}

	private <T> ResponseEntity<T> execute(String resourcePath, HttpMethod httpMethod, Class<T> responseType) {
		return execute(resourcePath, httpMethod, "", responseType);
	}

	private <T> ResponseEntity<T> execute(String resourcePath, HttpMethod httpMethod, String requestBody, Class<T> responseType) {
		return restTemplate.exchange(getBaseUrl() + resourcePath, httpMethod, new HttpEntity<String>(requestBody, getHeaders()), responseType);
	}
}