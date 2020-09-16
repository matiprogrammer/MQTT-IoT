package android.example.com.mqttaplication.RowTypes;

import android.example.com.mqttaplication.PresentersDatabase;
import android.example.com.mqttaplication.ViewHolderFactory;
import androidx.recyclerview.widget.RecyclerView;

public class MotionSensorRowType extends Row implements RowType {
    private String updateValueTopic;
    private String name;
    public MotionSensorRowType(String name) {
        super();
        this.updateValueTopic="ic/from/nm/motionDetector";
        this.name=name;
        addSubscribeTopic(updateValueTopic);

    }

    @Override
    public int getItemViewType() {
        return RowType.MOTION_SENSOR_ROW_TYPE;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        ViewHolderFactory.MotionSensorViewHolder motionSensorViewHolder= (ViewHolderFactory.MotionSensorViewHolder)viewHolder;
        motionSensorViewHolder.getPresenter().setModel(this);
       // motionSensorViewHolder.getPresenter().initialize();
        PresentersDatabase.getInstance().addPair(motionSensorViewHolder.getPresenter(),motionSensorViewHolder.getPresenter().getModel());
    }
    public String getUpdateValueTopic() {
        return updateValueTopic;
    }

    public String getName() {
        return name;
    }
}
