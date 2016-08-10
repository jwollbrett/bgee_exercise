package fr.jwollbrett.lausanne.bgee.exercise.output.manager;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TableCellBean implements Serializable {
	
	private int line;
	
	private int column;
	
	private String header;
	
	private String contents;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	

}
