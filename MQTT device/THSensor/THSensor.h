#ifndef THSensor_h
#define THSensor_h

#include "Arduino.h"
#include <DHT.h>
#include <Adafruit_Sensor.h>
#include <Base.h>
#include <Helper.h>

#define DHTTYPE DHT11 
class THSensor 
{
  public:
    THSensor(int pin);
    float readTemperature();
    float readHumidity();
	bool readAble();
	float getTemperature();

  private:
  float temperature,humidity;
  int _pin;
  DHT* dht;
  unsigned long prevMillis;
  const long interval=400;

};

#endif
