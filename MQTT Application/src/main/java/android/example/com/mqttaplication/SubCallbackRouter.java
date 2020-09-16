package android.example.com.mqttaplication;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class SubCallbackRouter implements MqttCallback {


    public SubCallbackRouter() {
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


        for (BasePresenter presenter: PresentersDatabase.getInstance().getAllPresenters().values()) {
            List<String> subscribedTopics = presenter.getSubscribedTopics();
            for(String subscribedTopic :subscribedTopics)
            if (isMatched(subscribedTopic, topic)) {
                presenter.messageArrived(topic, message);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    private boolean isMatched(String topicFilter, String topic) {
        if (topicFilter.startsWith("$queue/")) {
            topicFilter = topicFilter.replaceFirst("\\$queue/", "");
        } else if (topicFilter.startsWith("$share/")) {
            topicFilter = topicFilter.replaceFirst("\\$share/", "");
            topicFilter = topicFilter.substring(topicFilter.indexOf('/'));
        }
        return topicFilter.equals(topic);
    }
}
