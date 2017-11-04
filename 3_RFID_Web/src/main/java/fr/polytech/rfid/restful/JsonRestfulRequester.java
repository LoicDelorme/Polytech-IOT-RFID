package fr.polytech.rfid.restful;

import org.springframework.http.HttpHeaders;

import fr.polytech.rfid.serializers.JsonSerializer;

public class JsonRestfulRequester extends AbstractRestfulRequester {

	public JsonRestfulRequester(String baseUrl) {
		super(baseUrl, new JsonSerializer());
	}

	@Override
	public HttpHeaders getHeaders() {
		return new HttpHeaders();
	}
}