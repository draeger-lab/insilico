package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.insilico.sbmlsheets.editor.IdentifierChooser;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompoundTableView extends TableView<CompoundInstance>{
  
  public int i = 0;
  
  HashSet<String> compoundIds = new HashSet<String>();
  HashSet<String> ids = new HashSet<String>();
  private ObservableList<CompoundInstance> instances = FXCollections.observableArrayList();

  public CompoundTableView(){
    setEditable(true);
    initExampleData(3);
    this.setItems(instances);
    initColumns(this);
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
        addNewColumn("NewCol_"+getColumns().size());
      }else if (e.isShortcutDown()) {
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      }
      e.consume();
    });
  }
  
  public void copySelectionToClipboard() {
    final ArrayList<Integer> rows = new ArrayList<>();
    final ArrayList<Integer> cellColumns = new ArrayList<>();
    final ArrayList<String> cells = new ArrayList<>();
    for (final TablePosition tablePosition : this.getSelectionModel().getSelectedCells()) {
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

    if (this.getSelectionModel().cellSelectionEnabledProperty().getValue()) {
      for (Integer col : cellColumns) {
        strb.append(this.getColumns().get(col).getText());
        if (cellColumns.indexOf(col)==cellColumns.size()-1) {
          strb.append("\n");
        }else {
          strb.append("\t");
        }
      }
      for (String s : cells) {
        for (Integer i : cellColumns) {
          String[] split = s.split("_");
          if (split[1].equals(String.valueOf(i))) {
            Object cellData = this.getColumns().get(i).getCellData(Integer.parseInt(split[0]));
            strb.append(cellData == null ? "" : cellData);
          }
          if (cellColumns.indexOf(i)==cellColumns.size()-1) {
            strb.append("\n");
          }else {
            strb.append("\t");
          }
        }
      }
      
    }else {
      for (TableColumn col : this.getColumns()) {
        strb.append(col.getText());
        if (this.getColumns().indexOf(col)==this.getColumns().size()-1) {
          strb.append("\n");
        }else {
          strb.append("\t");
        }
      }
      boolean firstRow = true;
      for (final Integer row : rows) {
          for (final TableColumn<?, ?> column : this.getColumns()) {
              strb.append(column.getCellData(row) == null ? "" : column.getCellData(row));
              if (this.getColumns().indexOf(column)==this.getColumns().size()-1) {
                strb.append("\n");
              }else {
                strb.append("\t");
              }
          }
      }
    }
    
    final ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(strb.toString());
    Clipboard.getSystemClipboard().setContent(clipboardContent);
  }
  
  
  public void removeSelection() {
    for (CompoundInstance i : this.getSelectionModel().getSelectedItems()) {
      this.getItems().remove(i);
    }
  }
  
  public void addEmptyRow() {
    String newId = "spec_1";
    int k = 1;
    while (ids.contains(newId)) {
      k+=1;
      newId = "spec_"+k;
    }
    
    String newId2 = "spec_";
    int k2 = 1;
    while (compoundIds.contains(newId2)) {
      k2+=1;
      newId2 = "spec_"+k2;
    }
    
    compoundIds.add(newId2);
    ids.add(newId);
    System.out.println(newId);
    CompoundInstance newRowData = new CompoundInstance(newId, newId2);
    instances.add(newRowData);

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
  
  
  private void initExampleData(int j) {
    for (int i = 0; i < j; i++) {
      addEmptyRow();
    }
  }

  private void initColumns(CompoundTableView table) {
    
    for (int j = 0; j < CompoundInstance.attributeTypes.size(); j++) {
      final int k = j;
      
      String att = CompoundInstance.attributeTypes.get(j);
      String[] attSpl = att.split("_");
      System.out.println(k + " : " + attSpl[0] + "  -->  " + attSpl[1]);
      switch (attSpl[1]) {
        case "Bool":
          TableColumn<CompoundInstance,Boolean> boolCol = new TableColumn<CompoundInstance,Boolean>(attSpl[0]);
          boolCol.setCellValueFactory(new PropertyValueFactory<>(attSpl[0]));
          
          final CheckBoxTableCell<CompoundInstance, Boolean> ctCell = new CheckBoxTableCell<>();
          ctCell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
              @Override
              public ObservableValue<Boolean> call(Integer index) {
                return table.getItems().get(index).attributes.get(index);
              }
          });
          
          boolCol.setCellFactory(column -> new CheckBoxTableCell()); 
          this.getColumns().add(boolCol);
          boolCol.setEditable(true);
          break;
        case "Str":
          Callback<TableColumn<CompoundInstance, String>, TableCell<CompoundInstance, String>> cellFactoryStr
          = (TableColumn<CompoundInstance, String> param) -> new EditableCell<CompoundInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompoundInstance, String> strCol = new TableColumn<CompoundInstance, String>(attSpl[0]);
          strCol.setCellValueFactory(new Callback<CellDataFeatures<CompoundInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompoundInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          strCol.setCellFactory(cellFactoryStr);
          strCol.setOnEditCommit(
                  (TableColumn.CellEditEvent<CompoundInstance, String> t) -> {
                    String val = t.getNewValue();
                    switch (attSpl[0]) {
                      case "speciesId":
                        if (!t.getOldValue().equals(t.getNewValue())) {
                          compoundIds.remove(t.getOldValue());
                          String[] split = val.split("_");
                          String prefix = split[0];
                          String num = split[1];
                          while (compoundIds.contains(val)) {
                            if (split.length>1) {
                              if (num.matches("[0-9]+")) {
                                num = String.valueOf(Integer.parseInt(num)+1);
                                val = prefix + "_" + num;
                                
                              }else {
                                val = prefix + "_1";
                                num = "1";
                              }
                            }else {
                              val = prefix + "_1";
                              num = "1";
                            }
                          }
                          compoundIds.add(val);
                        }
                        break;
                    
                      case "id":
                        
                        if (!t.getOldValue().equals(t.getNewValue())) {
                          ids.remove(t.getOldValue());
                          String[] split = val.split("_");
                          String prefix = split[0];
                          String num = split[1];
                          while (ids.contains(val)) {
                            if (split.length>1) {
                              if (num.matches("[0-9]+")) {
                                num = String.valueOf(Integer.parseInt(num)+1);
                                val = prefix + "_" + num;
                                
                              }else {
                                val = prefix + "_1";
                                num = "1";
                              }
                            }else {
                              val = prefix + "_1";
                              num = "1";
                            }
                          }
                          ids.add(val);
                          
                        }
                        break;
                      case "sboTerm":
                        val = SBMLUtils.convertToSBO(t.getNewValue());
                        break;    
                      case "unit":
                        val = SBMLUtils.convertToUnit(t.getNewValue()).getName();
                        break;
                    }
                    
                    
                    
                      ((CompoundInstance) t.getTableView().getItems()
                      .get(t.getTablePosition().getRow()))
                      .setProperty(attSpl[0], val);
                      this.refresh();
                  });
          
          this.getColumns().add(strCol);
          strCol.setEditable(true);
          break;
        case "Int":
          Callback<TableColumn<CompoundInstance, String>, TableCell<CompoundInstance, String>> cellFactoryInt
          = (TableColumn<CompoundInstance, String> param) -> new EditableCell<CompoundInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompoundInstance, String> intCol = new TableColumn<CompoundInstance, String>(attSpl[0]);
          intCol.setCellValueFactory(new Callback<CellDataFeatures<CompoundInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompoundInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          intCol.setCellFactory(cellFactoryInt);
          intCol.setOnEditCommit(
                  (TableColumn.CellEditEvent<CompoundInstance, String> t) -> {
                    String value = t.getNewValue();
                        System.out.println("changing to proper int");
                        if (value.matches("^[0-9]+\\.[0-9]+$")) {
                          value = value.split("\\.")[0];
                        }else if (value.matches("^[0-9]+$")) {
                          System.out.println("int stays int");
                        }else {
                          System.out.println("default int");
                          value = "0";
                          }
                     
                      ((CompoundInstance) t.getTableView().getItems()
                      .get(t.getTablePosition().getRow()))
                      .setProperty(attSpl[0], value);
                     
                      this.refresh();
                  });

          this.getColumns().add(intCol);
          intCol.setEditable(true);
          break;
        
        case "Dbl":
          Callback<TableColumn<CompoundInstance, String>, TableCell<CompoundInstance, String>> cellFactoryDbl
          = (TableColumn<CompoundInstance, String> param) -> new EditableCell<CompoundInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompoundInstance, String> dblCol = new TableColumn<CompoundInstance, String>(attSpl[0]);
          dblCol.setCellValueFactory(new Callback<CellDataFeatures<CompoundInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompoundInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          dblCol.setCellFactory(cellFactoryDbl);
          dblCol.setOnEditCommit(
              (TableColumn.CellEditEvent<CompoundInstance, String> t) -> {
                String value = t.getNewValue();
                    System.out.println("changing to proper int");
                    if (value.matches("^[0-9]+\\.[0-9]+$")) {
                      System.out.println("dbl stays dbl");
                    }else if (value.matches("^[0-9]+$")) {
                      System.out.println("changing to proper double");
                      value = value+".0";
                    }else {
                      System.out.println("default double");
                      value = "0.0";
                    }
                 
                    ((CompoundInstance) t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setProperty(attSpl[0], value);
                        
                        this.refresh();
              });
      
         
          this.getColumns().add(dblCol);
          dblCol.setEditable(true);
          break;
       
        default:
          break;
      }

    }
 
  }

  public void addAnnotationColumn(String annotationType) {
    Callback<TableColumn<CompoundInstance, String>, TableCell<CompoundInstance, String>> cellFactory
    = (TableColumn<CompoundInstance, String> param) -> new EditableCell("str", this);
    TableColumn<CompoundInstance, String> strCol = new TableColumn(annotationType);
    
    this.getColumns().add(strCol);
    strCol.setEditable(true); 
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
    for (int y = 0; y < instances.size(); y++) {
      for (int x = 0; x < this.getColumns().size(); x++) {
        if (writtenColumns.contains(x)) {
        String colTitle = this.getColumns().get(x).getText();
        String value = this.getColumns().get(x).getCellData(y).toString();
        String value2 = value.toString();
        sbTabStringBuilder.append(value2+"\t");
        }
      }
      sbTabStringBuilder.append("\n");
    }
    return sbTabStringBuilder.toString().replaceAll("\n(\n)*", "\n");
  }
  
  
  
  HashSet<Integer> writtenColumns = new HashSet<>();
  private String createSbTabColumnHeader() {
    StringBuilder sbTabStringBuilder = new StringBuilder();
    for (TableColumn col : this.getColumns()) {
      String title = col.getText();
      System.out.println(title);
      if (CompoundInstance.attributeTypes.contains(title+"_Str")){
        sbTabStringBuilder.append(CompoundInstance.sbTabCol.get(CompoundInstance.attributeTypes.indexOf(title+"_Str")));
        writtenColumns.add(this.getColumns().indexOf(col));
      }else if (CompoundInstance.attributeTypes.contains(title+"_Int")) {
        sbTabStringBuilder.append(CompoundInstance.sbTabCol.get(CompoundInstance.attributeTypes.indexOf(title+"_Int")));
        writtenColumns.add(this.getColumns().indexOf(col));
      }else if  (CompoundInstance.attributeTypes.contains(title+"_Dbl")) {
        sbTabStringBuilder.append(CompoundInstance.sbTabCol.get(CompoundInstance.attributeTypes.indexOf(title+"_Dbl")));
        writtenColumns.add(this.getColumns().indexOf(col));
      }else if  (CompoundInstance.attributeTypes.contains(title+"_Bool")) {
        sbTabStringBuilder.append(CompoundInstance.sbTabCol.get(CompoundInstance.attributeTypes.indexOf(title+"_Bool")));
        writtenColumns.add(this.getColumns().indexOf(col));
      }else {
        if (IdentifierChooser.sbTabDefinitions.contains(title)) {
          writtenColumns.add(this.getColumns().indexOf(col));
          sbTabStringBuilder.append(title);
        }
      }
      sbTabStringBuilder.append("\t");
    }
    return sbTabStringBuilder.toString();
  }

  private Object createSBTabHeader() {
    return "!!SBtab TableID='compound' TableType='Compound' TableName='Compounds' SBtabVersion='1.0'";
  }

  public void addNewColumn(String colTitle) {
    for (CompoundInstance c : instances) {
      if (!c.attributeTypes.contains(colTitle.replaceAll("[:!]", "")+"_Str")) {
        c.sbTabCol.add(colTitle);
      }
    }

    
    Callback<TableColumn<CompoundInstance, String>, TableCell<CompoundInstance, String>> cellFactoryStr
    = (TableColumn<CompoundInstance, String> param) -> new EditableCell<CompoundInstance>("Str", this, colTitle.replaceAll("[:!]", ""));
    TableColumn<CompoundInstance, String> strCol = new TableColumn<CompoundInstance, String>(colTitle.replaceAll("[:!]", ""));
    strCol.setCellValueFactory(new Callback<CellDataFeatures<CompoundInstance, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<CompoundInstance, String> p) {
          // p.getValue() returns the Person instance for a particular TableView row
          return p.getValue().attributes.get(p.getValue().attributes.size()-1);
      }
    });
    strCol.setCellFactory(cellFactoryStr);
    strCol.setOnEditCommit(
            (TableColumn.CellEditEvent<CompoundInstance, String> t) -> {
                ((CompoundInstance) t.getTableView().getItems()
                .get(t.getTablePosition().getRow()))
                .setProperty(colTitle.replaceAll("[:!]", ""), t.getNewValue());
                this.refresh();
            });
    
   
    strCol.setUserData("editable");

    
    this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
      TableColumnBase<?, ?> column = null;
      if (e.isPrimaryButtonDown() &&  e.getClickCount() > 1) {
             
          EventTarget target = e.getTarget();
         
          while (target instanceof Node) {
              target = ((Node) target).getParent();
              if (target instanceof TableColumnHeader) {
                  column = ((TableColumnHeader) target).getTableColumn();
                  if (column != null) break;
              }
          }
          if (column != null) {
            if (column.getUserData().equals("editable")) {
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
              
              ContextMenu contextMenu = new ContextMenu();
              MenuItem choose = new MenuItem("Choose valid SBTab Column Title");
              contextMenu.getItems().add(choose);
              textField.setContextMenu(contextMenu);
              
              choose.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                  IdentifierChooser chooser = new IdentifierChooser();
                  Stage dialogStage = new Stage();
                  Scene dialogScene = new Scene(chooser, 300, 550);
                  dialogStage.centerOnScreen();

                  dialogStage.show();
                  
                  dialogStage.setScene(dialogScene);
                  
                  final Stage[] stages = new Stage[] {dialogStage};
                  
                  chooser.getB().setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      tableColumn.setText(chooser.getResultList().getSelectionModel().getSelectedItem());
                      tableColumn.setGraphic(null);
                      stages[0].close();
                    }
                  });
                  chooser.getB2().setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                      stages[0].close();
                      tableColumn.setGraphic(null);
                    }
                  });
                }
              });
              
        
              
          }
       e.consume();
      }
      }

    });
    

    
    this.getColumns().add(strCol);
    strCol.setEditable(true);
    
  }
  
  public HashSet<String> getIds(){
    return ids;
  }
  
}

