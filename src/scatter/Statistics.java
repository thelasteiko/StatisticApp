package scatter;

import java.util.Arrays;
import java.util.Collection;

import masks.StatMask;

/**
 * Calculates statistics from XYChart data that are numbers.
 * 
 * @author Melinda Robertson
 * @version 20151026
 */
public class Statistics<T extends Collection<E>, E> {

    private StatMask<T, E> data;

    /**
     * Creates a reference for retrieving statistical information. Input data is
     * sorted in ascending order and the mean is calculated.
     * 
     * @param data is the data to reference.
     */
    public Statistics(StatMask<T, E> data) {
        this.data = data;
    }
    
    public Statistics() {
        data = null;
    }

    /**
     * Retrieves the mask for the source data.
     * 
     * @return the mask holding the source data.
     */
    public StatMask<T, E> mask() {
        return data;
    }
     
    /**
     * Retrieve the average value. 
     * @param col is the number of the data set from the StatMask.
     * @return the mean.
     */
    public double mean(int col) {
        return mean(data.col(col));
    }

    /**
     * Retrieve the average value.
     * @param a is the data set.
     * @return the mean.
     */
    public double mean(double[] a) {
        double mean = 0;
        for (int i = 0; i < a.length; i++) {
            mean += a[i];
        }
        mean = mean / a.length;
        return mean;
    }
    
    /**
     * Retrieve the middle value.
     * @param col is the number of the data set from the StatMask.
     * @return the median.
     */
    public double median(int col) {
        return median(data.col(col));
    }

    /**
     * Retrieve the middle value.
     * @param a is the set of data.
     * @return the median.
     */
    public double median(double[] a) {
        double median = 0;
        if (a.length == 2) {
            median = (a[0] + a[1]) / 2;
        } else if (a.length == 1) {
            return a[0];
        } else if (a.length % 2 == 0) {
            median = (a[a.length / 2] + a[(a.length / 2) + 1]) / 2;
        } else {
            median = a[a.length / 2];
        }
        return median;
    }
    
    /**
     * Retrieve the most frequent value.
     * @param col is the number of the data set from the StatMask.
     * @return the mode.
     */
    public double mode(int col) {
        return mode(data.col(col));
    }

    /**
     * Calculate the most frequent number.
     * @param a is the data set.
     * @return the mode.
     */
    public double mode(double[] a) {
        double mode = 0;
        double current = a[0];
        int count = 0;
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == current) count++;
            else {
                current = a[i];
                count = 0;
            }
            if (max <= count) {
                max = count;
                mode = current;
            }
        }
        return mode;
    }
    
    /**
     * Calculate the range.
     * @param c is the number of the data set from the StatMask.
     * @return the range.
     */
    public double range(int c) {
        return range(data.col(c));
    }

    /**
     * Calculate the range.
     * @param a is the sorted data set.
     * @return the range.
     */
    public double range(double[] a) {
        return a[a.length - 1] - a[0];
    }
    
    /**
     * Calculate the deviation from the mean.
     * @param c is the number of the data set from the StatMask.
     * @return the standard deviation.
     */
    public double stdev(int c) {
        return stdev(data.col(c));
    }

    /**
     * Calculate the deviation from the mean.
     * @param a is the data set.
     * @return the standard deviation.
     */
    public double stdev(double[] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - mean(a), 2);
        }
        sum = sum / (a.length - 1);
        return Math.sqrt(sum);
    }
    
    /**
     * Calculate the y intercept for the line of best fit.
     * @param input is the number of the data set from the StatMask that
     *              represents the x-values.
     * @param output is the number of the data set from the StatMask that
     *              represents the y-values.
     * @return the y-intercept for the regression line.
     */
    public double a(int input, int output) {
        return a(data.col(input), data.col(output));
    }
    /**
     * Calculates the y-intercept for the line of best fit.
     * @param input is the data set for x-values.
     * @param output is the data set for y-values.
     * @return the y-intercept of the regression line.
     */
    public double a(double[] input, double[] output) {
        double sumx = this.sum(input);
        double sumy = this.sum(output);
        double sumxy = sum(multiply(input, output));
        double sumx2 = this.sum(multiply(input, input));
        double a = ((sumy * sumx2) - (sumx * sumxy))
                / ((input.length * sumx2) - (Math.pow(sumx, 2)));
        return a;
    }
    
    /**
     * Multiply two sets of numbers.
     * @param a the first set.
     * @param b the second set.
     * @return a list of products.
     */
    public double[] multiply(double[] a, double[] b) {
        if(a.length != b.length) return null;
        double[] ret = new double[a.length];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = a[i] * b[i];
        }
        return ret;
    }
    
    
    
    /**
     * Calculates the slope of the line of best fit.
     * @param input is the number of the data set from the StatMask that
     *              represents the x-values.
     * @param output is the number of the data set from the StatMask that
     *              represents the y-values.
     * @return the slope of the regression line.
     */
    public double b(int input, int output) {
        return b(data.col(input), data.col(output));
    }
    //TODO change the sorting of values...
    public double b(double[] input, double[] output) {
        double sumx = this.sum(input);
        double sumy = this.sum(output);
        double sumxy = this.sum(multiply(input, output));
        double sumx2 = this.sum(multiply(input, input));
        double b = (input.length * sumxy - sumx * sumy)
                / (input.length * sumx2 - Math.pow(sumx, 2));
        return b;
    }
    
    /**
     * 
     * @param input
     * @param output
     * @return
     */
