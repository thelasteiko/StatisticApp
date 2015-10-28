package tabs;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Tab;
import masks.DataManager;


public abstract class TabBase extends Tab implements Observer {
    
    protected DataManager data;

	abstract public void update();
	public TabBase(DataManager dm) {
	    data = dm;
	    getStyleClass().add("all");
	}
    @Override
    public void update(Observable data, Object list) {
        if (!(data instanceof DataManager)) return;
        this.data = (DataManager) data;
    }

}
