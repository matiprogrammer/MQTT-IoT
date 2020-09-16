package android.example.com.mqttaplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.RowTypes.RowType;
import android.example.com.mqttaplication.RowTypes.ScenarioRowType;
import android.example.com.mqttaplication.database.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;

public class ScenarioViewModel extends AndroidViewModel {

        private ScenarioRepository mRepository;

        private LiveData<List<Scenario>> mAllWords;
        public  LiveData<List<RowType>> scenarioRowTypeLiveData=null;

        public ScenarioViewModel (Application application) {
            super(application);
            mRepository = new ScenarioRepository(application);
            mAllWords = mRepository.getAllScenario();
        }
        private List<RowType> makeScenarioRows(List<Scenario> scenarios)
        {
            List<RowType> rows=new ArrayList<>();
            for(Scenario scenario:scenarios)
            {
                rows.add(new ScenarioRowType(scenario));
            }
            return rows;
        }
        LiveData<List<RowType>> getAllScenariosRows() {
            LiveData<List<Scenario>> scenarios=mRepository.getAllScenario();
            scenarioRowTypeLiveData=Transformations.map(scenarios,newData->makeScenarioRows(newData));
            return scenarioRowTypeLiveData;
        }

        public void insert(Scenario scenario) { mRepository.insert(scenario); }
        public void update(Scenario scenario){mRepository.update(scenario);}
        public void delete(Scenario scenario){mRepository.delete(scenario);}
    }

