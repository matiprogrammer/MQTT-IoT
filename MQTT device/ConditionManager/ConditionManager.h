#ifndef ConditionManager_h
#define ConditionManager_h
#include "Arduino.h"
#include <Condition.h>
#include <RelayCondition.h>
#include <THSensorCondition.h>
#include <MotionSensorCondition.h>
#include <TimerCondition.h>
#include <MyTimer.h>


class ConditionManager
{
	public:
	ConditionManager();
	void addCondition(Condition* condition);
	void checkConditions();
	void clear();
	void setRepeated(bool isRepeated);
	private:
	const char* _condition;
	int size;
	Condition* conditions[30];
	bool _isRepeated=false;
	
};
#endif
