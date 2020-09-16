package android.example.com.mqttaplication;

import android.example.com.mqttaplication.RowTypes.RowType;

import java.util.HashMap;
import java.util.Map;

public class PresentersDatabase {
    private static PresentersDatabase instance;
    protected Map< RowType,BasePresenter> presenters;

    public PresentersDatabase() {
        this.presenters = new HashMap<>();
    }

    public static synchronized PresentersDatabase getInstance() {
        if (instance == null) {
            instance = new PresentersDatabase();
        }
        return instance;
    }
    public void addPair(BasePresenter presenter, RowType model)
    {
        presenters.put(model,presenter);
    }
    public BasePresenter getPresenter(RowType model)
    {
        if(presenters!=null)
        return presenters.get(model);

        else return null;
    }

    public Map<RowType,BasePresenter> getAllPresenters()
    {
        return presenters;
    }
}
