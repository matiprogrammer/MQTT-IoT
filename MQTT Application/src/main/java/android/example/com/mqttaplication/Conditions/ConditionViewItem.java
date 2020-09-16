package android.example.com.mqttaplication.Conditions;

import android.example.com.mqttaplication.R;

public class ConditionViewItem {

    private Condition condition;
    private int imageId;
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }




    public ConditionViewItem() {

        imageId= R.drawable.ic_crop_free_black;
    }
}
