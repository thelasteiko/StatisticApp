package working;

import java.util.Arrays;
import java.util.Scanner;

import scatter.Statistics;

public class tester {

    public static void main(String[] args) {
        
        Statistics stat = new Statistics();
        
        double[] a = {
                -2, -1.76, -.54, 0.32, 3, 3.67, 5.32, 6.02, 7, 8.2
        };
        double[] b = {
                -.2, -.176, -.54, .032, .3, .367, .532, .602, .7, .82
        };
        int buckets = 4;
        double range = stat.transform(b, buckets);
        
        System.out.println(range);
        double last = stat.min(b);
        for (int i = buckets; i > 0; i--) {
            System.out.print(last + " + " + range + " = ");
            last += range;
            System.out.println(last);
        }
        System.out.println(last == stat.max(b));
    }
        
    public static void histogram() {
        int buckets = 3;
        double[][] a = {
                {6.7, 2.4, 1.7, 3.2, 5.7},
                {-1.4, 7.2, -8.9, -2.1, 3.4},
                {.8, .2, .43, .12, .74, .95}
        };
        
        System.out.println((max(a[2]) - min(a[2])) / a[0].length);
        //how do I step forward the correct amount?
        System.out.println("Buckets: " + buckets);
        for(int i = 0; i < a.length; i++) {
            double s = transform(a[i], buckets);
            System.out.println("Test " + i + ": ");
            for (int j = 0; j < buckets; j++) {
                System.out.println("Count: " + numberof(a[i], s, j));
            }
            
        }
        
        
    }
    
    /**
     * Calculate the number of buckets given the desired size
     * of each one or the size of each one given the buckets.
     * @param c is the number of the data set from the StatMask.
     * @param s is the desired size of the buckets or the number of buckets.
     * @return the number of buckets or the size of the buckets.
     */
    public static double transform(double[] c, int s) {
        //TODO needs to be modified for very small decimal sets
        double diff = max(c) - min(c); 
        return diff / s + diff / c.length;
    }
    
    /**
     * Retrieves the lowest number in the set by sorting it.
     * @param a is the data set.
     * @return the minimum.
     */
    public static double min(double[] a) {
        Arrays.sort(a);
        return a[0];
    }
    
    public static double max(double[] a) {
        Arrays.sort(a);
        return a[a.length-1];
    }
    
    /**
     * Returns the value of the lower bound for the indicated
     * 'bucket' for a histogram.
     * @param c is the number of the data set from the StatMask.
     * @param s is the size of the buckets.
     * @param b is the bucket number starting at 0 and going to the maximum number of buckets.
     * @return the lower bound for the bucket.
     */
    public static double bound(double[] c, double s, int b) {
        return b * s + min(c);
    }

    /**
     * Gets the number of data points for the indicated bucket.
     * 
     * @param c is the number of the data set from the StatMask.
     * @param s is the size of each bucket.
     * @param b is the bucket number from 0 to one less than the total number of buckets.
     *          0 <= b < B
     * @return the number of data points in the bucket.
     */
    public static int numberof(double[] c, double s, int b) {
        //double[] a = data.col(c);
        int count = 0;
        double r1 = bound(c, s, b);
        double r2 = bound(c, s, b + 1);
        System.out.println("Left Bound: " + r1);
        System.out.println("Right Bound: " + r2);
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= r1 && c[i] < r2) count++;
            if (c[i] >= r2) break;
        }
        return count;
    }

    public static void formatoutput() {
        String str = "java";
        int n = 9;
        System.out.printf("%-15s%03d", str, n);
    }

    public static void arrays() {
        int t, a, b, n, last;
        Scanner sc = new Scanner(System.in);
        t = sc.nextInt();

        while (t > 0) {
            a = sc.nextInt();
            b = sc.nextInt();
            n = sc.nextInt();
            last = a + b;
            System.out.print(last + " ");
            for (int i = 1; i < n; i++) {
                last += Math.pow(2, i) * b;
                if (i == n - 1) System.out.println(last);
                else System.out.print(last + " ");
            }
            t--;
        }
        sc.close();
    }
    
//    public static void table() {
//        TableView tableView = new TableView();
//        tableView.setEditable(true);
//        Callback<TableColumn, TableCell> cellFactory =
//                new Callback<TableColumn, TableCell>() {
//                    public TableCell call(TableColumn p) {
//                        return new EditingCell();
//                    }
//                };
//   
//        TableColumn columnMonth = new TableColumn("Month");
//        columnMonth.setCellValueFactory(
//                new PropertyValueFactory<Record,String>("fieldMonth"));
//   
//        TableColumn columnValue = new TableColumn("Value");
//        columnValue.setCellValueFactory(
//                new PropertyValueFactory<Record,Double>("fieldValue"));
//       
//        //--- Add for Editable Cell of Value field, in Double
//        columnValue.setCellFactory(cellFactory);
//        columnValue.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Record, Double>>() {
//                    @Override public void handle(TableColumn.CellEditEvent<Record, Double> t) {
//                        ((Record)t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())).setFieldValue(t.getNewValue());
//                    }
//                });
//    }

}
