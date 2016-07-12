package de.task.loader;

import java.io.IOException;
import java.util.List;

import de.commercetools.javacodingtask.models.Customer;
import de.commercetools.javacodingtask.models.Order;
import io.sphere.sdk.models.Base;

/**
 * Basic interface for fetching entities list from different data sources.
 */
public interface Loader {

	/**
	 * Split line to entries and parse them to customer and order properties.
	 * Returned order has {@code customerId} equal to customer {@code id}. Order
	 * @param line
	 *            comma separated line with customer and order values
	 * @param lastLineIndex
	 *            index of the last line
	 * @return Pair of parsed customer and order.
	 */
	void parseLineToCustomerOdrder(String line, int lastLineIndex);

	/**
	 * load order /customer list from source into memory.
	 *
	 * @throws IOException
	 *             In case of exception during source reading.
	 */
	void loadOrdersCustomersListFromSource() throws IOException;
	/**
	 * check the orders/customers buffer if are empty or no
	 *
	 * @return true if the buffers  empty or return false if buffers are not empty.
	 */
	boolean isBuffersEmpty() throws IOException;
	 
	/**
	 * delete the data from memory
	 */
	void freeBuffers();
	/**
	 * check the file for more data does'nt loaded yet according to {@code Properties.MEMORY_MAX_LIMIT}
	 *
	 * @return true if yes ,false if all data retrieved before
	 */
	boolean isFileHasMoreDataToLoad();

}
