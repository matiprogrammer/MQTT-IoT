package android.example.com.mqttaplication;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.database.ScenarioRepository;

import androidx.annotation.NonNull;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class BasePresenter<M, V> extends MqttBase{
    protected M model;
    private WeakReference<V> view;


public static ScenarioRepository repository;
public static void setRepository(ScenarioRepository repository)
{
    BasePresenter.repository=repository;
}
    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }
    public abstract void initialize();
    public abstract void onBindViewHolder();
    public abstract void messageArrived(String topic, MqttMessage message);
    public abstract List<String> getSubscribedTopics();

    protected void resetState() {
    }

    public M getModel(){return model;}

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
        if (setupDone()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return view() != null && model != null;
    }
}
