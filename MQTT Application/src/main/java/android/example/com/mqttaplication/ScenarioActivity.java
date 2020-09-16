package android.example.com.mqttaplication;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.Conditions.ConditionsViewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class ScenarioActivity extends AppCompatActivity {

    CheckBox repeatCheckBox;
    TextView scenarioName;
    ConditionsViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);

        repeatCheckBox=findViewById(R.id.repeat_check_box);
        scenarioName=findViewById(R.id.name_scenario);

        RecyclerView recyclerView = findViewById(R.id.downLayout);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());

        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);

        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        Bundle extras = getIntent().getExtras();
        Scenario scenario= extras.getParcelable(Scenario.SCENARIO);
        if(scenario!=null)
        {
            viewAdapter = new ConditionsViewAdapter(scenario.getConditionList(),this,false);
            repeatCheckBox.setChecked(scenario.isRepeated());
            scenarioName.setText(scenario.getName());
            repeatCheckBox.setEnabled(false);
        }

        recyclerView.setAdapter(viewAdapter);
    }
}
