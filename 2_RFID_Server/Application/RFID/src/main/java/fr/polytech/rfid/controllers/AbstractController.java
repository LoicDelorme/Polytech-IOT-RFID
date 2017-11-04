package fr.polytech.rfid.controllers;

import fr.polytech.rfid.deserializers.Deserializer;
import fr.polytech.rfid.deserializers.JsonDeserializer;
import fr.polytech.rfid.serializers.JsonSerializer;
import fr.polytech.rfid.serializers.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    public static final Serializer<String> SERIALIZER = new JsonSerializer();

    public static final Deserializer<String> DESERIALIZER = new JsonDeserializer();
}