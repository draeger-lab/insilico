package org.insilico.sbmlsheets.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.xml.stream.XMLStreamException;
import org.insilico.sbmlsheets.core.CompartmentInstance;
import org.insilico.sbmlsheets.core.CompartmentTableView;
import org.insilico.sbmlsheets.core.CompoundInstance;
import org.insilico.sbmlsheets.core.CompoundTableView;
import org.insilico.sbmlsheets.core.CustomTableView;
import org.insilico.sbmlsheets.core.EditorUtils;
import org.insilico.sbmlsheets.core.ReactionInstance;
import org.insilico.sbmlsheets.core.ReactionTableView;
import org.insilico.sbmlsheets.core.SBMLHelper;
import org.insilico.sbmlsheets.core.SBTabHelper;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableEditorView extends Tab{

  private SplitPane splitPane;
  private StackPane stackPane;
  private CompoundTableView compoundTableView = new CompoundTableView();
  private ReactionTableView reactionTableView = new ReactionTableView();
  private CompartmentTableView compartmentTableView = new CompartmentTableView();
  private CustomTableView customTableView = new CustomTableView();
  private TabPane tabPane = new TabPane();
  private Tab editTab = new Tab("Editing");
  private Tab sbTabTab = new Tab("SBTab Doc");
  private Tab sbmlTab = new Tab("SBML Doc");
  
  private SimpleIntegerProperty currentTableView = new SimpleIntegerProperty();
  
  private SBMLDocument sbmlDoc = null;
  
  VBox compoundVBox = new VBox();
  VBox reactionVBox = new VBox();
  VBox compartmentVBox = new VBox();
  VBox customVBox = new VBox();
  
  boolean enableOtherTableTypes = true;

  
  ArrayList<TableView> tables = new ArrayList<TableView>();
  
  ArrayList<String> identifierTypes = new ArrayList(Arrays.asList("sbo.go", "sbo.kegg", "kegg.compound", "kegg.compartment", "obo.chebi"));
  
  ArrayList<String> annotationTypes = new ArrayList<>(Arrays.asList("KEGG", "Bio"));
  ArrayList<String> tableTypes = new ArrayList<>(Arrays.asList("Compound", "Compartment", "Reaction"));
  
  public TableEditorView(String title) {
    this.setText(title);
    this.init();
  }
  

  public TableEditorView(String title, String filePath) {
    this.setText(title);
    this.init();
    String format = filePath.split("\\.")[1];
    switch (format) {
      case "tsv":
        format = "sbtab";
        break;
      case "csv":
        format = "csv ;";
        break;
      case "tab":
        format = "tab";
        break;
    }
    enableOtherTableTypes = false;
    customTableView = new CustomTableView(SBTabHelper.getFileContent(filePath), format);
    customVBox.getChildren().set(0,  customTableView);
    currentTableView.set(3);
    reactionVBox.setVisible(false);
    compartmentVBox.setVisible(false);
    compoundVBox.setVisible(false);
    customVBox.setVisible(true);
  }



  private void init() {
    tables.add(compoundTableView);
    tables.add(reactionTableView);
    tables.add(compartmentTableView);
    tables.add(customTableView);
    
    tabPane.getTabs().addAll(editTab, sbTabTab, sbmlTab);
    initEditTab();
    initSbTab();
    initSBMLTab();

    editTab.getContent().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode().equals(KeyCode.V) && e.isShortcutDown()) {
        pasteFromClipboard();
      }
    });
    
    
    this.setContent(tabPane);

  }
  


  public BooleanProperty useCompoundForSBML = new SimpleBooleanProperty(true);
  public BooleanProperty useCompartmentForSBML = new SimpleBooleanProperty(true);
  public BooleanProperty useReactionForSBML = new SimpleBooleanProperty(true);
  
  public BooleanProperty useCompoundForSBTab = new SimpleBooleanProperty(true);
  public BooleanProperty useCompartmentForSBTab = new SimpleBooleanProperty(true);
  public BooleanProperty useReactionForSBTab = new SimpleBooleanProperty(true);
  public BooleanProperty useCustomForSBTab = new SimpleBooleanProperty(true);
  
  public String sbmlString = "";
  
  private void initSBMLTab() {
    
    TextArea sbmlTextArea = new TextArea("");
    sbmlTab.setContent(sbmlTextArea);
    
    MenuItem menuItem0 = new MenuItem("Save File");
    menuItem0.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBMLHelper h = new SBMLHelper();
        h.saveAsFile(TableEditorView.this.getSbmlDoc());
      }
    });
    
    Menu menu1 = new Menu("Used SBTabs");
    CheckMenuItem item1 = new CheckMenuItem("Compound");
    CheckMenuItem item2 = new CheckMenuItem("Reaction");
    CheckMenuItem item3 = new CheckMenuItem("Compartment");
    useCompoundForSBML.bind(item1.selectedProperty());
    useCompartmentForSBML.bind(item3.selectedProperty());
    useReactionForSBML.bind(item2.selectedProperty());
    
    item1.selectedProperty().addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
        TableEditorView.this.updateSBMLDoc();
        }
    });
    item2.selectedProperty().addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
        TableEditorView.this.updateSBMLDoc();
        }
    });
    item3.selectedProperty().addListener(new ChangeListener<Boolean>() {
      public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
        TableEditorView.this.updateSBMLDoc();
        }
    });
    
    
    menu1.getItems().addAll(item1, item2, item3);
    
    Menu menu2 = new Menu("Save");
    
    TextAreaSkin customContextSkin = new TextAreaSkin(sbmlTextArea) {
      @Override
      public void populateContextMenu(ContextMenu contextMenu) {
          super.populateContextMenu(contextMenu);
          contextMenu.getItems().add(0, menuItem0);
          contextMenu.getItems().add(0, menu1);
          contextMenu.getItems().add(0, menu2);
      }
    };
    sbmlTextArea.setSkin(customContextSkin);
  }



  protected void updateSBMLDoc() {
    TextArea f = (TextArea) sbmlTab.getContent();
    
    SBMLHelper h = new SBMLHelper();
    setSbmlDocument(h.createSBMLDocFromSBTab(this, this.useCompoundForSBML.getValue(), this.useReactionForSBML.getValue(), this.useCompartmentForSBML.getValue()));

    f.setText(h.getDocContent(this.getSbmlDoc()));
    
  }



  private void initSbTab() {
    TextArea textArea = new TextArea("");
    sbTabTab.setContent(textArea);

    Menu menu1 = new Menu("Used Tables");
    CheckMenuItem item11 = new CheckMenuItem("Compound");
    CheckMenuItem item12 = new CheckMenuItem("Reaction");
    CheckMenuItem item13 = new CheckMenuItem("Compartment");
    CheckMenuItem item14 = new CheckMenuItem("Custom");
    useCompoundForSBTab.bind(item11.selectedProperty());
    useReactionForSBTab.bind(item12.selectedProperty());
    useCompartmentForSBTab.bind(item13.selectedProperty());
    useCustomForSBTab.bind(item14.selectedProperty());
    
    for (CheckMenuItem cmI : new ArrayList<>(Arrays.asList(item11, item12, item13, item14))) {
      cmI.setSelected(true);
      cmI.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
          TableEditorView.this.updateSBTab();
          }
      });
      menu1.getItems().add(cmI);
    }

    
    
    Menu menu = new Menu("Save as");
    
    MenuItem item1 = new MenuItem(".tsv");
    MenuItem item2 = new MenuItem(".csv (separator: ;)");
    MenuItem item3 = new MenuItem(".csv (separator: ,)");
    MenuItem item4 = new MenuItem(".csv (separator: \t)");
    MenuItem item5 = new MenuItem(".txt");
    
    
    item1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBTabHelper.saveAsFile(textArea.getText(), "tsv");
      }
    });
    
    item2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBTabHelper.saveAsFile(textArea.getText().replaceAll("(\\t|\\)+", ";"), "csv");
      }
    });
    
    item3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBTabHelper.saveAsFile(textArea.getText().replaceAll("(\\t+|\\)+", " "), "csv");
      }
    });
    
    item4.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBTabHelper.saveAsFile(textArea.getText().replaceAll("(\\t+|\\)+", "\t"), "csv");
      }
    });
    
    item5.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SBTabHelper.saveAsFile(textArea.getText(), "txt");
      }
    });
   
    menu.getItems().addAll(item1, item2, item3, item4, item5);

    TextAreaSkin customContextSkin = new TextAreaSkin(textArea) {
      @Override
      public void populateContextMenu(ContextMenu contextMenu) {
          super.populateContextMenu(contextMenu);
          contextMenu.getItems().add(0, new SeparatorMenuItem());
          contextMenu.getItems().add(0, menu);
          contextMenu.getItems().add(0, menu1);
      }
    };
    textArea.setSkin(customContextSkin);

    tabPane.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                if (t1==sbTabTab) {
                  TableEditorView.this.updateSBTab();
                } else if (t1==sbmlTab) {
                  TableEditorView.this.updateSBMLDoc();
                }
            }
        }
    );

  }



  protected void updateSBTab() {
    TextArea t = (TextArea) sbTabTab.getContent();
    SBTabHelper h = new SBTabHelper();
    t.setText(h.createSBTabDocFromTables(this, this.useCompoundForSBTab.getValue(), this.useReactionForSBTab.getValue(),
        this.useCompartmentForSBTab.getValue(), this.useCustomForSBTab.getValue()));

  }



  protected void setSbmlDocument(SBMLDocument sbmlDoc) {
    this.setSbmlDoc(sbmlDoc);
    TextArea t = (TextArea) sbmlTab.getContent();
    SBMLWriter sW = new SBMLWriter();
    
    try {
      t.setText(sW.writeSBMLToString(sbmlDoc));
    }
    catch (SBMLException e) {
      e.printStackTrace();
    }
    catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }



  protected void addIdentifierColumn(String colTitle) {
    switch (currentTableView.get()) {
      case 0:
        compoundTableView.addNewColumn(colTitle);
        break;
      case 1:
        reactionTableView.addNewColumn(colTitle);
        break;
      case 2:
        compartmentTableView.addNewColumn(colTitle);
        break;
      default:
        break;
    }
    
  }



  private void initEditTab() {
    compoundVBox.getChildren().add(compoundTableView);
    Pane pane = new Pane();
    pane.setMaxWidth(5);
    pane.setMaxHeight(5);
    compoundVBox.getChildren().add(pane);
    
    reactionVBox.getChildren().add(reactionTableView);
    reactionVBox.setVisible(false);
    compartmentVBox.getChildren().add(compartmentTableView);
    compartmentVBox.setVisible(false);
    customVBox.getChildren().add(customTableView);
    customVBox.setVisible(false);

    for (VBox box : Arrays.asList(compoundVBox, reactionVBox, compartmentVBox, customVBox)) {
      box.minWidthProperty().bind(Bindings.add(((TableView) box.getChildren().get(0)).widthProperty(),new SimpleDoubleProperty(10.0)));
      box.minHeightProperty().bind(Bindings.add(((TableView) box.getChildren().get(0)).heightProperty(),new SimpleDoubleProperty(10.0)));
    }
   
    StackPane sPane = new StackPane(compoundVBox, reactionVBox, compartmentVBox, customVBox);
    ScrollPane scPane = new ScrollPane(sPane);
    
    editTab.setContent(scPane);
    
    ContextMenu contextMenu = new ContextMenu();
    
    MenuItem item00 = new MenuItem("Copy Row Selection to Clipboard");
    item00.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          switch (currentTableView.get()) {
            case 0:
                compoundTableView.copySelectionToClipboard();
              break;
            case 1:
                reactionTableView.copySelectionToClipboard();
              break;
            case 2:
                compartmentTableView.copySelectionToClipboard();
              break;
            case 3:
              customTableView.copySelectionToClipboard();
            break;
            default:
              break;
          }
        }
    });
    
    MenuItem item0 = new MenuItem("Paste from Clipboard");
    item0.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          pasteFromClipboard();
      }
    });
    
    Menu menu0 = new Menu("Paste from File");
    
    MenuItem item01 = new MenuItem(".tsv");
    MenuItem item02 = new MenuItem(".csv (separator: ;)");
    MenuItem item03 = new MenuItem(".csv (separator: ,)");
    MenuItem item04 = new MenuItem(".tab");
    
    for (MenuItem mI : Arrays.asList(item01, item02, item03, item04)) {
      mI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String format = "txt";
          switch (mI.getText()) {
            case ".tsv":
              format = "sbtab";
             
              break;
            case ".csv (separator: ;)":
              format = "csv ;";
              break;
            case ".csv (separator: ,)":
              format = "csv ,";
              break;
            case ".tab":
              format = "tab";
              break;
          }
          
          customTableView = new CustomTableView(SBTabHelper.getFileContent(), format);
          customVBox.getChildren().set(0,  customTableView);
          currentTableView.set(3);
          reactionVBox.setVisible(false);
          compartmentVBox.setVisible(false);
          compoundVBox.setVisible(false);
          customVBox.setVisible(true);
        }
      });
      menu0.getItems().add(mI);
    }

    
    MenuItem item1 = new MenuItem("Add Row");
    item1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          switch (currentTableView.get()) {
            case 0:
                compoundTableView.addEmptyRow();
              break;
            case 1:
                reactionTableView.addEmptyRow();
              break;
            case 2:
                compartmentTableView.addEmptyRow();
              break;
            case 3:
              customTableView.addEmptyRow();
            break;
            default:
              break;
          }
        }
    });
    MenuItem item15 = new MenuItem("Add Column");
    item15.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          switch (currentTableView.get()) {
            case 0:
                compoundTableView.addNewColumn("NewCol_"+compoundTableView.getColumns().size());
              break;
            case 1:
                reactionTableView.addNewColumn("NewCol_"+reactionTableView.getColumns().size());
              break;
            case 2:
                compartmentTableView.addNewColumn("NewCol_"+compartmentTableView.getColumns().size());
              break;
            case 3:
              customTableView.addEmptyColumn();
            break;
            default:
              break;
          }
        }
    });
    
    MenuItem item24 = new MenuItem("Remove Selected Rows");
    item24.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          switch (currentTableView.get()) {
            case 0:
                compoundTableView.removeSelection();
              break;
            case 1:
                reactionTableView.removeSelection();
              break;
            case 2:
                compartmentTableView.removeSelection();
              break;
            case 3:
              customTableView.removeSelection();
            break;
            default:
              break;
          }
        }
    });
    
    MenuItem item25 = new MenuItem("Remove Selected Columns");
    item25.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          switch (currentTableView.getValue()) {
            case 0:
                compoundTableView.removeSelectedColumns();
              break;
            case 1:
                reactionTableView.removeSelectedColumns();
              break;
            case 2:
                compartmentTableView.removeSelectedColumns();
              break;
            case 3:
              customTableView.removeSelectedColumns();
            break;
            default:
              break;
          }
        }
    });
    
    

    
    Menu menu1 = new Menu("Change Table Type");
    RadioMenuItem item2 = new RadioMenuItem("Compound Table");
    RadioMenuItem item3 = new RadioMenuItem("Reaction Table");
    RadioMenuItem item4 = new RadioMenuItem("Compartment Table");
    RadioMenuItem item5 = new RadioMenuItem("Custom Table");
    item5.setSelected(true);
    ToggleGroup group = new ToggleGroup();
    item2.setToggleGroup(group);
    item3.setToggleGroup(group);
    item4.setToggleGroup(group);
    item5.setToggleGroup(group);

    currentTableView.addListener((observable, oldValue, newValue) -> {
      switch (newValue.intValue()) {
        case 0:
          item2.setSelected(true);
          break;
        case 1:
          item3.setSelected(true);
          break;
        case 2:
          item4.setSelected(true);
          break;
        case 3:
          item5.setSelected(true);
          break;
      }
    });
    
    item2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentTableView.set(0);
        reactionVBox.setVisible(false);
        compartmentVBox.setVisible(false);
        customVBox.setVisible(false);
        compoundVBox.setVisible(true);
        
      }
    });
    item3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentTableView.set(1);
        reactionVBox.setVisible(true);
        compartmentVBox.setVisible(false);
        compoundVBox.setVisible(false);
        customVBox.setVisible(false);
      }
    });
    item4.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentTableView.set(2);
        reactionVBox.setVisible(false);
        compartmentVBox.setVisible(true);
        compoundVBox.setVisible(false);
        customVBox.setVisible(false);
      }
    });
    item5.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentTableView.set(3);
        reactionVBox.setVisible(false);
        compartmentVBox.setVisible(false);
        compoundVBox.setVisible(false);
        customVBox.setVisible(true);
      }
    });
    
    menu1.getItems().addAll(item2, item3, item4, item5);

    
    Menu selectionMode = new Menu("Selection Mode");
    RadioMenuItem cellSelect = new RadioMenuItem("Cell");
    
    RadioMenuItem rowSelect = new RadioMenuItem("Row");
    rowSelect.setSelected(true);
    ToggleGroup toggleGroup2 = new ToggleGroup();
    cellSelect.setToggleGroup(toggleGroup2);
    rowSelect.setToggleGroup(toggleGroup2);
    
    for (RadioMenuItem r : Arrays.asList(cellSelect, rowSelect)) {
      r.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          for (TableView v : Arrays.asList(reactionTableView, compartmentTableView, compoundTableView, customTableView)) {
            
            switch (r.getText()) {
              case "Single":
                reactionTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                compartmentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                compoundTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                customTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                break;
              case "Multiple":
                reactionTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                compartmentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                compoundTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                customTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                break;
              case "Cell":
                reactionTableView.getSelectionModel().setCellSelectionEnabled(true);
                compartmentTableView.getSelectionModel().setCellSelectionEnabled(true);
                compoundTableView.getSelectionModel().setCellSelectionEnabled(true);
                customTableView.getSelectionModel().setCellSelectionEnabled(true);
                break;
              case "Row":
                reactionTableView.getSelectionModel().setCellSelectionEnabled(false);
                compartmentTableView.getSelectionModel().setCellSelectionEnabled(false);
                compoundTableView.getSelectionModel().setCellSelectionEnabled(false);
                customTableView.getSelectionModel().setCellSelectionEnabled(false);
                break;
            }
         }
        }
      });
    }
   
    
    selectionMode.getItems().addAll(cellSelect, rowSelect);
    
    
    Menu menu3 = new Menu("Save Selection to File");
    
    MenuItem item31 = new MenuItem("Separator: Tab");
    MenuItem item32 = new MenuItem("Separator: ,");
    MenuItem item33 = new MenuItem("Separator: ;");
    menu3.getItems().addAll(item31, item32, item33);

    for (MenuItem mI : Arrays.asList(item31, item32, item33)) {
      mI.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String separator = mI.getText().replace("Separator: ", "");
          if (separator.equals("Tab")) {
            separator = "\t";
          }
          
         
          switch (currentTableView.get()) {
            case 0:
              compoundTableView.getSelectionModel().setCellSelectionEnabled(true);
              compoundTableView.getSelectionModel().selectAll();
               EditorUtils.saveTableToFile(compoundTableView, separator);
               compoundTableView.getSelectionModel().setCellSelectionEnabled(cellSelect.isSelected());
              break;
            case 1:
              reactionTableView.getSelectionModel().setCellSelectionEnabled(true);
              reactionTableView.getSelectionModel().selectAll();
              EditorUtils.saveTableToFile(reactionTableView, separator);
              reactionTableView.getSelectionModel().setCellSelectionEnabled(cellSelect.isSelected());
              break;
            case 2:
              compartmentTableView.getSelectionModel().setCellSelectionEnabled(true);
              compartmentTableView.getSelectionModel().selectAll();
              EditorUtils.saveTableToFile(compartmentTableView, separator);
              compartmentTableView.getSelectionModel().setCellSelectionEnabled(cellSelect.isSelected());
              break;
            case 3:
              customTableView.getSelectionModel().setCellSelectionEnabled(true);
              customTableView.getSelectionModel().selectAll();
              EditorUtils.saveTableToFile(customTableView, separator);
              customTableView.getSelectionModel().setCellSelectionEnabled(cellSelect.isSelected());
            break;
            default:
              break;
          }
      }
    });
    }

    
    contextMenu.getItems().addAll(item0, item00, new SeparatorMenuItem(),  menu0, menu3, new SeparatorMenuItem(), menu1, item1, item15, item24, item25, new SeparatorMenuItem(), selectionMode);

    compoundVBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
        @Override
        public void handle(ContextMenuEvent event) {
          contextMenu.show(compoundVBox, event.getScreenX(), event.getScreenY());
        }
    });
    reactionVBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
        @Override
        public void handle(ContextMenuEvent event) {
          contextMenu.show(reactionVBox, event.getScreenX(), event.getScreenY());
        }
    });
    compartmentVBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
        @Override
        public void handle(ContextMenuEvent event) {
          contextMenu.show(compartmentVBox, event.getScreenX(), event.getScreenY());
        }
    });
    customVBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
      @Override
      public void handle(ContextMenuEvent event) {
        contextMenu.show(customVBox, event.getScreenX(), event.getScreenY());
      }
  });
    
  }



  protected void pasteFromClipboard() {
    if (Clipboard.getSystemClipboard().hasString()) {
      customTableView = new CustomTableView(Clipboard.getSystemClipboard().getContent(DataFormat.PLAIN_TEXT).toString(), "tab");
      customVBox.getChildren().set(0,  customTableView);
      currentTableView.set(3);
      reactionVBox.setVisible(false);
      compartmentVBox.setVisible(false);
      compoundVBox.setVisible(false);
      customVBox.setVisible(true);
    }else {
      new QuickInfoPopup("Import Info", "Clipboard empty!");
    }
  }


  public ReactionTableView getReactionTableView() {
    return reactionTableView;
  }



  public CompartmentTableView getCompartmentTableView() {
    return compartmentTableView;
  }



  public CompoundTableView getCompoundTableView() {
    return compoundTableView;
  }

  public CustomTableView getImportTableView() {
    return customTableView;
  }



  public SBMLDocument getSbmlDoc() {
    return sbmlDoc;
  }



  public void setSbmlDoc(SBMLDocument sbmlDoc) {
    this.sbmlDoc = sbmlDoc;
  }
  
}  

