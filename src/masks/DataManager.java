package masks;

import io.FileIO;
import io.XYChartIO;

import java.io.File;
import java.util.Iterator;
import java.util.Observable;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import scatter.Statistics;

/**
 * Manages the data for display in various JavaFX tables and charts.
 * So in the future it would be nice for this to have optional data type displays.
 * That is, use the FileIO class that suits the type of data needed.
 * Right now I have no idea what other type I need...
 * but to make it that way I need to have a generic type for this class
 * and load that data type into the FileIO and use a switch type system to
 * choose which subclass to use.
 * @author Melinda Robertson
 * @version 20151011
 */
public class DataManager extends Observable {
	
	/**
	 * Loads and saves the data.
	 */
	private final static FileIO<ObservableList<Data<Number,Number>>,Data<Number, Number>> io = new XYChartIO();
	/**
	 * The data.
	 */
	private ObservableList<XYChart.Data<Number, Number>> data;
	/**
	 * Performs calculations on the data.
	 */
	private Statistics<ObservableList<Data<Number, Number>>,Data<Number, Number>> st;
	/**
	 * Creates a new DataManager without adding any data
	 * to the initial set.
	 */
	public DataManager() {
		begin(null);
	}
	/**
	 * Creates the manager with initial data.
	 * @param data is the x y pair data.
	 */
	public DataManager(File filename) {
		begin(filename);
	}
	/**
	 * Retrieve the current data in an observable list as a reference.
	 * @return the data.
	 */
	public ObservableList<XYChart.Data<Number, Number>> getData() {
		return data;
	}
	/**
	 * Adds a new x y pair to the data set.
	 * @param x is the x coordinate.
	 * @param y is the y coordinate.
	 */
	public void add(double x, double y) {
		if (data == null) return;
		XYChart.Data<Number, Number> e = new XYChart.Data<Number, Number>(x, y);
		data.add(e);
	}
	/**
	 * Finds and removes the first occurrence of the given x y pair.
	 * @param x is the x coordinate.
	 * @param y is the y coordinate.
	 */
	public void remove(double x, double y) {
		if (data == null) return;
		Iterator<Data<Number, Number>> it = data.iterator();
		while(it.hasNext()) {
			Data<Number, Number> temp = it.next();
			if((Double) temp.getXValue() == x && (Double) temp.getYValue() == y) {
				it.remove();
				break;
			}
		}
	}
	/**
	 * Retrieve the statistical data calculator.
	 * @return the data calculator.
	 */
	public Statistics<ObservableList<Data<Number, Number>>,Data<Number, Number>> stat() {
		return st;
	}
	/**
	 * Load the appropriate data structure using the IO object.
	 * @param filename is the file to load.
	 */
	public void load(File filename) {
		begin(filename);
	}
	/**
	 * Loads the last file accessed.
	 */
	public void load() {
		begin(io.lastfile());
	}
	/**
	 * Saves data to the indicated file using the IO object.
	 * @param filename is the file to save to.
	 */
	public void save(File filename) {
		io.save(filename, data);
	}
	/**
	 * Saves data to the last file accessed.
	 */
	public void save() {
		io.savelast(data);
	}
	/**
	 * Starting point to overwrite data.
	 * @param filename is the file to load data from.
	 */
	private void begin(File filename) {
		data = io.load(filename);
		XYMask m = new XYMask(data);
		st = new Statistics<ObservableList<Data<Number, Number>>,Data<Number, Number>>(m);
	}
}
