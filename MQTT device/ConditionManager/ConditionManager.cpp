#include "ConditionManager.h"

ConditionManager::ConditionManager()
{
	size=0;
}
void ConditionManager::addCondition(Condition* condition)
{
	conditions[size++]=condition;
	
}
void ConditionManager::checkConditions()
{
	bool effect=true;
 for(int i=0;i<size;i++)
 {
    Serial.print("Warunek: ");
  conditions[i]->getCondition();
  effect=conditions[i]->isConditionFulfilled(effect);


  if(effect)
  Serial.println(" -true ");
  else 
 {
  Serial.println(" -false ");
 }

 }
 if(effect && _isRepeated&& size>0)
 {Serial.println("RESETUJEEEE!!!!!!!!!!!!!!11");
	 for(int i=0;i<size;i++)
	 {
		 conditions[i]->reset();
	 }
 }
Serial.println("");
Serial.println("");
}

void ConditionManager::clear()
{
	for(int i=0;i<=size;i++)
		conditions[i]=NULL;
	size=0;
}

void ConditionManager::setRepeated(bool isRepeated)
{
	_isRepeated=isRepeated;
}

