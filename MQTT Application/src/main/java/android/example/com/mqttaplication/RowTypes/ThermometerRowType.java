package android.example.com.mqttaplication.RowTypes;

import android.example.com.mqttaplication.PresentersDatabase;
import android.example.com.mqttaplication.ViewHolderFactory;
import androidx.recyclerview.widget.RecyclerView;

public class ThermometerRowType extends Row implements RowType {

    private String name;
    private float value;
    private String updateValueTopic;

    public ThermometerRowType(String name) {
        super();
        this.name = name;
        this.updateValueTopic="ic/from/nm/thermometer";
        addSubscribeTopic(updateValueTopic);
    }

    @Override
    public int getItemViewType() {
        return RowType.THERMOMETER_ROW_TYPE;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {

        ViewHolderFactory.ThermometerViewHolder thermometerViewHolder= (ViewHolderFactory.ThermometerViewHolder)viewHolder;
        thermometerViewHolder.getPresenter().setModel(this);
        //thermometerViewHolder.getPresenter().initialize();
        PresentersDatabase.getInstance().addPair(thermometerViewHolder.getPresenter(),thermometerViewHolder.getPresenter().getModel());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    public String getUpdateValueTopic() {
        return updateValueTopic;
    }

    public void setUpdateValueTopic(String updateValueTopic) {
        this.updateValueTopic = updateValueTopic;
    }
}
