package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.text.parser.ParseException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;


public class ReactionInstance{
//define in attributeTypes whether value should be treated as string, double, integer
  public static ArrayList<String> attributeTypes = new ArrayList<>(Arrays.asList(
      "id_Str", "name_Str", "reactionId_Str", "sumFormula_Str", "location_Str", "enzyme_Str", "model_Str", "pathway_Str", 
      "subreactionOf_Str", "isComplete_Bool", "isReversible_Bool", "isInEquilibrium_Bool", "isExchangeReaction_Bool",
       "flux_Dbl", "isNonEnzymatic_Bool", "kineticLaw_Str", 
      "gene_Str", "operon_Str", "enzymeSpeciesId_Str",
      "enzymeParameterId_Str", "buildReaction_Bool", "buildEnzyme_Bool", "buildEnzymeProduction_Bool", 
      "comment_Str", "referenceName_Str", "referencePubMed_Str", "referenceDOI_Str", "description_Str", "modifier_Str", "miriamAnnotations_Str", "type_Str", "symbol_Str",
      "positionX_Dbl", "positionY_Dbl", "sboTerm_Str", "identifiers_Str"));
 
  public static ArrayList<String> sbTabCol = new ArrayList<>(Arrays.asList(
      "!ID", "!Name", "!SBML:reaction:id", "!ReactionFormula", "!Location", "!Enzyme", "!Model", "!Pathway", "!SubreactionOf", 
      "!IsComplete", "!IsReversible", "!IsInEquilibrium", "!IsExchangeReaction", "!Flux", "!IsNonEnzymatic", 
      "!KineticLaw", "!Gene", "!Operon",
      "!Enzyme:SBML:species:id", "!Enzyme:SBML:parameter:id",
      "!BuildReaction", "!BuildEnzyme", "!BuildEnzymeProduction", "!Comment", "!ReferenceName", "!ReferencePubMed", "!ReferenceDOI", "!Description", "!Modifier",
      "!MiriamAnnotations", "!Type", "!Symbol", "!PositionX", "!PositionY", "!SBOTerm", "!Identifiers"));
  
  public static ArrayList<String> primaryKeys = new ArrayList<>(Arrays.asList("id", "reactionId"));
  
  String defaultDouble = "0.0";
  String defaultInt = "0";
  public StringProperty id = new SimpleStringProperty("nan");
  public StringProperty name = new SimpleStringProperty("nan");
  public StringProperty reactionId = new SimpleStringProperty("nan");
  public StringProperty sumFormula = new SimpleStringProperty("nan + nan <=> nan");
  public StringProperty location = new SimpleStringProperty("nan");
  public StringProperty enzyme = new SimpleStringProperty("nan");
  public StringProperty model = new SimpleStringProperty("nan");
  public StringProperty pathway = new SimpleStringProperty("nan");
  public StringProperty subreactionOf = new SimpleStringProperty("nan");
  public BooleanProperty isComplete = new SimpleBooleanProperty(false);
  public BooleanProperty isReversible = new SimpleBooleanProperty(false);
  public BooleanProperty isInEquilibrium = new SimpleBooleanProperty(false);
  public BooleanProperty isExchangeReaction = new SimpleBooleanProperty(false);
  public BooleanProperty isNonEnzymatic = new SimpleBooleanProperty(false);

  public StringProperty flux = new SimpleStringProperty("0.0");
  public StringProperty kineticLaw = new SimpleStringProperty("nan");
  public StringProperty gene = new SimpleStringProperty("nan");
  public StringProperty operon = new SimpleStringProperty("nan");

  public StringProperty enzymeSpeciesId = new SimpleStringProperty("nan");
  public StringProperty enzymeParameterId = new SimpleStringProperty("nan");
  
  public BooleanProperty buildReaction = new SimpleBooleanProperty(false);
  public BooleanProperty buildEnzyme = new SimpleBooleanProperty(false);
  public BooleanProperty buildEnzymeProduction = new SimpleBooleanProperty(false);
  
  public StringProperty comment = new SimpleStringProperty("nan");
  public StringProperty referenceName = new SimpleStringProperty("nan");
  public StringProperty referencePubMed = new SimpleStringProperty("nan");
  public StringProperty referenceDOI = new SimpleStringProperty("nan");
  public StringProperty description = new SimpleStringProperty("nan");
  public StringProperty modifier = new SimpleStringProperty("nan");
  
  public StringProperty miriamAnnotations = new SimpleStringProperty("nan");
  public StringProperty type = new SimpleStringProperty("nan");
  public StringProperty symbol = new SimpleStringProperty("nan");
  
  public StringProperty positionX = new SimpleStringProperty("0.0");
  public StringProperty positionY = new SimpleStringProperty("0.0");
  
  public StringProperty sboTerm = new SimpleStringProperty("0");
  public StringProperty identifiers = new SimpleStringProperty("nan");
  
