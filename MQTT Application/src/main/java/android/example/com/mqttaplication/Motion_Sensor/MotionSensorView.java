package android.example.com.mqttaplication.Motion_Sensor;

import android.example.com.mqttaplication.MotionSensorListener;

public interface MotionSensorView {
    MotionSensorListener getMotionListener();
void setName(String name);
void setDetectionImage();
void setNormalImage();
}
