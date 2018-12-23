package org.insilico.sbmlsheets.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class SheetReader {
	static final String DEFAULTSEPERATOR = ",";
	
	public static Spreadsheet readSheetFromFile(String uri) {
		LinkedList<LinkedList<String>> content = new LinkedList<LinkedList<String>>();
		String seperator = DEFAULTSEPERATOR;
		
		try (Scanner s = new Scanner(new FileReader(uri))){
			String line = s.nextLine();
			
			seperator = findSeperator(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while parsing csv or tsv file");
			e.printStackTrace();
		}
		
		return new Spreadsheet();
	}
	
	private static final String findSeperator(String line) {
		return ",";
	}

}
