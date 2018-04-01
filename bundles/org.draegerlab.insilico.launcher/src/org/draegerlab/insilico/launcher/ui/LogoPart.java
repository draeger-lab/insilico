package org.draegerlab.insilico.launcher.ui;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class LogoPart {
	
	@Inject
	Stage primaryStage;
	
	@Inject
	Logger logger;
	
	@PostConstruct
	void init(BorderPane parent) {
		Pane right = new Pane();
		right.setStyle("-fx-background: #FFFFFF;");
		parent.setCenter(right);
		
		// Setup window
		primaryStage.setResizable(false);
	}
}
