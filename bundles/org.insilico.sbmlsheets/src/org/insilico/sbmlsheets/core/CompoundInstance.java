package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class CompoundInstance{
  
  public static ArrayList<String> attributeTypes = new ArrayList<>(Arrays.asList(
      "id_Str", "name_Str", "speciesId_Str", "speciesType_Str", "initValue_Dbl", "unit_Str", "location_Str", "state_Str", 
      "compoundReactionFormula_Str", "structureFormula_Str", "charge_Int", "mass_Dbl", "isConstant_Bool", 
      "enzymeRole_Str", "regulatorRole_Str",
      "comment_Str", "referenceName_Str", "referencePubMed_Str", "referenceDOI_Str", "description_Str", "miriamAnnotations_Str", "type_Str", "symbol_Str",
      "positionX_Dbl", "positionY_Dbl", "sboTerm_Str", "identifiers_Str"));
 
  public static ArrayList<String> sbTabCol = new ArrayList<>(Arrays.asList(
      "!ID", "!Name", "!SBML:species:id", "!SBML:speciestype:id", "!InitialValue", "!Unit", "!Location", "!State",
      "!CompoundSumFormula", "!StructureFormula", "!Charge", "!Mass", "!IsConstant",
      "!EnzymeRole", "!RegulatorRole", "!Comment", "!ReferenceName",
      "!ReferencePubMed", "!ReferenceDOI", "!Description",
      "!MiriamAnnotations", "!Type", "!Symbol", "!PositionX", "!PositionY", "!SBOTerm", "!Identifiers"));
  
  public static ArrayList<String> primaryKeys = new ArrayList<>(Arrays.asList("id", "speciesId"));
  
  double defaultDouble = 0.0;
  int defaultInt = 0;
  public StringProperty id = new SimpleStringProperty("nan");
  public StringProperty name = new SimpleStringProperty("nan");
  public StringProperty speciesId = new SimpleStringProperty("nan");
  public StringProperty speciesType = new SimpleStringProperty("nan");
  public StringProperty initValue = new SimpleStringProperty("0.0");
  public StringProperty unit = new SimpleStringProperty("dimensionless");
  public StringProperty location = new SimpleStringProperty("nan");
  public StringProperty state = new SimpleStringProperty("nan");
  public StringProperty compoundReactionFormula = new SimpleStringProperty("nan");
  public StringProperty structureFormula = new SimpleStringProperty("nan");
  public StringProperty charge = new SimpleStringProperty("0");
  public StringProperty mass = new SimpleStringProperty("0.0");
  public BooleanProperty isConstant = new SimpleBooleanProperty(false);
  public StringProperty enzymeRole = new SimpleStringProperty("nan");
  public StringProperty regulatorRole = new SimpleStringProperty("nan");
  public StringProperty comment = new SimpleStringProperty("nan");
  public StringProperty referenceName = new SimpleStringProperty("nan");
  public StringProperty referencePubMed = new SimpleStringProperty("nan");
  public StringProperty referenceDOI = new SimpleStringProperty("nan");
  public StringProperty description = new SimpleStringProperty("nan");
  
  public StringProperty miriamAnnotations = new SimpleStringProperty("nan");
  public StringProperty type = new SimpleStringProperty("nan");
  public StringProperty symbol = new SimpleStringProperty("nan");
  
  public StringProperty positionX = new SimpleStringProperty("0.0");
  public StringProperty positionY = new SimpleStringProperty("0.0");
  public StringProperty sboTerm = new SimpleStringProperty("SBO:0000001");
  public StringProperty identifiers = new SimpleStringProperty("nan");
  ArrayList<ObservableValue> attributes = new ArrayList<>(Arrays.asList(id, name, speciesId, speciesType, initValue, 
      unit, location, state, compoundReactionFormula,structureFormula, charge, mass, isConstant,
      enzymeRole, regulatorRole, comment, referenceName,
      referencePubMed, referenceDOI, description, miriamAnnotations, type, symbol, positionX, 
      positionY, sboTerm, identifiers));
  public HashMap<String, String> annotations = new HashMap<>();
  
  public CompoundInstance(String idStr, String speciesIdStr) {
    this.id.setValue(idStr);
    this.speciesId.setValue(speciesIdStr);
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


  public String getSpeciesId() {
    return speciesId.getValue();
  }


  public String getSpeciesType() {
    return speciesType.getValue();
  }


  public String getInitValue() {
    return initValue.getValue();
  }


  public String getUnit() {
    return unit.getValue();
  }


  public String getLocation() {
    return location.getValue();
  }


  public String getState() {
    return state.getValue();
  }


  public String getCompoundReactionFormula() {
    return compoundReactionFormula.getValue();
  }


  public String getStructureFormula() {
    return structureFormula.getValue();
  }


  public String getCharge() {
    return charge.getValue();
  }


  public String getMass() {
    return mass.getValue();
  }


  public boolean getIsConstant() {
    return isConstant.getValue();
  }


  public String getEnzymeRole() {
    return enzymeRole.getValue();
  }


  public String getRegulatorRole() {
    return regulatorRole.getValue();
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
  
  public BooleanProperty getIsConstantProperty() {
    return isConstant;
  }

  public StringProperty idProperty() {
    return id;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty speciesIdProperty() {
    return speciesId;
  }

  public StringProperty speciesTypeProperty() {
    return speciesType;
  }

  
  public StringProperty initValueProperty() {
    return initValue;
  }


  public StringProperty unitProperty() {
    return unit;
  }


  public StringProperty locationProperty() {
    return location;
  }


  public StringProperty stateProperty() {
    return state;
  }


  public StringProperty compoundReactionFormulaProperty() {
    return compoundReactionFormula;
  }


  public StringProperty structureFormulaProperty() {
    return structureFormula;
  }


  public StringProperty chargeProperty() {
    return charge;
  }


  public StringProperty massProperty() {
    return mass;
  }


  public StringProperty enzymeRoleProperty() {
    return enzymeRole;
  }


  public StringProperty regulatorRoleProperty() {
    return regulatorRole;
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
      case "speciesId":
        speciesId.setValue(value);
        break;
      case "speciesType":
        speciesType.setValue(value);
        break;
      case "initValue":
        initValue.setValue(value);
        break;
      case "unit":
        unit.setValue(value);
        break;
      case "location":
        location.setValue(value);
        break;
      case "state":
        state.setValue(value);
        break;
      case "charge":
        charge.setValue(value);
        break;
      case "mass":
        mass.setValue(value);
        break;
      case "isConstant":
        isConstant.setValue(Boolean.getBoolean(value));
        break;
      case "compoundReactionFormula":
        compoundReactionFormula.setValue(value);
        break;
      case "structureFormula":
        structureFormula.setValue(value);
        break;
      case "enzymeRole":
        enzymeRole.setValue(value);
        break;
      case "regulatorRole":
        regulatorRole.setValue(value);
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
    }
  }
  

  public Species instanceToSBMLSpecies(int level, int version, Model model) {
    Species species = model.createSpecies();
    species.setId(speciesId.getValue());
    species.setName(name.getValue());
    species.setInitialAmount(SBMLUtils.convertToDbl(initValue.getValue()));
    Kind u = SBMLUtils.convertToUnit(unit.getValue());
    if (u != null) {
      species.setUnits(u);
    }
    species.setCompartment(location.getValue());
    species.setConstant(isConstant.getValue());
    species.setSBOTerm(SBMLUtils.convertToSBO(sboTerm.getValue()));
    return species;
  }

}