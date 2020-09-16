package android.example.com.mqttaplication.Condition;

import android.example.com.mqttaplication.Conditions.Condition;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "scenario")
public class Scenario implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scenarioId")
    private int Id;

    @ColumnInfo(name="isRepeated")
    private boolean isRepeated;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="isEnabled")
    private boolean isEnabled;

    @Ignore
    private JSONObject scenarioJSON= new JSONObject();

    public static String SCENARIO="Scenario";
    public static String SCENARIO_NAME="scenarioName";
    public static String REPEATED="repeated";

    @ColumnInfo(name="conditionList")
    private List<Condition> conditionList;

    public Scenario(int Id, boolean isRepeated, String name, JSONObject scenarioJSON, boolean isEnabled) {
        this.Id = Id;
        this.isRepeated = isRepeated;
        this.name = name;
        this.scenarioJSON = scenarioJSON;
        this.isEnabled=isEnabled;
    }

    public Scenario(List<Condition> conditionList, boolean isRepeated, String name) {
        this.conditionList = conditionList;
        this.isRepeated = isRepeated;
        this.name = name;
        isEnabled=false;
        //scenarioJSON=writeToJSON(conditionList);
    }

   public JSONObject  writeToJSON(List<Condition> conditions)
    {
        JSONObject json=new JSONObject();
        JSONArray conditionArray=new JSONArray();
        for (Condition condition: conditions) {
            conditionArray.put(condition.getJSONObject());
        }
        try {
            json.put("conditions",conditionArray);
            json.put(SCENARIO_NAME,name);
            json.put(REPEATED,isRepeated);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public JSONObject getScenarioJSON() {
        return writeToJSON(conditionList);
    }

    protected Scenario(Parcel in) {
        conditionList =new ArrayList<>();
         in.readList(conditionList,Condition.class.getClassLoader());
        isRepeated = in.readByte() != 0;
        name = in.readString();
        String scenarioAsString;
        scenarioAsString=in.readString();
        isEnabled=in.readByte() != 0;

        try {
            scenarioJSON=new JSONObject(scenarioAsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static final Creator<Scenario> CREATOR = new Creator<Scenario>() {
        @Override
        public Scenario createFromParcel(Parcel in) {
            return new Scenario(in);
        }

        @Override
        public Scenario[] newArray(int size) {
            return new Scenario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(conditionList);
        dest.writeByte((byte) (isRepeated ? 1 : 0));
        dest.writeString(name);
        dest.writeString(scenarioJSON.toString());
        dest.writeByte((byte) (isEnabled ? 1 : 0));
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return Id;

    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(int id) {
        Id = id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
