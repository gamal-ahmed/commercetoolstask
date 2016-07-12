package de.task.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.commercetools.javacodingtask.models.Customer;
import de.commercetools.javacodingtask.models.Order;
import de.task.reader.CSVReader;
import de.task.utils.Properties;

/**
 * Customers/orders importer which load lines {@code CSVReader} load it in a two
 * different lists of Customers/orders.
 */
public class CustomersOrdersLoader implements Loader {
	private static final Log log = LogFactory.getLog(CustomersOrdersLoader.class);

	private CSVReader reader;

	public CustomersOrdersLoader(CSVReader reader) {
		this.reader = reader;
		this.customersBuffer = new ArrayList<Customer>();
		this.ordersBuffer = new ArrayList<Order>();
	}

	/**
	 * All lines come from {@code CSVReader.getFileLines()}
	 */
	private List<String> lines;
	private List<Customer> customersBuffer;

	public List<Customer> getCustomersBuffer() {
		return customersBuffer;
	}

	public void setCustomersBuffer(List<Customer> customersBuffer) {
		this.customersBuffer = customersBuffer;
	}

	public List<Order> getOrdersBuffer() {
		return ordersBuffer;
	}

	public void setOrdersBuffer(List<Order> ordersBuffer) {
		this.ordersBuffer = ordersBuffer;
	}

	private List<Order> ordersBuffer;

	/**
	 * CSV header line. Because this line may be repeated, we store it for
	 * further comparing
	 */
	private String headerLine;
	/**
	 * This value points to line index in {@code lines} list from which next
	 * {@link #loadOrdersCustomersListFromSource()} will start reading. Default
	 * is 0, when last line is read {@code lastLine == lines.size()}
	 */
	private int lastLineIndex;
	private Customer currentCustomer;
	private Order currentOrder;

	public void loadOrdersCustomersListFromSource() throws IOException {

		if (lines == null) {
			log.debug("Start reading  from CSV file \"" + Properties.CSV_FILE_PATH + "\"");
			lines = reader.getFileLines();
			lastLineIndex = 0;
			log.debug("Read " + lines.size() + " lines from file \"" + Properties.CSV_FILE_PATH + "\"");
			headerLine = lines.size() > 0 ? lines.get(0) : "";
		}

		int counter = 0;

		while (counter++ < Properties.MEMORY_MAX_LIMIT && lastLineIndex++ != lines.size()) {

			String line = lines.get(lastLineIndex);

			// skip empty lines and header

			if (line.length() == 0 || headerLine.equals(line))
				continue;

			// because order id is not provided in CSV, we generate it
			// pair.order.setId(CustomerOrderUtil.generateOrderUUID());
			parseLineToCustomerOdrder(line, lastLineIndex);
			customersBuffer.add(currentCustomer);
			ordersBuffer.add(currentOrder);

		}

	}

	public void parseLineToCustomerOdrder(String line, int lastLineIndex) {
		log.debug("parsing line no " + lastLineIndex);

		if (line == null || line.equals(""))
			return;
		String[] split = line.split(",");
		// parse customer
		currentCustomer = new Customer();
		currentCustomer.setId(split[0]);
		currentCustomer.setFirstName(split[1]);
		currentCustomer.setLastName(split[2]);
		currentCustomer.setAge(Integer.valueOf(split[3]));
		currentCustomer.setStreet(split[4]);
		currentCustomer.setCity(split[5]);
		currentCustomer.setState(split[6]);
		currentCustomer.setZip(split[7]);

		// parse order
		currentOrder = new Order();
		currentOrder.setCustomerId(currentCustomer.getId());
	//	currentOrder.setCurrency(Currency.getInstance(split[8].substring(0, 1)));
		currentOrder.setPick(split[9]);
	//	currentOrder.setCentAmount(Long.parseLong(split[8].substring(1)));

	}

	public boolean isBuffersEmpty() throws IOException {
		if (customersBuffer != null && ordersBuffer != null)
			/*
			 * according to the task pdf there is a bussines rule that there are
			 * no orders without customer or customers without orders
			 * 
			 */
			if (customersBuffer.isEmpty() || ordersBuffer.isEmpty())
			return true;
			else
			return false;
		return true;
	}

	public void freeBuffers() {

		if (customersBuffer != null && ordersBuffer != null) {
			customersBuffer.clear();
			ordersBuffer.clear();
		}

	}

	public boolean isFileHasMoreDataToLoad() {
		return !(lastLineIndex == lines.size());
	}

}
