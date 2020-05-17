package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.insilico.sbmlsheets.editor.IdentifierChooser;
import org.sbml.jsbml.CVTerm;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;

public class CompartmentTableView extends TableView<CompartmentInstance>{
  
public int i = 0;
  
  HashSet<String> compartmentIds = new HashSet<String>();
  HashSet<String> ids = new HashSet<String>();
  private ObservableList<CompartmentInstance> instances = FXCollections.observableArrayList();

  
  public CompartmentTableView(){
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
    for (CompartmentInstance i : this.getSelectionModel().getSelectedItems()) {
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
  
  
  public void addEmptyRow() {
    String newId = "comp_1";
    int k = 1;
    while (ids.contains(newId)) {
      k+=1;
      newId = "comp_"+k;
    }
    
    String newId2 = "comp_1";
    int k2 = 1;
    while (compartmentIds.contains(newId2)) {
      k2+=1;
      newId2 = "comp_"+k2;
    }
    
    compartmentIds.add(newId2);
    ids.add(newId);
    System.out.println(newId);
    CompartmentInstance newRowData = new CompartmentInstance(newId, newId2);
    instances.add(newRowData);

  }
  
  boolean addingIdentColumn = false;
  
  public void addNewColumn(String colTitle) {
    addingIdentColumn = true;
    for (CompartmentInstance c : instances) {
      if (!c.attributeTypes.contains(colTitle.replaceAll("[:!]", "")+"_Str")) {
        //add
        c.attributeTypes.add(colTitle.replaceAll("[:!]", "")+"_Str");
        c.sbTabCol.add(colTitle);
      }
    }

    
    Callback<TableColumn<CompartmentInstance, String>, TableCell<CompartmentInstance, String>> cellFactoryStr
    = (TableColumn<CompartmentInstance, String> param) -> new EditableCell<CompartmentInstance>("Str", this, colTitle.replaceAll("[:!]", ""));
    TableColumn<CompartmentInstance, String> strCol = new TableColumn<CompartmentInstance, String>(colTitle.replaceAll("[:!]", ""));
    strCol.setCellValueFactory(new Callback<CellDataFeatures<CompartmentInstance, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<CompartmentInstance, String> p) {
          return p.getValue().attributes.get(p.getValue().attributes.size()-1);
      }
    });
    strCol.setCellFactory(cellFactoryStr);
    strCol.setOnEditCommit(
            (TableColumn.CellEditEvent<CompartmentInstance, String> t) -> {
                ((CompartmentInstance) t.getTableView().getItems()
                .get(t.getTablePosition().getRow()))
                .setProperty(colTitle.replaceAll("[:!]", ""), t.getNewValue());
                this.refresh();
            });
    strCol.setUserData("editable");
    
    
    this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
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
                  dialogStage.centerOnScreen();
                  Scene dialogScene = new Scene(chooser, 300, 550);
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
  
  
  private void initExampleData(int m) {
    for (int l = 0; l < m; l++) {
      addEmptyRow();
    }
  }

  private void initColumns(CompartmentTableView table) {
    
    for (int j = 0; j < CompartmentInstance.attributeTypes.size(); j++) {
      final int k = j;
      
      String att = CompartmentInstance.attributeTypes.get(j);
      String[] attSpl = att.split("_");
      System.out.println(k + " : " + attSpl[0] + "  -->  " + attSpl[1]);
      switch (attSpl[1]) {
        case "Bool":
          TableColumn<CompartmentInstance,Boolean> boolCol = new TableColumn<CompartmentInstance,Boolean>(attSpl[0]);
          boolCol.setCellValueFactory(new PropertyValueFactory<>(attSpl[0]));
          boolCol.setUserData("notEditable");
          final CheckBoxTableCell<CompartmentInstance, Boolean> ctCell = new CheckBoxTableCell<>();
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
          Callback<TableColumn<CompartmentInstance, String>, TableCell<CompartmentInstance, String>> cellFactoryStr
          = (TableColumn<CompartmentInstance, String> param) -> new EditableCell<CompartmentInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompartmentInstance, String> strCol = new TableColumn<CompartmentInstance, String>(attSpl[0]);
          strCol.setCellValueFactory(new Callback<CellDataFeatures<CompartmentInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompartmentInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          strCol.setUserData("notEditable");
          strCol.setCellFactory(cellFactoryStr);
          strCol.setOnEditCommit(
                  (TableColumn.CellEditEvent<CompartmentInstance, String> t) -> {
                    String val = t.getNewValue();
                    switch (attSpl[0]) {
                      case "sbmlCompartmentId":
                        if (!t.getOldValue().equals(t.getNewValue())) {
                          compartmentIds.remove(t.getOldValue());
                          String[] split = val.split("_");
                          String prefix = split[0];
                          String num = split[1];
                          while (compartmentIds.contains(val)) {
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
                          compartmentIds.add(val);
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
  
                        ((CompartmentInstance) t.getTableView().getItems()
                        .get(t.getTablePosition().getRow()))
                        .setProperty(attSpl[0], val);
                        this.refresh();
                  });
          
          
          this.getColumns().add(strCol);
          strCol.setEditable(true);
          break;
        case "Int"://
          Callback<TableColumn<CompartmentInstance, String>, TableCell<CompartmentInstance, String>> cellFactoryInt
          = (TableColumn<CompartmentInstance, String> param) -> new EditableCell<CompartmentInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompartmentInstance, String> intCol = new TableColumn<CompartmentInstance, String>(attSpl[0]);
          intCol.setCellValueFactory(new Callback<CellDataFeatures<CompartmentInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompartmentInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          intCol.setUserData("notEditable");
          intCol.setCellFactory(cellFactoryInt);
          intCol.setOnEditCommit(
                  (TableColumn.CellEditEvent<CompartmentInstance, String> t) -> {
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
                     
                      ((CompartmentInstance) t.getTableView().getItems()
                      .get(t.getTablePosition().getRow()))
                      .setProperty(attSpl[0], value);
                      this.refresh();
                  });
          
          this.getColumns().add(intCol);
          intCol.setEditable(true);
          break;
        case "Dbl":
          Callback<TableColumn<CompartmentInstance, String>, TableCell<CompartmentInstance, String>> cellFactoryDbl
          = (TableColumn<CompartmentInstance, String> param) -> new EditableCell<CompartmentInstance>(attSpl[1], this, attSpl[0]);
          TableColumn<CompartmentInstance, String> dblCol = new TableColumn<CompartmentInstance, String>(attSpl[0]);
          dblCol.setCellValueFactory(new Callback<CellDataFeatures<CompartmentInstance, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CompartmentInstance, String> p) {
                return p.getValue().attributes.get(k);
            }
         });
          dblCol.setCellFactory(cellFactoryDbl);
          dblCol.setUserData("notEditable");
          dblCol.setOnEditCommit(
              (TableColumn.CellEditEvent<CompartmentInstance, String> t) -> {
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
                 
                    ((CompartmentInstance) t.getTableView().getItems()
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
    Callback<TableColumn<CompartmentInstance, String>, TableCell<CompartmentInstance, String>> cellFactory
    = (TableColumn<CompartmentInstance, String> param) -> new EditableCell("str", this);
    TableColumn<CompartmentInstance, String> strCol = new TableColumn(annotationType);
    
    this.getColumns().add(strCol);
    strCol.setEditable(true);
    //create 
  }
  
  public String toCSVString() {
    
    StringBuilder csvStringBuilder = new StringBuilder();
    for (TableColumn<CompartmentInstance, ?> column : this.getColumns()) {
      csvStringBuilder.append(column.getText()+"\t");
    }
    
    for (int y = 0; y < instances.size(); y++) {
      for (int x = 0; x < this.getColumns().size(); x++) {
        csvStringBuilder.append(this.getColumns().get(x).getCellData(y).toString()+"\t");
      }
      csvStringBuilder.append("\n");
    }
      
    return csvStringBuilder.toString();
    
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
      if (CompartmentInstance.attributeTypes.contains(title+"_Str")){
        writtenColumns.add(this.getColumns().indexOf(col));
        sbTabStringBuilder.append(CompartmentInstance.sbTabCol.get(CompartmentInstance.attributeTypes.indexOf(title+"_Str")));
      }else if (CompartmentInstance.attributeTypes.contains(title+"_Int")) {
        writtenColumns.add(this.getColumns().indexOf(col));
        sbTabStringBuilder.append(CompartmentInstance.sbTabCol.get(CompartmentInstance.attributeTypes.indexOf(title+"_Int")));
      }else if  (CompartmentInstance.attributeTypes.contains(title+"_Dbl")) {
        writtenColumns.add(this.getColumns().indexOf(col));
        sbTabStringBuilder.append(CompartmentInstance.sbTabCol.get(CompartmentInstance.attributeTypes.indexOf(title+"_Dbl")));
      }else if  (CompartmentInstance.attributeTypes.contains(title+"_Bool")) {
        writtenColumns.add(this.getColumns().indexOf(col));
        sbTabStringBuilder.append(CompartmentInstance.sbTabCol.get(CompartmentInstance.attributeTypes.indexOf(title+"_Bool")));
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
    return "!!SBtab TableID='compartment' TableType='Compartment' TableName='Compartments' SBtabVersion='1.0'";
  }
  
  public HashSet<String> getIds(){
    return ids;
  }
  
 
}


  
  
  
  

