package android.example.com.mqttaplication;

import android.example.com.mqttaplication.Condition.Scenario;
import android.example.com.mqttaplication.RowTypes.MotionSensorRowType;
import android.example.com.mqttaplication.RowTypes.RelayRowType;
import android.example.com.mqttaplication.RowTypes.RowType;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.example.com.mqttaplication.RowTypes.ScenarioRowType;
import android.example.com.mqttaplication.RowTypes.ThermometerRowType;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MultipleTypesAdapter extends RecyclerView.Adapter {

    private List<RowType> dataSet;


    public MultipleTypesAdapter(List<RowType> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).getItemViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder= ViewHolderFactory.create(viewGroup, i);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        dataSet.get(i).onBindViewHolder(viewHolder);


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<RowType> getData()
    {return dataSet;}
    public void setData(List<RowType> rowList)
    {
       // removeAllScenarios();
        rowList.add(0,new RelayRowType(1, "Przekaźnik 1"));
        rowList.add(1,new RelayRowType(2, "Przekaźnik 2"));
        rowList.add(2,new ThermometerRowType("Temperatura"));
        rowList.add(3,new MotionSensorRowType("Czujnik ruchu"));
        dataSet=rowList;
        //dataSet=rowList;
        notifyDataSetChanged();
    }

    private void removeAllScenarios()
    {
        for(int i=4;i<dataSet.size();i++)
        dataSet.remove(i);
        notifyDataSetChanged();

    }

    public void remove(int position)
    {
            dataSet.remove(position);
            notifyItemRemoved(position);
    }

    public List<Scenario> getScenarioRows()
    {
        List<Scenario> scenarioList=new ArrayList<>();
        for(int i=4;i<dataSet.size();i++)
            scenarioList.add(((ScenarioRowType)dataSet.get(i)).getScenario());
        return scenarioList;

    }
}
