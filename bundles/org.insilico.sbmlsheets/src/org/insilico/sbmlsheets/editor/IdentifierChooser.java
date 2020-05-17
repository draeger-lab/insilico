package org.insilico.sbmlsheets.editor;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class IdentifierChooser extends StackPane{

  public static ArrayList<String> sbTabDefinitions = new ArrayList<>(Arrays.asList("!Reaction:Comment", "!Reaction:ReferenceName", "!Reaction:ReferencePubMed",
      "!Reaction:ReferenceDOI", "!Reaction:Description", "!Reaction:Modifier", "!Reaction:Name", "!Reaction:MiriamAnnotation", 
      "!Reaction:Type", "!Reaction:Symbol", "!Reaction:PositionX", "!Reaction:PositionY", "!Reaction:ID", "!Reaction:SBML:reaction:id",
      "!Reaction:ReactionFormula", "!Reaction:Location", "!Reaction:Enzyme", "!Reaction:Regulator", "!Reaction:Model", 
      "!Reaction:Pathway", "!Reaction:SubreactionOf", "!Reaction:IsComplete", "!Reaction:IsReversible", "!Reaction:IsInEquilibrium", 
      "!Reaction:IsExchangeReaction", "!Reaction:Flux", "!Reaction:IsNonEnzymatic", "!Reaction:KineticLaw", "!Reaction:KineticLaw:Name",
      "!Reaction:KineticLaw:Formula", "!Reaction:Gene", "!Reaction:Gene:Symbol", "!Reaction:Operon", "!Reaction:Enzyme:SBML:species:id", 
      "!Reaction:Enzyme:SBML:parameter:id", "!Reaction:BuildReaction", "!Reaction:BuildEnzyme", "!Reaction:BuildEnzymeProduction",
      "!Reaction:SBOTerm", "!Reaction:Identifiers", "!Reaction:SBML:fbc:GeneAssociation", "!Reaction:SBML:fbc:LowerBound",
      "!Reaction:SBML:fbc:UpperBound", "!Compound:Comment", "!Compound:ReferenceName", "!Compound:ReferencePubMed", "!Compound:ReferenceDOI", 
      "!Compound:Description", "!Compound:Name", "!Compound:MiriamAnnotation", "!Compound:Type", "!Compound:Symbol", "!Compound:PositionX",
      "!Compound:PositionY", "!Compound:ID", "!Compound:SBML:species:id", "!Compound:SBML:speciestype:id", "!Compound:InitialValue", 
      "!Compound:InitialConcentration", "!Compound:HasOnlySubstanceUnits", "!Compound:BoundaryCondition", "!Compound:Unit", "!Compound:Location",
      "!Compound:State", "!Compound:CompoundSumFormula", "!Compound:StructureFormula", "!Compound:Charge", "!Compound:Mass",
      "!Compound:IsConstant", "!Compound:EnzymeRole", "!Compound:RegulatorRole", "!Compound:SBOTerm", "!Compound:Identifiers", 
      "!Compound:SBML:fbc:ChemicalFormula", "!Compound:SBML:fbc:Charge", "!Enzyme:Comment", "!Enzyme:ReferenceName", "!Enzyme:ReferencePubMed", 
      "!Enzyme:ReferenceDOI", "!Enzyme:Description", "!Enzyme:Name", "!Enzyme:MiriamAnnotation", "!Enzyme:Type", "!Enzyme:Symbol", 
      "!Enzyme:PositionX", "!Enzyme:PositionY", "!Enzyme:ID", "!Enzyme:CatalysedReaction", "!Enzyme:KineticLaw", "!Enzyme:KineticLaw:Name", 
      "!Enzyme:KineticLaw:Formula", "!Enzyme:Pathway", "!Enzyme:Gene", "!Enzyme:Identifiers", "!Protein:Comment", "!Protein:ReferenceName", 
      "!Protein:ReferencePubMed", "!Protein:ReferenceDOI", "!Protein:Description", "!Protein:Name", "!Protein:MiriamAnnotation", "!Protein:Type", 
      "!Protein:Symbol", "!Protein:PositionX", "!Protein:PositionY", "!Protein:ID", "!Protein:Gene", "!Protein:Mass", "!Protein:Size",
      "!Compartment:Comment", "!Compartment:ReferenceName", "!Compartment:ReferencePubMed", "!Compartment:ReferenceDOI", "!Compartment:Description",
      "!Compartment:Name", "!Compartment:MiriamAnnotation", "!Compartment:Type", "!Compartment:Symbol", "!Compartment:PositionX",
      "!Compartment:PositionY", "!Compartment:ID", "!Compartment:SBML:compartment:id", "!Compartment:OuterCompartment", 
      "!Compartment:OuterCompartment:SBML:compartment:id", "!Compartment:IsConstant", "!Compartment:Size", "!Compartment:Unit", "!Compartment:SBOTerm",
      "!Compartment:Identifiers", "!Quantity:Comment", "!Quantity:ReferenceName", "!Quantity:ReferencePubMed", "!Quantity:ReferenceDOI", 
      "!Quantity:Description", "!Quantity:Name", "!Quantity:MiriamAnnotation", "!Quantity:Type", "!Quantity:Symbol", "!Quantity:PositionX",
      "!Quantity:PositionY", "!Quantity:ID", "!Quantity:Quantity", "!Quantity:Reference", "!Quantity:QuantityName", "!Quantity:QuantityType", 
      "!Quantity:Value", "!Quantity:Mean", "!Quantity:Std", "!Quantity:Min", "!Quantity:Max", "!Quantity:Median", "!Quantity:GeometricMean",
      "!Quantity:Sign", "!Quantity:ProbDist", "!Quantity:SBML:parameter:id", "!Quantity:Unit", "!Quantity:IsConstant", "!Quantity:Scale",
      "!Quantity:Time", "!Quantity:TimePoint", "!Quantity:Condition", "!Quantity:pH", "!Quantity:Temperature", "!Quantity:Location", 
      "!Quantity:Location:SBML:compartment:id", "!Quantity:Compound", "!Quantity:Compound:SBML:species:id", "!Quantity:Compound:Identifiers:kegg:compound", 
      "!Quantity:Reaction", "!Quantity:Reaction:SBML:reaction:id", "!Quantity:Reaction:Identifiers:kegg:reaction", 
      "!Quantity:Enyzme", "!Quantity:Enyzme:SBML:species:id", "!Quantity:Enyzme:SBML:parameter:id", "!Quantity:Gene", "!Quantity:Organism",
      "!Quantity:Provenance", "!Quantity:SBOTerm", "!Quantity:Identifiers", "!Quantity:BiologicalElement", "!Quantity:MathematicalType",
      "!Quantity:DataGeometricStd", "!Quantity:PriorMedian", "!Quantity:PriorStd", "!Quantity:PriorGeometricStd", "!Quantity:LowerBound",
      "!Quantity:UpperBound", "!Quantity:DataStd", "!Quantity:PhysicalType", "!Quantity:Dependence", "!Quantity:UseAsPriorInformation",
      "!Quantity:SBMLElement", "!Quantity:Abbreviation", "!Quantity:MatrixInfo", "!Regulator:Comment", "!Regulator:ReferenceName",
      "!Regulator:ReferencePubMed", "!Regulator:ReferenceDOI", "!Regulator:Description", "!Regulator:Name", "!Regulator:MiriamAnnotation",
      "!Regulator:Type", "!Regulator:Symbol", "!Regulator:PositionX", "!Regulator:PositionY", "!Regulator:ID", "!Regulator:State", 
      "!Regulator:TargetGene", "!Regulator:TargetOperon", "!Regulator:TargetPromoter", "!Regulator:Identifiers", "!Gene:Comment",
      "!Gene:ReferenceName", "!Gene:ReferencePubMed", "!Gene:ReferenceDOI", "!Gene:Description", "!Gene:Name", "!Gene:MiriamAnnotation",
      "!Gene:Type", "!Gene:Symbol", "!Gene:PositionX", "!Gene:PositionY", "!Gene:ID", "!Gene:LocusName", "!Gene:GeneProduct", 
      "!Gene:GeneProduct:SBML:species:id", "!Gene:Operon", "!Gene:Identifiers", "!Gene:SBML:fbc:ID", "!Gene:SBML:fbc:Name",
      "!Gene:SBML:fbc:GeneProduct", "!Gene:SBML:fbc:GeneAssociation", "!Gene:SBML:fbc:Label", "!Relation:Comment", "!Relation:ReferenceName",
      "!Relation:ReferencePubMed", "!Relation:ReferenceDOI", "!Relation:Description", "!Relation:ID", "!Relation:From", "!Relation:To", 
      "!Relation:IsSymmetric", "!Relation:Value:QuantityType", "!Definition:ComponentName", "!Definition:ComponentType", "!Definition:IsPartOf",
      "!Definition:Format", "!Definition:Description", "!QuantityMatrix:Comment", "!QuantityMatrix:ReferenceName", "!QuantityMatrix:ReferencePubMed", 
      "!QuantityMatrix:ReferenceDOI", "!QuantityMatrix:Description", "!QuantityMatrix:Name", "!QuantityMatrix:MiriamAnnotation", "!QuantityMatrix:Type",
      "!QuantityMatrix:Symbol", "!QuantityMatrix:PositionX", "!QuantityMatrix:PositionY", "!QuantityMatrix:Time", "!QuantityMatrix:TimePoint",
      "!QuantityMatrix:>Table:Column", "!QuantityMatrix:>Document:Table:Column", "!QuantityMatrix:ID", "!QuantityMatrix:QuantityName", 
      "!QuantityMatrix:QuantityType", "!QuantityMatrix:Value", "!QuantityMatrix:Mean", "!QuantityMatrix:Std", "!QuantityMatrix:Min", "!QuantityMatrix:Max", 
      "!QuantityMatrix:Median", "!QuantityMatrix:GeometricMean", "!QuantityMatrix:Sign", "!QuantityMatrix:ProbDist", "!QuantityMatrix:SBML:parameter:id", 
      "!QuantityMatrix:Unit", "!QuantityMatrix:Scale", "!QuantityMatrix:Compound", "!QuantityMatrix:Compound:SBML:species:id", "!QuantityMatrix:Reaction",
      "!QuantityMatrix:Reaction:SBML:reaction:id", "!QuantityMatrix:Enyzme", "!QuantityMatrix:Enyzme:SBML:species:id", "!QuantityMatrix:Enyzme:SBML:parameter:id",
      "!QuantityMatrix:Protein", "!QuantityMatrix:Protein:SBML:species:id", "!QuantityMatrix:Enyzme:SBML:parameter:id", "!QuantityMatrix:Gene",
      "!StoichiometricMatrix:ReactionID", "!StoichiometricMatrix:Stoichiometry", "!StoichiometricMatrix:Substrate", "!StoichiometricMatrix:Product",
      "!StoichiometricMatrix:Location", "!Position:Element", "!Position:PositionX", "!Position:PositionY", "!Measurement:Sample", "!Measurement:Time", 
      "!Measurement:Unit", "!Measurement:ValueType", "!Measurement:Description", "!QuantityInfo:QuantityType", "!QuantityInfo:Symbol", 
      "!QuantityInfo:Unit", "!QuantityInfo:Constant", "!QuantityInfo:Element", "!QuantityInfo:RelatedElement", "!QuantityInfo:Scaling", 
      "!QuantityInfo:Dependence", "!QuantityInfo:PriorMedian", "!QuantityInfo:PriorStd", "!QuantityInfo:LowerBound", "!QuantityInfo:UpperBound", 
      "!QuantityInfo:ErrorStd", "!QuantityInfo:DataStd", "!QuantityInfo:SBMLElement", "!QuantityInfo:SBMLElementType", "!QuantityInfo:Abbreviation", 
      "!QuantityInfo:ID", "!QuantityInfo:MatrixInfo", "!Config:Option", "!Config:Value", "!rxnconContingencyList:UID:Contingency", 
      "!rxnconContingencyList:Target", "!rxnconContingencyList:Contingency", "!rxnconContingencyList:Modifier", "!rxnconContingencyList:Reference:Identifiers:pubmed",
      "!rxnconContingencyList:Quality", "!rxnconContingencyList:Comment", "!rxnconContingencyList:InternalComplexID", "!rxnconReactionList:ID",
      "!rxnconReactionList:UID:Reaction", "!rxnconReactionList:ComponentA:Name", "!rxnconReactionList:ComponentA:Domain", "!rxnconReactionList:ComponentA:Residue",
      "!rxnconReactionList:Reaction", "!rxnconReactionList:ComponentB:Name", "!rxnconReactionList:ComponentB:Domain", "!rxnconReactionList:ComponentB:Residue",
      "!rxnconReactionList:Quality", "!rxnconReactionList:Literature:Identifiers:pubmed", "!rxnconReactionList:Comment", "!FbcObjective:ID", 
      "!FbcObjective:Name", "!FbcObjective:SBML:fbc:type", "!FbcObjective:SBML:fbc:active", "!FbcObjective:SBML:fbc:objective", "!Layout:ID",
      "!Layout:Name", "!Layout:SBML:layout:modelEntity", "!Layout:SBML:layout:compartment:id", "!Layout:SBML:layout:reaction:id", 
      "!Layout:SBML:layout:species:id", "!Layout:SBML:layout:curveSegment", "!Layout:SBML:layout:X", "!Layout:SBML:layout:Y", "!Layout:SBML:layout:width",
      "!Layout:SBML:layout:height", "!Layout:SBML:layout:text", "!Layout:SBML:layout:speciesRole", "!ReactionStoichiometry:ID",
      "!ReactionStoichiometry:Reaction", "!ReactionStoichiometry:Stoichiometry", "!ReactionStoichiometry:Substrate", "!ReactionStoichiometry:Product",
      "!ReactionStoichiometry:Location", "!SparseMatrixOrdered:RowNumber", "!SparseMatrixOrdered:ColumnNumber", "!SparseMatrixOrdered:Value",
      "!SparseMatrix:RowID", "!SparseMatrix:ColumnID", "!SparseMatrix:Value", "!SparseMatrixRow:RowID", "!SparseMatrixRow:RowString", 
      "!SparseMatrixColumn:ColumnID", "!SparseMatrixColumn:ColumnString", "!Identifiers:"));
  
      
  ArrayList<String> superDefinitions = new ArrayList<>(Arrays.asList("Reaction", "!Compound", "!Enzyme", "!Protein", 
      "!Compartment", "Quantity", "Regulator", "Gene", "Relation", "Definition"));
  
  public IdentifierChooser(){
    init();
  }

  TextField searchBar = new TextField();
  long lastUpdateTime = System.currentTimeMillis();
  Button b = new Button("OK");
  Button b2 = new Button("Cancel");
  SimpleLongProperty timeElapsedSinceLastEdit = new SimpleLongProperty(0);
  ListView<String> resultList = new ListView<>();
  String bestMatch = "";
  
  
  private void init() {
    resultList.getItems().addAll(sbTabDefinitions);
    Label label = new Label("Search Definition");
    searchBar.setPromptText("Search...");
    resultList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    initListener();
    
    VBox vbox = new VBox();
    ScrollPane pane = new ScrollPane(resultList);
    
    this.getChildren().add(new VBox(new HBox(label, searchBar), new HBox(b, b2), pane));
  }
  
  
  private void setMatch() {
    this.searchBar.setText(bestMatch);
  }


  public ListView<String> getResultList() {
    return resultList;
  }


  public TextField getSearchBar() {
    return searchBar;
  }



  public Button getB() {
    return b;
  }


  public Button getB2() {
    return b2;
  }


  private void initListener() {
    
   searchBar.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        resultList.getItems().clear();
        try {
          Thread.sleep(90);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        int minDist= 1000;
         
          if (!newValue.equals(resultList.getSelectionModel().getSelectedItem())) {
            String s = newValue;
            for (String p : sbTabDefinitions) {
              if (p.startsWith("!"+s.replaceAll("!", ""))) {
                resultList.getItems().add(p);
              }
            }
          }

      }
    });
    
  }

  public static int minDistance(String word1, String word2) {
    int len1 = word1.length();
    int len2 = word2.length();
 
    int[][] dp = new int[len1 + 1][len2 + 1];
 
    for (int i = 0; i <= len1; i++) {
        dp[i][0] = i;
    }
 
    for (int j = 0; j <= len2; j++) {
        dp[0][j] = j;
    }
 
    for (int i = 0; i < len1; i++) {
        char c1 = word1.charAt(i);
        for (int j = 0; j < len2; j++) {
            char c2 = word2.charAt(j);
 
            if (c1 == c2) {
                dp[i + 1][j + 1] = dp[i][j];
            } else {
                int replace = dp[i][j] + 1;
                int insert = dp[i][j + 1] + 1;
                int delete = dp[i + 1][j] + 1;
 
                int min = replace > insert ? insert : replace;
                min = delete > min ? min : delete;
                dp[i + 1][j + 1] = min;
            }
        }
    }
 
    return dp[len1][len2];
}
  
  
}
