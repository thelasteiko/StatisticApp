package scatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import masks.DataManager;

public class TableBox extends VBox {
	
	private TableView<XYChart.Data<Number, Number>> table;
	private DataManager data;

	public TableBox(DataManager dm) {
		this.setPadding(new Insets(10, 2, 2, 10));
		
		Label label = new Label("X|Y Table");		
		
		table = new TableView<XYChart.Data<Number, Number>>();
		ScrollPane scpane = new ScrollPane(table);
		scpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		TableColumn<XYChart.Data<Number, Number>, Double> xCol =
				new TableColumn<XYChart.Data<Number, Number>, Double>("X");
		xCol.setMaxWidth(10);
		xCol.setCellValueFactory(
				new PropertyValueFactory<XYChart.Data<Number, Number>, Double>("XValue"));
		xCol.setOnEditCommit(
				new EventHandler<CellEditEvent<XYChart.Data<Number, Number>, Double>>() {

					@Override
					public void handle(CellEditEvent<XYChart.Data<Number, Number>, Double> e) {
						Data<Number, Number> p = e.getRowValue();
						p.setXValue(e.getNewValue());
					}
					
				});
		
		TableColumn<XYChart.Data<Number, Number>, Double> yCol =
				new TableColumn<XYChart.Data<Number, Number>, Double>("Y");
		yCol.setMaxWidth(10);
		yCol.setCellValueFactory(
				new PropertyValueFactory<XYChart.Data<Number, Number>, Double>("YValue"));
		yCol.setOnEditCommit(
				new EventHandler<CellEditEvent<XYChart.Data<Number, Number>, Double>>() {

					@Override
					public void handle(CellEditEvent<XYChart.Data<Number, Number>, Double> e) {
						Data<Number, Number> p = e.getRowValue();
						p.setYValue(e.getNewValue());
					}
					
				});
		
		this.setSpacing(3);
		this.getChildren().addAll(label, scpane, buildAddPane());
	}
	
	private HBox buildAddPane() {
		HBox box = new HBox();
		
		final TextField xField = new TextField();
		xField.setPromptText("X");
		
		final TextField yField = new TextField();
		yField.setPromptText("Y");
		
		final Button addBtn = new Button("Add");
		addBtn.setOnAction( new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				data.add(Double.parseDouble(xField.getText()),
						Double.parseDouble(yField.getText()));
			}
			
		});
		
		final Button rmBtn = new Button("Remove");
		rmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.remove(Double.parseDouble(xField.getText()),
						Double.parseDouble(yField.getText()));
			}
		});
		
		box.setSpacing(5);
		box.setPadding(new Insets(10, 0, 0, 10));
		box.getChildren().addAll(xField, yField, addBtn, rmBtn);
		
		return box;
	}
}
