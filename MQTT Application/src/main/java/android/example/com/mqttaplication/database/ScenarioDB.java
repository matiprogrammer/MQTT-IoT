package android.example.com.mqttaplication.database;

import org.json.JSONObject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "scenario")

public class ScenarioDB {

    @PrimaryKey
    @ColumnInfo(name = "scenarioId")
    private String sId;

    @ColumnInfo(name="scenarioJSON")
    private JSONObject scenarioJSON;

    @ColumnInfo()
    private boolean isRepeated;
    private String name;
}
