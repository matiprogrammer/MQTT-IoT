package android.example.com.mqttaplication.Conditions;

import android.content.ClipData;
import android.content.Context;
import android.example.com.mqttaplication.Motion_Sensor.MotionSensorCondition;
import android.example.com.mqttaplication.Relay.RelayCondition;
import android.example.com.mqttaplication.Thermometer.ThermometerCondition;
import android.example.com.mqttaplication.Timer.TimerCondition;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;

public class ConditionDragListener implements View.OnDragListener {

    private ArrayList<Condition> conditions;
    int order=0;
    private Context context;
    ConditionsViewAdapter conditionsViewAdapter;

    public ConditionDragListener(Context context) {
        this.context=context;


    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                if(event.getClipDescription().getLabel().toString().equals(DeleteLongClickListener.TAG))
                    return false;
                break;
            case DragEvent.ACTION_DRAG_ENTERED:

                break;
            case DragEvent.ACTION_DRAG_EXITED:

                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View draggedView = (View) event.getLocalState();
                draggedView.getId();
                ClipData.Item item = event.getClipData().getItemAt(0);
                String  conditionKind=item.getText().toString();

                if(conditions==null)
                    conditions= new ArrayList<>();

                switch (conditionKind)
                {
                    case "relay":
                        final RelayCondition relayCondition= new RelayCondition("relay1",false);
                        addOrReplaceItem(relayCondition,v);
                        DialogFragment.showRelayDialog(relayCondition,context, true);


                        break;
                    case "timer":
                        final TimerCondition timerCondition=new TimerCondition(0);
                        addOrReplaceItem(timerCondition,v);
                        DialogFragment.showTimerDialog(timerCondition,context,true);

                        break;
                    case "tsensor":
                        final ThermometerCondition thermometerCondition= new ThermometerCondition(0);
                        addOrReplaceItem(thermometerCondition,v);
                        DialogFragment.showThermometerCondition(thermometerCondition,context,true);

                        break;
                    case "motion_sensor":
                        MotionSensorCondition motionSensorCondition= new MotionSensorCondition(0);
                        addOrReplaceItem(motionSensorCondition,v);
                        break;

                        default:
                            return false;
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:


            default:
                break;
        }
        return true;
    }

    public void addConditionContainer()
    {
        conditionsViewAdapter.addItem(new Condition());
    }

    public void setAdapter(ConditionsViewAdapter conditionsViewAdapter)
    {
        this.conditionsViewAdapter=conditionsViewAdapter;
    }

    public void addOrReplaceItem(Condition condition, View v)
    {

        if(conditionsViewAdapter.existing.contains(v.getId())) {
            int position=conditionsViewAdapter.existing.indexOf(v.getId());
            conditionsViewAdapter.insertItem(position, condition);
            v.setId(v.getId());
        }
        else {
            conditionsViewAdapter.insertItem(conditionsViewAdapter.getItemCount()-1,condition);
            conditionsViewAdapter.existing.add(v.getId());
            v.setId(v.getId());
            addConditionContainer();
        }
    }

}
