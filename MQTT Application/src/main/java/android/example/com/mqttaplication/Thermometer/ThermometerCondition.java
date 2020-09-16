package android.example.com.mqttaplication.Thermometer;

import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.R;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ThermometerCondition extends Condition {
    private char conditionChar;
    private float temperature;
    private int complete, decimal;
    public static String name="thsensor";
    public static String TEMPERATURE="temperature";
    public static String CHAR="char";

    public ThermometerCondition( char conditionChar, float temperature) {
        super(R.drawable.temperature);
        this.conditionChar = conditionChar;
        this.temperature = temperature;
        this.complete=(int)temperature;
        this.decimal= (int)(temperature-(float)complete)*10;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public int getComplete() {
        return complete;
    }

    public int getDecimal() {
        return decimal;
    }

    public char getConditionChar() {
        return conditionChar;
    }

    public void setConditionChar(char conditionChar) {
        this.conditionChar = conditionChar;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(int complete, int decimal) {
        this.temperature = (float)complete+(float)decimal/10;
        this.complete=complete;
        this.decimal=decimal;
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject json =new JSONObject();
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        try {
            json.put(Condition.NAME,name);
            json.put(TEMPERATURE,temperature);
            json.put(CHAR,String.valueOf(conditionChar));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static Condition getObjectFromJson(JSONObject json) {
        try {
            return new ThermometerCondition(json.getString(CHAR).charAt(0),Float.parseFloat(json.getString(TEMPERATURE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ThermometerCondition(int order) {
        super(R.drawable.temperature,order);
    }

    protected ThermometerCondition(Parcel in)
    {
        super(in);
        char charArray[]= new char[1];
        in.readCharArray(charArray);
        conditionChar=charArray[0];
        temperature=in.readFloat();
        complete=in.readInt();
        decimal=in.readInt();
    }


    public static final Parcelable.Creator<ThermometerCondition> CREATOR =
            new Parcelable.Creator<ThermometerCondition >() {

                public ThermometerCondition createFromParcel(Parcel in) {
                    return new ThermometerCondition(in);
                }

                public ThermometerCondition[] newArray(int size) {
                    return new ThermometerCondition[size];
                }
            };

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeCharArray(new char[]{conditionChar});
        dest.writeFloat(temperature);
        dest.writeInt(complete);
        dest.writeInt(decimal);
    }

}
