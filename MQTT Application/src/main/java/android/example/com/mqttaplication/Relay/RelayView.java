package android.example.com.mqttaplication.Relay;

public interface RelayView {
    boolean getSwitchState();
    void enableOnCheckedChangeListener();
   void disableOnCheckedChangeListener();
    void setSwitchChecked(boolean value);
    void setRelayName(String name);
}
