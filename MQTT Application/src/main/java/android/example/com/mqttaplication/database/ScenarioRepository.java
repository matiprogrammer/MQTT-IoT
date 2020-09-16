package android.example.com.mqttaplication.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.example.com.mqttaplication.Condition.Scenario;

import java.util.List;

public class ScenarioRepository {

    private ScenarioDAO mScenarioDao;
    private LiveData<List<Scenario>> mAllScenarios;

    public ScenarioRepository(Application application) {
        ScenarioDatabase db = ScenarioDatabase.getDatabase(application);
        mScenarioDao = db.scenarioDao();
        mAllScenarios=mScenarioDao.getAllScenarios();
    }

   public LiveData<List<Scenario>> getAllScenario() {
        return mAllScenarios;
    }

   public void insert(final Scenario scenario) {
        ScenarioDatabase.databaseWriteExecutor.execute(() -> {
            mScenarioDao.insert(scenario);
        });
    }
    public void update(Scenario scenario){
       new updateAsyncTask(mScenarioDao).execute(scenario);
    }

    public void delete(Scenario scenario)
    {
        ScenarioDatabase.databaseWriteExecutor.execute(() -> {
            mScenarioDao.delete(scenario);
        });
    }

    private static class updateAsyncTask extends android.os.AsyncTask<Scenario, Void, Void> {

        private ScenarioDAO mAsyncTaskDao;

        updateAsyncTask(ScenarioDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Scenario... scenarios) {
            mAsyncTaskDao.update(scenarios[0]);
            return null;
        }
    }
}
