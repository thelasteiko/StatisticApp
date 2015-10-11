package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class XYChartIO implements FileIO<ObservableList<Data<Number, Number>>, Data<Number, Number>> {
	
	/**
	 * The last file that was accessed by this object.
	 */
	private String lastfile;
	/**
	 * The current directory that this object will look for
	 * and access files in.
	 */
	private Path currentpath;
	
	/**
	 * Creates a file input/output utility for reading and writing
	 * x y pairs in an observable list.
	 * @param p is the path.
	 */
	public XYChartIO(String p) {
		lastfile = null;
		currentpath = Paths.get(p);
	}
	
	/**
	 * Creates a file input/output utility for reading and writing
	 * x y pairs in an observable list.
	 */
	public XYChartIO() {
		lastfile = null;
		currentpath = Paths.get("");
	}
	//TODO will be using file chooser so need to change how path is used
	//probably can still use the currentpath to set up the file chooser
	//may be good to run it from this class
	@Override
	public ObservableList<Data<Number, Number>> load(String path) {
		ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
		if(path == null) return data;
		if(!path.equals(lastfile))
			lastfile = currentpath.toAbsolutePath() + path;
		try(BufferedReader br = new BufferedReader(new FileReader(lastfile))) {
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
		return data;
	}

	@Override
	public void save(String path, ObservableList<Data<Number, Number>> list) {
		if(path == null) return;
		if(!path.equals(lastfile))
			lastfile = this.currentpath.toAbsolutePath() + path;
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(lastfile))) {
			for(Data<Number, Number> d: list) {
				bw.write(d.getXValue() + "," + d.getYValue() + "\n");
			}
		} catch (IOException e) {
			System.out.println("There was an error writing the file.");
			e.printStackTrace();
		}
	}

	@Override
	public String lastfile() {
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

	@Override
	public void setDir(String directory) {
		currentpath = Paths.get(directory);
	}

}
