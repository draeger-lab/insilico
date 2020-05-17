package org.insilico.sbmlsheets.core;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;
import org.insilico.sbmlsheets.editor.SpreadsheetView;
import org.insilico.sbmlsheets.editor.TableEditorView;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SBMLHelper implements TreeNodeChangeListener {

  String modelName = "defaultModelName";
  String compartmentName = "defaultCompartmentName";
  
  public SBMLHelper(){
    
  }
  
  SBMLHelper(String modelName, String compartmentName){
    this.modelName = modelName;
    this.compartmentName = compartmentName;
  }
  
  public void saveAsFile(SBMLDocument doc) {
    Stage stage = new Stage();
    FileChooser fileChooser = new FileChooser();
    String fileType = "xml";
    FileChooser.ExtensionFilter extFilter = 
        new FileChooser.ExtensionFilter(fileType.toUpperCase()+" files (*."+fileType+")", "*."+fileType);
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(stage);
    if(file != null){
      SBMLWriter writer = new SBMLWriter();
      try {
        writer.write(doc,  file.getAbsolutePath());
      }
      catch (SBMLException | FileNotFoundException | XMLStreamException e) {
        e.printStackTrace();
      }
    }
  }
  
  public String getDocContent(SBMLDocument doc) {
    SBMLWriter writer = new SBMLWriter();
    try {
      return writer.writeSBMLToString(doc);
    }
    catch (SBMLException | XMLStreamException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  public static String getFileContent() {
    Stage stage = new Stage();
    FileChooser chooser = new FileChooser();
    File file = chooser.showOpenDialog(stage);
    if (file != null) {
      final List<String> lines;
      try {
        try {
          SBMLReader reader = new SBMLReader();
          SBMLDocument doc = reader.readSBMLFromFile(file.getAbsolutePath());
          SBMLWriter writer = new SBMLWriter();
          return writer.writeSBMLToString(doc);
        }
        catch (SBMLException | XMLStreamException e) {
          e.printStackTrace();
        }
      } catch (IOException ex) { 
          return "";
      } 
    }
    return "";
  }  
  
  
  public SBMLDocument createEmptySBMLDoc(int level, int version) {
    SBMLDocument doc = new SBMLDocument(level, version);
    doc.addTreeNodeChangeListener(this);
       Model model = doc.createModel(modelName);
    Compartment compartment = model.createCompartment(compartmentName);
    compartment.setSize(1d);

    return doc;

  }

  @Override
  public void propertyChange(PropertyChangeEvent arg0) {
    
  }

  @Override
  public void nodeAdded(TreeNode arg0) {
    
  }

  @Override
  public void nodeRemoved(TreeNodeRemovedEvent arg0) {
    
  }
  
  public SBMLDocument createSBMLDocFromSBTab(TableEditorView editor, boolean addCompounds, boolean addReactions, boolean addCompartments) {
    int level = 3;
    int version = 2;
    SBMLDocument doc = createEmptySBMLDoc(level, version);

    Model model = doc.getModel();
   
    if (addCompounds) {
      for (CompoundInstance cI : editor.getCompoundTableView().getItems()) {
        cI.instanceToSBMLSpecies(level, version, model);
        
      }
    }
    if (addCompartments) {
      for (CompartmentInstance coI : editor.getCompartmentTableView().getItems()) {
        coI.instanceToSBMLCompartment(level, version, model);
      }
    }
    if (addReactions) {
      for (ReactionInstance rI : editor.getReactionTableView().getItems()) {
        rI.instanceToSBMLReaction(level, version, model);
      }
    }
    
    return doc;
  }
}
