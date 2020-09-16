#include "MotionSensor.h"

 ICACHE_RAM_ATTR void MotionSensor::detectsMovement() {
  Serial.println("MOTION DETECTED!!!");
  motionDetected = true;
  startTimer = true;
  lastTrigger = millis();
}

bool MotionSensor::isDetected()
{
	return motionDetected;
}

MotionSensor::MotionSensor(int pin)
{
    pinMode(pin, INPUT_PULLUP);
    _pin=pin;
	
}
void MotionSensor::begin()
{
	instance=this;
    attachInterrupt(digitalPinToInterrupt(_pin), ISRFunc, RISING);
}

void MotionSensor::loop()
{
	unsigned long now = millis();
  
  if (startTimer && (now - lastTrigger > (800))) {
    Serial.println("Motion stopped...");
    motionDetected = false;
    startTimer = false;
    
  }
}

 ICACHE_RAM_ATTR static void ISRFunc(void)
{
	instance->detectsMovement();
}



