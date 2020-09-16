#ifndef MotionSensor_h
#define MotionSensor_h
#include "Arduino.h"
#include <Base.h>

 class MotionSensor :public Base
{
  public:
  	ICACHE_RAM_ATTR void detectsMovement();
    MotionSensor(int pin);
	void begin();
    bool motionDetected = false;
    unsigned long lastTrigger = 0;
    boolean startTimer = false;
	bool isDetected();
	void loop();
  private:
    int _pin;
   
};
	static MotionSensor *instance;
		ICACHE_RAM_ATTR static void ISRFunc(void);
#endif
