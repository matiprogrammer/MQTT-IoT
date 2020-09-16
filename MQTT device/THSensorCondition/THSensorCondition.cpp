#include "THSensorCondition.h"

	THSensorCondition::THSensorCondition(THSensor* thsensor, float conditionTemperature, char conditionChar) : _conditionTemperature(conditionTemperature), _conditionChar(conditionChar)
	{
		_thsensor=thsensor;
	}
	
	bool THSensorCondition::isConditionFulfilled(bool prevCondition)
	{
		if(prevCondition)
	//readTemperature();
	switch(_conditionChar)
	{
		case '>':
		if(_thsensor->getTemperature()>_conditionTemperature)
			return true;
		break;
		
		case '<':
		if(_thsensor->getTemperature()<_conditionTemperature)
			return true;
		break;
		
		case '=':
		if(_thsensor->getTemperature()>_conditionTemperature-0.5 && _thsensor->getTemperature()<_conditionTemperature+0.5)
			return true;
		break;
		default: return false;
	}
	
	return false;
	}
	
	char* THSensorCondition::getCondition()
	{
		Serial.print("tempereture ");
		Serial.print(_conditionChar);
		Serial.print(" ");
		Serial.print(_conditionTemperature);
	return "";
	}
