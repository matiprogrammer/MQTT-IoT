package android.example.com.mqttaplication.Conditions;

import android.app.Dialog;
import android.content.Context;
import android.example.com.mqttaplication.R;
import android.example.com.mqttaplication.Relay.RelayCondition;
import android.example.com.mqttaplication.Thermometer.ThermometerCondition;
import android.example.com.mqttaplication.Timer.TimerCondition;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

public class DialogFragment {
    public static void showTimerDialog(final TimerCondition timerCondition, Context context, boolean isEditable)
    {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.fragment_timer_condition);
        final NumberPicker secondsPicker =  d.findViewById(R.id.seconds);
        final NumberPicker minutesPicker =  d.findViewById(R.id.minutes);
        final NumberPicker hoursPicker =  d.findViewById(R.id.hours);
        final CheckBox latchCheckBox= d.findViewById(R.id.latch_checkbox);
        final RadioGroup countStyleRadioButton= d.findViewById(R.id.style_count_radio);
        if(timerCondition.isReverse())
            countStyleRadioButton.check(R.id.count_down_button);
        else
            countStyleRadioButton.check(R.id.count_up_button);
        int hours,minutes;
        hours=(int)(timerCondition.getSeconds()/3600);
        minutes=(int)(timerCondition.getSeconds()%3600/60);
        secondsPicker.setMaxValue(60);
        secondsPicker.setMinValue(0);
        secondsPicker.setValue((int)(timerCondition.getSeconds())-hours*3600-minutes*60);
        minutesPicker.setMaxValue(60);
        minutesPicker.setMinValue(0);
        minutesPicker.setValue(minutes);
        hoursPicker.setMaxValue(24);
        hoursPicker.setMinValue(0);
        hoursPicker.setValue(hours);

        latchCheckBox.setChecked(timerCondition.isLatch());

        Button applyButton =  d.findViewById(R.id.apply_button);
        Button cancelButton =  d.findViewById(R.id.cancel_button);
        if(isEditable) {
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long seconds = secondsPicker.getValue() + minutesPicker.getValue() * 60 + hoursPicker.getValue() * 3600;
                    timerCondition.setSeconds(seconds);
                    timerCondition.setLatch(latchCheckBox.isChecked());
                    int checkedStyleId = countStyleRadioButton.getCheckedRadioButtonId();
                    if (checkedStyleId == R.id.count_down_button)
                        timerCondition.setReverse(true);
                    else timerCondition.setReverse(false);

                    d.dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
        else
        {
            applyButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            secondsPicker.setEnabled(false);
            minutesPicker.setEnabled(false);
            hoursPicker.setEnabled(false);
            latchCheckBox.setEnabled(false);
            countStyleRadioButton.setEnabled(false);
            d.findViewById(R.id.count_up_button).setEnabled(false);
            d.findViewById(R.id.count_down_button).setEnabled(false);
        }
        d.show();
    }

    public static void showRelayDialog(final RelayCondition relayCondition, Context context, boolean isEditable)
    {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.fragment_relay_condition);
        Button applyButton =  d.findViewById(R.id.apply_button);
        Button cancelButton =  d.findViewById(R.id.cancel_button);
        final RadioGroup stateRadioGroup=d.findViewById(R.id.state_radio_group);
        final RadioGroup relayRadioGroup=d.findViewById(R.id.relay_radio_group);
        if(relayCondition.getName().equals("relay1"))
            relayRadioGroup.check(R.id.relay1);
        else
            relayRadioGroup.check(R.id.relay2);

        if(relayCondition.getState())
            stateRadioGroup.check(R.id.on);
        else
            stateRadioGroup.check(R.id.off);
        if(isEditable) {
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int checkedState = stateRadioGroup.getCheckedRadioButtonId();
                    if (checkedState == R.id.on)
                        relayCondition.setRelayConditionState(true);
                    else relayCondition.setRelayConditionState(false);

                    int checkedRelay = relayRadioGroup.getCheckedRadioButtonId();
                    if (checkedRelay == R.id.relay1)
                        relayCondition.setName("relay1");
                    else
                        relayCondition.setName("relay2");
                    d.dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
        else
        {
            applyButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            d.findViewById(R.id.on).setEnabled(false);
            d.findViewById(R.id.off).setEnabled(false);
            d.findViewById(R.id.relay1).setEnabled(false);
            d.findViewById(R.id.relay2).setEnabled(false);
        }
        d.show();
    }

    public static void showThermometerCondition(final ThermometerCondition thermometerCondition, Context context, boolean isEditable)
    {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.fragment_temperature_condition);
        Button applyButton =  d.findViewById(R.id.apply_button);
        Button cancelButton =  d.findViewById(R.id.cancel_button);
        final NumberPicker charPicker =  d.findViewById(R.id.char_picker);
        final NumberPicker completePicker =  d.findViewById(R.id.complete_number);
        final NumberPicker decimalPicker =  d.findViewById(R.id.deimal_number);
        final String[] charValues={">","=","<"};
        final String[] numberValues={"0","10","20","30","40","50","60","70","80","90"};

        charPicker.setMinValue(0);
        charPicker.setMaxValue(2);
        if(thermometerCondition.getConditionChar()=='>')
            charPicker.setValue(0);
        else if(thermometerCondition.getConditionChar()=='=')
            charPicker.setValue(1);
        else
            charPicker.setValue(2);
        charPicker.setDisplayedValues(charValues);

        final int minValue = -15;
        final int maxValue = 50;

        completePicker.setMinValue(0);
        completePicker.setMaxValue(maxValue - minValue);
        completePicker.setValue(thermometerCondition.getComplete()-minValue);
        completePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minValue);
            }
        });

        decimalPicker.setMaxValue(9);
        decimalPicker.setMinValue(0);
        decimalPicker.setValue(thermometerCondition.getDecimal());
        decimalPicker.setDisplayedValues(numberValues);
        if(isEditable) {
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    char _charSelected = charValues[charPicker.getValue()].charAt(0);
                    thermometerCondition.setConditionChar(_charSelected);
                    float temperature = (float) completePicker.getValue() + minValue + (float) (decimalPicker.getValue()) / 10;
                    thermometerCondition.setTemperature(completePicker.getValue() + minValue, (decimalPicker.getValue()));
                    d.dismiss();
                }
            });


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
        else
        {
            charPicker.setEnabled(false);
            completePicker.setEnabled(false);
            decimalPicker.setEnabled(false);
            applyButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
        }
        d.show();


    }
}
