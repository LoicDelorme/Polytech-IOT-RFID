package fr.polytech.rfid.deserializers;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class JsonDeserializer implements Deserializer<String> {

    public static final Jsonb JSON_BUILDER = JsonbBuilder.create();

    @Override
    public <O> O from(String in, Class<O> clazz) {
        return JSON_BUILDER.fromJson(in, clazz);
    }
}