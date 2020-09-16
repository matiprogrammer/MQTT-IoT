package android.example.com.mqttaplication.Condition;

import android.example.com.mqttaplication.BasePresenter;
import android.example.com.mqttaplication.RowTypes.ScenarioRowType;
import android.example.com.mqttaplication.SingletonMqttClient;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

public class ScenarioPresenter extends BasePresenter<ScenarioRowType, ScenarioView> {
    @Override
    public void initialize() {
        ScenarioRowType model=getModel();
        view().setName(model.getName());
    }

    @Override
    public void onBindViewHolder() {
        ScenarioRowType model=getModel();
        view().setName(model.getName());
        view().setSwitch(model.getChecked());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {

    }

    @Override
    public List<String> getSubscribedTopics() {
        return getModel().getSubscribeTopics();
    }

    @Override
    protected void updateView() {

    }

    public void onClickItem()
    {
        view().onItemClick(model.getScenario());
    }
    public void onSwitchCheckedChanged(boolean isChecked)
    {
        ScenarioRowType model=getModel();


        try {
            if((isChecked)&&!model.getChecked()) {

                SingletonMqttClient.getInstance().publish("ic/from/nm/newScenario", model.getScenario().getScenarioJSON().toString().getBytes(),1,true);
            }
            else if(!isChecked && model.getChecked())
                SingletonMqttClient.getInstance().publish("ic/from/nm/newScenario",new byte[]{},1,true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if(isChecked!=model.getChecked()) {
            model.setChecked(isChecked);
            view().getSwitchListener().onScenarioSwiteched(getModel().getScenario(),isChecked);
        }


    }

}
