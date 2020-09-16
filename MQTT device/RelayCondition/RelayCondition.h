#ifndef RelayCondition_h
#define RelayCondition_h
#include "Arduino.h"
#include <Condition.h>
#include <Relay.h>

class RelayCondition :public Condition
{
	public:
	RelayCondition(Relay* relay,  int conditionState);
	bool isConditionFulfilled(bool prevCondition);
	char* getCondition();
	void reset();
	private:
	Relay* _relay;
	int _conditionState;
};
#endif