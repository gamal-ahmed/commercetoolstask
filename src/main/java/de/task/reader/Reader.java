package de.task.reader;

import java.io.IOException;
import java.util.List;

public interface Reader {

	 /**
	   * Fetch lines from CSV file.
	   *
	   * @return List of  strings.
	   * First line is header line. Some of entries may be empty or contain also header line.
	   * @throws IOException In case of exception during source reading.
	   */
	  List<String> getFileLines() throws IOException;
}
