package org.insilico.core.application.ui;

import org.eclipse.core.resources.IProject;


/**
 * Constants of the bundle {@link org.insilico.core.application.ui}.
 * 
 * @author roman
 * @see org.insilico.core.application.ui
 *
 */
public interface Constants {
    /**
     * The identifier of the launcher window snippet.
     */
    public static final String LAUNCHER_WINDOW_ID =
            "org.insilico.core.application.ui.launcherWindow";

    /**
     * The identifier of the project window snippet.
     */
    public static final String PROJECT_WINDOW_ID = "org.insilico.core.application.ui.projectWindow";


    /**
     * The identifier of the "Open Project Window" command.
     */
    public static final String OPEN_PROJECT_WINDOW_COMMAND_ID =
            "org.insilico.core.application.ui.commands.openProjectWindow";

    /**
     * The identifier of the "Open Project Window" handler.
     */
    public static final String OPEN_PROJECT_WINDOW_HANDLER_ID =
            "org.insilico.core.application.ui.handlers.openProjectWindowHandler";

    public static final String PROJECT_PARAMETER_ID =
            "org.insilico.core.application.ui.parameters.project";


    /**
     * Returns the name of the context of the window which displays the content of the given
     * project.
     * 
     * @param project The project the window displays.
     * @return The name of the window's context.
     */
    public static String nameOfWindowContext(IProject project) {
        return PROJECT_WINDOW_ID + ":" + project.getName();
    }
}
