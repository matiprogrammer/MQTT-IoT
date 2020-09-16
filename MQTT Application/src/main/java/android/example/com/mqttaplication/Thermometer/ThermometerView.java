package android.example.com.mqttaplication.Thermometer;

public interface ThermometerView {
    void updateValue(String newValue);
    void setName(String name);
}
