package tabs;

import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import masks.DataManager;
import masks.XYMask;
/**
 * This is gonna need to change to a line graph.
 * We create one LineChart with two series.
 * Let's say the first one is the scatter, and the second the line,
 * and with css we get rid of the line for the first one,
 * while (this is optional) we take out the symbols of the second one, and keep both legends.
 * @see {http://stackoverflow.com/questions/26803380/how-to-combine-scatter-chart-with-line-chart-to-show-line-of-regression-javafx}
 * @author Melinda Robertson
 * @version 20151011
 */
public class ChartTab extends TabBase {
	/**
	 * The x axis for input values.
	 */
	private final NumberAxis xAxis = new NumberAxis();
	/**
	 * The y axis for output values.
	 */
	private final NumberAxis yAxis = new NumberAxis();
	/**
	 * Series to plot x y pairs as in a scatter chart.
	 */
	private final XYChart.Series<Number, Number> scatter = new XYChart.Series<Number, Number>();
	/**
	 * Series to hold only two points for the regression line.
	 */
	private final XYChart.Series<Number, Number> line = new XYChart.Series<Number, Number>();
	/**
	 * The line chart. Using a stylesheet to display both data points and a line on the same graph.
	 */
	private final LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);

	/**
	 * Creates a pane to view the chart in.
	 * @param dm holds the data the chart uses.
	 */
	public ChartTab(DataManager dm) {
		super(dm);
		setText("XY Chart");
		xAxis.setSide(Side.BOTTOM);
		xAxis.setLabel("X");
		yAxis.setSide(Side.LEFT);
		yAxis.setLabel("Y");
		
		scatter.setData(data.getData());
		
		chart.getData().add(scatter);
		chart.getData().add(line);
		chart.setLegendVisible(false);
		setContent(chart);
	}

	/**
	 * This means I need to update the line series with just two new
	 * points which are the result of the min and max x values plugged
	 * into the regression line.
	 * But I should also update the scatter plot data when it updates...
	 */
	@Override
	public void update() {
	    scatter.setData(data.getData());
	    line.getData().clear();
		double min = data.stat().min(XYMask.X);
		double max = data.stat().max(XYMask.X);
		line.getData().add(new Data<Number, Number>(
		        min, data.stat().predict(XYMask.X, XYMask.Y, min)));
		line.getData().add(new Data<Number, Number>(
		        max, data.stat().predict(XYMask.X, XYMask.Y, max)));
	}
}
