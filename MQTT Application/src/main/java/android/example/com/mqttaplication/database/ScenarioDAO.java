package android.example.com.mqttaplication.database;

import androidx.lifecycle.LiveData;
import android.example.com.mqttaplication.Condition.Scenario;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScenarioDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Scenario scenario);

    @Delete
    void delete(Scenario scenario);

    @Update
    void update(Scenario scenario);

    @Update
    void update(List<Scenario> scenarios);

    @Query("SELECT * FROM scenario")
    LiveData<List<Scenario>>getAllScenarios();

    @Query("DELETE  FROM scenario")
    void deleteAll();
}
