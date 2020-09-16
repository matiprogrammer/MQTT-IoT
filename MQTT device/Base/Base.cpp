#include "Base.h"


 bool Base::isConditionFulfilled(bool prevCondition)
{
  return false;
}

 void Base::setCondition(char* condition)
 {
	 _condition=condition;
	 Serial.println("jstem w base");
 }
 
 char* Base::getCondition()
 {
	 return _condition;
 }