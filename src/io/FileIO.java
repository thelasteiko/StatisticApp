package io;

import java.io.File;
import java.util.Collection;

/**
 * An interface for file input/output.
 * @author Melinda Robertson
 * @version 20151011
 * @param <T> is the type of Collection the data to save or load is stored in.
 * @param <E> is the type of data that is stored in the Collection.
 */
public interface FileIO<T extends Collection<E>, E> {
	
	/**
	 * Loads from the given file a collection of data. If the
	 * directory is set only the file name is needed.
	 * @param path is the path of the file.
	 * @return a collection of data.
	 */
	public T load(File path);
	
	/**
	 * Saves to the given file path a collection of data.
	 * If the directory is set only the file name is needed.
	 * @param path is the path of the file.
	 * @param list is the collection to save to file.
	 */
	public void save(File path, T list);
	
	/**
	 * Retrieves the full path of the last file read.
	 * @return the full path name of the last file.
	 */
	public File lastfile();
	
	/**
	 * Loads a collection of data from the last file that
	 * was accessed.
	 * @return a collection of data.
	 */
	public T loadlast();
	
	/**
	 * Saves a collection of data to the last file accessed.
	 * @param list is the collection to save to file.
	 */
	public void savelast(T list);
}
