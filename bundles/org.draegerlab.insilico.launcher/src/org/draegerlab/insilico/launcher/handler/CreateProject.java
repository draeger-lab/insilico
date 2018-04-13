package org.draegerlab.insilico.launcher.handler;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class CreateProject {

    @Inject
    Logger logger;

    @Inject
    EModelService es;

    @Inject
    IWorkspace ws;

    @Inject
    MApplication app;

    @Execute
    public void createProject(Stage stage) {

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select Lab Location...");

        File location = dc.showDialog(stage);

        if (location != null) {
            logger.debug("Selected location: " + location);
            Path pa = new Path(location.getAbsolutePath());
            logger.debug("Selected path: " + pa);

            if (!ws.validateProjectLocation(null, pa).isOK()) {
                return;
            }

            IProject project = ws.getRoot().getProject(pa.lastSegment());


            IProjectDescription desc = ws.newProjectDescription(pa.lastSegment());
            desc.setLocation(pa);

            if (!project.exists()) {
                try {
                    project.create(desc, null);
                    logger.debug("Created Project!");
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }

            // Open window
            MUIElement elem =
                    es.cloneSnippet(app, "org.draegerlab.insilico.workbench.labwindow", null);
            if (elem != null && elem instanceof MTrimmedWindow) {
                MTrimmedWindow window = (MTrimmedWindow) elem;

                IEclipseContext windowCtx = app.getContext().createChild();
                windowCtx.set(IProject.class, project);
                window.setContext(windowCtx);

                app.getChildren().add(window);
            }
        }
        else {
            logger.debug("No lab selected.");
        }

    }
}