  ArrayList<ObservableValue> attributes = new ArrayList<>(Arrays.asList(id, name, reactionId, sumFormula, location, 
      enzyme, model, pathway, subreactionOf, isComplete, isReversible, isInEquilibrium, isExchangeReaction,
      flux, isNonEnzymatic, kineticLaw, gene, operon, 
      enzymeSpeciesId, enzymeParameterId, buildReaction, buildEnzyme, buildEnzymeProduction,
      comment, referenceName, referencePubMed, referenceDOI, description, modifier, miriamAnnotations, type, symbol, positionX, positionY,
      sboTerm, identifiers));
  
  public HashMap<String, String> annotations = new HashMap<>();
  
  public ReactionInstance(String idStr, String reactionIdStr) {
    this.id.setValue(idStr);
    this.reactionId.setValue(reactionIdStr);
  }

  /*
   * property value getter/setter
   */

  public String getId() {
    return id.getValue();
  }


  public String getName() {
    return name.getValue();
  }


  public String getReactionId() {
    return reactionId.getValue();
  }

  public String getSumFormula() {
    return sumFormula.getValue();
  }
  
  public String getLocation() {
    return location.getValue();
  }


  public String getEnzyme() {
    return enzyme.getValue();
  }


  public String getModel() {
    return model.getValue();
  }

  public String getPathway() {
    return pathway.getValue();
  }
  
  
    public String getSubreactionOf() {
    return subreactionOf.getValue();
  }
   
   public String geKineticLaw() {
    return kineticLaw.getValue();
  } 

  
   public String getGene() {
     return gene.getValue();
   }

   public String getOperon() {
     return operon.getValue();
   }
   
  public String getFlux() {
    return flux.getValue().toString();
  }
  
  public String getEnzymeSpeciesId() {
    return enzymeSpeciesId.getValue().toString();
  }
  public String getEnzymeParameterId() {
    return enzymeParameterId.getValue().toString();
  }



  public boolean getBuildReaction() {
    return buildReaction.getValue();
  }
  public boolean getBuildEnzyme() {
    return buildEnzyme.getValue();
  }
  public boolean getBuildEnzymeProduction() {
    return buildEnzymeProduction.getValue();
  }


  public boolean getIsComplete() {
    return isComplete.getValue();
  }
  
  public boolean getIsReversible() {
    return isReversible.getValue();
  }
  
  public boolean getIsInEquilibrium() {
    return isInEquilibrium.getValue();
  }
  
  public boolean getIsExchangeReaction() {
    return isExchangeReaction.getValue();
  }
  
  public boolean getIsNonEnzymatic() {
    return isNonEnzymatic.getValue();
  }

  public String getComment() {
    return comment.getValue();
  }

  public String getReferenceName() {
    return referenceName.getValue();
  }

  public String getReferencePubMed() {
    return referencePubMed.getValue();
  }

  public String getReferenceDOI() {
    return referenceDOI.getValue();
  }

  public String getDescription() {
    return description.getValue();
  }

  public String getModifier() {
    return modifier.getValue();
  }
  
  public String getMiriamAnnotations() {
    return miriamAnnotations.getValue();
  }
  public String getType() {
    return type.getValue();
  }
  public String getSymbol() {
    return symbol.getValue();
  }
  public String getPositonX() {
    return positionX.getValue();
  }
  public String getPositionY() {
    return positionY.getValue();
  }
  public String getSboTerm() {
    return sboTerm.getValue();
  }
  public String getIdentifiers() {
    return identifiers.getValue();
  }


  /*
   * property getter/setter
   */
   
  public StringProperty idProperty() {
    return id;
  }


  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty reactionIdProperty() {
    return reactionId;
  }

  public StringProperty locationProperty() {
    return location;
  }

  
  public StringProperty sumFormulaProperty() {
    return sumFormula;
  }
  
  public StringProperty enzymeProperty() {
    return enzyme;
  }


  public StringProperty modelProperty() {
    return model;
  }


  public StringProperty pathwayProperty() {
    return pathway;
  }
  public StringProperty subreactionOfProperty() {
    return subreactionOf;
  }
  
  public StringProperty fluxProperty() {
    return flux;
  }
  
  public StringProperty kineticLawProperty() {
    return kineticLaw;
  }

  
  public StringProperty geneProperty() {
    return gene;
  }

  public StringProperty operonProperty() {
    return operon;
  }

  public StringProperty enzymeSpeciesIdProperty() {
    return enzymeSpeciesId;
  }
  public StringProperty enzymeParameterIdProperty() {
    return enzymeParameterId;
  }

  public BooleanProperty buildReactionProperty() {
    return buildReaction;
  }
  public BooleanProperty buildEnzymeProperty() {
    return buildEnzyme;
  }
  public BooleanProperty buildEnzymeProductionProperty() {
    return buildEnzymeProduction;
  }

