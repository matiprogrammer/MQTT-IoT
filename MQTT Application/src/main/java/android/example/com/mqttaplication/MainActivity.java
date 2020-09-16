package android.example.com.mqttaplication;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.RowTypes.MotionSensorRowType;
import android.example.com.mqttaplication.RowTypes.RelayRowType;
import android.example.com.mqttaplication.RowTypes.RowType;
import android.example.com.mqttaplication.RowTypes.ScenarioRowType;
import android.example.com.mqttaplication.RowTypes.ThermometerRowType;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.example.com.mqttaplication.database.ScenarioRepository;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ScenerioSwitched, MotionSensorListener{

    String CHANNEL_ID="onMoveNotification";
    int SCENARIO_ACTIVITY=1;
    private ArrayList<RowType> elementsList;
    private MultipleTypesAdapter multipleTypesAdapter;
    private static final String serverURI="tcp://broker.hivemq.com:1883";
    public boolean isConnected=false;
     MqttAndroidClient mqttClient;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    private ScenarioViewModel scenarioViewModel;
    NotificationCompat.Builder builder;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Scenario scenario;
        if(requestCode==SCENARIO_ACTIVITY)
            if(resultCode== Activity.RESULT_OK)
            {
                 scenario=data.getParcelableExtra("Scenario");
                 String name=scenario.getName();
                //multipleTypesAdapter.notifyDataSetChanged();
                 scenarioViewModel.insert(scenario);
            }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab= findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, NewScenarioActivity.class);
                startActivityForResult(intent,SCENARIO_ACTIVITY);
            }
        });
        createNotificationChannel();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

         builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.move)
                .setContentTitle("MQTT App")
                .setContentText("Wykryto ruch!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);



        elementsList=new ArrayList<>();
          SingletonMqttClient singletonMqttClient= new SingletonMqttClient(this);
         mqttClient= SingletonMqttClient.getInstance();

         recyclerView = findViewById(R.id.recycled_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        BasePresenter.setRepository(new ScenarioRepository(getApplication()));
        multipleTypesAdapter= new MultipleTypesAdapter(elementsList);
        recyclerView.setAdapter(multipleTypesAdapter);
        elementsList.add(new RelayRowType(1, "Przekaźnik 1"));
        elementsList.add(new RelayRowType(2, "Przekaźnik 2"));
        elementsList.add(new ThermometerRowType("Temperatura"));
        elementsList.add(new MotionSensorRowType("Czujnik ruchu"));

        scenarioViewModel= new ViewModelProvider(this).get(ScenarioViewModel.class);
        enableSwipeToDeleteAndUndo();


        MqttConnectOptions options = new MqttConnectOptions();
        options.setKeepAliveInterval(300);
        options.setCleanSession(false);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setAutomaticReconnect(true);

        if(mqttClient.isConnected())
        {
            initialize();
        }
        try {
            if(!mqttClient.isConnected()) {
                IMqttToken token = mqttClient.connect(options);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        initialize();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    }
                });
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private void initialize()
    {

        scenarioViewModel.getAllScenariosRows().observe(MainActivity.this, new Observer<List<RowType>>() {

            @Override
            public void onChanged(List<RowType> scenarioRowTypes) {
                multipleTypesAdapter.setData(scenarioRowTypes);

            }
        });
        for (int i = 0; i < elementsList.size(); i++)
            PresentersDatabase.getInstance().getPresenter(elementsList.get(i)).initialize();
        SubCallbackRouter subCallbackRouter = new SubCallbackRouter();
        mqttClient.setCallback(subCallbackRouter);
        try {
            mqttClient.publish("ic/from/nm/UpdateAll", new MqttMessage("update".getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                if(position>3) {
                    scenarioViewModel.delete(((ScenarioRowType) multipleTypesAdapter.getData().get(position)).getScenario());
                    multipleTypesAdapter.remove(position);
                }

            }
        };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttClient.unregisterResources();
        mqttClient.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mqttClient.registerResources(this);
    }

    @Override
    public void onScenarioSwiteched(Scenario scenario, boolean isChecked) {
        List<Scenario> scenarioRows=multipleTypesAdapter.getScenarioRows();
        for(Scenario s :scenarioRows)
        {
            if(s!=scenario)
            if(s.getEnabled())
                s.setEnabled(false);
            scenarioViewModel.update(s);
        }
        scenario.setEnabled(isChecked);
        scenarioViewModel.update(scenario);
    }


    @Override
    public void onMotionDetected() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
    }
}
