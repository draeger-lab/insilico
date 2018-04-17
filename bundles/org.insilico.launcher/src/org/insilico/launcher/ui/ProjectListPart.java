package org.insilico.launcher.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.insilico.ui.components.SingleLineListCell;
import org.insilico.ui.components.SingleLineListCell.GraphicStyle;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
                // setSubtitle("");
            }
            else {
                setText(item.getName());
                // setSubtitle(item.getLocation().toOSString());
            }
        }
    }

    @Inject
    IWorkspace workspace;

    @Inject
    IEclipseContext ctx;

    @PostConstruct
    public void init(BorderPane parent) {
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
                    + "-fx-border-style: solid;\n");
            // cell.getStyleClass().add("darker");

            cell.setOnMouseReleased(event -> {
                ProjectListPart.this.openProject(cell.getItem());
            });
            return cell;
        });


        parent.setCenter(list);
    }

    public void openProject(IProject project) {
        // TODO open project
        IEclipseContext ctx = this.ctx.createChild("cell");

    }
}
