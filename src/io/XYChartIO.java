package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
/**
 * Loads and Saves data from an observable list in the following format:
 * x1,y1
 * x2,y2
 * x3,y3
 * The file can be text or comma delimited.
 * @author Melinda Robertson
 * @version 20151011
 */
public class XYChartIO implements FileIO<ObservableList<Data<Number, Number>>, Data<Number, Number>> {
	
	/**
	 * The last file that was accessed by this object.
	 */
	private File lastfile;
	
	/**
	 * Creates a file input/output utility for reading and writing
	 * x y pairs in an observable list.
	 * @param p is the path.
	 */
	public XYChartIO(String p) {
		lastfile = null;
	}
	
	/**
	 * Creates a file input/output utility for reading and writing
	 * x y pairs in an observable list.
	 */
	public XYChartIO() {
		lastfile = null;
	}

	@Override
	public ObservableList<Data<Number, Number>> load(File path) {
		ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
		if(path == null) return data;
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			//iterate through each line
			String line;
			double x, y;
			int i;
			while((line = br.readLine()) != null) {
				i = line.indexOf(',');
				if (i > 0) {
					x = Double.parseDouble(line.substring(0, i));
					y = Double.parseDouble(line.substring(i+1));
					Data<Number, Number> e = new Data<Number, Number>(x, y);
					data.add(e);
				}
			}
		} catch (IOException e) {
			System.out.println("There was an error reading the file.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("Wrong line format.");
			e.printStackTrace();
		}
		lastfile = path;
		return data;
	}

	@Override
	public void save(File path, ObservableList<Data<Number, Number>> list) {
		if(path == null) return;
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for(Data<Number, Number> d: list) {
				bw.write(d.getXValue() + "," + d.getYValue() + "\n");
			}
		} catch (IOException e) {
			System.out.println("There was an error writing the file.");
			e.printStackTrace();
		}
		lastfile = path;
	}

	@Override
	public File lastfile() {
		return lastfile;
	}

	@Override
	public ObservableList<Data<Number, Number>> loadlast() {
		return load(lastfile);
	}

	@Override
	public void savelast(ObservableList<Data<Number, Number>> list) {
		save(lastfile, list);
	}
}
