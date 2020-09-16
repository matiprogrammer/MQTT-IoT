package android.example.com.mqttaplication.Condition;

import android.example.com.mqttaplication.ScenerioSwitched;

public interface ScenarioView {
     ScenerioSwitched getSwitchListener();
    void setName(String name);
    void setSwitch(boolean state);
    void onItemClick(Scenario data);
}
