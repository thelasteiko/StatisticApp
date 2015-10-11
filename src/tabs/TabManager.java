package tabs;

import javafx.scene.control.TabPane;
import masks.DataManager;

/**
 * The tab pane that holds all relevant tabs. This is the
 * core for the statistical program though not the driving agent.
 * @author Melinda Robertson
 * @version 20151008
 */
public class TabManager extends TabPane {
	
    /**
     * TODO I need to change this some way cause TabPane already
     * has a list of tabs.
     */
	private TabBase[] mytabs;
	/**
	 * Controls all data flow and updates.
	 */
	private final DataManager data;
	
	/**
	 * Initialize and create tab pane and all tabs.
	 * @param data is the data that the tabs will reference.
	 */
	public TabManager(final DataManager dm) {
		this.data = dm;
		setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		
		//tabs = FXCollections.observableList(new LinkedList<TabBase>());
		mytabs = new TabBase[4];
		/*
		 * getTabs().add(new ChartTab(dm));
		 * getTabs().add(new SummaryTab(dm));
		 * getTabs().add(new HistogramTab(dm));
		 * getTabs().add(new OptionsTab(dm));
		 */
		int n = 0;
		mytabs[n++] = new SummaryTab(dm);
		mytabs[n++] = new ChartTab(dm);
		mytabs[n++] = new HistogramTab(dm);
		
		for(TabBase t: mytabs) {
		    getTabs().add(t);
		}
		//TODO tabs: histogram, options?
	}

	public void update() {
		for (TabBase b : mytabs) {
			b.update();
		}
	}

}
