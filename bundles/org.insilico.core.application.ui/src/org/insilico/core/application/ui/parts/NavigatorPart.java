package org.insilico.core.application.ui.parts;

import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.fx.code.editor.services.EditorOpener;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.ui.controls.filesystem.FileItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceEvent;
import org.eclipse.fx.ui.controls.filesystem.ResourceItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceTreeView;

import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;

public class NavigatorPart {

    @Inject
    @Log
    Logger logger;

    @Inject
    EditorOpener textEditorOpener;

    @PostConstruct
    void init(BorderPane p, IProject project) {
        ResourceTreeView viewer = new ResourceTreeView();
        viewer.setRootDirectories(FXCollections.observableArrayList(
                ResourceItem.createObservedPath(Paths.get(project.getLocationURI()))));
        viewer.addEventHandler(ResourceEvent.openResourceEvent(), this::handleEvent);
        p.setCenter(viewer);
    }

    private void handleEvent(ResourceEvent<ResourceItem> e) {
        e.getResourceItems().stream().filter(r -> r instanceof FileItem).map(r -> (FileItem) r)
                .forEach(this::handle);


    }

    private void handle(FileItem item) {
        logger.debug("Handle " + item);
        textEditorOpener.openEditor(item.getUri());
    }
}
