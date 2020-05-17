package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.insilico.sbmlsheets.editor.IdentifierChooser;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CustomTableView extends TableView<ObservableList<StringProperty>>{

  private ObservableList<String[]> instances = FXCollections.observableArrayList();

  int firstLineToParse = 0;
  boolean parseHeader = true;
  String headerSeparator = "\\t";
  String instancesSeparator = "\\t";
  
  public CustomTableView(){
    String str = "Col1\tCol2\nitem1\titem2";
    populateTable(this, str ,true);
    setEditable(true);
    initSelectionListeners();
  }
  
  public CustomTableView(String string) {
    populateTable(this, string ,true);
    setEditable(true);
    initSelectionListeners();
  }
  
  public CustomTableView(String string, String format) {
    setTableFormat(format);
    populateTable(this, string ,true);
    setEditable(true);
    initSelectionListeners();
  }
  
  private void initSelectionListeners() {
    this.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
       if (e.getCode().equals( KeyCode.DELETE)) {
        if (e.isShortcutDown()) {
          removeSelectedColumns();
        }else {
        removeSelection();
        }
          
      }else if (e.getCode().equals(KeyCode.C) && e.isShortcutDown()) {
        copySelectionToClipboard();
        
      }else if (e.getCode().equals(KeyCode.A) && e.isShortcutDown()) {
        this.getSelectionModel().selectAll();
      }else if (e.isShortcutDown() && e.getCode().equals(KeyCode.N) && e.getCode().equals(KeyCode.R)) {
        addEmptyRow();
      }else if (e.isShortcutDown() && e.getCode().equals(KeyCode.N) && e.getCode().equals(KeyCode.C)) {
        addEmptyColumn();
      }else if (e.isShortcutDown()) {
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      }
      e.consume();
    });
  }
  
  public void copySelectionToClipboard() {
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();

    for (final TablePosition tablePosition : this.getSelectionModel().getSelectedCells()) {
      if (!cells.contains(tablePosition.getRow()+"_"+tablePosition.getColumn())){
        cells.add(tablePosition.getRow()+"_"+tablePosition.getColumn());
      }
      if (!cellColumns.contains(tablePosition.getColumn())) {
        cellColumns.add(tablePosition.getColumn());
      }
      
    }
    final StringBuilder sB = new StringBuilder();

    if (this.getSelectionModel().cellSelectionEnabledProperty().getValue()) {
      for (Integer col : cellColumns) {
        sB.append(this.getColumns().get(col).getText());
        if (cellColumns.indexOf(col)==cellColumns.size()-1) {
          sB.append("\n");
        }else {
          sB.append("\t");
        }
      }
      for (String s : cells) {
        for (Integer i : cellColumns) {
          String[] split = s.split("_");
          if (split[1].equals(String.valueOf(i))) {
            Object cellData = this.getColumns().get(i).getCellData(Integer.parseInt(split[0]));
            sB.append(cellData == null ? "" : cellData);
          }
          if (cellColumns.indexOf(i)==cellColumns.size()-1) {
            sB.append("\n");
          }else {
            sB.append("\t");
          }
        }
      }
      
    }else {
      for (TableColumn col : this.getColumns()) {
        sB.append(col.getText());
        if (this.getColumns().indexOf(col)==this.getColumns().size()-1) {
          sB.append("\n");
        }else {
          sB.append("\t");
        }
      }
      for (ObservableList<StringProperty> i : this.getSelectionModel().getSelectedItems()) {
        for (StringProperty sP : i) {
          sB.append(sP.getValue()+"\t");
        }sB.append("\n");
      }
    }
    
    ClipboardContent content = new ClipboardContent();
    content.putString(sB.toString());
    Clipboard.getSystemClipboard().setContent(content);
  }

  public void removeSelection() {
    for (ObservableList<StringProperty> i : this.getSelectionModel().getSelectedItems()) {
      this.getItems().remove(i);
    }
  }
  
  public void removeSelectedColumns() {
    HashSet<TableColumn> s = new HashSet<>();
    for (TablePosition t : this.getSelectionModel().getSelectedCells()) {
      if (t.getTableColumn().getUserData()!=null) {
        if (t.getTableColumn().getUserData().equals("editable")) {
          s.add(t.getTableColumn());
        }
      }
    }
    this.getColumns().removeAll(s);
  }
  
  private String getDataString() {
    String str = "!ID   !Name   !Location   !IsConstant !SBOTerm    !InitialConcentration   !hasOnlySubstanceUnits  !SBML:fbc:chemicalFormula   !SBML:fbc:charge    !Identifiers:bigg.metabolite\r\n" + 
        "M_glc__D_e  D-Glucose   e   False   SBO:0000247 nan False   C6H12O6 0   glc__D\r\n" + 
        "M_gln__L_c  L-Glutamine c   False   SBO:0000247 nan False   C5H10N2O3   0   gln__L\r\n" + 
        "M_gln__L_e  L-Glutamine e   False   SBO:0000247 nan False   C5H10N2O3   0   gln__L\r\n" + 
        "M_glu__L_c  L-Glutamate c   False   SBO:0000247 nan False   C5H8NO4 -1  glu__L\r\n";
    return str;
  }

  private void populateTable(
      final TableView<ObservableList<StringProperty>> table,
      final String dataStr, final boolean hasHeader) {
    table.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
      if (e.isPrimaryButtonDown() &&  e.getClickCount() > 1) {
          EventTarget target = e.getTarget();
          TableColumnBase<?, ?> column = null;
          while (target instanceof Node) {
              target = ((Node) target).getParent();
              if (target instanceof TableColumnHeader) {
                  column = ((TableColumnHeader) target).getTableColumn();
                  if (column != null) break;
              }
          }
          if (column != null) {
              TableColumnBase<?,?> tableColumn = column;
              TextField textField = new TextField(column.getText());
              textField.setMaxWidth(column.getWidth());
              textField.setOnAction(a -> {
                  tableColumn.setText(textField.getText());
                  tableColumn.setGraphic(null);
              });
              textField.focusedProperty().addListener((src, ov, nv) -> {
                  if (!nv) tableColumn.setGraphic(null);
              });
              column.setGraphic(textField);
              textField.requestFocus();
          }
          e.consume();
      }
  });
    
    table.getItems().clear();
    table.getColumns().clear();
    table.setPlaceholder(new Label("Loading..."));
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        int x = firstLineToParse;
        final String dataStr2 = dataStr.replaceAll("\\r",  "");
        if (hasHeader) {
          final String headerLine = dataStr2.split("\\n")[x];
          System.out.println("headerLine: "+headerLine);
          final String[] headerValues = headerLine.split(headerSeparator);
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              for (int column = x; column < headerValues.length; column++) {
                table.getColumns().add(
                    createColumn(column, headerValues[column]));
              }
            }
          });
        }

        String dataLine;
        int cnt = x+1;
        for (int i = cnt; i <  dataStr2.split("\\n").length; i++) {
          String s = dataStr2.split("\\n")[i];
            System.out.println("rowLine "+cnt+": "+s);
            final String[] dataValues = s.split(instancesSeparator);
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                for (int columnIndex = table.getColumns().size(); columnIndex < dataValues.length; columnIndex++) {
                  table.getColumns().add(createColumn(columnIndex, ""));
                }
                ObservableList<StringProperty> data = FXCollections
                    .observableArrayList();
                for (String value : dataValues) {
                  data.add(new SimpleStringProperty(value));
                }
                table.getItems().add(data);
              }
            });
          cnt++;
        }
        return null;
      }
    };
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  private TableColumn<ObservableList<StringProperty>, String> createColumn(
      final int columnIndex, String columnTitle) {
    
   Callback<TableColumn<ObservableList<StringProperty>, String>, TableCell<ObservableList<StringProperty>, String>> cellFactoryStr
    = (TableColumn<ObservableList<StringProperty>, String> param) -> new EditableCell<ObservableList<StringProperty>>("Str", this, columnTitle);
    TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
    column.setUserData("editable");
    String title;
    if (columnTitle == null || columnTitle.trim().length() == 0) {
      title = "Column " + (columnIndex + 1);
    } else {
      title = columnTitle;
    }
    
    column.setText(title);
    column
        .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
            ObservableList<StringProperty> values = cellDataFeatures.getValue();
            if (columnIndex >= values.size()) {
              return new SimpleStringProperty("");
            } else {
              return cellDataFeatures.getValue().get(columnIndex);
            }
          }
        });
    column.setCellFactory(cellFactoryStr);
    column.setOnEditCommit(
        (TableColumn.CellEditEvent<ObservableList<StringProperty>, String> t) -> {
            ObservableList<StringProperty> s = ((ObservableList<StringProperty>) t.getTableView().getItems().get(t.getTablePosition().getRow()));
            s.set(columnIndex, new SimpleStringProperty(t.getNewValue()));
            this.refresh();
        });
    column.setEditable(true);
    
    
    
    return column;
  }
    
    

  
  public void setTableFormat(String format) {
    switch (format) {
      case "tab":
        firstLineToParse = 0;
        headerSeparator = "\t+|\\s\\s+";
        instancesSeparator = "\t+|\\s\\s+";
        parseHeader = true;
        break;
      case "sbtab":
        firstLineToParse = 1;
        headerSeparator = "\t|\\s\\s+";
        instancesSeparator = "\t|   |  ";
        parseHeader = true;
        break;
      case "csv ;":
        firstLineToParse = 0;
        headerSeparator = "[;]";
        instancesSeparator = "[;]";
        parseHeader = true;
        break;
      case "csv ,":
        firstLineToParse = 0;
        headerSeparator = "[,]";
        instancesSeparator = "[,]";
        parseHeader = true;
        break;
      case "txt":
        firstLineToParse = 0;
        headerSeparator = "[,]";
        instancesSeparator = "[,]";
        parseHeader = true;
        break;
      default:
        break;
        
    }
  }
  
  public String randomChar() {
    Random r = new Random();
    return String.valueOf((char)(r.nextInt(26) + 'a'));
  }

  public void addEmptyRow() {
    ObservableList<StringProperty> data = FXCollections
        .observableArrayList();
    for (TableColumn<ObservableList<StringProperty>, ?> value : this.getColumns()) {
      data.add(new SimpleStringProperty("nan"));
    }
    this.getItems().add(data);
  
  }

  public void addEmptyColumn() {
    int idx = this.getColumns().size();
    this.getColumns().add(createColumn(idx, "newCol_"+idx));
    
    
      
  }
  
  public String toSBTabString() {
    StringBuilder sbTabStringBuilder = new StringBuilder();
    
    sbTabStringBuilder.append(createSBTabHeader());
    sbTabStringBuilder.append("\n");
    sbTabStringBuilder.append(createSbTabColumnHeader());
    sbTabStringBuilder.append("\n");
    sbTabStringBuilder.append(createSbTabRows());
    return sbTabStringBuilder.toString();
  }

  private String createSbTabRows() {
    StringBuilder sbTabStringBuilder = new StringBuilder();
    for (int y = 0; y <this.getItems().size(); y++) {
      for (int x = 0; x < this.getColumns().size(); x++) {
        String colTitle = this.getColumns().get(x).getText();
        String value = this.getColumns().get(x).getCellData(y).toString();
        String value2 = value.toString();
       
        sbTabStringBuilder.append(value2+"\t");
        
      }
      sbTabStringBuilder.append("\n");
    }
    return sbTabStringBuilder.toString();
  
  }

  private String createSbTabColumnHeader() {
    StringBuilder sbTabStringBuilder = new StringBuilder();
    for (TableColumn col : this.getColumns()) {
      String title = col.getText();
      System.out.println(title);
      sbTabStringBuilder.append(title+"\t");
    }
    return sbTabStringBuilder.toString();
  }

  private Object createSBTabHeader() {
    return "!!SBtab TableID='default' TableType='Definition' TableName='CustomTable' SBtabVersion='1.0'";
  }
  
}
