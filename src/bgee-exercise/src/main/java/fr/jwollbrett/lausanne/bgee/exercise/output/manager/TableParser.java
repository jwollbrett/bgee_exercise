package fr.jwollbrett.lausanne.bgee.exercise.output.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

/**
 * Class allowing to parse tabulate files
 * @author Kobe70
 *
 */
public class TableParser {
	
	private static Logger LOGGER=Logger.getLogger(TableParser.class);
	private static String COLUMNSEPARATOR="\\t";
	private File outputFile;
	private BufferedReader br;
	private int currentLineNumber;
	private String[] columnHeaders;
	
	public TableParser() {
		currentLineNumber=-1;
	}
	
	private void readColumnLabels() throws IOException {
		currentLineNumber=currentLineNumber+1;
		String line=br.readLine();
		columnHeaders=line.split(COLUMNSEPARATOR);
		currentLineNumber=currentLineNumber+1;
	}
	
	private void initialize() throws IOException {
		br=new BufferedReader(new FileReader(outputFile));
		readColumnLabels();
	}
	
	/**
	 * Parse one line of a table file
	 * @return a {@code Collection} of {@code TableCellBean}
	 * @throws IOException
	 */
	public Collection<TableCellBean> parseLine() throws IOException {
		
		if (currentLineNumber == -1) {
			initialize();
		}
		
		Collection<TableCellBean> res=null;
		String line=br.readLine();
		currentLineNumber=currentLineNumber+1;		
		if (line != null)  {
			if (res==null) {
				res=new ArrayList<TableCellBean>();
			}
			String[] values=line.split(COLUMNSEPARATOR);
			
			int shortest=values.length;
			if (shortest > columnHeaders.length) {
				shortest=columnHeaders.length;
				LOGGER.warn("Line "+currentLineNumber+" number of values ("+values.length+") exceeds number of headers ("+columnHeaders.length+"). Extra values will be ignored.");				
			}
			if (shortest < columnHeaders.length) {
				LOGGER.warn("Line "+currentLineNumber+" number of values ("+values.length+") is less than numner of headers ("+columnHeaders.length+"). Result will contain missing values.");				
			}
			
			for (int index=0; index < shortest ; index++) {
				TableCellBean tcb=new TableCellBean();
				tcb.setLine(currentLineNumber);
				tcb.setColumn(index+1);
				tcb.setHeader(columnHeaders[index]);
				tcb.setContents(values[index]);
				res.add(tcb);
			}
		}
		return res;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	

}
