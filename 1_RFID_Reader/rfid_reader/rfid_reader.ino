// Import libraries
#include <ESP8266WiFi.h>
#include <EEPROM.h>
#include <MFRC522.h>
#include <SPI.h>
#include <RestClient.h>
#include <ArduinoJson.h>

// Constants (chip RC522)
#define SELECT_RC522_PIN  15
#define RESET_RC522_PIN 16

// Constants (DOOR)
#define DOOR_PIN 5
#define DOOR_OPEN LOW
#define DOOR_CLOSED HIGH
#define DOOR_STATUS_OFFSET 0

// Constants (LED)
#define LED_PIN 2
#define LED_TURNED_ON LOW
#define LED_TURNED_OFF HIGH

// Constants (COMMUNICATION)
#define DEFAULT_SERIAL_SPEED 115200
#define DEFAULT_EEPROM_BLOCK_SIZE 512
#define DEFAULT_JSON_BUFFER 512

// Constants (NETWORK)
#define DEFAULT_LOCAL_NETWORK_SSID ((const char *) "linksys_1")
#define DEFAULT_LOCAL_NETWORK_PASSWORD ((const char *) "")

// Constants (WEB-SERVICE)
#define DEFAULT_API_BASE_URL ((const char *) "192.168.20.108")
#define DEFAULT_API_SPECIFIC_URL ((const char *) "/api/authorization/isGranted/1/")
#define DEFAULT_API_BASE_PORT 8090

// Instances
MFRC522 mfrc522 = MFRC522(SELECT_RC522_PIN, RESET_RC522_PIN);
RestClient restClient = RestClient(DEFAULT_API_BASE_URL, DEFAULT_API_BASE_PORT);

// Variables
bool doorStatus = DOOR_CLOSED;
String lastReadCard = "";

bool waitingForNewCard() {
  Serial.println("Waiting for new card...");
  while (!mfrc522.PICC_IsNewCardPresent()) {
    delay(50);
  }

  return true;
}

bool waitingForCardInformation() {
  Serial.println("Waiting for a card information...");
  return mfrc522.PICC_ReadCardSerial();
}

void dumpByteArray(byte *buffer, byte bufferSize) {
  for (byte offset = 0; offset < bufferSize; offset++) {
    Serial.print(buffer[offset], DEC);
  }
}

String getCardUuid(byte *cardUuidArray, byte bufferSize) {
  String cardUuid = "";
  for (byte offset = 0; offset < bufferSize; offset++) {
    cardUuid = String(cardUuid + String(cardUuidArray[offset], DEC));
  }

  return cardUuid;
}

String sendCardUuidToWebService(String cardUuid) {
  String computedSpecificApiUrl = String(DEFAULT_API_SPECIFIC_URL + cardUuid);
  
  char specificApiUrl[sizeof(computedSpecificApiUrl) * 4];
  computedSpecificApiUrl.toCharArray(specificApiUrl, sizeof(specificApiUrl));
  
  if (WiFi.status() != WL_CONNECTED) {
    restClient.begin(DEFAULT_LOCAL_NETWORK_SSID, DEFAULT_LOCAL_NETWORK_PASSWORD);
  }

  Serial.print("Sending a REST request to the following URL: ");
  Serial.println(specificApiUrl);
  
  String webServiceResponse = "";
  int webServiceResponseStatus = restClient.get(specificApiUrl, &webServiceResponse);
  
  Serial.print("Status code from web-service: ");
  Serial.println(webServiceResponseStatus);
  Serial.print("Response body from web-service: ");
  Serial.println(webServiceResponse);

  return webServiceResponse;
}

JsonObject& parseWebServiceResponse(String response) {
  StaticJsonBuffer<DEFAULT_JSON_BUFFER> jsonBuffer;
  JsonObject& parsedResponse = jsonBuffer.parseObject(response);
  
  return parsedResponse;
}

void openDoor() {
  doorStatus = DOOR_OPEN;
  Serial.println("Door open!");
  digitalWrite(DOOR_PIN, doorStatus);
  EEPROM.write(DOOR_STATUS_OFFSET, doorStatus);
  EEPROM.commit();

  digitalWrite(LED_PIN, LED_TURNED_OFF);
  delay(1000);
  digitalWrite(LED_PIN, LED_TURNED_ON);

  delay(2000);

  doorStatus = DOOR_CLOSED;
  Serial.println("Door closed!");
  digitalWrite(DOOR_PIN, doorStatus);
  EEPROM.write(DOOR_STATUS_OFFSET, doorStatus);
  EEPROM.commit();
}

void setup() {
  // SERIAL
  Serial.begin(DEFAULT_SERIAL_SPEED);
  Serial.print("Serial initialization... ");
  Serial.println("\tPASS");

  // EEPROM
  Serial.print("EEPROM block size initialization... ");
  EEPROM.begin(DEFAULT_EEPROM_BLOCK_SIZE);
  Serial.println("\tPASS");

  // SPI
  Serial.print("SPI bus initialization... ");
  SPI.begin();
  Serial.println("\tPASS");

  // REST
  restClient.begin(DEFAULT_LOCAL_NETWORK_SSID, DEFAULT_LOCAL_NETWORK_PASSWORD);
  
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

  // LED
  Serial.print("Led initialization... ");
  pinMode(LED_PIN, OUTPUT);
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

  // GET CARD UUID
  String cardUuid = getCardUuid(mfrc522.uid.uidByte, mfrc522.uid.size);
  Serial.print("Card UUID = ");
  Serial.println(cardUuid);

  // WEB SERVICE REQUEST
  String webServiceResponse = sendCardUuidToWebService(cardUuid);

  // WEB SERVICE RESPONSE PARSING
  JsonObject& parsedWebServiceResponse = parseWebServiceResponse(webServiceResponse);

  // CARD UUID AUTHORIZATION CHECKING
  if (parsedWebServiceResponse["success"]) {
    openDoor();
  } else {
    digitalWrite(LED_PIN, LED_TURNED_OFF);
    delay(1000);
    digitalWrite(LED_PIN, LED_TURNED_ON);

    digitalWrite(LED_PIN, LED_TURNED_OFF);
    delay(1000);
    digitalWrite(LED_PIN, LED_TURNED_ON);

    digitalWrite(LED_PIN, LED_TURNED_OFF);
    delay(1000);
    digitalWrite(LED_PIN, LED_TURNED_ON);
  }

  // DO NOT LOOP UNTIL USER REMOVED ITS CARD
  mfrc522.PICC_ReadCardSerial();
  while (cardUuid.equals(getCardUuid(mfrc522.uid.uidByte, mfrc522.uid.size))) {
    delay(50);
    mfrc522.PICC_ReadCardSerial();
  }
}
