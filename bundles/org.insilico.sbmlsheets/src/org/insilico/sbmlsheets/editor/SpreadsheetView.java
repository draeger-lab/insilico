package org.insilico.sbmlsheets.editor; 

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.insilico.sbmlsheets.core.CompartmentTableView;
import org.insilico.sbmlsheets.core.CompoundTableView;
import org.insilico.sbmlsheets.core.SBTabHelper;
import org.insilico.sbmlsheets.core.CustomTableView;
import org.insilico.sbmlsheets.core.ReactionTableView;
import org.insilico.sbmlsheets.core.SBMLHelper;
import org.insilico.sbmlsheets.core.Spreadsheet;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLWriter;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;


public class SpreadsheetView {
  
  @Inject
  IWorkspace ws;

  @Inject
  EModelService ms;

  @Inject
  MApplication app;

  
  @Inject
  private Spreadsheet doc;
  
  VBox compoundVBox = new VBox();
  VBox reactionVBox = new VBox();
  VBox compartmentVBox = new VBox();
  VBox importVBox = new VBox();
 
  
  ArrayList<TableView> tables = new ArrayList<TableView>();

  
  @PostConstruct
  private void init(BorderPane parent) {
    parent.setCenter(new TableEditorView("New", doc.getFileLocation()).getContent());
  }

}

