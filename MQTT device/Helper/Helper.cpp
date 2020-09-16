#include "Helper.h"

bool contains(const char* array1, int length1, const char* array2, int length2)
{
  int same=0;
  for(int i=0;i<length1;i++)
  {
    for(int j=0;j<length2;j++)
    {
      if(*(array1+i+j)==*(array2+j))
      {
        same++;
        if(same==length2)
        {
            
          return true;
        }
      }
      else
      { 
        same=0;
        break;
      }
    }
  }

  if(same==length2)
    return true;
  else
    return false;
}




float getFloatNumber(const char* array1, int length1)
{
  char numberArray[20];
  int pointer=0;
  for(int i=0;i<length1;i++)
    {    
        if(contains("0123456789.,",12,(array1+i),1))
        {
        numberArray[pointer++]=*(array1+i);
        }
    }
    return atof(numberArray);
}
