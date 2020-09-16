#include "Arduino.h"
#include "Relay.h"

Relay::Relay(int pin)
{
  pinMode(pin, OUTPUT);
  _pin=pin;
  turnOff();
  _nextState=0;
}
Relay::Relay(int pin, const char* publishTopic): _publishTopic(publishTopic)
{
	pinMode(pin, OUTPUT);
	_pin=pin;
	turnOff();
	_nextState=0;

}
const char* Relay::getPublishTopic()
{
	return _publishTopic;
}
void Relay::turnOn()
{
  digitalWrite(_pin,LOW);
  _state=1;
  _nextState=_state;
}
void Relay::turnOff()
{
  digitalWrite(_pin,HIGH);
  _state=0;
   _nextState=_state;
}

void Relay::toggle()
{
	if(_state)
		_nextState=0;
	else
		_nextState=1;
}
int Relay::getState()
{
  return _state;
}
void Relay::callback(char* topic, char* message, unsigned int length)
{
	if(strcmp(message,"1")==0)
	{
		turnOn();
	}
	else{
		turnOff();
		}
}

void Relay::setNextState(int state)
{
	_nextState=state;
}
int Relay::getNextState()
{
		return _nextState;
}

void Relay::forceUpdateState(bool force)
{
	_forceUpdateState=force;
}

bool Relay::updateState()
{	if(_forceUpdateState)
	{
		_forceUpdateState=false;
	return true;
	}

	bool _result=false;
	if(_nextState!=_state)
	{
		_result=true;
	}
	if(_nextState==1)
		turnOn();
	else turnOff();
	
	return _result;
}