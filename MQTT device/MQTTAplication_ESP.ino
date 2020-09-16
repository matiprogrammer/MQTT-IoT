 #include <MotionSensor.h>

#include <THSensor.h>
#include <Relay.h>/////////
#include <Base.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include "user_interface.h"
#include "wpa2_enterprise.h"
#include <Helper.h>
#include <Condition.h>
#include <RelayCondition.h>
#include <THSensorCondition.h>
#include <MotionSensorCondition.h>
#include <TimerCondition.h>
#include <MyTimer.h>
#include <ConditionManager.h>
#include <ArduinoJson.h>

#define DHTPIN 4
#define DHTTYPE DHT11
#define MOTIONSENSORPIN 14
#define timeSeconds 3
unsigned long now = millis();

unsigned long prevMillisDTH = 0;
unsigned long prevMillisMS = 0;
const long interval = 4000;
bool lastMotionSensorState=false;
const char* ssid = "AndroidAP0066";//"TP-LINK_D28A2E";
const char* password ="pdil7931";//"01413069";
const char* mqtt_server = "broker.hivemq.com";
PROGMEM const char* relay1Publish="ic/from/nm/relay1";
PROGMEM const char* relay2Publish="ic/from/nm/relay2";
#define mqtt_port 1883
#define MQTT_USER "esp"
#define MQTT_PASSWORD "password"
#define MQTT_SERIAL_RECEIVER_CH "ic/from/nm/sd"
#define MQTT_SERIAL_PUBLISH_CH "lalal"
WiFiClient wifiClient;

PubSubClient client(wifiClient);
Relay* relay1=new Relay(16,relay1Publish);
Relay*   relay2=new Relay(5,relay2Publish);
THSensor*   thsensor=new THSensor(DHTPIN);
MotionSensor *motionSensor= new MotionSensor(MOTIONSENSORPIN);
MyTimer timer;
Condition* chain[6];

ConditionManager conditionManager;
void setup_wifi() {
  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  randomSeed(micros());
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}


void reconnect() {
  // Loop until we're reconnected
  Serial.println("recconect");
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "NodeMCUClient-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str(), MQTT_USER, MQTT_PASSWORD)) {
      Serial.println("connected");
      //Once connected, publish an announcement...
      client.publish("/ic/presence/nm/", "hello world");
      // ... and resubscribe
      client.subscribe("ic/from/nm/setRelay1");
      client.subscribe("ic/from/nm/setRelay2");
      client.subscribe("ic/from/nm/UpdateAll");
      client.subscribe("ic/from/nm/newScenario");
      client.subscribe("ic/from/nm/deleteScenario");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}


void callback(char* topic, byte *payload, unsigned long length) {
  Serial.println("-------new message from broker-----");
  Serial.print("channel:");
  Serial.println(topic);
  Serial.print("data:");
  Serial.write(payload, length);
  Serial.println();
  char message[length + 1];
  int PIN;
  for (int i = 0; i < length; i++)
  {
    message[i] = (char)payload[i];
  }
  message[length] = 0;
  if (strcmp(topic, "ic/from/nm/setRelay1") == 0)
  {
    relay1->callback(topic, message, length);
  }
  else if (strcmp(topic, "ic/from/nm/setRelay2") == 0)
  {
    relay2->callback(topic, message, length);
  }

  else if (strcmp(topic, "ic/from/nm/UpdateAll") == 0)
  {
 Serial.println(" =============================update   +++++++++++++++");
 publishRelayState(relay1);
    publishRelayState(relay2);
    readTemperature(true);
  }
  else if (strcmp(topic, "ic/from/nm/newScenario") == 0)
  {
    conditionManager.clear();
    deserializeConditions(message);
  }
  else if (strcmp(topic, "ic/from/nm/deleteScenario") == 0)
  {
    conditionManager.clear();
  }

}

char* getCharState(int state)
{
char* relaystate= new char[2];
    itoa(state, relaystate, 10);
  return relaystate;
}

