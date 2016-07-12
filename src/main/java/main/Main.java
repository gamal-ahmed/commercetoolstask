package main;

import java.io.File;
import java.io.IOException;

import de.commercetools.javacodingtask.client.Client;
import de.commercetools.javacodingtask.client.ClientFactory;
import de.task.loader.CustomersOrdersLoader;
import de.task.reader.CSVReader;
import de.task.upload.Uploader;
import de.task.utils.Properties;

public class Main {

	public static void main(String[] args) {

		File file = new File(Properties.CSV_FILE_PATH);
		CSVReader reader = new CSVReader(file);
		Client client = ClientFactory.create(Properties.SENDER_EMAIL);

		CustomersOrdersLoader loader;
		loader = new CustomersOrdersLoader(reader);
		Uploader uploader = new Uploader(client, loader);

		try {
			loader.loadOrdersCustomersListFromSource();
			uploader.uploadToServer();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// CustomersOrdersImporter importer=new CustomersOrdersImporter(file,
		// size)
	}

}
