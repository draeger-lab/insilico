package org.insilico.sbmlsheets.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit.Kind;
import com.sun.javafx.print.Units;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;


public class CompartmentInstance{
  
  //define in attributeTypes whether value should be treated as string, double, integer
  public static ArrayList<String> attributeTypes = new ArrayList<>(Arrays.asList(
      "id_Str", "name_Str", "sboId_Str", "sbmlCompartmentId_Str", "outerCompartment_Str",  
      "outerCompartmentId_Str", "size_Dbl", "unit_Str", "comment_Str", "referenceName_Str",
      "referencePubMed_Str", "referenceDOI_Str", 
      "description_Str", "miriamAnnotations_Str", "type_Str", "symbol_Str",
      "positionX_Dbl", "positionY_Dbl", "sboTerm_Str", "identifiers_Str"));
 
  public static ArrayList<String> sbTabCol = new ArrayList<>(Arrays.asList(
      "!ID", "!Name", "!Identifiers:obo.sbo", "!SBML:compartment:id", "!OuterCompartment", 
      "!OuterCompartment:SBML:compartment:id", "!Size", "!Unit", "!Comment", "!ReferenceName",
      "!ReferencePubMed", "!ReferenceDOI", "!Description", 
      "!MiriamAnnotations", "!Type", "!Symbol", "!PositionX", "!PositionY", "!SBOTerm", "!Identifiers"));

  public static ArrayList<String> primaryKeys = new ArrayList<>(Arrays.asList("id", "sbmlCompartmentId"));
  
  String defaultDouble = "0.0";
  String defaultInt = "0";
  public StringProperty id = new SimpleStringProperty("nan");
  public StringProperty name = new SimpleStringProperty("nan");
  public StringProperty sboId = new SimpleStringProperty("0");
  public StringProperty sbmlCompartmentId = new SimpleStringProperty("nan");
  public StringProperty outerCompartment = new SimpleStringProperty("nan");
  public StringProperty outerCompartmentId = new SimpleStringProperty("nan");
  public StringProperty size = new SimpleStringProperty("0.0");
  public StringProperty unit = new SimpleStringProperty("dimensionless");

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
  
  public StringProperty sboTerm = new SimpleStringProperty("0");
  public StringProperty identifiers = new SimpleStringProperty("nan");
  ArrayList<ObservableValue> attributes = new ArrayList<>(Arrays.asList(
      id, name, sboId, sbmlCompartmentId, outerCompartment, outerCompartmentId, size,
      unit, comment, referenceName, referencePubMed, referenceDOI, description,
      miriamAnnotations, type, symbol, positionX, positionY,
      sboTerm, identifiers));
  
  public HashMap<String, String> annotations = new HashMap<>();
  public CompartmentInstance(String idStr, String compartmentIdStr) {
    this.id.setValue(idStr);
    this.sbmlCompartmentId.setValue(compartmentIdStr);
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

  public String getSboId() {
    return sboId.getValue();
  }

  public String getSbmlCompartmentId() {
    return sbmlCompartmentId.getValue();
  }
  
  public String getOuterCompartment() {
    return outerCompartment.getValue();
  }


  public String getOuterCompartmentId() {
    return outerCompartmentId.getValue();
  }


  public String getUnit() {
    return unit.getValue();
  }

  public String getSize() {
    return size.getValue().toString();
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

  
  public StringProperty idProperty() {
    return id;
  }
  
  public StringProperty nameProperty() {
    return name;
  }



  public StringProperty sboIdProperty() {
    return sboId;
  }

  public StringProperty outerCompartmentProperty() {
    return outerCompartment;
  }

  
  public StringProperty sbmlCompartmentIdProperty() {
    return sbmlCompartmentId;
  }


  public StringProperty outerCompartmentIdProperty() {
    return outerCompartmentId;
  }


  public StringProperty unitProperty() {
    return unit;
  }


  public StringProperty sizeProperty() {
    return size;
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
  
  public ObservableValue getProperty(String attribute) {
    for (int j=0; j < attributeTypes.size(); j++) {
      if (attributeTypes.get(j).split("_")[0].equals(attribute)) {
        return attributes.get(j);
      }
    }
    return id;
  }

  private void setProperDouble(StringProperty attribute, String newValue, String oldValue) {
    if (newValue.matches("^[0-9]+\\.[0-9]+$")) {
      attribute.set(newValue);
    }else if (newValue.matches("^[0-9]+$")) {
      attribute.set(newValue+".0");
    }else {
      if (oldValue.matches("^[0-9]+\\.[0-9]+$")) {
        attribute.set(oldValue);
      }else if (oldValue.matches("^[0-9]+$")) {
        attribute.set(oldValue+".0");
      }else {
        attribute.set(defaultDouble);
      }
    }
  }
  
  private void setProperInteger(StringProperty attribute, String newValue, String oldValue) {
    if (newValue.matches("^[0-9]+\\.[0-9]+$")) {
      attribute.setValue(newValue.split("\\.")[0]);
    }else if (newValue.matches("^[0-9]+")) {
      attribute.setValue(newValue);
    }else {
      if (oldValue.matches("^[0-9]+\\.[0-9]+$")) {
        attribute.setValue(oldValue.split("\\.")[0]);
      }else if (oldValue.matches("^[0-9]+$")) {
        attribute.setValue(oldValue);
      }else {
        attribute.setValue(defaultInt);
      }
    }
  }

  private String getCorrectValue(String newValue, String type) {
    String value = newValue.toString();
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
        System.out.println("string stays string");
        //
        
      default:
        break;
    }
    return newValue;
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
      case "sboId":
        sboId.setValue(value);
        break;
      case "outerCompartment":
        outerCompartment.setValue(value);
        break;
      case "sbmlCompartmentId":
        sbmlCompartmentId.setValue(value);
        break;
      case "outerCompartmentId":
        outerCompartmentId.setValue(value);
        break;
      case "unit":
        unit.setValue(value);
        break;
      case "size":
        size.setValue(value);
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
      default:
        break;
    }
  }
  
  public Compartment instanceToSBMLCompartment(int level, int version, Model model) {
    Compartment compartment = model.createCompartment();
    compartment.setId(sbmlCompartmentId.getValue());
    compartment.setName(name.getValue());
    compartment.setSBOTerm(SBMLUtils.convertToSBO(sboTerm.getValue()));

    compartment.setSize(SBMLUtils.convertToDbl(size.getValue()));
    Kind u = SBMLUtils.convertToUnit(unit.getValue());
    if (u != null) {
      compartment.setUnits(u);
    }
    return compartment;
  }
}
  
  