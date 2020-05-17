package org.insilico.sbmlsheets.core;

import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

public class SpeciesTableView extends TableView {

  public HashSet<String> speciesIds = new HashSet<String>();  
  
  private final ObservableList<SpeciesRow> defaultData =
      FXCollections.observableArrayList(
      new SpeciesRow("species1", "cell"),
      new SpeciesRow("species2", "cell"));
  
  public SpeciesTableView(){
    setEditable(true);
    initColumns();
    
  }

  private void initColumns() {
    TableColumn speciesIdCol = new TableColumn("speciesId");
    speciesIdCol.setMinWidth(50);
    speciesIdCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesId"));
    speciesIdCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesIdCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesId(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesNameCol = new TableColumn("speciesName");
    speciesNameCol.setMinWidth(50);
    speciesNameCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesName"));
    speciesNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesNameCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesName(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesTypeCol = new TableColumn("speciesType"); 
    speciesTypeCol.setMinWidth(50);
    speciesTypeCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesType"));
    speciesTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesTypeCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesType(t.getNewValue());
            }
        }
    );

    TableColumn speciesCompartmentCol = new TableColumn("speciesCompartment"); 
    speciesCompartmentCol.setMinWidth(50);
    speciesCompartmentCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesCompartment"));
    speciesCompartmentCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesCompartmentCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesCompartment(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesInitialAmountCol = new TableColumn("speciesInitialAmount"); 
    speciesInitialAmountCol.setMinWidth(50);
    speciesInitialAmountCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesInitialAmount"));
    speciesInitialAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesInitialAmountCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesInitialAmount(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesInitialConcentrationCol = new TableColumn("speciesInitialConcentration");
    speciesInitialConcentrationCol.setMinWidth(50);
    speciesInitialConcentrationCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesInitialConcentration"));
    speciesInitialConcentrationCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesInitialConcentrationCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesInitialConcentration(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesSubstanceUnitsCol = new TableColumn("speciesSubstanceUnits");
    speciesSubstanceUnitsCol.setMinWidth(50);
    speciesSubstanceUnitsCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesSubstanceUnits"));
    speciesSubstanceUnitsCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesSubstanceUnitsCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesSubstanceUnits(t.getNewValue());
            }
        }
    );

    TableColumn speciesHasOnlySubstanceUnitsCol = new TableColumn("speciesHasOnlySubstanceUnits");
    speciesHasOnlySubstanceUnitsCol.setMinWidth(50);
    speciesHasOnlySubstanceUnitsCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesHasOnlySubstanceUnits"));
    speciesHasOnlySubstanceUnitsCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesHasOnlySubstanceUnitsCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesHasOnlySubstanceUnits(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesBoundaryConditionCol = new TableColumn("speciesBoundaryCondition");
    speciesBoundaryConditionCol.setMinWidth(50);
    speciesBoundaryConditionCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesBoundaryCondition"));
    speciesBoundaryConditionCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesBoundaryConditionCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesBoundaryCondition(t.getNewValue());
            }
        }
    );

    TableColumn speciesConstantCol = new TableColumn("speciesConstant");
    speciesConstantCol.setMinWidth(50);
    speciesConstantCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesConstant"));
    speciesConstantCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesConstantCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesConstant(t.getNewValue());
            }
        }
    );
    
    TableColumn speciesChargeCol = new TableColumn("speciesCharge");
    speciesChargeCol.setMinWidth(50);
    speciesChargeCol.setCellValueFactory(
        new PropertyValueFactory<SpeciesRow, String>("speciesCharge"));
    speciesChargeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    speciesChargeCol.setOnEditCommit(
        new EventHandler<CellEditEvent<SpeciesRow, String>>() {
            @Override
            public void handle(CellEditEvent<SpeciesRow, String> t) {
                ((SpeciesRow) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setSpeciesCharge(t.getNewValue());
            }
        }
    );

    getColumns().addAll(speciesIdCol, speciesNameCol, speciesTypeCol, speciesCompartmentCol,
        speciesInitialAmountCol, speciesSubstanceUnitsCol, 
        speciesHasOnlySubstanceUnitsCol, speciesBoundaryConditionCol, speciesConstantCol, speciesChargeCol);
    
    
  }
}



//////
class SpeciesRow {

  public String speciesId;
  public String speciesName = "null";
  public String speciesType = "null";
  public String speciesCompartment;
  public String speciesInitialAmount = "1.0";
  public String speciesInitialConcentration = "1.0";
  public String speciesSubstanceUnits = "null";
  public String speciesHasOnlySubstanceUnits = "false";
  public String speciesBoundaryCondition = "false";
  public String speciesCharge = "0";
  public String speciesConstant = "false";
  
  SpeciesRow(String speciesId, String compartment){
    this.speciesId = speciesId;
    this.speciesCompartment = compartment;
  }

  public String getSpeciesId() {
    return speciesId;
  }

  public void setSpeciesId(String speciesId) {
    this.speciesId = speciesId;
  }

  public String getSpeciesName() {
    return speciesName;
  }

  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }

  public String getSpeciesType() {
    return speciesType;
  }

  public void setSpeciesType(String speciesType) {
    this.speciesType = speciesType;
  }

  public String getSpeciesCompartment() {
    return speciesCompartment;
  }

  public void setSpeciesCompartment(String speciesCompartment) {
    this.speciesCompartment = speciesCompartment;
  }

  public String getSpeciesInitialAmount() {
    return speciesInitialAmount;
  }

  public void setSpeciesInitialAmount(String speciesInitialAmount) {
    this.speciesInitialAmount = speciesInitialAmount;
  }

  public String getSpeciesInitialConcentration() {
    return speciesInitialConcentration;
  }

  public void setSpeciesInitialConcentration(String speciesInitialConcentration) {
    this.speciesInitialConcentration = speciesInitialConcentration;
  }

  public String getSpeciesSubstanceUnits() {
    return speciesSubstanceUnits;
  }

  public void setSpeciesSubstanceUnits(String speciesSubstanceUnits) {
    this.speciesSubstanceUnits = speciesSubstanceUnits;
  }

  public String getSpeciesHasOnlySubstanceUnits() {
    return speciesHasOnlySubstanceUnits;
  }

  public void setSpeciesHasOnlySubstanceUnits(String speciesHasOnlySubstanceUnits) {
    this.speciesHasOnlySubstanceUnits = speciesHasOnlySubstanceUnits;
  }

  public String getSpeciesBoundaryCondition() {
    return speciesBoundaryCondition;
  }

  public void setSpeciesBoundaryCondition(String speciesBoundaryCondition) {
    this.speciesBoundaryCondition = speciesBoundaryCondition;
  }

  public String getSpeciesCharge() {
    return speciesCharge;
  }

  public void setSpeciesCharge(String speciesCharge) {
    this.speciesCharge = speciesCharge;
  }

  public String getSpeciesConstant() {
    return speciesConstant;
  }

  public void setSpeciesConstant(String speciesConstant) {
    this.speciesConstant = speciesConstant;
  }


  
  
  
}