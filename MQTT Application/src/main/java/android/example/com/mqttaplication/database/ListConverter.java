package android.example.com.mqttaplication.database;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.Motion_Sensor.MotionSensorCondition;
import android.example.com.mqttaplication.Relay.RelayCondition;
import android.example.com.mqttaplication.Thermometer.ThermometerCondition;
import android.example.com.mqttaplication.Timer.TimerCondition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class ListConverter {
    @TypeConverter
    public static List<Condition> fromString(String conditionsString) {
        List<Condition> list= new ArrayList<>();
        try {
            JSONObject json = new JSONObject(conditionsString);
            JSONArray conditionsArray=json.getJSONArray("conditions");
            for(int i=0;i<conditionsArray.length();i++)
            {
                JSONObject condition=conditionsArray.getJSONObject(i);
                switch (condition.getString(Condition.NAME))
                {
                    case "motionSensor":
                        list.add(MotionSensorCondition.getObjectFromJson(condition));
                        break;

                    case "relay1":
                    case "relay2":
                        list.add((RelayCondition.getObjectFromJson(condition)));
                        break;
                    case"thsensor":
                        list.add(ThermometerCondition.getObjectFromJson(condition));
                        break;
                    case "timer":
                        list.add(TimerCondition.getObjectFromJson(condition));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @TypeConverter
    public static String ToString(List<Condition> conditions) {
        JSONObject json=new JSONObject();
        JSONArray conditionArray=new JSONArray();
        for (Condition condition: conditions) {
            conditionArray.put(condition.getJSONObject());
        }
        try {
            json.put("conditions",conditionArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