//    public String regression(int input, int output) {
//        NumberFormat DF = new DecimalFormat("#0.00");
//        return "y = " + DF.format(a(input, output)) + " + "
//                + DF.format(b(input, output)) + "x";
//    }
    
    
    public double predict(int input, int output, double x) {
        return a(input, output) + b(input, output) * x;
    }
    
    public double predict(double[] input, double[] output, double x) {
        return a(input, output) + b(input, output) * x;
    }
    
    /**
     * Calculates how closely related two sets of data are.
     * -1 < r < -0.5 strong negative relationship
     * -0.5 < r < 0 weak negative relationship
     * 0 < r < 0.5 weak positive relationship
     * 0.5 < r < 1 strong positive relationship
     * @param c1 is the number of the first data set from the StatMask.
     * @param c2 is the number of the second data set from the StatMask.
     * @return the correlation coefficient.
     */
    public double r(int c1, int c2) {
        return r(data.col(c1), data.col(c2));
    }
    /**
     * Calculates how closely related two sets of data are.
     * -1 < r < -0.5 strong negative relationship
     * -0.5 < r < 0 weak negative relationship
     * 0 < r < 0.5 weak positive relationship
     * 0.5 < r < 1 strong positive relationship
     * @param c1 is the first data set.
     * @param c2 is the second data set.
     * @return the correlation coefficient.
     */
    public double r(double[] c1, double[] c2) {
        double sumx = this.sum(c1);
        double sumy = this.sum(c2);
        double sumxy = this.sum(multiply(c1, c2));
        double sumx2 = this.sum(multiply(c1, c1));
        double sumy2 = this.sum(multiply(c2, c2));
        double r = (n() * sumxy - sumx * sumy)
                / Math.sqrt((n() * sumx2 - Math.pow(sumx, 2))
                        * (n() * sumy2 - Math.pow(sumy, 2)));
        return r;
    }

    /**
     * Retrieves the lowest number in the set.
     * @param c is the number of the data set from the StatMask.
     * @return the minimum.
     */
    public double min(int c) {
        return min(data.col(c));
    }
    
    /**
     * Retrieves the lowest number in the set by sorting it.
     * @param a is the data set.
     * @return the minimum.
     */
    public double min(double[] a) {
        Arrays.sort(a);
        return a[0];
    }

    public double max(int c) {
        return max(data.col(c));
    }
    
    public double max(double[] a) {
        Arrays.sort(a);
        return a[a.length-1];
    }

    public double q1(int c) {
        return q1(data.col(c));
    }
    public double q1(double[] a) {
        Arrays.sort(a);
        if(a.length <= 3) return a[0];
        int m, q1a, q1b;
        if (a.length % 2 == 0) m = a.length / 2 - 1;
        else m = a.length / 2;

        if (m % 2 != 0) q1a = q1b = m / 2;
        else {
            q1a = m / 2;
            q1b = m / 2 + 1;
        }

        return (a[q1a] + a[q1b]) / 2;

    }

    public double q3(int c) {
        return q3(data.col(c));
    }
    public double q3(double[] a) {
        Arrays.sort(a);
        if (a.length == 1) return a[0];
        if (a.length == 2) return a[1];
        if (a.length == 3) return a[2];
        int m, q3a, q3b;
        m = a.length / 2 + 1;

        if (m % 2 != 0) q3a = q3b = m / 2 + a.length-m;
        else {
            q3a = m / 2 + a.length-m;
            q3b = m / 2 + a.length-m + 1;
        }
        return (a[q3a] + a[q3b]) / 2;
    }

    public int n() {
        return data.size();
    }

    public double sum(int c) {
        return sum(data.col(c));
    }

    // TODO this needs fixing. Need to find a better way
    // to analyze ranges.
    public double sum(int[] c, int[] r) {
        return sum(data.flatrange(c, r));
    }

    /**
     * Sums a list of doubles.
     * 
     * @param list is the list of doubles.
     * @return the sum of all numbers in the list.
     */
    public double sum(double[] list) {
        double sum = 0.0;
        for (int i = 0; i < list.length; i++) {
            sum += list[i];
        }
        return sum;
    }

    // B = (max-min)/s + 1
    // r = b * s + min

    /**
     * Calculate the number of buckets given the desired size
     * of each one or the size of each one given the buckets.
     * @param c is the number of the data set from the StatMask.
     * @param s is the desired size of the buckets or the number of buckets.
     * @return the number of buckets or the size of the buckets.
     */
    public double transform(int c, double s) {
        //TODO reevaluate how diff is calculated, does not work well with whole numbers
        /*
         * If diff is < 2
         *  diff / s + diff / n()
         * else
         *  diff / s + 1
         */
        double diff = Math.abs(max(c) - min(c));
        return diff / s + diff / n();
    }

    /**
     * Returns the value of the lower bound for the indicated
     * 'bucket' for a histogram.
     * @param c is the number of the data set from the StatMask.
     * @param s is the size of the buckets.
     * @param b is the bucket number starting at 0 and going to the maximum number of buckets.
     * @return the lower bound for the bucket.
     */
    public double bound(int c, double s, int b) {
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
    public int numberof(int c, double s, int b) {
        double[] a = data.col(c);
        Arrays.sort(a);
        int count = 0;
        double r1 = bound(c, s, b);
        double r2 = bound(c, s, b + 1);
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= r1 && a[i] < r2) count++;
            if (a[i] >= r2) break;
        }
        return count;
    }
}
