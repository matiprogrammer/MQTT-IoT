package android.example.com.mqttaplication.RowTypes;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;
import java.util.List;

public class Row  {
private List<String> subscribeTopics=new ArrayList<>();

    public Row() {}

    public List<String> getSubscribeTopics() {
        return subscribeTopics;
    }

    public void setSubscribeTopics(List<String> subscribeTopics) {
        this.subscribeTopics = subscribeTopics;
    }

    public void addSubscribeTopic(String topic)
    {
        subscribeTopics.add(topic);
    }
}
