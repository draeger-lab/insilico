package org.insilico.sbmlsheets.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.misc.IOUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.insilico.sbmlsheets.editor.SpreadsheetView;
import org.insilico.sbmlsheets.editor.TableEditorView;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;

public class SBTabHelper {

  public static void saveAsFile(String text, String fileType) {
    Stage stage = new Stage();
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = 
        new FileChooser.ExtensionFilter(fileType.toUpperCase()+" files (*."+fileType+")", "*."+fileType);
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(stage);
    if(file != null){
      saveFile(text, file);
    }
  }
  
  
  private static void saveFile(String content, File file){
    try {
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }     
  }


  public static String getFileContent(String uri) {
    final List<String> lines;
    try {
      StringBuilder sB = new StringBuilder();
         lines = Files.readAllLines(new File(uri).toPath());
         for (String s : lines) {
           sB.append(s.replaceAll("\t","  ")+"\n");
         }
         return sB.toString();
    } catch (IOException ex) { 
        return "";
    } 

  }
  
  public static String getFileContent() {
    Stage stage = new Stage();
    FileChooser chooser = new FileChooser();
    File file = chooser.showOpenDialog(stage);
    
    if (file != null) {
      final List<String> lines;
      try {
        StringBuilder sB = new StringBuilder();
           lines = Files.readAllLines(file.toPath());
           for (String s : lines) {
             sB.append(s.replaceAll("\t","  ")+"\n");
           }
           return sB.toString();
      } catch (IOException ex) { 
          return "";
      } 
    }
    return "";
  }  

  public String createSBTabDocFromTables(TableEditorView editor, boolean addCompounds, boolean addReactions, boolean addCompartments, boolean addImport) {
    String sbTabStr = "";
    
    if (addCompounds) {
      sbTabStr += editor.getCompoundTableView().toSBTabString();
      sbTabStr += "\n\n\n";
    }
    if (addCompartments) {
      sbTabStr += editor.getCompartmentTableView().toSBTabString();
      sbTabStr += "\n\n\n";
    }
    if (addReactions) {
      sbTabStr += editor.getReactionTableView().toSBTabString();
      sbTabStr += "\n\n\n";
    }
    if (addImport) {
      sbTabStr += editor.getImportTableView().toSBTabString();
      sbTabStr += "\n\n\n";
    }
    
    return sbTabStr;
  }
  
  
  
  private static String parseOther(List<String> lines, String tableFormat) {
    String sep = "\\t";
    StringBuilder sB = new StringBuilder();
    if (tableFormat.contains("txt")) {
      for (int i = 0; i < lines.size(); i++) {
        sB.append(lines.get(i) + "\\n");
      }
    }else {
      sep = tableFormat.split("separator: ")[1].replaceAll(")","");
      for (int i = 0; i < lines.size(); i++) {
        sB.append(lines.get(1).replaceAll(sep, "\\t") + "\\n");
      }
    }
    return sB.toString();
  }


  private static String parseTSV(List<String> lines) {
    StringBuilder sB = new StringBuilder();
    sB.append(lines.get(1).replaceAll("(\\s+|\\t+)*", "\\t") + "\\n");
    for (int i = 2; i < lines.size(); i++) {
      sB.append(lines.get(i).replaceAll("(\\s\\s+|\\t+)*", "\\t") + "\\n");
    }
    return sB.toString();  
    
  }

}
