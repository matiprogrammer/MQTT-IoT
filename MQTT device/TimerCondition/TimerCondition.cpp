#include "TimerCondition.h"

TimerCondition::TimerCondition(MyTimer* timer,unsigned long secToElaspe, bool latch, bool isReversed):_isReversed(isReversed), _timer(timer),_latch(latch)
{
	_startSeconds=_timer->timeElapsed(0);
	_secondsToElapse=secToElaspe;
	_isLatched=false;
}
bool TimerCondition::isConditionFulfilled(bool prevCondition)
{ 

	if(prevCondition && _latch)
		_isLatched=true;
	
	if(!prevCondition && !_latch)
		_startSeconds=_timer->timeElapsed(0);
	
	if(!prevCondition && !_isLatched)
	_startSeconds=_timer->timeElapsed(0);

int secondsElapsed=_timer->timeElapsed(_startSeconds);

	Serial.print("        czas uplynal.......................................");
Serial.println(secondsElapsed);
Serial.print("        startSeconds.......................................");
Serial.println(_startSeconds);

	if(prevCondition || _isLatched)
	{
		if(_isReversed)
		{
			if(secondsElapsed<_secondsToElapse	)
			return true;
		}
		else if(!_isReversed)
		{
			if(secondsElapsed>_secondsToElapse)
				return true;
		}
		return false;

		
	}
	

	return false;
}

void TimerCondition::reset()
{
	_isLatched=false;
	_startSeconds=_timer->timeElapsed(0);
}

 char* TimerCondition::getCondition()
 {
	 Serial.print("timer: ");
	 Serial.print(_secondsToElapse);
	 Serial.print("   latch: ");
	 Serial.print(_latch);
	return "sd";
 }
