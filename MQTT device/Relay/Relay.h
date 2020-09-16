#ifndef Relay_h
#define Relay_h

#include "Arduino.h"
#include <Helper.h>
#include <Base.h>

class Relay 
{
  public:
    Relay(int pin);
	Relay(int pin, const char* publishTopic);
    void turnOn();
    void turnOff();
	void toggle();
    int getState();
	void callback(char* topic, char* message, unsigned int length);
	void setNextState(int state);
	int getNextState();
	bool updateState();
	const char* getPublishTopic();
	void forceUpdateState(bool force);
  private:
    int _pin;
    int _state, _nextState;
	char* _subscribedTopic;
	const char* _publishTopic;
	bool _forceUpdateState=false;
	
  };
  
  #endif
