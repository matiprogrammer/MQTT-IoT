package android.example.com.mqttaplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class MvpViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder {
   protected  P presenter;
    public MvpViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
        presenter.bindView(this);
    }
    public P getPresenter()
    {
        return presenter;
    }

    public void unbindPresenter() {
        presenter = null;
    }
}
