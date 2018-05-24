package org.insilico.core.application;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;

@SuppressWarnings("restriction")
public class ApplicationLifecycle {
    @PostContextCreate
    public void init(IEclipseContext context) {

    }


    @PreSave
    public void shutdown(IWorkspace workspace) {
        // Save workspace
        try {
            workspace.save(true, null);
        }
        catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
