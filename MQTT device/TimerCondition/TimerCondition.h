#ifndef TimerCondition_h
#define TimerCondition_h
#include "Arduino.h"
#include <Condition.h>
#include <MyTimer.h>

class TimerCondition :public Condition
{
	public:
	TimerCondition(MyTimer* timer, unsigned long secToElaspe, bool latch, bool isReversed);
	bool isConditionFulfilled(bool prevCondition);
	 char* getCondition();
	void reset();
	private:
	unsigned long _startSeconds,_secondsToElapse;
	MyTimer* _timer;
	bool _latch, _isLatched, _isReversed;
};


#endif