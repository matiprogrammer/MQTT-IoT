package android.example.com.mqttaplication.Motion_Sensor;

import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.R;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MotionSensorCondition extends Condition {
    public static String name="motionSensor";
    public String condition="condition";
    public int detect=1;
    public MotionSensorCondition(int order) {
        super(R.drawable.move,order);
    }
    public MotionSensorCondition(){super(R.drawable.move);}

    protected MotionSensorCondition(Parcel in)
    {
        super(in);
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject json= new JSONObject();
        try {
            json.put(Condition.NAME,name);
            json.put(condition,detect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static Condition getObjectFromJson(JSONObject json) {
        return new MotionSensorCondition();
    }

    @Override
    public String getDescribe()
    {
        return "relay";
    }

    public static final Parcelable.Creator<MotionSensorCondition> CREATOR =
            new Parcelable.Creator<MotionSensorCondition >() {

                public MotionSensorCondition createFromParcel(Parcel in) {
                    return new MotionSensorCondition(in);
                }

                public MotionSensorCondition[] newArray(int size) {
                    return new MotionSensorCondition[size];
                }
            };
    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

}
