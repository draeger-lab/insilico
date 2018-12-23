package org.insilico.sbmlsheets.core;

import java.net.URL;
import java.util.LinkedList;

import javax.inject.Inject;

public class Spreadsheet {
	
	LinkedList<LinkedList<String>> data;
	
	String tableType;
	
	String tableName;
	@Inject
	URL fileLocation;
	
	public Spreadsheet() {
		data = new LinkedList<LinkedList<String>>();
		tableType = "";
		tableName = "";
	}
	@Inject
	public Spreadsheet(LinkedList<LinkedList<String>> data, String tableType, String tableName) {
		this.data = data;
		this.tableType = tableType;
		this.tableName = tableName;
	}
	
	public LinkedList<String> getRow(int index){
		return data.get(index);
	}
	
	public void setLastRow(LinkedList<String> row) {
		data.addLast(row);
	}
	
	public void setEmptyRow(int index) {
		data.add(index, new LinkedList<String>());
	}
	
	public void modifyRow(int index, LinkedList<String> row) {
		data.set(index, row);
	}
	
}
