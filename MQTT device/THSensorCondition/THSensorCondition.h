#ifndef THSensorCondition_h
#define THSensorCondition_h
#include "Arduino.h"
#include "THsensor.h"
#include "Condition.h"

class THSensorCondition :public Condition
{
	
	public:
		THSensorCondition(THSensor* thsensor, float conditionTemperature, char conditionChar);
		bool isConditionFulfilled(bool prevCondition);
		char* getCondition();
	
	private:
		THSensor* _thsensor;
		float _conditionTemperature;
		char _conditionChar;

};
#endif