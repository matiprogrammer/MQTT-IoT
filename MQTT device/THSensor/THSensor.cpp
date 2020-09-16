#include "THSensor.h"


THSensor::THSensor(int pin)
{
	prevMillis=0;

  dht=new DHT(pin,DHTTYPE);
dht->begin();
  _pin=pin;
  	temperature=0;
  
}

float THSensor::readTemperature()
{

		float newT=dht->readTemperature();
    if (isnan(newT)) {
      Serial.println("Failed to read from DHT sensor!");
	  temperature=1234;
	  
    }
	 else {
      temperature = newT;
      Serial.print("temperatura: ");
      Serial.println(temperature);
    }
	return temperature;  
}
float THSensor::readHumidity()
{
  humidity= dht->readHumidity();
  return humidity;
}

bool THSensor::readAble()
{
	unsigned long currentMillis = millis();
	if (currentMillis - prevMillis >= interval) {
    prevMillis = currentMillis;
	return true;
	}
	return false;
}

float THSensor::getTemperature()
{
	return temperature;
}
