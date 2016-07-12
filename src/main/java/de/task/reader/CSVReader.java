package de.task.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class CSVReader implements Reader {

	private File file;
	private int noOfLines;

	public CSVReader(File file) {
		this.file = file;

	}

	public List<String> getFileLines() throws IOException {
		
		List<String> lines = new ArrayList<String>();
		//load file line by line to avoid out of memory exceptions
		LineIterator it = FileUtils.lineIterator(this.file, "UTF-8");
		try {
			while (it.hasNext()) {
				String line = it.nextLine();
				if (line.length() > 0)
					lines.add(line);

			}
		} finally {
			LineIterator.closeQuietly(it);
		}
		return lines;

	}

	public int getNoOfLines() {
		return noOfLines;
	}

	public void setNoOfLines(int noOfLines) {
		this.noOfLines = noOfLines;
	}

}
