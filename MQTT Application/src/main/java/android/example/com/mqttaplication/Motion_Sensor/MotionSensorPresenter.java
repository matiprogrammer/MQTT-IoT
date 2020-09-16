package android.example.com.mqttaplication.Motion_Sensor;

import android.example.com.mqttaplication.BasePresenter;
import android.example.com.mqttaplication.RowTypes.MotionSensorRowType;
import android.example.com.mqttaplication.RowTypes.ThermometerRowType;
import android.example.com.mqttaplication.SingletonMqttClient;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.util.List;

public class MotionSensorPresenter extends BasePresenter<MotionSensorRowType,MotionSensorView> {


    @Override
    protected void updateView() {
        final MotionSensorRowType model = getModel();
        view().setName(model.getName());
    }
    @Override
    public void initialize() {
        final MotionSensorRowType model = getModel();
        view().setName(model.getName());

        final MqttAndroidClient mqttAndroidClient = SingletonMqttClient.getInstance();

        IMqttToken subToken = null;
        try {

            mqttAndroidClient.subscribe(getModel().getUpdateValueTopic(), 1,null,new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBindViewHolder() {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String subTopic = getModel().getUpdateValueTopic();

        if (topic.equals(subTopic)) ;
        {
            if (new String(message.getPayload()).equals("1")) {
                view().setDetectionImage();
                view().getMotionListener().onMotionDetected();
            }
            else
                view().setNormalImage();
        }
    }

    @Override
    public List<String> getSubscribedTopics() {
        return getModel().getSubscribeTopics();
    }


}
