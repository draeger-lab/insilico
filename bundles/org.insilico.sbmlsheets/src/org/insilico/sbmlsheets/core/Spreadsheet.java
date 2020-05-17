package org.insilico.sbmlsheets.core;

import javax.inject.Inject;

public class Spreadsheet {
	  
  private final int INITIAL_ROWS = 3;
  String fileLocation;


  @Inject
  public Spreadsheet(String uri) {
     this.fileLocation = uri;
  }


  public String getFileLocation() {
    return fileLocation;

  }
	
}
