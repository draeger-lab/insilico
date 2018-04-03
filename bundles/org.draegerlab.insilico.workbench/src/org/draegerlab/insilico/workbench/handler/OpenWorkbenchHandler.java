package org.draegerlab.insilico.workbench.handler;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.log.Logger;

@SuppressWarnings("restriction")
public class OpenWorkbenchHandler {
	
	@Inject
	Logger logger;
	

	@Execute
	public void openWorkbench(IWorkspace ws) {
		logger.debug("Do it!");
		
		IProject[] projects = ws.getRoot().getProjects();
		
		logger.debug("I know " + projects.length + " Projects!");
		
		IWorkspaceRoot root = ws.getRoot();
		IProject p = root.getProject("Hello World " + Math.random());
		
		if (!p.exists()) {
			try {
				p.create(null);
				logger.debug("Created Project!");
			} catch (CoreException e) {
				logger.error(e);
			}
		}
		else {
			logger.debug("Project was there!");
		}
		
		
		Path pa = new Path("Hi");
		ws.getRoot().getFile(pa).getProject();
		
//		// Check if workbench exists
//		IProject p = root.getProject("Hello World");
//		
//		
//		// Create if needed
//		if (!p.exists()) {
//			try {
//				p.create(null);
//			}
//			catch(CoreException e) {
//				e.printStackTrace();
//			}
//		}
//		
		
		
		// Create window
	}
}
