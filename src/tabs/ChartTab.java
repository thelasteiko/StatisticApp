package tabs;

import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import masks.DataManager;
/**
 * This is gonna need to change to a line graph.
 * We create one LineChart with two series.
 * Let's say the first one is the scatter, and the second the line,
 * and with css we get rid of the line for the first one,
 * while (this is optional) we take out the symbols of the second one, and keep both legends.
 * @see {http://stackoverflow.com/questions/26803380/how-to-combine-scatter-chart-with-line-chart-to-show-line-of-regression-javafx}
 * @author Melinda Robertson
 *
 */
public class ChartTab extends TabBase {
	
	private final NumberAxis xAxis = new NumberAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final XYChart.Series<Number, Number> scatter = new XYChart.Series<Number, Number>();
	private final XYChart.Series<Number, Number> line = new XYChart.Series<Number, Number>();
	private final LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
	
	private DataManager data;
	
	public ChartTab(DataManager dm) {
		data = dm;
		setText("XY Chart");
		xAxis.setSide(Side.BOTTOM);
		xAxis.setLabel("X");
		yAxis.setSide(Side.LEFT);
		yAxis.setLabel("Y");
		
		scatter.setData(data.getData());
		
		chart.getData().add(scatter);
		chart.getData().add(line);
		chart.setLegendVisible(false);
//		chart.setCreateSymbols(false);
		setContent(chart);
	}

	/**
	 * This means I need to update the line series with just two new
	 * points which are the result of the min and max x values plugged
	 * into the regression line.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
