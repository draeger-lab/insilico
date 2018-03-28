package org.draegerlab.insilico.launcher.ui;

import javax.annotation.PostConstruct;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LogoPart {
	@PostConstruct
	void init(BorderPane parent) {
		Pane right = new Pane();
		right.setStyle("-fx-background: #FFFFFF;");
		parent.setCenter(right);
	}
}
