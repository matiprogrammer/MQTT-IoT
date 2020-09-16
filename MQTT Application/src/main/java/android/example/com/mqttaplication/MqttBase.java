package android.example.com.mqttaplication;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttBase {

    MqttAndroidClient mqttClient;
    public MqttBase() {
        mqttClient= SingletonMqttClient.getInstance();
    }

    public void mqttPublish(String topic,String message)
    {
        try {
            mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public IMqttToken mqttSubscribe(String topic, int qos)
    {
        try {
            final IMqttToken subToken = SingletonMqttClient.getInstance().subscribe(topic, qos);

            return subToken;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
    }
}
