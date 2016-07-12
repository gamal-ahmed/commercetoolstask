package de.task.upload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.commercetools.javacodingtask.client.Client;
import de.commercetools.javacodingtask.client.ImportResults;
import de.commercetools.javacodingtask.errors.ServiceUnavailableException;
import de.task.loader.CustomersOrdersLoader;
import de.task.utils.Properties;

/**
 * this class is to get data from {@code CustomersOrdersLoader} and upload them
 * to the server using {@code Client}.
 */
public class Uploader {
	private final static Log log = LogFactory.getLog(Uploader.class);

	/**
	 * Customers/orders loader which load the data come from {@code CSVReader}
	 * in the memory
	 */
	private CustomersOrdersLoader loader;

	/**
	 * Client for importing customer and order data.
	 */
	private Client client;

	public Uploader(Client client, CustomersOrdersLoader loader) {

		this.client = client;
		this.loader = loader;
	}

	/**
	 * Read data from reader according to {@code Properties.MEMORY_MAX_LIMIT} to
	 * avoid server failing response and send using client. If sending error
	 * occurs - retry {@code Properties.MAX_TRIES} times if we got 503 response.
	 *
	 * @return number of parsed and sent customer/order pairs.
	 */
	public int uploadToServer() {
		int uploadedRows = 0;

		try {
			if (loader.isBuffersEmpty())
				return -1;

			while (loader.isFileHasMoreDataToLoad()) {

				ImportResults customerResults = null;
				ImportResults orderResults = null;

				int tries = Properties.MAX_TRIES;
				do {
					try {
						customerResults = client.importCustomer(loader.getCustomersBuffer());
						uploadedRows += loader.getCustomersBuffer().size();
					} catch (ServiceUnavailableException serviceUnavailableException) {
						log.warn("ServiceUnavailableException", serviceUnavailableException);
						log.warn("Retrying");
					} catch (Exception exception) {
						log.error("Importing customers exception", exception);
						tries = 0;
					}
				} while (customerResults == null && tries-- > 0);

				tries = Properties.MAX_TRIES;
				do {
					try {
						orderResults = client.importOrders(loader.getOrdersBuffer());
						uploadedRows += loader.getOrdersBuffer().size();
					} catch (ServiceUnavailableException serviceUnavailableException) {
						log.warn("ServiceUnavailableException", serviceUnavailableException);
						log.warn("Retrying");
					} catch (Exception exception) {
						log.error("Importing orders exception", exception);
						tries = 0;
					}
				} while (orderResults == null && tries > 0);

				// if all file data not uploaded then load more in memory and
				// upload to the server
				if (loader.isFileHasMoreDataToLoad()) {
					loader.freeBuffers();
					loader.loadOrdersCustomersListFromSource();
				}
			}

		} catch (Exception exception) {
			log.error("Erro In Parsing the file", exception);
			return -1;
		}

		return uploadedRows;
	}
}
