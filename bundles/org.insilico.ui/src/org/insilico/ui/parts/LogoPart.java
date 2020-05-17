package org.insilico.ui.parts;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.core.handlers.CreateProjectHandler;
import org.insilico.ui.handlers.OpenProjectWindowHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

//@SuppressWarnings("restriction") commented out by marietta 16032020
public class LogoPart {

    @Inject
    Logger logger;

    @Inject
    MPart part;


    @PostConstruct
    void init(BorderPane parent, Stage primaryStage) {
        // Setup window
        primaryStage.setResizable(false);

        parent.setStyle("-fx-border-color: -color-divider;\n" + "-fx-border-width: 0 0 0 1px;\n"
                + "-fx-border-style: solid;\n");

      
        Label label = new Label("InSilico");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.getStyleClass().add("font-display-2");
        label.requestFocus();

        ImageView logo = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("res/insilico_square_color_128.png")));
        
        VBox box = new VBox(12.0, logo, label);
        box.setPadding(new Insets(80.0, 100.0, 0.0, 100.0));
        parent.setCenter(box);

        Button b = new Button("Create New");
        b.setOnAction(e -> {
            // Choose location
            DirectoryChooser dc = new DirectoryChooser();
            File file = dc.showDialog(primaryStage);

            if (file != null) {
                // Create project
                Path location = new Path(file.getAbsolutePath());
                IProject project = CreateProjectHandler.execute(part.getContext(), location, null);

                // Open window
                if (project != null) {
                    OpenProjectWindowHandler.execute(part.getContext(), project);
                    primaryStage.close();
                }
            }
        });

        parent.setBottom(b);
    }
}

/*
 * public class LogoPart {
 * 
 * 
 * 
 * @Inject IEclipseContext ctx;
 * 
 * @Inject MPart part;
 * 
 * 
 * @PostConstruct void init(BorderPane parent, Stage primaryStage) { logger.debug("Has menu " +
 * (part.getMenus() != null)); // Setup window primaryStage.setResizable(false);
 * 
 * parent.setStyle("-fx-border-color: -color-divider;\n" + "-fx-border-width: 0 0 0 1px;\n" +
 * "-fx-border-style: solid;\n");
 * 
 * Label label = new Label("InSilico"); label.setAlignment(Pos.CENTER);
 * label.setTextAlignment(TextAlignment.CENTER); label.getStyleClass().add("font-display-2");
 * parent.setCenter(label);
 * 
 * Button b = new Button("Create New"); b.setOnAction(e -> { logger.info("Button pressed");
 * 
 * ECommandService cs = ctx.get(ECommandService.class); EHandlerService hs =
 * ctx.get(EHandlerService.class);
 * 
 * Map<String, Object> args = new HashMap<String, Object>();
 * 
 * 
 * ParameterizedCommand cmd = cs.createCommand("org.draegerlab.insilico.workbench.command.openlab",
 * null); hs.executeHandler(cmd, ctx); });
 * 
 * parent.setBottom(b);
 * 
 * } }
 */
