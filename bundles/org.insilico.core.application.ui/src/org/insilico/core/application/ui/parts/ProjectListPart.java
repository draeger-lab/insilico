package org.insilico.core.application.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.core.application.ui.handlers.OpenProjectWindowHandler;
import org.insilico.core.ui.components.SingleLineListCell;
import org.insilico.core.ui.components.SingleLineListCell.GraphicStyle;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProjectListPart {
    public class ProjectCell extends SingleLineListCell<IProject> {
        public ProjectCell(GraphicStyle gStyle) {
            super(gStyle);
        }

        @Override
        protected void updateItem(IProject item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText("");
            }
            else {
                setText(item.getName());
            }
        }
    }

    @Inject
    IWorkspace workspace;

    @Inject
    MPart part;

    @PostConstruct
    void init(BorderPane parent, Stage primaryStage) {
        ListView<IProject> list = new ListView<IProject>();
        list.getItems().addAll(workspace.getRoot().getProjects());
        list.getStyleClass().add("darker");

        workspace.addResourceChangeListener(e -> {
            list.getItems().clear();
            list.getItems().addAll(workspace.getRoot().getProjects());
        });

        list.setCellFactory((l) -> {
            ProjectCell cell = new ProjectCell(GraphicStyle.SQUARE_GRAPHIC);

            Node n = new Pane();
            n.getStyleClass().add("darker");

            cell.setGraphic(n);

            cell.setStyle("-fx-border-color: -color-divider;\n" + "-fx-border-width: 0 0 1px 0;\n"
                    + "-fx-border-style: solid;\n"); // cell.getStyleClass().add("darker");

            cell.setOnMouseReleased(event -> {
                OpenProjectWindowHandler.execute(part.getContext(), cell.getItem());
                primaryStage.close();
            });
            return cell;
        });


        parent.setCenter(list);
    }
}

/*
 * public class ProjectListPart { public class ProjectCell extends SingleLineListCell<IProject> {
 * 
 * 
 * @Inject IWorkspace workspace;
 * 
 * @Inject IEclipseContext ctx;
 * 
 * @PostConstruct public void init(BorderPane parent) { ListView<IProject> list = new
 * ListView<IProject>(); list.getItems().addAll(workspace.getRoot().getProjects());
 * list.getStyleClass().add("darker");
 * 
 * workspace.addResourceChangeListener(e -> { list.getItems().clear();
 * list.getItems().addAll(workspace.getRoot().getProjects()); });
 * 
 * list.setCellFactory((l) -> { ProjectCell cell = new ProjectCell(GraphicStyle.SQUARE_GRAPHIC);
 * 
 * Node n = new Pane(); n.getStyleClass().add("darker");
 * 
 * cell.setGraphic(n);
 * 
 * cell.setStyle("-fx-border-color: -color-divider;\n" + "-fx-border-width: 0 0 1px 0;\n" +
 * "-fx-border-style: solid;\n"); // cell.getStyleClass().add("darker");
 * 
 * cell.setOnMouseReleased(event -> { ProjectListPart.this.openProject(cell.getItem()); }); return
 * cell; });
 * 
 * 
 * parent.setCenter(list); }
 * 
 * public void openProject(IProject project) { // TODO open project IEclipseContext ctx =
 * this.ctx.createChild("cell");
 * 
 * } }
 */
