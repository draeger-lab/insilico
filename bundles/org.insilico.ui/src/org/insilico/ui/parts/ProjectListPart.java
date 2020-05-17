package org.insilico.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.ui.components.list.SingleLineListCell;
import org.insilico.ui.components.list.SingleLineListCell.GraphicStyle;
import org.insilico.ui.handlers.OpenProjectWindowHandler;

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
                // Only do something if a project was set.
                if (cell.getItem() == null) {
                    return;
                }

                OpenProjectWindowHandler.execute(part.getContext(), cell.getItem());
                primaryStage.close();
            });
            return cell;
        });


        parent.setCenter(list);
    }
}
