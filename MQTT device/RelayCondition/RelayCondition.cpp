#include "RelayCondition.h"


RelayCondition::RelayCondition(Relay *relay, int conditionState)
{
	_relay=relay;
	_conditionState=conditionState;
	
}

bool RelayCondition::isConditionFulfilled(bool prevCondition)
{
	if(prevCondition)
	{
		if(_relay->getNextState()!=_conditionState)
		{
			_relay->setNextState(_conditionState);		
		}
		return true;
	}
	
		
		return false;
}
char* RelayCondition::getCondition()
{
	Serial.print("Relay condition: ");
	Serial.print(_conditionState);
	return "";
}

void RelayCondition::reset()
{_relay->turnOff();
	_relay->forceUpdateState(true);
	
}