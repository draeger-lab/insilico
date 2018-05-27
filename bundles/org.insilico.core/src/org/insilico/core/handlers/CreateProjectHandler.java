package org.insilico.core.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.log.Logger;
import org.insilico.core.Constants;

/**
 * Handler which creates a new project. When this handler is called the project location must be
 * available via dependency injection. Optional a project name can be injected. These values must be
 * stored with the keys {@link CreateProjectHandler#LOCATION_KEY} and
 * {@link CreateProjectHandler#NAME_KEY}.
 * 
 * @author roman
 *
 */
@SuppressWarnings("restriction")
public class CreateProjectHandler {
    @Inject
    Logger logger;

    @Inject
    IWorkspace ws;

    @Execute
    public IProject createProject(@Named(Constants.LOCATION_PARAMETER_ID) String loc,
            @Optional @Named(Constants.NAME_PARAMETER_ID) String projectName) {

        IPath location = new Path(loc);
        // Check if location is okay
        if (!ws.validateProjectLocation(null, location).isOK()) {
            logger.error("Cannot create project! Location is not valid: " + location);
            return null;
        }

        // Choose name
        String name = (projectName != null) ? projectName : location.lastSegment();

        // Create handle
        IProject project = ws.getRoot().getProject(name);

        if (project.exists()) {
            logger.error("Cannot create project! Project already exists.");
            return null;
        }

        // Setup project and apply
        IProjectDescription projectDesc = ws.newProjectDescription(name);
        projectDesc.setLocation(location);

        // Try to create project
        try {
            project.create(projectDesc, null);
        }
        catch (CoreException e) {
            logger.error(e, "Failed to create project!");
            return null;
        }

        return project;
    }


    /**
     * Creates a {@link ParameterizedCommand} which can be used to create a project at the given
     * location.
     * 
     * @param ctx a {@link IEclipseContext} to access the required service instances.
     * @param location a {@link IPath} where the project should be created.
     * @param name A (optional) name for the project. If <code>null</code> is passed, the last
     *        segment of the location will be used as name.
     * @return a prepared {@link ParameterizedCommand}.
     */
    public static ParameterizedCommand createCommand(IEclipseContext ctx, IPath location,
            String name) {
        ECommandService cs = ctx.get(ECommandService.class);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.LOCATION_PARAMETER_ID, location.toString());
        params.put(Constants.NAME_PARAMETER_ID, name);

        return cs.createCommand(Constants.CREATE_PROJECT_COMMAND_ID, params);
    }


    /**
     * Executes the create command.
     * 
     * @param ctx
     * @param location
     * @param name
     * @return
     */
    public static IProject execute(IEclipseContext ctx, IPath location, String name) {
        ParameterizedCommand cmd = createCommand(ctx, location, name);
        EHandlerService hs = ctx.get(EHandlerService.class);

        Object rv = hs.executeHandler(cmd);

        if (rv instanceof IProject) {
            return ((IProject) rv);
        }
        else {
            return null;
        }
    }
}
