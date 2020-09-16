package android.example.com.mqttaplication.Conditions;

import android.content.Context;
import android.example.com.mqttaplication.R;
import android.example.com.mqttaplication.Relay.RelayCondition;
import android.example.com.mqttaplication.Thermometer.ThermometerCondition;
import android.example.com.mqttaplication.Timer.TimerCondition;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class ConditionsViewHolder extends RecyclerView.ViewHolder {


    public ImageView getImage() {
        return image;
    }

    public void setImage(int id) {
        this.image.setImageResource(id);
    }

    private ImageView image;
    private Context context;
private boolean isEditable;
    public ConditionsViewHolder(@NonNull View itemView, Context context, boolean isEditable) {
        super(itemView);
        if(itemView!=null) {
            this.image = itemView.findViewById(R.id.condition_container);
        }
        this.context=context;
        this.isEditable=isEditable;
    }

    public void setClickListener(final Condition condition)
    {
         image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDialog(condition);
             }
         });
    }

    public void showDialog(Condition condition)
    {

        if(condition instanceof RelayCondition)
            DialogFragment.showRelayDialog((RelayCondition)condition, context, isEditable);
        else if(condition instanceof TimerCondition)
            DialogFragment.showTimerDialog((TimerCondition) condition, context,isEditable);
        else if(condition instanceof ThermometerCondition)
            DialogFragment.showThermometerCondition((ThermometerCondition)condition, context,isEditable);
    }
}
