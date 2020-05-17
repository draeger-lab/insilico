package org.insilico.ui.parts;

import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MPopupMenu;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.fx.code.editor.services.EditorOpener;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.ui.controls.filesystem.FileItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceEvent;
import org.eclipse.fx.ui.controls.filesystem.ResourceItem;
import org.eclipse.fx.ui.controls.filesystem.ResourceTreeView;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.BorderPane;

//@SuppressWarnings("restriction") //added by marietta 15032020
public class NavigatorPart {

    @Inject
    @Log
    Logger logger;

    @Inject
    EditorOpener textEditorOpener;

    @Inject
    ESelectionService selectionService;

    @PostConstruct
    void init(BorderPane p, IProject project, MPart part, EMenuService ms) {
        ResourceTreeView viewer = new ResourceTreeView();
        viewer.setRootDirectories(FXCollections.observableArrayList(
                ResourceItem.createObservedPath(Paths.get(project.getLocationURI()))));
        viewer.addEventHandler(ResourceEvent.openResourceEvent(), this::handleEvent);
        p.setCenter(viewer);
        
        // Observe Selection
        viewer.getSelectedItems().addListener(new ListChangeListener<ResourceItem>() {
          @Override
          public void onChanged(Change<? extends ResourceItem> c) {
              String replaceStr = Pattern.quote("file:" + project.getLocation().toOSString() + "/");
              selectionService.setSelection(viewer.getSelectedItems().
                      stream().
                      // Map ResourceItem to IResource handle.
                      map(item -> { 
                          return project.findMember(
                                  item.getUri().replaceAll(
                                          replaceStr, "")
                                  );
                          }
                      ).filter(res -> { 
                          return res != null;
                          }
                      ).collect(Collectors.toList()));
              
          }
        });

        // Register Context Menu
        part.getMenus().forEach(menu -> {
            if (menu instanceof MPopupMenu) {
                ms.registerContextMenu(viewer, menu.getElementId());
            }
        });
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
