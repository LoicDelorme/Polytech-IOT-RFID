// Import libraries
#include <ESP8266WiFi.h>
#include <EEPROM.h>
#include <MFRC522.h>
#include <SPI.h>
#include <RestClient.h>
#include <ArduinoJson.h>

// Constants
#define SELECT_RC522_PIN  15 // For the chip RC522 (select)
#define RESET_RC522_PIN 16 // For the chip RC522 (reset)

#define DOOR_PIN 5 // For the door (= relay)
#define DOOR_OPEN LOW // The door is opened
#define DOOR_CLOSED HIGH // The door is closed
#define DOOR_STATUS_OFFSET 0 // For the door

#define DEFAULT_SERIAL_SPEED 115200
#define DEFAULT_EEPROM_BLOCK_SIZE 512
#define DEFAULT_JSON_BUFFER 512

#define DEFAULT_LOCAL_NETWORK_SSID ((const char *) "ssid")
#define DEFAULT_LOCAL_NETWORK_PASSWORD ((const char *) "pwd")
#define DEFAULT_BASE_URL ((const char *) "http://localhost:8090")
#define DEFAULT_API_URL ((const char *) "/api/authorization/isGranted/1/")

// Instances
MFRC522 mfrc522 = MFRC522(SELECT_RC522_PIN, RESET_RC522_PIN);
RestClient restClient = RestClient(DEFAULT_BASE_URL, DEFAULT_LOCAL_NETWORK_SSID, DEFAULT_LOCAL_NETWORK_PASSWORD);

// Variables
bool doorStatus = DOOR_CLOSED;

// Methods
bool waitingForNewCard() {
  Serial.println("Waiting for new card...");
  while (!mfrc522.PICC_IsNewCardPresent()) {
    delay(100);
  }

  return true;
}

bool waitingForCardInformation() {
  Serial.println("Waiting for a card information...");
  return mfrc522.PICC_ReadCardSerial();
}

void dumpByteArray(byte *buffer, byte bufferSize) {
  for (byte offset = 0; offset < bufferSize; offset++) {
    Serial.print(buffer[offset] < 0x10 ? " 0" : " ");
    Serial.print(buffer[offset], DEC);
  }
}

String sendCardUuidToWebservice(byte *cardUuidArray) {
  String cardUuid = String((char*) cardUuidArray);
  String apiUrl = String(DEFAULT_API_URL + cardUuid);
  
  char apiUrlChar[sizeof(apiUrl)];
  apiUrl.toCharArray(apiUrlChar, sizeof(apiUrlChar));
  
  Serial.println("Starting REST Client");
  if (restClient.connect() == WL_CONNECTED) {
    String webserviceResponse = "";
    int webserviceResponseStatus = restClient.get(apiUrlChar, &webserviceResponse);
    
    Serial.print("Status code from webservice: ");
    Serial.println(webserviceResponseStatus);
    Serial.print("Response body from webservice: ");
    Serial.println(webserviceResponse);

    return webserviceResponse;
  }

  return "";
}

JsonObject& parseWebserviceResponse(String response) {
  StaticJsonBuffer<DEFAULT_JSON_BUFFER> jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(response);
  
  root.printTo(Serial);
  return root;
}

void openDoor() {
  doorStatus = DOOR_OPEN;
  Serial.println("Door will be opened!");
  digitalWrite(DOOR_PIN, doorStatus);
  EEPROM.write(DOOR_STATUS_OFFSET, doorStatus);
  EEPROM.commit();

  // BIP
  delay(5000);

  doorStatus = DOOR_CLOSED;
  Serial.println("Door will be closed!");
  digitalWrite(DOOR_PIN, doorStatus);
  EEPROM.write(DOOR_STATUS_OFFSET, doorStatus);
  EEPROM.commit();
}

void setup() {
  // SERIAL
  Serial.print("Serial initialization... ");
  Serial.begin(DEFAULT_SERIAL_SPEED);
  Serial.println("\tPASS");

  // EEPROM
  Serial.print("EEPROM block size initialization... ");
  EEPROM.begin(DEFAULT_EEPROM_BLOCK_SIZE);
  Serial.println("\tPASS");

  // SPI
  Serial.print("SPI bus initialization... ");
  SPI.begin();
  Serial.println("\tPASS");

  // WIFI
  Serial.print("Wifi initialization... ");
  WiFi.begin(DEFAULT_LOCAL_NETWORK_SSID, DEFAULT_LOCAL_NETWORK_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(250);
    Serial.print(".");
  }
  Serial.print("Successfully connected, IP address: ");
  Serial.println(WiFi.localIP());
  
  // RC522
  Serial.print("RC522 chip initialization... ");
  mfrc522.PCD_Init();
  Serial.println("\tPASS");
  
  // DOOR
  Serial.print("Door initialization... ");
  pinMode(DOOR_PIN, OUTPUT);
  doorStatus = EEPROM.read(DOOR_STATUS_OFFSET);
  digitalWrite(DOOR_PIN, doorStatus);
  Serial.println("\tPASS");
}

void loop() {
  // CARD DETECTION
  bool cardDetectionStatus = waitingForNewCard();
  if (!cardDetectionStatus) {
    Serial.println("Card can't be detected...");
    return;
  }
  Serial.println("New card detected!");

  // CARD INFORMATION
  bool cardInformationStatus = waitingForCardInformation();
  if (!cardInformationStatus) {
    Serial.println("Card information can't be read...");
    return;
  }
  Serial.println("Card information read!");

  // CARD UUID
  Serial.print("Card UUID = ");
  dumpByteArray(mfrc522.uid.uidByte, mfrc522.uid.size);
  Serial.println();

  // WEB SERVICE CONNECTION
  String webserviceResponse = sendCardUuidToWebservice(mfrc522.uid.uidByte);

  // CARD UUID AUTHORIZATION CHECKING
  JsonObject& parsedWebserviceResponse = parseWebserviceResponse(webserviceResponse);

  // PROCESS RESPONSE
  if (parsedWebserviceResponse["success"]) {
    openDoor();
  } else {
    // BIP BIP BIP
  }

  // DO NOT LOOP UNTIL USER REMOVED ITS CARD
  while (mfrc522.PICC_IsNewCardPresent()) {
    delay(100);
  }
}
