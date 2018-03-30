package org.draegerlab.insilico.launcher.ui;

import javax.annotation.PostConstruct;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProjectListPart {

	@PostConstruct
	public void init(BorderPane parent) {

		ListView<String> list = new ListView<String>();
		list.getItems().add("Hallo");
		list.getItems().add("World");
		list.getItems().add("Test");
		list.getItems().add("Project");
		
		for (int i = 0; i < 100; i++) {
			list.getItems().add("Item " + i);
		}
		
		list.setStyle("-fx-border-width: 0 1 0 0;\n" + 
				"	-fx-border-color: -border-color;");
		
		list.setPrefWidth(200.0);
		list.setCellFactory((l) -> {
			ListCell<String> cell = new ListCell();
			cell.getStyleClass().add("single-line-cell");
			return cell;
		});
		
		
		parent.setLeft(list);
	}
}
