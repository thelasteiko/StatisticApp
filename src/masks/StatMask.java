package masks;

import java.util.Collection;

/**
 * An interface that defines methods to interact with a Statistics object.
 * Acts as a go between for complex data so that it can be manipulated more
 * easily. As a note, all methods should return a sorted set. Statistics
 * assumes that all lists will be sorted.
 * @author Melinda Robertson
 * @version 20151002
 */
public interface StatMask<T extends Collection<E>,E> {
	
	/**
	 * Sets the data using a collection, type changes based on
	 * type of mask.
	 * @param list is the collection of data.
	 */
	public void setData(T list);
	/**
	 * Returns the requested column.
	 * @param c is the number of the data set to return.
	 * @return a list of the numbers in the requested data set.
	 */
	public double[] col(int c);
	/**
	 * Returns the requested row.
	 * @param r is the number of the data row to return.
	 * @return a list of all numbers in the requested row.
	 */
	public double[] row(int r);
	/**
	 * Returns the requested rows.
	 * @param r are the numbers of the rows to return.
	 * @return a table of the rows requested.
	 */
	public double[][] rows(int... r);
	/**
	 * Returns the first column multiplied by the second.
	 * @param c1 is the first column.
	 * @param c2 is the second column.
	 * @return a list of the product results from c1*c2.
	 */
	public double[] colcol(int c1, int c2); //can also be used for squaring
	/**
	 * Returns all the requested columns in the order requested.
	 * @param col is the list of columns to retrieve.
	 * @return a table of requested data sets.
	 */
	public double[][] cols(int... col);
	/**
	 * Retrieve all available data sets without any transformations.
	 * @return a table of all data sets.
	 */
	public double[][] all();
	/**
	 * The number of data points.
	 * @return the number of data points.
	 */
	public int size();
	
	/**
	 * Returns one list of all numbers in the specified rows
	 * and columns.
	 * @param c
	 * @param r
	 * @return a list of numbers.
	 */
	public double[] flatrange(int[] c, int[] r);
}
