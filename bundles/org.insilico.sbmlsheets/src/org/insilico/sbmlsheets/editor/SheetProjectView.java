package org.insilico.sbmlsheets.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.insilico.sbmlsheets.core.HelpContent;
import org.insilico.sbmlsheets.core.SBMLUtils;
import org.insilico.sbmlsheets.core.TableEditor;
import org.insilico.sbmlsheets.core.SheetWriter;

public class SheetProjectView {
   @Inject
   TableEditor project;


   ObservableList<String> pathsInDir;
   private final int PREF_WIDTH = 400;
   private final int PREF_HEIGHT_SCROLL = 700;
   

   @PostConstruct
   private void init(BorderPane parent) {
      this.makeView(parent);
   }

   private BorderPane makeView(BorderPane parent) {
      
      this.pathsInDir = FXCollections.observableArrayList(this.project.readFilesInDir(new File(this.project.getUri().replace(".sheets", ""))));
      MenuBar bar = new MenuBar();
      
      TabPane pane = new TabPane();
      Tab tab1 = new Tab("Start SBML Sheets Project");
      pane.getTabs().add(tab1);
      Menu fileMenu = new Menu("File");
      MenuItem b = new MenuItem("Create New Table");
      MenuItem open1 = new MenuItem("Open Table File");
      MenuItem closeWindow = new MenuItem("Close InSilico");
      fileMenu.getItems().addAll(b, open1, closeWindow);
      bar.getMenus().add(fileMenu);
      BorderPane bPane = new BorderPane();
      bPane.setTop(bar);
      TextArea textArea = new TextArea(HelpContent.getStartText());
      textArea.setEditable(false);
      bPane.setCenter(textArea);
      tab1.setContent(bPane);
      
      
      open1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.getExtensionFilters().addAll(
              new ExtensionFilter("CSV files", "*.csv"),
              new ExtensionFilter("TAB files", "*.tab"),
              new ExtensionFilter("TSV files", "*tsv"));
          File file = fileChooser.showOpenDialog(new Stage());
          if(file != null){
            TableEditorView newEditorTab = new TableEditorView("Editor ("+file.getName()+")", file.getAbsolutePath());//, project.getUri());
            pane.getTabs().add(newEditorTab);
          }else {
          new QuickInfoPopup("Import failed", "File could not be imported, check for text property!");
          }
 
        }
     });
     
      b.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          TableEditorView newEditorTab = new TableEditorView("New Editor ("+pane.getTabs().size()+")");//, project.getUri());
          pane.getTabs().add(newEditorTab);
        }
     });
      
      parent.setCenter(pane);
     
      return parent;
   }

}
