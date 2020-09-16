#ifndef MotionSensorCondition_h
#define MotionSensorCondition_h
#include "Arduino.h"
#include <Condition.h>
#include <MotionSensor.h>

class MotionSensorCondition :public Condition
{
	public:
	MotionSensorCondition(MotionSensor* motionSensor, int condition);
	bool isConditionFulfilled(bool prevCondition);
	char* getCondition();
	private:
	MotionSensor* _motionSensor;
	int _condition;
	
};
#endif