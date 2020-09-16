package android.example.com.mqttaplication.RowTypes;

import androidx.recyclerview.widget.RecyclerView;

public interface RowType {
    int RELAY_ROW_TYPE = 0;
    int THERMOMETER_ROW_TYPE = 1;
    int MOTION_SENSOR_ROW_TYPE = 2;
    int CONDITION_ROW_TYPE=3;

    int getItemViewType();
    void onBindViewHolder(RecyclerView.ViewHolder viewHolder);
}
