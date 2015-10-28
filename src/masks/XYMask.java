package masks;

import java.util.Arrays;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 * A mask that uses an observable list to create and manage a standard listing
 * of x y pairs. For use in Statistics.
 * 
 * @author Melinda Robertson
 * @version 20151005
 */
public class XYMask implements
        StatMask<ObservableList<Data<Number, Number>>, Data<Number, Number>> {
    
    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;
    public static final int X2 = 3;
    public static final int Y2 = 4;

    /**
     * The data that will be used to calculate various statistics.
     */
    private ObservableList<XYChart.Data<Number, Number>> data;

    /**
     * Stores the observable list for later reference. As a note, all methods
     * use the reference to retrieve data so that it is always the most up to
     * date information.
     * 
     * @param data
     *            is the Observable List.
     */
    public XYMask(ObservableList<XYChart.Data<Number, Number>> data) {
        setData(data);
    }

    public XYMask() {
        this.data = null;
    }

    @Override
    public double[] col(int c) throws NullPointerException {
        if (data == null) throw new NullPointerException();
        if (c > 4 || c < 0) throw new NullPointerException();
        double[] x = new double[data.size()];

        int i = 0;
        switch (c) {
        case X:
            for (XYChart.Data<Number, Number> d : data)
                x[i++] = (Double) d.getXValue();
            break;
        case Y:
            for (XYChart.Data<Number, Number> d : data)
                x[i++] = (Double) d.getYValue();
            break;
        case XY:
            for (XYChart.Data<Number, Number> d : data)
                x[i++] = (Double) d.getXValue() * (Double) d.getYValue();
            break;
        case X2:
            for (XYChart.Data<Number, Number> d : data)
                x[i++] = (Double) d.getXValue() * (Double) d.getXValue();
            break;
        case Y2:
            for (XYChart.Data<Number, Number> d : data)
                x[i++] = (Double) d.getYValue() * (Double) d.getYValue();
            break;
        }
        //Arrays.sort(x);
        return x;
    }
    
    @Override
    public double[][] cols(int... c) throws NullPointerException {
        if (data == null) throw new NullPointerException();
        if (c.length == 0) throw new NullPointerException();
        double[][] cols = new double[c.length][data.size()];
        int j = 0;
        for (XYChart.Data<Number, Number> d : data) {
            for (int i = 0; i < c.length; i++) {
                cols[i][j] = value(d, c[i]);
            }
            j++;
        }
        return cols;
    }
    
    /**
     * Gets the correct value for the requested column on
     * the indicated Data object.
     * @param d is the Data object.
     * @param c is the requested column.
     * @return the result.
     */
    @SuppressWarnings("rawtypes")
    private double value(Data d, int c) {
        switch (c) {
        case X:
            return (Double) d.getXValue();
        case Y:
            return (Double) d.getYValue();
        case XY:
            return (Double) d.getXValue()
                    * (Double) d.getYValue();
        case X2:
            return (Double) d.getXValue()
                    * (Double) d.getXValue();
        case Y2:
            return (Double) d.getYValue()
                    * (Double) d.getYValue();
        default:
            throw new NullPointerException();
        }
    }

    @Override
    public double[] colcol(int c1, int c2) throws NullPointerException {
        if (data == null) throw new NullPointerException();
        if (c1 > 1 || c2 > 1 || c1 < 0 || c2 < 0) throw new NullPointerException();
        double[] xy = new double[data.size()];
        int i = 0;
        for (XYChart.Data<Number, Number> d : data) {
            double x = value(d, c1);
            double y = value(d, c2);
            xy[i++] = x * y;
        }
        //Arrays.sort(xy);
        return xy;
    }

    @Override
    public double[] row(int r) throws NullPointerException {
        if (data == null) throw new NullPointerException();
        if (r > data.size() || r < 0) throw new NullPointerException();
        double[] row = new double[2];
        Data<Number, Number> d = data.get(r);
        row[0] = (Double) d.getXValue();
        row[1] = (Double) d.getYValue();
        return row;
    }

    @Override
    public double[][] rows(int... r) throws NullPointerException {
        // Each r[i] pertains to a Data object in data
        if (data == null) throw new NullPointerException();
        if (r.length > data.size()) throw new NullPointerException();
        double[][] rows = new double[2][r.length];
        for (int i = 0; i < rows.length; i++) {
            Data<Number, Number> d = data.get(r[i]);
            rows[0][i] = (Double) d.getXValue();
            rows[1][i] = (Double) d.getYValue();
        }
        return rows;
    }

    @Override
    public double[][] all() {
        if (data == null) throw new NullPointerException();
        double[][] dat = new double[5][this.data.size()];
        int i = 0;
        for (Data<Number, Number> d : data) {
            dat[X][i] = value(d, X);
            dat[Y][i] = value(d, Y);
            dat[XY][i] = value(d, XY);
            dat[X2][i] = value(d, X2);
            dat[Y2][i++] = value(d, Y2);
        }
        return dat;
    }
    
    @Override
    public double[] flatrange(int[] c, int[] r) throws NullPointerException {
        double[][] a = cols(c); //would be better to do this at the same time
        double[] ret = new double[a.length * r.length];
        int k = 0;
        for(int i = 0; i < a.length; i++) {
             for(int j = 0; j < r.length; j++) {
                ret[k] = a[i][r[j]]; 
             }
        }
        return ret;
    }

    @Override
    public int size() {
        if (data == null) return -1;
        return this.data.size();
    }

    @Override
    public void setData(ObservableList<Data<Number, Number>> list) {
        this.data = (ObservableList<Data<Number, Number>>) list;
    }
}
