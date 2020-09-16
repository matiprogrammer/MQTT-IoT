package android.example.com.mqttaplication.Conditions;

import android.example.com.mqttaplication.R;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import androidx.room.ColumnInfo;

public class Condition implements Parcelable {

public static String NAME="name";
    private  int imageResource;

    public Condition(int imageResource){this.imageResource=imageResource;}
    public Condition(int imageResource, int order) {
    this.imageResource=imageResource;
    }
    public Condition()
    {
       imageResource= R.drawable.ic_crop_free_black;
    }

    public Condition(Parcel in) {
        imageResource = in.readInt();
    }

    public  JSONObject getJSONObject()
    {
        return new JSONObject();
    }
    public static Condition getObjectFromJson(JSONObject json){return new Condition();}
    void setImageResource(int imageId)
    {

        this.imageResource=imageId;
    }
    public int getImageId() {

        return imageResource;
    }
    public String getDescribe()
    {

        return "";
    }

    public static final Creator<Condition> CREATOR = new Creator<Condition>() {
        @Override
        public Condition createFromParcel(Parcel in) {
            return new Condition(in);
        }

        @Override
        public Condition[] newArray(int size) {
            return new Condition[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
    }

}
