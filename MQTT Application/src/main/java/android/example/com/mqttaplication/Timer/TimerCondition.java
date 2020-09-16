package android.example.com.mqttaplication.Timer;

import android.example.com.mqttaplication.Conditions.Condition;
import android.example.com.mqttaplication.R;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TimerCondition extends Condition {
    private long seconds;
    private boolean isLatch;
    private boolean isReverse;
    public static String COUNT_TIME = "countTime";
    public static String LATCH = "latch";
    public static String REVERSE = "reverse";
    public static String timer="timer";
    public TimerCondition(long seconds, boolean isLatch, boolean isReverse) {
        super(R.drawable.clock);
        this.seconds = seconds;
        this.isLatch = isLatch;
        this.isReverse = isReverse;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    public boolean isLatch() {
        return isLatch;
    }

    public void setLatch(boolean latch) {
        isLatch = latch;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public TimerCondition(int order) {
        super(R.drawable.clock, order);
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put(Condition.NAME, "timer");
            json.put(COUNT_TIME, seconds);
            json.put(LATCH, isLatch);
            json.put(REVERSE, isReverse);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static Condition getObjectFromJson(JSONObject json) {

        try {
            return new TimerCondition(json.getLong(COUNT_TIME), json.getBoolean(LATCH), json.getBoolean(REVERSE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    protected TimerCondition(Parcel in)
    {
        super(in);
        seconds=in.readLong();
        isLatch=in.readByte() !=0;
        isReverse=in.readByte() !=0;
    }

    public static final Parcelable.Creator<TimerCondition> CREATOR =
            new Parcelable.Creator<TimerCondition >() {

                public TimerCondition createFromParcel(Parcel in) {
                    return new TimerCondition(in);
                }

                public TimerCondition[] newArray(int size) {
                    return new TimerCondition[size];
                }
            };
    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(seconds);
        dest.writeByte((byte) (isLatch ? 1 : 0));
        dest.writeByte((byte) (isReverse ? 1 : 0));
    }


}
