package working;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import masks.DataManager;
import masks.XYMask;

/**
 * Creates a scatter chart from the data in a table. Allows the user to add and
 * remove data points and displays statistical information of the sets.
 * 
 * @author Melinda Robertson
 * @version 28 March 2015
 */
public class TableChart extends Application {

    /**
     * The number of lines that the x and y statistics are separate.
     */
    public static final int NUMBER_LINES = 10;

    /**
     * For displaying the double values.
     */
    private final NumberFormat DF = new DecimalFormat("#0.00");

    /**
     * Holds all components.
     */
    private HBox mainHolder;
    /**
     * Holds the x y table and statistics(calcBox).
     */
    private BorderPane tableBox;
    /**
     * Holds the statistics.
     */
    private GridPane calcBox;

    /**
     * Displays the x-y pairs from the table.
     */
    private ScatterChart<Number, Number> chart;
    /**
     * The table for the x-y pairs.
     */
    private TableView<XYChart.Data<Number, Number>> table;
    /**
     * The data for the chart.
     */
    // private ObservableList<XYChart.Data<Number, Number>> data;

    /**
     * The text objects that display the statistics.
     */
    private ObservableList<Text> display;
    private DataManager data;

    /**
     * Starts the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Scatter Chart");

        data = new DataManager();

        Group root = new Group();
        Scene scene = new Scene(root);
        mainHolder = new HBox();

        // Builds a border pane that holds the x-y table and statistics.
        buildTableBox();

        // ----------CREATE THE CHART------------------
        buildChart();

        // ---------ADD ALL THE THINGS---------------------
        mainHolder.getChildren().addAll(chart, tableBox);

        // Set the style and show the stage.
        scene.getStylesheets().add("scatter/scatter.css");
        stage.setScene(scene);
        root.getChildren().addAll(mainHolder);
        stage.show();
    }

    /**
     * Creates the chart to display data points.
     */
    private void buildChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setSide(Side.BOTTOM);
        xAxis.setLabel("X");
        yAxis.setSide(Side.LEFT);
        yAxis.setLabel("Y");

        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setData(data.getData());

