package android.example.com.mqttaplication.Relay;

import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.R;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RelayCondition extends Condition {
    public static String condition="condition";
    private boolean state;
    private String name;

    public RelayCondition(String name, boolean state) {
        super(R.drawable.switch_black);
        this.name = name;
        this.state = state;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public RelayCondition(int order) {

        super(R.drawable.switch_black,order);
    }

    public boolean getState()
    {
        return this.state;
    }
    public void setRelayConditionState(boolean state)
    {
    this.state=state;
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put(Condition.NAME,name);
            json.put(condition,state ? 1 : 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static Condition getObjectFromJson(JSONObject json) {

        try {
            boolean state=(json.getInt(condition)==1);
            return new RelayCondition(json.getString(Condition.NAME),state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected RelayCondition(Parcel in)
{
 super(in);
 state=in.readByte() !=0;
 name=in.readString();
}

    @Override
    public String getDescribe()
    {
        return "relay";
    }

    public static final Parcelable.Creator<RelayCondition> CREATOR =
            new Parcelable.Creator<RelayCondition >() {

                public RelayCondition createFromParcel(Parcel in) {
                    return new RelayCondition(in);
                }

                public RelayCondition[] newArray(int size) {
                    return new RelayCondition[size];
                }
            };
    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (state ? 1 : 0));
        dest.writeString(name);
    }
}
