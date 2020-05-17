package org.insilico.sbmlsheets.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditableCell<T> extends TableCell<T, String> {

  String type = "Str";
  private TextField textField;
  private TableView table;
  String columnType = "id";
  String previousValue = "nan";

  public EditableCell(String type, TableView table) {
    this.table = table;
    this.type = type;
  }
  
  public EditableCell(String type, TableView table, String columnType) {
    this.type = type;
    this.columnType = columnType;
    this.table = table;
  }
 

  @Override
  public void startEdit() {
      if (!isEmpty()) {
          super.startEdit();
          createTextField();
          setText(null);
          setGraphic(textField);
          textField.selectAll();
      }
  }

  @Override
  public void cancelEdit() {
      super.cancelEdit();

      setText((String) getItem());
      setGraphic(null);
  }

  @Override
  public void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);

      if (empty) {
          setText(item);
          setGraphic(null);
      } else {
          if (isEditing()) {
              if (textField != null) {
                  textField.setText(getString());
              }
              setText(null);
              setGraphic(textField);
          } else {
              setText(getString());
              setGraphic(null);
          }
      }
  }

  private void createTextField() {
    
      textField = new TextField(getString());
      textField.setStyle(".text-field:error{\r\n" + 
          "    -fx-text-border: red;\r\n" + 
          "    -fx-focus-border: red;\r\n" + 
          "    -fx-text-fill: black;\r\n" + 
          "}");

      textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
      textField.setOnAction((e) -> commitEdit(textField.getText()));
      
      textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
          if (event.getCode().equals(KeyCode.ENTER)) {
            table.requestFocus();
          } else if (event.getCode().equals(KeyCode.ESCAPE)) {
            textField.setText(previousValue);
            table.requestFocus();
          }
        
        }
    });
   
      textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

          if (!newValue) {
              
              String value = textField.getText();
              
              System.out.println("type: "+type + " attribute: "+columnType+" value: "+value);
              
              
              
              switch (type) {
                case "Int":
                  System.out.println("changing to proper int");
                  if (value.matches("^[0-9]+\\.[0-9]+$")) {
                    value = value.split("\\.")[0];
                  }else if (value.matches("^[0-9]+$")) {
                    System.out.println("int stays int");
                  }else {
                    System.out.println("default int");
                    value = "0";
                    }
                  break;
                case "Dbl":
                    if (value.matches("^[0-9]+\\.[0-9]+$")) {
                      System.out.println("dbl stays dbl");
                    }else if (value.matches("^[0-9]+$")) {
                      System.out.println("changing to proper double");
                      value = value+".0";
                    }else {
                      System.out.println("default double");
                      value = "0.0";
                    }
                  break;
                case "Str":
                  switch (columnType) {
                    case "sumFormula":
                      String s = "((\\d+(\\.\\d+)? )?\\S+)+( \\+ ((\\d+(\\.\\d+)? )?\\S+)+)* [<]?=> ((\\d+(\\.\\d+)? )?\\S+)+( \\+ ((\\d+(\\.\\d+)? )?\\S+)+)*";
                      if (!value.matches(s)) {
                        value = "nan + nan <=> nan";
                      }else {
                        
                      }
                      break;
                    case "sboTerm":
                      if (!value.matches("SBO:[0-9]{7}|\\d{1,7}")) {
                        value = "SBO:0000001";
                      }else {
                        if (value.matches("\\d{1,7}")){
                          String zero = "0";
                          value = "SBO:"+IntStream.range(0, 7-value.length()).mapToObj(i -> zero).collect(Collectors.joining(""))+value;
                        }
                      }
                    case "id":
                   
                    default:
                      break;
                      
                  }
               
                  break;
                  //
                  
                default:
                  break;
              }
              
              textField.textProperty().set(value);
              textField.setAccessibleText(value);
              
              setString(value); 
              System.out.println("Commiting textfield:" + textField.getText() + " and item value:"+value + " getString():"+getString());
              commitEdit(value); 
              
              
          }else {
            previousValue = textField.getText();
          }
      });
      
  }

  private void setString(String newValue) {
    setItem(newValue);
  }
  
  private String getString() {
      return getItem() == null ? "" : getItem();
  }

  public String getType() {
    return type;
  }
  

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public void commitEdit(String item) {
      if (!isEditing() && !item.equals(getItem())) {
          TableView table = getTableView();
          if (table != null) {
              TableColumn column = getTableColumn();
              CellEditEvent event = new CellEditEvent<>(
                  table, new TablePosition(table, getIndex(), column), 
                  TableColumn.editCommitEvent(), item
              );
              Event.fireEvent(column, event);
          }
      }

      super.commitEdit(item);
  }


  
  
}