package org.draegerlab.insilico.launcher.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LauncherFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create Launcher UI
		
		BorderPane root = new BorderPane();
		
		Pane left = new Pane();
		left.setStyle("-fx-background: #FAFAFA;");
		
		Pane right = new Pane();
		right.setStyle("-fx-background: #FFFFFF;");
		
		root.setLeft(left);
		root.setCenter(right);
		
		Scene s = new Scene(root, 400, 600);
		primaryStage.setScene(s);
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("InSilico Launcher");
		primaryStage.centerOnScreen();
		
		primaryStage.show();
	}
	
}
