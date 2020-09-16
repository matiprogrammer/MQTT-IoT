package android.example.com.mqttaplication.Conditions;

import android.content.Context;
import android.example.com.mqttaplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ConditionsViewAdapter extends RecyclerView.Adapter<ConditionsViewHolder> {

    private List<Condition> itemDtoList;
    private ConditionDragListener dragListener;
    private ImageView imageItem;
    public ArrayList<Integer> existing;
    private Context context;
    private  ImageView imageClearContainer;
    private boolean isEditable;
    public ConditionsViewAdapter(List<Condition> itemDtoList, ConditionDragListener dragListener, Context context, ImageView imageClearContainer) {
        this.itemDtoList = itemDtoList;
        this.dragListener=dragListener;
        setHasStableIds(true);
        this.existing= new ArrayList<>();
        this.context =context;
        this.imageClearContainer=imageClearContainer;
        isEditable=true;
    }

    public ConditionsViewAdapter(List<Condition> itemDtoList, Context context, boolean isEditable) {
        this.itemDtoList = itemDtoList;
        setHasStableIds(true);
        this.context =context;
        this.isEditable=isEditable;
    }


    @NonNull
    @Override
    public ConditionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.condition_view_item, viewGroup, false);
          imageItem = itemView.findViewById(R.id.condition_container);
        if(dragListener!=null)
        imageItem.setOnDragListener(dragListener);

        ConditionsViewHolder ret = new ConditionsViewHolder(itemView, context, isEditable);
        return ret;
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionsViewHolder conditionsViewHolder,final int i) {

       final Condition condition= itemDtoList.get(i);

        conditionsViewHolder.setImage(condition.getImageId());
        conditionsViewHolder.getImage().setId(i);
        if(isEditable)
        conditionsViewHolder.getImage().setOnLongClickListener(new DeleteLongClickListener(i,imageClearContainer));

       conditionsViewHolder.setClickListener(itemDtoList.get(i));
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        if(itemDtoList!=null)
            return itemDtoList.size();
        else return 0;
    }

    public void addItem(Condition conditionViewItem)
    {
        itemDtoList.add(conditionViewItem);
        notifyDataSetChanged();
    }

    public void insertItem(int position,Condition conditionViewItem)
    {
        itemDtoList.set(position,conditionViewItem);

        notifyDataSetChanged();

    }

    public void deleteItem(int position)
    {
        itemDtoList.remove(position);
       existing.remove(position);
        for(int i=0;i<existing.size();i++)
        {
            existing.set(i,i);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public List<Condition> getList()
    {
        return itemDtoList;
    }


}
