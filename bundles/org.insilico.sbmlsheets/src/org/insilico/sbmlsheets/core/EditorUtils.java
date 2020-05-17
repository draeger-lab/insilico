package org.insilico.sbmlsheets.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditorUtils {
   
  
  private static void saveFile(String content, File file){
    try {
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    } catch (IOException ex) {
    }     
  }
  
  private static String createString(String text, String fileType) {
    StringBuilder string = new StringBuilder();
   
    return null;
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


  public static void saveTableToFile(CompoundTableView tableView, String separator) {
    tableView.getSelectionModel().selectAll();
    final ArrayList<Integer> rows = new ArrayList<>();
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();
    for (final TablePosition tablePosition : tableView.getSelectionModel().getSelectedCells()) {
      if (!rows.contains(tablePosition.getRow())){
        rows.add(tablePosition.getRow());
      }
      if (!cells.contains(tablePosition.getRow()+"_"+tablePosition.getColumn())){
        cells.add(tablePosition.getRow()+"_"+tablePosition.getColumn());
      }
      if (!cellColumns.contains(tablePosition.getColumn())) {
        cellColumns.add(tablePosition.getColumn());
      }
      
    }
    final StringBuilder strb = new StringBuilder();

    for (TableColumn col : tableView.getColumns()) {
        strb.append(col.getText());
        if (tableView.getColumns().indexOf(col)==tableView.getColumns().size()-1) {
          strb.append("\n");
        }else {
          strb.append(separator);
        }
      }
      boolean firstRow = true;
      for (final Integer row : rows) {
          for (final TableColumn<?, ?> column : tableView.getColumns()) {
              strb.append(column.getCellData(row) == null ? "" : column.getCellData(row));
              if (tableView.getColumns().indexOf(column)==tableView.getColumns().size()-1) {
                strb.append("\n");
              }else {
                strb.append(separator);
              }
          }
      }
    
      
    
      Stage stage = new Stage();
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showSaveDialog(stage);
      if(file != null){
        saveFile(strb.toString(), file);
      }
  }
  
  public static void saveTableToFile(CompartmentTableView tableView, String separator) {
    tableView.getSelectionModel().selectAll();
    final ArrayList<Integer> rows = new ArrayList<>();
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();
    for (final TablePosition tablePosition : tableView.getSelectionModel().getSelectedCells()) {
      if (!rows.contains(tablePosition.getRow())){
        rows.add(tablePosition.getRow());
      }
      if (!cells.contains(tablePosition.getRow()+"_"+tablePosition.getColumn())){
        cells.add(tablePosition.getRow()+"_"+tablePosition.getColumn());
      }
      if (!cellColumns.contains(tablePosition.getColumn())) {
        cellColumns.add(tablePosition.getColumn());
      }
      
    }
    final StringBuilder strb = new StringBuilder();

    for (TableColumn col : tableView.getColumns()) {
        strb.append(col.getText());
        if (tableView.getColumns().indexOf(col)==tableView.getColumns().size()-1) {
          strb.append("\n");
        }else {
          strb.append(separator);
        }
      }
      boolean firstRow = true;
      for (final Integer row : rows) {
          for (final TableColumn<?, ?> column : tableView.getColumns()) {
              strb.append(column.getCellData(row) == null ? "" : column.getCellData(row));
              if (tableView.getColumns().indexOf(column)==tableView.getColumns().size()-1) {
                strb.append("\n");
              }else {
                strb.append(separator);
              }
          }
      }
    
    
      Stage stage = new Stage();
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showSaveDialog(stage);
      if(file != null){
        saveFile(strb.toString(), file);
      }
  }
  
  public static void saveTableToFile(ReactionTableView tableView, String separator) {
   
    tableView.getSelectionModel().selectAll();
    final ArrayList<Integer> rows = new ArrayList<>();
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();
    for (final TablePosition tablePosition : tableView.getSelectionModel().getSelectedCells()) {
      if (!rows.contains(tablePosition.getRow())){
        rows.add(tablePosition.getRow());
      }
      if (!cells.contains(tablePosition.getRow()+"_"+tablePosition.getColumn())){
        cells.add(tablePosition.getRow()+"_"+tablePosition.getColumn());
      }
      if (!cellColumns.contains(tablePosition.getColumn())) {
        cellColumns.add(tablePosition.getColumn());
      }
      
    }
    final StringBuilder strb = new StringBuilder();

    for (TableColumn col : tableView.getColumns()) {
        strb.append(col.getText());
        if (tableView.getColumns().indexOf(col)==tableView.getColumns().size()-1) {
          strb.append("\n");
        }else {
          strb.append(separator);
        }
      }
      boolean firstRow = true;
      for (final Integer row : rows) {
          for (final TableColumn<?, ?> column : tableView.getColumns()) {
              strb.append(column.getCellData(row) == null ? "" : column.getCellData(row));
              if (tableView.getColumns().indexOf(column)==tableView.getColumns().size()-1) {
                strb.append("\n");
              }else {
                strb.append(separator);
              }
          }
      }
    
      
    
      Stage stage = new Stage();
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showSaveDialog(stage);
      if(file != null){
        saveFile(strb.toString(), file);
      }
  }
  
  
  public static void saveTableToFile(CustomTableView tableView, String separator) {
    tableView.getSelectionModel().selectAll();
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();

    for (final TablePosition tablePosition : tableView.getSelectionModel().getSelectedCells()) {
      if (!cells.contains(tablePosition.getRow()+"_"+tablePosition.getColumn())){
        cells.add(tablePosition.getRow()+"_"+tablePosition.getColumn());
      }
      if (!cellColumns.contains(tablePosition.getColumn())) {
        cellColumns.add(tablePosition.getColumn());
      }
      
    }
    final StringBuilder sB = new StringBuilder();

    for (TableColumn col : tableView.getColumns()) {
        sB.append(col.getText());
        if (tableView.getColumns().indexOf(col)==tableView.getColumns().size()-1) {
          sB.append("\n");
        }else {
          sB.append(separator);
        }
      }
      for (ObservableList<StringProperty> i : tableView.getSelectionModel().getSelectedItems()) {
        for (StringProperty sP : i) {
          sB.append(sP.getValue()+separator);
        }sB.append("\n");
      }
    
      
    
      Stage stage = new Stage();
      FileChooser fileChooser = new FileChooser();
      File file = fileChooser.showSaveDialog(stage);
      if(file != null){
        saveFile(sB.toString(), file);
      }
    }
}
