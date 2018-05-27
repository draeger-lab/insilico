package org.insilico.ui.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.insilico.ui.Constants;

/**
 * Handler which opens a new lab window and displays the content of the given project. The project
 * must be injected.
 * 
 * @author roman
 *
 */
@SuppressWarnings("restriction")
public class OpenProjectWindowHandler {
    @Inject
    IWorkspace ws;

    @Inject
    EModelService ms;

    @Inject
    MApplication app;

    @Inject
    Logger logger;

    @Execute
    public void openWindow(@Named(Constants.PROJECT_PARAMETER_ID) String projectName) {

        IProject project = ws.getRoot().getProject(projectName);

        // Open if needed
        if (!project.isOpen()) {
            try {
                project.open(null);
            }
            catch (CoreException e) {
                logger.error(e, "Failed to open project!");
            }
        }

        // Setup window
        MTrimmedWindow window =
                (MTrimmedWindow) ms.cloneSnippet(app, Constants.PROJECT_WINDOW_ID, null);
        window.setLabel("InSilico - " + project.getName());

        // Temporary store project in context.
        app.getContext().set(IProject.class, project);

        // Show window
        app.getChildren().add(window);

        // Move value down
        app.getContext().remove(IProject.class);
        window.getContext().set(IProject.class, project);

        // Focus window
        app.setSelectedElement(window);
        window.getContext().activate();
    }


    /**
     * Creates a {@link ParameterizedCommand} which can be used to execute this handler.
     * 
     * @param ctx a {@link IEclipseContext} to access the {@link ECommandService} instance.
     * @param project the {@link IProject} which should be opened.
     * @return the prepared command.
     */
    public static ParameterizedCommand createCommand(IEclipseContext ctx, IProject project) {
        ECommandService cs = ctx.get(ECommandService.class);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PROJECT_PARAMETER_ID, project.getName());

        return cs.createCommand(Constants.OPEN_PROJECT_WINDOW_COMMAND_ID, params);
    }


    /**
     * Executes this handler.
     * 
     * @param ctx a {@link IEclipseContext} to access the needed service instances.
     * @param project the {@link IProject} to be shown.
     */
    public static void execute(IEclipseContext ctx, IProject project) {
        ParameterizedCommand cmd = createCommand(ctx, project);
        EHandlerService hs = ctx.get(EHandlerService.class);

        hs.executeHandler(cmd);
    }
}