void publishRelayState(Relay* relay)
{
char* state=getCharState(relay->getState());
const char* topic=relay->getPublishTopic();
 client.publish(topic,state);
    free(state);
}
void deserializeConditions(char* condition)
{
  
  StaticJsonDocument<1000> doc;
  
  DeserializationError error = deserializeJson(doc, condition);

  // Test if parsing succeeds.
  if (error) {
    Serial.println("deserializeJson() failed: ");
    Serial.println(error.c_str());
    return;
  }
  JsonArray array = doc["conditions"].as<JsonArray>();
  
  for(JsonVariant v : array)
  { const char* name=v["name"];
    if(strcmp(name,"relay1")==0)
    {
      int condition=v["condition"];
      conditionManager.addCondition(new RelayCondition(relay1,condition));
      Serial.print(name);Serial.print(".....condition: ");
      Serial.println(condition);
    }
    else if(strcmp(name,"relay2")==0)
    {
      int condition=v["condition"];
      conditionManager.addCondition(new RelayCondition(relay2,condition));
      Serial.print(name);Serial.print(".....condition: ");
      Serial.println(condition);
    }
    else if(strcmp(name,"thsensor")==0)
    {
      float temperature=v["temperature"];
      const char* conditionChar=v["char"];
      conditionManager.addCondition(new THSensorCondition(thsensor,temperature, conditionChar[0]));
    Serial.print(name);Serial.print(".....condition: ");
      Serial.print(temperature);
      Serial.println(conditionChar);
    }
    else if(strcmp(name,"motionSensor")==0)
    {
      int condition=v["condition"];
      conditionManager.addCondition(new MotionSensorCondition(motionSensor,condition));
      Serial.print(name);Serial.print(".....condition: ");
      Serial.println(condition);
    }
    else if(strcmp(name,"timer")==0)
    {
      unsigned long countTime=v["countTime"];
      bool latch=v["latch"].as<bool>();
      bool reverse=v["reverse"].as<bool>();
      conditionManager.addCondition(new TimerCondition(&timer,countTime,latch,reverse));
      Serial.print(name);Serial.print(".....condition: ");
      Serial.print(countTime);
      Serial.print("   ");
      Serial.println(latch);
    }
    
  }

  bool isRepeated=doc["repeated"];
  conditionManager.setRepeated(isRepeated);
}
void setup() {
  Serial.begin(115200);
  Serial.setTimeout(500);// Set time out for
  
motionSensor->begin();
//deserializeConditions("{\"conditions\":[{\"name\":\"thsensor\",\"temperature\":21.90,\"char\":\">\"},{\"name\":\"timer\", \"countTime\":\"10\",\"latch\":false},{\"name\":\"motionSensor\",\"condition\":\"1\"},{\"name\":\"timer\", \"countTime\":\"0\",\"latch\":true},{\"name\":\"relay2\",\"condition\":\"1\"},{\"name\":\"motionSensor\",\"condition\":\"1\"},{\"name\":\"relay2\",\"condition\":\"0\"}]}");

  setup_wifi();
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);
  reconnect();

}

char* readTemperature(bool force)
{
  char temperature[8];
  if (readAble(prevMillisDTH, 4000) || force)
  {
    dtostrf(thsensor->readTemperature(), 6, 2, temperature);
    client.publish("ic/from/nm/thermometer",temperature);
  }
  return temperature;
}

bool readAble(unsigned long prevMillis, unsigned long _interval)
{
  unsigned long currentMillis = millis();
  if (currentMillis - prevMillis >= _interval) {
    prevMillis = currentMillis;
    return true;
  }
  return false;
}

void sendMessageMotionDetected(char* message)
{
  client.publish("ic/from/nm/motionDetector", message);
  
  Serial.println("SendMotionDetected");
}
void loop() {
  delay(400);
  client.loop();
  motionSensor->loop();
  conditionManager.checkConditions();
  if(relay1->updateState())
  {
    publishRelayState(relay1);
  }
if(relay2->updateState())
  {
    publishRelayState(relay2);
  }
    readTemperature(false);
  if (motionSensor->motionDetected)
  {
    sendMessageMotionDetected("1");
    lastMotionSensorState=true;
  }
  else if(lastMotionSensorState)
  {
 sendMessageMotionDetected("0");
 lastMotionSensorState=false;
  }
  
}
