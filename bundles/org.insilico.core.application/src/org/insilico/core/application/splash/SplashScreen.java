package org.insilico.core.application.splash;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen {
    private Stage stage;

    public void show() {
        if (stage != null) {
            return;
        }

        Pane root = new Pane();

        Label l = new Label("Splash");
        l.setAlignment(Pos.CENTER);
        l.setBackground(Background.EMPTY);

        double w = 250.0 / 255.0;
        Scene scene = new Scene(l, 320, 180, new Color(w, w, w, 1.0));

        stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    public void hide() {
        if (stage == null) {
            return;
        }

        stage.hide();
        stage = null;
    }
}
