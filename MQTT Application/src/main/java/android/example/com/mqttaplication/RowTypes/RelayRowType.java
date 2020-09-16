package android.example.com.mqttaplication.RowTypes;

import android.example.com.mqttaplication.PresentersDatabase;
import android.example.com.mqttaplication.ViewHolderFactory;
import androidx.recyclerview.widget.RecyclerView;

public class RelayRowType extends Row implements RowType {

    private int RELAY_NUMBER;
    private String relayName;
    private String changeStateTopic;
    private boolean isChecked;



    public RelayRowType(int RELAY_NUMBER, String relay_name ) {
        super();
        this.RELAY_NUMBER = RELAY_NUMBER;
        relayName = relay_name;
        changeStateTopic="ic/from/nm/relay"+(RELAY_NUMBER);
        isChecked=false;
        addSubscribeTopic(changeStateTopic);

    }

    @Override
    public int getItemViewType() {
        return RowType.RELAY_ROW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        final ViewHolderFactory.RelayViewHolder relayViewHolder=(ViewHolderFactory.RelayViewHolder) viewHolder;
            relayViewHolder.getPresenter().setModel(this);
        //relayViewHolder.getPresenter().initialize();
        PresentersDatabase.getInstance().addPair(relayViewHolder.getPresenter(),relayViewHolder.getPresenter().getModel());
    }

    public int getRelayNumber()
    {
        return RELAY_NUMBER;
    }

    public String getRelayName() {
        return relayName;
    }

    public void setRelayName(String relayName) {
        this.relayName = relayName;
    }

    public String getChangeStateTopic() {
        return changeStateTopic;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
