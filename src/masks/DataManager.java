package masks;

import io.FileIO;
import io.XYChartIO;

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
 * @version 20151003
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
	public DataManager(String filename) {
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
	
	public void load(String filename) {
		begin(filename);
	}
	
	public void load(String dir, String file) {
		io.setDir(dir);
		begin(file);
	}
	
	public void load() {
		begin(io.lastfile());
	}
	
	public void cd(String dir) {
		io.setDir(dir);
	}
	
	public void save(String filename) {
		io.save(filename, data);
	}
	
	public void save() {
		io.savelast(data);
	}
	
	public void save(String dir, String file) {
		io.setDir(dir);
		io.save(file, data);
	}
	
	private void begin(String filename) {
		data = io.load(filename);
		XYMask m = new XYMask(data);
		st = new Statistics<ObservableList<Data<Number, Number>>,Data<Number, Number>>(m);
	}
}
