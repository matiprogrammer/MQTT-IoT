package android.example.com.mqttaplication.Conditions;

import android.content.ClipData;
import android.example.com.mqttaplication.Motion_Sensor.MotionSensorCondition;
import android.example.com.mqttaplication.Relay.RelayCondition;
import android.example.com.mqttaplication.Thermometer.ThermometerCondition;
import android.example.com.mqttaplication.Timer.TimerCondition;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class DeleteDragListener implements View.OnDragListener {
    private ConditionsViewAdapter conditionsViewAdapter;
    private ImageView imageContainer;
    public DeleteDragListener(ConditionsViewAdapter conditionsViewAdapter, ImageView imageContainer) {
        this.conditionsViewAdapter=conditionsViewAdapter;
        this.imageContainer=imageContainer;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                if(event.getClipDescription().getLabel().toString().equals(DeleteLongClickListener.TAG)) {
                    imageContainer.setVisibility(View.VISIBLE);
                    return true;
                }
                else
                    return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                break;
            case DragEvent.ACTION_DRAG_EXITED:

                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View draggedView = (View) event.getLocalState();
                ClipData.Item item = event.getClipData().getItemAt(0);
                String  conditionKind=item.getText().toString();
                int position=Integer.parseInt(event.getClipData().getItemAt(1).getText().toString());
            conditionsViewAdapter.deleteItem(position);
                imageContainer.setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                imageContainer.setVisibility(View.INVISIBLE);

            default:
                break;
        }
        return true;
    }
}
