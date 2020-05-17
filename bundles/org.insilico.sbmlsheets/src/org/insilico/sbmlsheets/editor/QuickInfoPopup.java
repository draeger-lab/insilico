package org.insilico.sbmlsheets.editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuickInfoPopup extends Stage{

  
  public QuickInfoPopup(String title, String text) {
    
    init(title, text);
    this.show();

    Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
        ae -> {
         this.close();}));
    timeline.playFromStart();
  }

  private void init(String title, String text) {
    this.initModality(Modality.APPLICATION_MODAL);
    this.setTitle(title);
    
    this.initOwner(new Stage());
    VBox dialogVbox = new VBox(20);
    Label label = new Label(text);
    Scene dialogScene = new Scene(new StackPane(label), 300, 100);
    this.setScene(dialogScene);
  }
}
