package tabs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import masks.DataManager;

/**
 * To make this work I need a data set that is specific to frequency.
 * Each 'bucket' will be a different XYChart.Data object with a title
 * and value.
 * This tab should have the following:
 *  ComboBox
 *  x2 TextFields
 *  BarChart
 *  TODO the chart works basically but needs tweaking on functions
 *      the labels don't work well
 *      the number of buckets or the size isn't matching up to what they should be
 *      
 * @author Melinda Robertson
 * @version 20151008
 */
public class HistogramTab extends TabBase {
    
    /**
     * The histogram.
     */
    private BarChart<String, Number> barchart;
    /**
     * The list of data items indicating how many data points
     * fall within each range.
     */
    private ObservableList<Data<String, Number>> buckets;
    /**
     * Holds the combo box and text fields that modify the data
     * for the histogram.
     */
    private VBox fieldbox;
    private TextField size;
    private TextField number;
    private ComboBox<String> setlist;
    
    /**
     * Creates a tab that has a histogram implemented using a
     * BarChart.
     * @param dm is the data manager holding the pertinent data.
     */
    public HistogramTab(DataManager dm) {
        super(dm);
        setText("Histogram");
        
        HBox mainholder = new HBox();
        buildChart();
        buildFields();
        
        barchart.setLegendVisible(false);
        
        mainholder.getChildren().addAll(barchart, fieldbox);
        setContent(mainholder);
        
    }
    
    /**
     * Creates the text fields and combo box.
     */
    private void buildFields() {
        fieldbox = new VBox();
        
        //------------Combo Box-------------------------
        Label lblcmb = new Label("Choose data set");
        setlist = new ComboBox<String>();
        ObservableList<String> mylist = FXCollections.observableArrayList();
        mylist.addAll("X", "Y", "XY", "X2", "Y2");
        setlist.setItems(mylist);
        setlist.setOnAction((observable)-> {
            update();
        });
        setlist.getSelectionModel().select(0);
        
        //-------------FIELDS----------------------
        Label lblsize = new Label("Size of Bars");
        size = new TextField();        
        Label lblnum = new Label("Number of Bars");
        number = new TextField();
        
        //-------------Action Listeners-----------------------
        size.setOnAction((observable) -> {
            int col = setlist.getSelectionModel().getSelectedIndex();
            double newsize = data.stat().transform(col, Double.parseDouble(size.getText()));
            System.out.println(newsize);
            number.setText(String.valueOf((int) newsize+1));
            update();
        });
        number.setOnAction((observable) -> {
            int col = setlist.getSelectionModel().getSelectedIndex();
            size.setText(String.valueOf(
                    data.stat().transform(
                            col, Integer.parseInt(number.getText()))));
            update();
        });
        fieldbox.getChildren().addAll(lblcmb, setlist, lblsize, size, lblnum, number);
    }
    
    /**
     * Creates the bar chart and set the series.
     */
    private void buildChart() {        
        //So I have one axis with named things
        final CategoryAxis bounds = new CategoryAxis();
        bounds.setSide(Side.BOTTOM);
        bounds.setLabel("Frequency Bands");  //the bottom; String
        //and one axis with numbers
        final NumberAxis frequencies = new NumberAxis();
        frequencies.setSide(Side.LEFT);
        frequencies.setLabel("Number of Data Points");   //the side; Numbers
        
        //which I add to my chart making sure that the axis' line up with the data type
        barchart = new BarChart<String, Number>(bounds, frequencies);
        buckets = FXCollections.observableList(new LinkedList<Data<String, Number>>());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setData(buckets);
        barchart.getData().add(series);
    }

    @Override
    public void update() {
        buckets.clear();    //clear the current list
        if(data.empty()) return;
        //defaults showing all values in one bar
        //n is number of buckets
        int n = 1;
        int col = setlist.getSelectionModel().getSelectedIndex();
      //s is size of buckets
        double s = data.stat().max(col)-data.stat().min(col);
        //fields will change when
        //the fields are updated then this will be called        
        if (size.getText().isEmpty() && number.getText().isEmpty()) return;
        else if (size.getText().isEmpty()) {    //get size from number
            n = Integer.parseInt(number.getText());
            s = data.stat().transform(col, n);
        } else {
            s = Double.parseDouble(size.getText());   //size makes number
            //TODO with number ranges > 1 this works
            //for < 1 it has -1 buckets...
            n = (int) data.stat().transform(col, s);
        }
        NumberFormat DF = new DecimalFormat("#0.#");
        for (int b = 0; b < n; b++) {   //fill data with the frequencies
            //TODO why aren't the strings showing when I change the size?
            String str = DF.format(data.stat().bound(col, s, b)) + " - " + DF.format(data.stat().bound(col, s, b+1));
            buckets.add(new Data<String, Number>(str, data.stat().numberof(col, s, b)));
        }
    }

}
