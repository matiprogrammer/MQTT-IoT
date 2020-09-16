#include "MotionSensorCondition.h"

MotionSensorCondition::MotionSensorCondition(MotionSensor* motionSensor, int condition) :_condition(condition)
{
	_motionSensor=motionSensor;
}
bool MotionSensorCondition::isConditionFulfilled(bool prevCondition)
{
	if(prevCondition)
		if( _condition==1)
		{	
			return _motionSensor->isDetected();
		}
		else if( _condition==0)
			return !_motionSensor->isDetected();
	return false;
}

 char* MotionSensorCondition::getCondition()
{	
Serial.print("Motion sensor condition: ");
Serial.print(_condition);
	return "";
}


