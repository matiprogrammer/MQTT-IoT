package android.example.com.mqttaplication;

import android.app.Activity;
import android.content.Intent;
import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.Conditions.ConditionDragListener;
import android.example.com.mqttaplication.Conditions.ConditionsViewAdapter;
import android.example.com.mqttaplication.Conditions.DeleteDragListener;
import android.example.com.mqttaplication.Conditions.MyTouchListener;
import android.example.com.mqttaplication.Condition.Scenario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class NewScenarioActivity extends AppCompatActivity  {


    MyTouchListener touchListener;
    ConditionDragListener dragListener;
    CheckBox repeatCheckBox;
    EditText scenarioName;
    ImageView clearContainer;
    ConditionsViewAdapter viewAdapter;
    private List<Condition> conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_scenario);

        repeatCheckBox=findViewById(R.id.repeat_check_box);
        scenarioName=findViewById(R.id.name_scenario);
        clearContainer= findViewById(R.id.clear_container);
        RecyclerView recyclerView = findViewById(R.id.downLayout);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());

        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);

        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        dragListener= new ConditionDragListener(this);
         viewAdapter = new ConditionsViewAdapter(this.initViewItemDtoList(),dragListener, this,clearContainer);
        recyclerView.setAdapter(viewAdapter);
        dragListener.setAdapter(viewAdapter);
        touchListener= new MyTouchListener();

       clearContainer.setOnDragListener(new DeleteDragListener(viewAdapter, clearContainer));
       clearContainer.setVisibility(View.INVISIBLE);
        findViewById(R.id.relay).setOnTouchListener(touchListener);
        findViewById(R.id.tsensor).setOnTouchListener(touchListener);
        findViewById(R.id.timer).setOnTouchListener(touchListener);
        findViewById(R.id.motion_sensor).setOnTouchListener(touchListener);

        FloatingActionButton checkButton= findViewById(R.id.check_button);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent= new Intent();
                viewAdapter.getList().remove(viewAdapter.getList().size()-1);
                returnIntent.putExtra("Scenario",new Scenario(viewAdapter.getList(),repeatCheckBox.isChecked(),scenarioName.getText().toString()));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }


    private List<Condition> initViewItemDtoList()
    {
        List<Condition> ret = new ArrayList<>();
        ret.add(new Condition());

        return ret;
    }


}
