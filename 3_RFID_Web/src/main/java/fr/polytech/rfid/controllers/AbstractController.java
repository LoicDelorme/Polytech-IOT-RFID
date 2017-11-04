package fr.polytech.rfid.controllers;

import java.util.logging.Logger;

import fr.polytech.rfid.deserializers.Deserializer;
import fr.polytech.rfid.deserializers.JsonDeserializer;
import fr.polytech.rfid.restful.JsonRestfulRequester;
import fr.polytech.rfid.restful.RestfulRequester;

public class AbstractController {

	public static final Logger LOGGER = Logger.getLogger(AbstractController.class.getSimpleName());

	public static final Deserializer<String> DESERIALIZER = new JsonDeserializer();

	public static final RestfulRequester RESTFUL_REQUESTER = new JsonRestfulRequester("http://localhost:8090/api/");
}