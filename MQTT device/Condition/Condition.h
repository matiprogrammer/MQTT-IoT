#ifndef Condition_h
#define Condition_h
#include "Arduino.h"

class Condition
{
  public:
	Condition();
	virtual bool isConditionFulfilled(bool prevCondition);
	virtual  char* getCondition();
	virtual void reset();
 protected:
	
};

#endif
