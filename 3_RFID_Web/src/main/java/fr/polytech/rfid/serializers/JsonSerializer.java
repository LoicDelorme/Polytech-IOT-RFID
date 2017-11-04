package fr.polytech.rfid.serializers;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class JsonSerializer implements Serializer<String> {

	public static final Jsonb JSON_BUILDER = JsonbBuilder.create(new JsonbConfig().withNullValues(false));

	@Override
	public <I> String to(I in) {
		return JSON_BUILDER.toJson(in);
	}
}