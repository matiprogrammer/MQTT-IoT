package android.example.com.mqttaplication.RowTypes;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.PresentersDatabase;
import android.example.com.mqttaplication.ViewHolderFactory;
import androidx.recyclerview.widget.RecyclerView;

public class ScenarioRowType extends Row implements RowType {

private Scenario scenario;

    public ScenarioRowType(Scenario scenario) {
        super();
        this.scenario = scenario;
    }

    public String getName()
    {
        return scenario.getName();
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setChecked(boolean isChecked)
    {
        scenario.setEnabled(isChecked);
    }

    public boolean getChecked(){return scenario.getEnabled();}

    @Override
    public int getItemViewType() {
        return RowType.CONDITION_ROW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        ViewHolderFactory.ScenarioViewHolder scenarioViewHolder= (ViewHolderFactory.ScenarioViewHolder)viewHolder;
        scenarioViewHolder.getPresenter().setModel(this);
        // motionSensorViewHolder.getPresenter().initialize();
        scenarioViewHolder.getPresenter().onBindViewHolder();
        PresentersDatabase.getInstance().addPair(scenarioViewHolder.getPresenter(),scenarioViewHolder.getPresenter().getModel());
    }


}
