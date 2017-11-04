package fr.polytech.rfid.restful;

import org.springframework.http.HttpHeaders;

public interface RestfulRequester {

	public String getBaseUrl();

	public HttpHeaders getHeaders();

	public <T> T get(String resourcePath, Class<T> responseType);

	public <T> T post(String resourcePath, Object object, Class<T> responseType);

	public <T> T put(String resourcePath, Object object, Class<T> responseType);

	public <T> T delete(String resourcePath, Class<T> responseType);
}