  public BooleanProperty isCompleteProperty() {
    return isComplete;
  }
  public BooleanProperty isReversibleProperty() {
    return isReversible;
  }
  public BooleanProperty isInEquilibriumProperty() {
    return isInEquilibrium;
  }
  public BooleanProperty isExchangeReactionProperty() {
    return isExchangeReaction;
  }

  public BooleanProperty isNonEnzymaticProperty() {
    return isNonEnzymatic;
  }
  
  public StringProperty commentProperty() {
    return comment;
  }

  public StringProperty referenceNameProperty() {
    return referenceName;
  }

  public StringProperty referencePubMedProperty() {
    return referencePubMed;
  }

  public StringProperty referenceDOIProperty() {
    return referenceDOI;
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public StringProperty modifierProperty() {
    return modifier;
  }
  
  public StringProperty miriamAnnotationsProperty() {
    return miriamAnnotations;
  }
  public StringProperty typeProperty() {
    return type;
  }
  public StringProperty symbolProperty() {
    return symbol;
  }
  public StringProperty positionXProperty() {
    return positionX;
  }public StringProperty positionYProperty() {
    return positionY;
  }
  
  public StringProperty sboTermProperty() {
    return sboTerm;
  }public StringProperty identifiersProperty() {
    return identifiers;
  }
  
  
  public String toCSVLine() {
    StringBuilder stringBuilder = new StringBuilder();
    for (ObservableValue prop : attributes) {
        stringBuilder.append(prop.getValue()+"\t");
        stringBuilder.append("\t");
    }
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }

  public void setProperty(String attribute, String value) {
  
    switch (attribute) {
      case "id":
        id.setValue(value);
        break;
      case "name":
        name.setValue(value);
        break;
      case "reactionId":
        reactionId.setValue(value);
        break;
      case "location":
        location.setValue(value);
        break;
      case "sumFormula":
        sumFormula.setValue(value);
        break;
      case "enzyme":
        enzyme.setValue(value);
        break;
      case "model":
        model.setValue(value);
        break;
      case "pathway":
        pathway.setValue(value);
        break;
      case "subreactionOf":
        subreactionOf.setValue(value);
        break;
      case "kineticLaw":
        kineticLaw.setValue(value);
        break;
      case "gene":
        gene.setValue(value);
        break;
      case "operon":
        operon.setValue(value);
        break;
      case "flux":
        flux.setValue(value);
        break;
      case "enzymeSpeciesId":
        enzymeSpeciesId.setValue(value);
        break;
      case "enzymeParameterId":
        enzymeParameterId.setValue(value);
        break;
      case "buildReaction":
        buildReaction.setValue(Boolean.getBoolean(value));
        break;
      case "buildEnzyme":
        buildEnzyme.setValue(Boolean.getBoolean(value));
        break;
      case "buildEnzymeProduction":
        buildEnzymeProduction.setValue(Boolean.getBoolean(value));
        break;
      case "isComplete":
        isComplete.setValue(Boolean.getBoolean(value));
        break;
      case "isReversible":
        isReversible.setValue(Boolean.getBoolean(value));
        break;
      case "isInEquilibrium":
        isInEquilibrium.setValue(Boolean.getBoolean(value));
        break;
      case "isExchangeReaction":
        isExchangeReaction.setValue(Boolean.getBoolean(value));
        break;
      case "comment":
        comment.setValue(value);
        break;
      case "referenceName":
        referenceName.setValue(value);
        break;
      case "referencePubMed":
        referencePubMed.setValue(value);
        break;
      case "referenceDOI":
        referenceDOI.setValue(value);
        break;
      case "description":
        description.setValue(value);
        break;
      case "modifier":
        modifier.setValue(value);
        break;
      case "miriamAnnotations":
        miriamAnnotations.setValue(value);
        break;
      case "type":
        type.setValue(value);
        break;
      case "symbol":
        symbol.setValue(value);
        break;
      case "positionX":
        positionX.setValue(value);
        break;
      case "positionY":
        positionY.setValue(value);
        break;
      case "sboTerm":
        sboTerm.setValue(value);
        break;
      case "identifiers":
        identifiers.setValue(value);
        break;
      default:
        break;
    }
  }
  
  public Reaction instanceToSBMLReaction(int level, int version, Model model) {
 
    Reaction reaction = model.createReaction();
    reaction.setId(reactionId.getValue());
    reaction.setName(name.getValue());
    reaction.setSBOTerm(SBMLUtils.convertToSBO(sboTerm.getValue()));
    reaction.setCompartment(location.getValue());
    reaction.setReversible(isReversible.getValue());
    reaction.setKineticLaw(new KineticLaw());
    
    return reaction;
  }
  
}
