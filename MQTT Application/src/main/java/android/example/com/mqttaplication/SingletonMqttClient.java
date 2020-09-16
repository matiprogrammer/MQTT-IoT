package android.example.com.mqttaplication;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class SingletonMqttClient {

    private static MqttAndroidClient mqttAndroidClient;
    private static final String serverURI="tcp://broker.hivemq.com:1883";
    private static Context context;
    public SingletonMqttClient( Context context) {
        this.context=context;
    }

    public static synchronized MqttAndroidClient getInstance()
    {
        if(mqttAndroidClient==null)
        {
            String clientId = MqttClient.generateClientId();
            mqttAndroidClient =
                    new MqttAndroidClient(context, serverURI, clientId);
        }

            return mqttAndroidClient;
    }
}
