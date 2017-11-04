// Import libraries
#include <PubSubClient.h>
#include <ESP8266WiFi.h>
#include <EEPROM.h>
#include <ArduinoJson.h>
#include <MFRC522.h>

// Constants
#define SELECT_RC522_PIN  15 // For the chip RC522 (select)
#define RESET_RC522_PIN 16 // For the chip RC522 (reset)

#define DOOR_PIN 5 // For the door (relay)
#define DOOR_STATUS_OFFSET 0 // For the door (relay)
#define DOOR_OPEN LOW // The door is opened
#define DOOR_CLOSED HIGH // The door is closed

#define DEFAULT_SERIAL_SPEED 115200
#define DEFAULT_EEPROM_BLOCK_SIZE 512
#define DEFAULT_JSON_BUFFER 512

#define MQTT_SERVER ((const char *) "192.168.1.5")
#define MQTT_SSID ((const char *) "dlink1")
#define MQTT_PASSWORD ((const char *) "ceciestunmotdepasse")
#define MQTT_TOPIC_IN ((const char *) "Relai")
#define MQTT_TOPIC_OUT ((const char *) "outTopic")

// Instances
MFRC522 mfrc522(SELECT_RC522_PIN, RESET_RC522_PIN);
WiFiClient wifiClient;
PubSubClient pubSubClient(wifiClient);

// Variables
bool doorStatus = DOOR_CLOSED;

// Methods
void dumpByteArray(byte *buffer, byte bufferSize) {
  for (byte offset = 0; offset < bufferSize; offset++) {
    Serial.print(buffer[offset] < 0x10 ? " 0" : " ");
    Serial.print(buffer[offset], DEC);
  }
}

void sendCardUuidToServer(byte *buffer, byte bufferSize) {
  StaticJsonBuffer<DEFAULT_JSON_BUFFER> staticJsonBuffer;

  JsonObject& root = staticJsonBuffer.createObject();
  root["MSG"] = 1;
  root["SRC"] = 2;
  root["GW_RSSI"] = 3;

  JsonArray& data = root.createNestedArray("UID");
  for (byte offset = 0; offset < bufferSize; offset++) {
    data.add(mfrc522.uid.uidByte[offset]);
  }

  root.printTo(Serial);
}

void mqttConnectionOrReconnection() {
  while (!pubSubClient.connected()) {
    Serial.println("Attempt connection...");
    if (pubSubClient.connect("ESP8266Client")) {
      Serial.println("Connected to ESP8266Client!");
      pubSubClient.publish(MQTT_TOPIC_OUT, "RFID Reader connected!");
      pubSubClient.subscribe(MQTT_TOPIC_IN);
    } else {
      Serial.print("Connection failed with state (");
      Serial.print(pubSubClient.state());
      Serial.println("). New attempt in 5 seconds.");
      delay(5000);
    }
  }
}

void processMqttServerStatus(char* topic, byte* payload, unsigned int length) {
  Serial.println("---------------------------------------------------------");
  Serial.print("MQTT message received on topic [");
  Serial.print(topic);
  Serial.print("] ");
  for (int offset = 0; offset < length; offset++) {
    Serial.print((char) payload[offset]);
  }
  Serial.println("\n---------------------------------------------------------");

  if ((char) payload[0] == '1') {
    doorStatus = DOOR_OPEN;
    Serial.println("Door open!");
  } else if ((char) payload[0] == '0') {
    doorStatus = DOOR_CLOSED;
    Serial.println("Door closed!");
  } else if ((char) payload[0] == '2') {
    doorStatus = !doorStatus;
    Serial.println("Door switched its status!");
  }

  digitalWrite(DOOR_PIN, doorStatus);
  EEPROM.write(DOOR_STATUS_OFFSET, doorStatus);
  EEPROM.commit();
}

void wifiConnection() {
  Serial.print("Wifi will connect to the following SSID: ");
  Serial.println(MQTT_SSID);

  WiFi.begin(MQTT_SSID, MQTT_PASSWORD);

  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }

  Serial.print("Successfully connected with IP address: ");
  Serial.println(WiFi.localIP());
}

void setup() {
  Serial.print("Serial initialization... ");
  Serial.begin(DEFAULT_SERIAL_SPEED);
  Serial.println("\tPASS");

  Serial.print("EEPROM block size initialization... ");
  EEPROM.begin(DEFAULT_EEPROM_BLOCK_SIZE);
  Serial.println("\tPASS");

  Serial.print("SPI bus initialization... ");
  SPI.begin();
  Serial.println("\tPASS");

  Serial.print("RC522 chip initialization... ");
  mfrc522.PCD_Init();
  Serial.println("\tPASS");

  Serial.print("Door initialization... ");
  pinMode(DOOR_PIN, OUTPUT);
  doorStatus = EEPROM.read(DOOR_STATUS_OFFSET);
  digitalWrite(DOOR_PIN, doorStatus);
  Serial.println("\tPASS");

  Serial.print("Check EEPROM registry... ");
  for (int offset = 0; offset < DEFAULT_EEPROM_BLOCK_SIZE; offset++) {
    Serial.print("EEPROM[");
    Serial.print(offset, DEC);
    Serial.print("] = ");
    Serial.println(EEPROM.read(offset));
  }
  Serial.println("\tPASS");

  wifiConnection();

  pubSubClient.setServer(MQTT_SERVER, 1883);
  pubSubClient.setCallback(processMqttServerStatus);
}

void loop() {
  mqttConnectionOrReconnection();
  pubSubClient.loop();

  Serial.println("Waiting for a new card...");
  while (!mfrc522.PICC_IsNewCardPresent()) {
    delay(50);
  }
  Serial.println("New card detected!");

  Serial.println("Waiting for a card information...");
  if (!mfrc522.PICC_ReadCardSerial()) {
    Serial.println("Card information can't be read...");
    return;
  }
  Serial.println("Card information read...");

  Serial.print("Card UUID = ");
  dumpByteArray(mfrc522.uid.uidByte, mfrc522.uid.size);

  sendCardUuidToServer(mfrc522.uid.uidByte, mfrc522.uid.size);

  while (mfrc522.PICC_IsNewCardPresent()) {
    delay(50);
  }
}
