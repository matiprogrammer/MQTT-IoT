package android.example.com.mqttaplication;

import android.content.Intent;
import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.Condition.ScenarioPresenter;
import android.example.com.mqttaplication.Condition.ScenarioView;
import android.example.com.mqttaplication.Motion_Sensor.MotionSensorPresenter;
import android.example.com.mqttaplication.Motion_Sensor.MotionSensorView;
import android.example.com.mqttaplication.Relay.RelayPresenter;
import android.example.com.mqttaplication.Relay.RelayView;
import android.example.com.mqttaplication.RowTypes.RowType;
import android.example.com.mqttaplication.Thermometer.ThermometerPresenter;
import android.example.com.mqttaplication.Thermometer.ThermometerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ViewHolderFactory {
    public static class ScenarioViewHolder extends MvpViewHolder<ScenarioPresenter> implements ScenarioView
    {
        public ScenerioSwitched switchedListener;
        private TextView textViewName;
        private Switch aSwitch;

        public ScenarioViewHolder(@NonNull final View itemView, ScenarioPresenter _presenter) {
            super(itemView);
            bindPresenter(_presenter);
            textViewName=itemView.findViewById(R.id.condition_name);
            aSwitch=itemView.findViewById(R.id.switch1);
            switchedListener=(ScenerioSwitched) itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickItem();

                }
            });

            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onSwitchCheckedChanged(isChecked);

                }
            });
        }


        @Override
        public ScenerioSwitched getSwitchListener() {
            return switchedListener;
        }

        @Override
        public void setName(String name) {
            textViewName.setText(name);
        }

        @Override
        public void setSwitch(boolean state) {
            aSwitch.setChecked(state);
        }

        @Override
        public void onItemClick(Scenario data) {
            Intent intent= new Intent(itemView.getContext(), ScenarioActivity.class);
            intent.putExtra(Scenario.SCENARIO,data);
            itemView.getContext().startActivity(intent);
        }

    }
    public static class RelayViewHolder extends MvpViewHolder<RelayPresenter> implements RelayView {

        public Switch aSwitch;
        public TextView relayName;
        private boolean listenerEnabled=true;

        public RelayViewHolder(@NonNull View itemView, RelayPresenter _presenter) {
            super(itemView);
            bindPresenter(_presenter);
            aSwitch =itemView.findViewById(R.id.element_switch);
            relayName =itemView.findViewById(R.id.element_name);

            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(listenerEnabled)
                    presenter.onSwitchCheckedChanged(isChecked);
                }
            });
        }
        @Override
        public void enableOnCheckedChangeListener()
        {
            listenerEnabled=true;
        }
        @Override
        public void disableOnCheckedChangeListener()
        {
            listenerEnabled=false;
        }
        @Override
        public boolean getSwitchState() {
           return aSwitch.isChecked();
        }

        @Override
        public void setSwitchChecked(boolean value) {
            aSwitch.setChecked(value);
        }

        @Override
        public void setRelayName(String name) {
            relayName.setText(name);
        }

    }

    public static class ThermometerViewHolder extends MvpViewHolder<ThermometerPresenter> implements ThermometerView {

        public TextView thermometerName;
        public TextView thermometerValue;
        public TextView temperatureUnit;
        public ThermometerViewHolder(@NonNull View itemView,ThermometerPresenter _presenter) {
            super(itemView);
            bindPresenter(_presenter);
            thermometerName= itemView.findViewById(R.id.element_name);
            thermometerValue= itemView.findViewById(R.id.thermometer_value);
            temperatureUnit=itemView.findViewById(R.id.temperature_unit);
            temperatureUnit.setText("\u2103");
        }

        @Override
        public void updateValue(String newValue) {
            thermometerValue.setText(newValue);
        }

        @Override
        public void setName(String name) {
            thermometerName.setText(name);
        }
    }

    public static class MotionSensorViewHolder extends MvpViewHolder<MotionSensorPresenter> implements MotionSensorView {
        TextView motionSensorName;
        ImageView motionSensorImage;
        private MotionSensorListener mListener;
        public MotionSensorViewHolder(@NonNull View itemView, MotionSensorPresenter _presenter) {
            super(itemView);
            bindPresenter(_presenter);
            motionSensorName=itemView.findViewById(R.id.element_name);
            motionSensorImage=itemView.findViewById(R.id.image_motion_sensor);
            mListener=(MotionSensorListener)itemView.getContext();
        }

        @Override
        public MotionSensorListener getMotionListener() {
            return this.mListener;
        }

        @Override
        public void setName(String name) {
            motionSensorName.setText(name);
        }

        @Override
        public void setDetectionImage() {
            int id = itemView.getResources().getIdentifier("@android:drawable/ic_notification_overlay", null, null);
            motionSensorImage.setImageResource(id);
        }

        @Override
        public void setNormalImage() {
            int id = itemView.getResources().getIdentifier("@android:drawable/presence_online", null, null);
            motionSensorImage.setImageResource(id);
        }
    }

    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {

        switch (viewType) {
            case RowType.RELAY_ROW_TYPE:
                View relayTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.relay_row_design, parent, false);
                RelayViewHolder relayViewHolder=new ViewHolderFactory.RelayViewHolder(relayTypeView,new RelayPresenter());

                return relayViewHolder;

            case RowType.THERMOMETER_ROW_TYPE:
                View thermometerTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.thermometer_row_design, parent, false);
                return new ViewHolderFactory.ThermometerViewHolder(thermometerTypeView, new ThermometerPresenter());

            case RowType.MOTION_SENSOR_ROW_TYPE:
                View motionSensorTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_sensor_row_design, parent, false);
                return new ViewHolderFactory.MotionSensorViewHolder(motionSensorTypeView,new MotionSensorPresenter());

                case RowType.CONDITION_ROW_TYPE:
                    View conditionTypeView= LayoutInflater.from(parent.getContext()).inflate(R.layout.condition_row_design,parent,false);

                    return new ScenarioViewHolder(conditionTypeView,new ScenarioPresenter());


            default:
                return null;
        }

    }
}