        chart = new ScatterChart<Number, Number>(xAxis, yAxis);
        chart.getData().add(series);
        chart.setLegendVisible(false);
    }

    /**
     * Creates a border pane to hold the x-y table and statistics.
     */
    @SuppressWarnings("unchecked")
    private void buildTableBox() {
        tableBox = new BorderPane();
        tableBox.getStyleClass().add("border-pane");

        Label label = new Label("X|Y Table");

        // --------------CREATE THE TABLE----------------------
        table = new TableView<XYChart.Data<Number, Number>>();
        // data = FXCollections.observableArrayList();

        TableColumn<XYChart.Data<Number, Number>, Double> xCol = new TableColumn<XYChart.Data<Number, Number>, Double>(
                "X");

        xCol.setCellValueFactory(new PropertyValueFactory<XYChart.Data<Number, Number>, Double>(
                "XValue"));
        xCol.setOnEditCommit(new EventHandler<CellEditEvent<XYChart.Data<Number, Number>, Double>>() {

            @Override
            public void handle(
                    CellEditEvent<XYChart.Data<Number, Number>, Double> e) {
                ((Data<Number, Number>) e.getTableView().getItems()
                        .get(e.getTablePosition().getRow())).setXValue(e
                        .getNewValue());
            }

        });
        TableColumn<XYChart.Data<Number, Number>, Double> yCol = new TableColumn<XYChart.Data<Number, Number>, Double>(
                "Y");
        yCol.setCellValueFactory(new PropertyValueFactory<XYChart.Data<Number, Number>, Double>(
                "YValue"));
        yCol.setOnEditCommit(new EventHandler<CellEditEvent<XYChart.Data<Number, Number>, Double>>() {
            @Override
            public void handle(
                    CellEditEvent<XYChart.Data<Number, Number>, Double> e) {
                ((Data<Number, Number>) e.getTableView().getItems()
                        .get(e.getTablePosition().getRow())).setYValue(e
                        .getNewValue());
            }
        });
        table.setItems(data.getData());
        table.getColumns().addAll(xCol, yCol);
        table.setMaxWidth(162);

        // -------------CONSTRUCT THE BORDERPANE--------------
        tableBox.setTop(label);
        tableBox.setBottom(buildAddPane());
        buildCalcBox();
        tableBox.setCenter(calcBox);
        tableBox.setLeft(table);
    }

    /**
     * Creates a panel to add and remove items in the x-y table and chart.
     * 
     * @return a panel with some text fields and buttons.
     */
    private HBox buildAddPane() {
        HBox box = new HBox();

        // -------------FIELDS----------------------
        final TextField xField = new TextField();
        xField.setPromptText("X");

        final TextField yField = new TextField();
        yField.setPromptText("Y");

        // ---------------BUTTONS--------------------
        final Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                try {
                    data.add(Double.parseDouble(xField.getText()),
                            Double.parseDouble(yField.getText()));
                } catch (InputMismatchException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Please input a number.");
                }

                xField.clear();
                yField.clear();
                updateCalcBox();
                xField.requestFocus();
            }

        });
        addBtn.addEventFilter(KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {

                    @Override
                    public void handle(KeyEvent arg0) {
                        if (KeyCode.ENTER.equals(arg0.getCode())) {
                            addBtn.fire();
                        }
                    }

                });

        final Button rmBtn = new Button("Remove");
        rmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Double x = Double.parseDouble(xField.getText());
                    Double y = Double.parseDouble(yField.getText());
                    data.remove(x, y);
                } catch (InputMismatchException e2) {
                    JOptionPane.showMessageDialog(null,
                            "Please input a number.");
                }

                xField.clear();
                yField.clear();
                updateCalcBox();
                xField.requestFocus();
            }
        });
        rmBtn.addEventFilter(KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent e) {
                        if (KeyCode.ENTER.equals(e.getCode())) {
                            rmBtn.fire();
                        }
                    }
                });

        // Setup the box and add children.
        box.setSpacing(5);
        box.setPadding(new Insets(10, 0, 0, 10));
        box.getChildren().addAll(xField, yField, addBtn, rmBtn);

        return box;
    }

    /**
     * Creates the grid pane to display statistics from the x-y table.
     */
    private void buildCalcBox() {
        // Layout things.
        calcBox = new GridPane();
        calcBox.getStyleClass().add("grid");
        calcBox.getColumnConstraints().add(new ColumnConstraints(100));
        calcBox.getColumnConstraints().add(new ColumnConstraints(100));
        calcBox.getColumnConstraints().add(new ColumnConstraints(100));

        int n = 0;
        // --------------LABELS-------------------
        calcBox.add(new Label("X Stats"), 1, n);
        calcBox.add(new Label("Y Stats"), 2, n++);
        calcBox.add(new Label("N:"), 0, n++);
        calcBox.add(new Label("Mean:"), 0, n++);
        calcBox.add(new Label("Mode:"), 0, n++);
        calcBox.add(new Label("Range:"), 0, n++);
        calcBox.add(new Label("StDv:"), 0, n++);
        
        calcBox.add(new Label("Min:"), 0, n++);
        calcBox.add(new Label("Q1:"), 0, n++);
        calcBox.add(new Label("Median:"), 0, n++);
        calcBox.add(new Label("Q3:"), 0, n++);
        calcBox.add(new Label("Max:"), 0, n++);

        // -------------DISPLAY FIELDS-----------------
        display = FXCollections.observableArrayList();
        for (int i = 1; i <= NUMBER_LINES; i++) {
            Text text1 = new Text();
            calcBox.add(text1, 1, i);
            display.add(text1);

            Text text2 = new Text();
            calcBox.add(text2, 2, i);
            display.add(text2);
        }

    }

    /**
     * Updates the text in the calcBox.
     */
    private void updateCalcBox() {
        set(0, f(data.stat().n()));
        set(1, f(data.stat().n()));
        set(2, f(data.stat().mean(XYMask.X)));
        set(3, f(data.stat().mean(XYMask.Y)));
        set(4, f(data.stat().mode(XYMask.X)));
        set(5, f(data.stat().mode(XYMask.Y)));
        set(6, f(data.stat().range(XYMask.X)));
        set(7, f(data.stat().range(XYMask.Y)));
        set(8, f(data.stat().stdev(XYMask.X)));
        set(9, f(data.stat().stdev(XYMask.Y)));
        set(10, f(data.stat().min(XYMask.X)));// min 10 11
        set(11, f(data.stat().min(XYMask.Y)));
        set(12, f(data.stat().q1(XYMask.X)));// Q1 12 13
        set(13, f(data.stat().q1(XYMask.Y)));
        set(14, f(data.stat().median(XYMask.X)));
        set(15, f(data.stat().median(XYMask.Y)));
        set(16, f(data.stat().q3(XYMask.X)));// Q3 16 17
        set(17, f(data.stat().q3(XYMask.Y)));
        set(18, f(data.stat().max(XYMask.X)));// max 18 19
        set(19, f(data.stat().max(XYMask.Y)));
    }

    private String f(double k) {
        return "" + DF.format(k);
    }

    private void set(int index, String stat) {
        display.get(index).setText(stat);
    }

    /**
     * Main Method!
     * 
     * @param args are the args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
