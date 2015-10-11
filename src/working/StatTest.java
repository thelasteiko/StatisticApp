package working;

public interface StatTest {
    
    /**
     * Run the test on one set of data.
     * @param a the data to run the test on.
     * @return the result of the test.
     */
    public double runtest(double[] a);
    /**
     * Run the test on several sets of data.
     * @param a the data to run the test on.
     * @return a set of results from the test.
     */
    public double[] runtest(double[]... a);
    

}
