#ifndef MyTimer_h
#define MyTimer_h
#include "Arduino.h"
#include <Ticker.h>


class MyTimer
{
	public:
	MyTimer();
	unsigned long timeElapsed(unsigned long sec);
	unsigned long getSecondsElapsed();
	private:
	void updateSeconds();
	Ticker _ticker;
	unsigned long secondsElapsed;
};
#endif