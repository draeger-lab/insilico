package org.insilico.sbmlsheets.editor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.insilico.sbmlsheets.core.Spreadsheet;
import org.sbml.jsbml.SBase;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;

public class SpreadsheetView {

	@Inject
	private Spreadsheet doc;
	
	@PostConstruct
    private void init(BorderPane parent) {
        TextField test = new TextField("das ist ein weiterer Test!");
        parent.setCenter(test);
    }

}
