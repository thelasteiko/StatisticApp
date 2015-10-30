package tabs;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import masks.DataManager;
import masks.XYMask;

public class SummaryTab extends TabBase {

    /**
     * The number of lines that the x and y statistics are separate.
     */
    public static final int NUMBER_LINES = 10;

    /**
     * For displaying the double values.
     */
    private final NumberFormat DF = new DecimalFormat("#0.00");
    /**
     * Holds the statistics(calcBox).
     */
    private GridPane calcBox;
    private VBox holder;
    /**
     * The text objects that display the statistics.
     */
    private ObservableList<Text> display;

    /**
     * Creates a summary frame for the data provided by the DataManager.
     * 
     * @param dm is the DataManager.
     */
    public SummaryTab(DataManager dm) {
        super(dm);
        setText("Summary");
        // Layout things.
        calcBox = new GridPane();
        calcBox.getStyleClass().add("all");
        calcBox.getStyleClass().add("grid");
        calcBox.getColumnConstraints().add(new ColumnConstraints(100)); // col 1
        calcBox.getColumnConstraints().add(new ColumnConstraints(100)); // col 2
        calcBox.getColumnConstraints().add(new ColumnConstraints(100)); // col 3

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
        holder = new VBox();
        holder.getStyleClass().add("all");
        
        Label eqlb = new Label("Equation:");
        Text equ = new Text();
        calcBox.add(eqlb, 0, n, 2, 1);
        calcBox.add(equ, 2, n++, 2, 1);
        display.add(equ);
        holder.getChildren().addAll(calcBox);
        
        setContent(holder);
        // update();
    }

    /**
     * Updates the text in the calcBox.
     */
    @Override
    public void update() {
        if (data.empty()) {
            clearall();
            return;
        }
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
        set(20, "y = " + f(data.stat().b(XYMask.X, XYMask.Y)) + "x + "
                + f(data.stat().a(XYMask.X, XYMask.Y)));
    }

    /**
     * Formats a double as a string in the form 0.00.
     * 
     * @param k is the number.
     * @return a string representation.
     */
    private String f(double k) {
        return "" + DF.format(k);
    }

    /**
     * Changes the indicated text field to the string.
     * 
     * @param index is the index of the text field.
     * @param stat is the new string.
     */
    private void set(int index, String stat) {
        display.get(index).setText(stat);
    }

    private void clearall() {
        for (Text t : display) {
            t.setText("");
        }
    }

}
