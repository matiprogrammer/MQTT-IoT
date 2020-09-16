package android.example.com.mqttaplication.database;

import android.content.Context;
import android.example.com.mqttaplication.Condition.Scenario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Scenario.class}, version = 3, exportSchema = false)
@TypeConverters({ListConverter.class})
public abstract class ScenarioDatabase extends RoomDatabase {
    public abstract ScenarioDAO scenarioDao();

    private static volatile ScenarioDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ScenarioDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScenarioDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScenarioDatabase.class, "scenario_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ScenarioDAO dao = INSTANCE.scenarioDao();
                dao.deleteAll();

                //Sce word = new Word("Hello");
                //dao.insert(word);
                //word = new Word("World");
                //dao.insert(word);
            });
        }
    };
}


