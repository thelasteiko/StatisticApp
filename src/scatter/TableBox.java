package scatter;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TableCell;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//import javafx.util.Callback;
import masks.DataManager;

/**
 * This holds the x y table that list all the related data points. May have made
 * errors on how the data is maintained. Once I get this started... TODO check
 * to see if data is being updated correctly, I set the table to be edited
 * directly so I might remove the buttons if it works out.
 * 
 * @author Melinda Robertson
 * @version 20151015
 */
public class TableBox extends VBox implements Observer {

    /**
     * The table to hold x y pairs.
     */
    private TableView<XYChart.Data<Number, Number>> table;
    /**
     * Manages the data. Although the data manager is an observer, it has values
     * that needs to be manipulated by this object so it is included here for
     * convenience.
     */
    private DataManager data;

    /**
     * Constructs a two column table of x y pairs.
     * 
     * @param dm is the DataManager object that holds all the data.
     */
    @SuppressWarnings("unchecked")
    public TableBox(DataManager dm) {
        data = dm;
        data.addObserver(this);
        this.getStyleClass().add("all");
        this.getStyleClass().add("border-pane");
        this.setPadding(new Insets(10, 2, 2, 10));

        Label label = new Label("X|Y Table");

        table = new TableView<XYChart.Data<Number, Number>>();
        ObservableList<XYChart.Data<Number, Number>> d = data.getData();
        table.setItems(d);
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ScrollPane scpane = new ScrollPane(table);
        scpane.setHbarPolicy(ScrollBarPolicy.NEVER);
        
//        Callback<TableColumn<Data<Number, Number>, Double>, TableCell<Data<Number, Number>, Double>> cellFactory =
//                new Callback<TableColumn<Data<Number, Number>, Double>, TableCell<Data<Number, Number>, Double>>() {
//                    public TableCell<Data<Number, Number>, Double> call(TableColumn<XYChart.Data<Number, Number>, Double> p) {
//                        return new EditableTableCell();
//                    }
//                };

        TableColumn<XYChart.Data<Number, Number>, Double> xCol = new TableColumn<XYChart.Data<Number, Number>, Double>(
                "X");
        //xCol.setMaxWidth(10);
        //xCol.setMinWidth(50);
        xCol.setCellValueFactory(new PropertyValueFactory<XYChart.Data<Number, Number>, Double>(
                "XValue"));
        //xCol.setCellFactory(cellFactory);
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
        //yCol.setMaxWidth(10);
        //yCol.setMinWidth(50);
        yCol.setCellValueFactory(new PropertyValueFactory<XYChart.Data<Number, Number>, Double>(
                "YValue"));
        //yCol.setCellFactory(cellFactory);
        yCol.setOnEditCommit(new EventHandler<CellEditEvent<XYChart.Data<Number, Number>, Double>>() {

            @Override
            public void handle(
                    CellEditEvent<XYChart.Data<Number, Number>, Double> e) {
                ((Data<Number, Number>) e.getTableView().getItems()
                        .get(e.getTablePosition().getRow())).setYValue(e
                        .getNewValue());
            }

        });
        table.getColumns().addAll(xCol, yCol);
        
        this.setSpacing(3);
        this.getChildren().addAll(label, scpane, buildAddPane());
    }

    /**
     * Creates a pane for the add/delete buttons and text fields. After testing
     * this may be removed in favor of editing the table directly.
     * 
     * @return an HBox with two text fields and two buttons.
     */
    private HBox buildAddPane() {
        HBox box = new HBox();

        final TextField xField = new TextField();
        xField.setPromptText("X");
        xField.setMaxWidth(50);

        final TextField yField = new TextField();
        yField.setPromptText("Y");
        yField.setMaxWidth(50);

        final Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                try {
                    data.add(Double.parseDouble(xField.getText()),
                            Double.parseDouble(yField.getText()));
                } catch (InputMismatchException er) {
                    
                }
                xField.clear();
                yField.clear();
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
                data.remove(Double.parseDouble(xField.getText()),
                        Double.parseDouble(yField.getText()));
                } catch (InputMismatchException er) {
                    
                }
                xField.clear();
                yField.clear();
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

        box.setSpacing(5);
        box.setPadding(new Insets(10, 0, 0, 10));
        box.getChildren().addAll(xField, yField, addBtn, rmBtn);

        return box;
    }

    @Override
    public void update(Observable data, Object list) {
        if (!(data instanceof DataManager)) return;
        this.data = (DataManager) data;
        table.setItems(this.data.getData());
    }
}
