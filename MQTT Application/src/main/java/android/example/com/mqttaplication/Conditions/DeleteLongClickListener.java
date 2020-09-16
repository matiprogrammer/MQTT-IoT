package android.example.com.mqttaplication.Conditions;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;
import android.widget.ImageView;

public class DeleteLongClickListener implements View.OnLongClickListener {
    public static String TAG="delete";
    private int position;
private ImageView imageClearContainer;
    public DeleteLongClickListener(int position, ImageView imageClearContainer) {
        this.position = position;
        this.imageClearContainer=imageClearContainer;
    }

    @Override
    public boolean onLongClick(View v) {
        if(imageClearContainer!=null)
        imageClearContainer.setVisibility(View.VISIBLE);
        ClipData.Item item = new ClipData.Item(TAG);
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(TAG, mimeTypes, item);
        data.addItem(new ClipData.Item(Integer.toString(position)));
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDragAndDrop(data, shadowBuilder, v, 0);
        return true;
    }
}
