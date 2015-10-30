package tabs;

import java.util.Observer;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import masks.DataManager;

/**
 * The tab pane that holds all relevant tabs. This is the
 * core for the statistical program though not the driving agent.
 * @author Melinda Robertson
 * @version 20151015
 */
public class TabManager extends TabPane implements Observer {
	/**
	 * It's a thing. Go with it.
	 */
	private TabBase[] mytabs;
	/**
	 * Initialize and create tab pane and all tabs.
	 * @param data is the data that the tabs will reference.
	 */
	public TabManager(final DataManager dm) {
	    dm.addObserver(this);
		setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		setSide(Side.TOP);
		setRotateGraphic(false);
		
		mytabs = new TabBase[3];
		
		int n = 0;
		mytabs[n++] = new SummaryTab(dm);
		mytabs[n++] = new ChartTab(dm);
		mytabs[n++] = new HistogramTab(dm);
		
		for(Tab t: mytabs) {
		    getTabs().add(t);
		}
		//TODO tabs: options?
	}

    @Override
    public void update(java.util.Observable data, Object list) {
        if(!(data instanceof DataManager)) return;
        for (TabBase b : mytabs) {
            b.update(data, null);
        }
    }

}
