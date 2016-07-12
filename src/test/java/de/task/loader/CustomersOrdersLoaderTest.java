package de.task.loader;

import static org.junit.Assert.*;

import org.junit.Test;

import de.commercetools.javacodingtask.models.Customer;
import de.commercetools.javacodingtask.models.Order;

public class CustomersOrdersLoaderTest {

	@Test
	public void testCustomersOrdersLoader() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadOrdersCustomersListFromSource() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseLineToCustomerOdrder() {
		CustomersOrdersLoader loader = new CustomersOrdersLoader(null);
		loader.parseLineToCustomerOdrder("1,Emma,Silva,6,Nuteb Extension,Irupota,GA,04563,$5855.06,WHITE", 0);

		Customer c = new Customer();
		c.setId("1");
		c.setFirstName("Emma");
		c.setLastName("Silva");
		c.setAge(6);
		c.setStreet("Nuteb Extension");
		c.setCity("Irupota");
		c.setState("GA");
		c.setZip("04563");
		Order o = new Order();
		o.setCustomerId("1");

		o.setCentAmount(585506);
		o.setPick("WHITE");

		assertEquals(c, loader.getCurrentCustomer());
		assertEquals(o, loader.getCurrentOrder());

	}

}
