package android.example.com.mqttaplication.Relay;

import android.example.com.mqttaplication.BasePresenter;
import android.example.com.mqttaplication.PresentersDatabase;
import android.example.com.mqttaplication.RowTypes.RelayRowType;
import android.example.com.mqttaplication.SingletonMqttClient;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RelayPresenter extends BasePresenter<RelayRowType, RelayView> {



    @Override
    protected void updateView() {

    }

    @Override
    public void initialize() {
        RelayRowType model = getModel();

        view().setRelayName(model.getRelayName());

        final MqttAndroidClient mqttAndroidClient = SingletonMqttClient.getInstance();

        IMqttToken subTokenRelay1 = null;
        try {
            subTokenRelay1 = mqttAndroidClient.subscribe(getModel().getChangeStateTopic(), 1);
            subTokenRelay1.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBindViewHolder() {
        RelayRowType model = getModel();
        view().setRelayName(model.getRelayName());
        view().setSwitchChecked(model.isChecked());
    }
////
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String subTopic = getModel().getChangeStateTopic();
        if (topic.equals(subTopic)) ;
        {   //view().disableOnCheckedChangeListener();
            getModel().setChecked(new String(message.getPayload(), StandardCharsets.UTF_8).equals("1"));
            view().setSwitchChecked(getModel().isChecked());
            //view().enableOnCheckedChangeListener();
        }
    }

    @Override
    public List<String> getSubscribedTopics() {
        return getModel().getSubscribeTopics();
    }

    public void onSwitchCheckedChanged(boolean isChecked) {
        RelayRowType model=getModel();
        model.setChecked(isChecked);
        try {
            SingletonMqttClient.getInstance().publish("ic/from/nm/setRelay"+(model.getRelayNumber()),new MqttMessage((isChecked?"1":"0").getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

}
