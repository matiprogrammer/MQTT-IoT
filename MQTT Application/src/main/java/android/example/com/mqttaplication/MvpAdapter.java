package android.example.com.mqttaplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MvpAdapter<M, P extends BasePresenter, VH extends MvpViewHolder<P>> extends MvpRecyclerAdapter<M, P, VH>{
    private final List<M> models;
    public MvpAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data) {
        models.clear();
        presenters.clear();

        for (M item : data) {
            addInternal(item);
        }

        notifyDataSetChanged();
    }
    private void addInternal(M item) {
        System.err.println("Adding item " + getModelId(item));
        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }
}
