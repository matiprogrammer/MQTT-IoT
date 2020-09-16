#include "MyTimer.h"

MyTimer::MyTimer()
{
	secondsElapsed=0;
	_ticker.attach(1,std::bind(&MyTimer::updateSeconds, this));
}

unsigned long MyTimer::timeElapsed(unsigned long sec)
{
	return secondsElapsed-sec;
}

void MyTimer::updateSeconds()
{
	secondsElapsed++;
}

unsigned long MyTimer::getSecondsElapsed()
{
	return secondsElapsed;
}