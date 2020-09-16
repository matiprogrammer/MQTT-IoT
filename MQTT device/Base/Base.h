#ifndef Base_h
#define Base_h
#include "Arduino.h"

class Base
{
  public:

 virtual bool isConditionFulfilled( bool prevCondition);
 virtual void setCondition(char* condition);
 char* getCondition();
 
 protected:
 char* _condition;
};

#endif
