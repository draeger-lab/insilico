package org.draegerlab.insilico.launcher.controller;

import org.draegerlab.insilico.launcher.ui.LauncherFX;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.fx.core.URI;
import org.eclipse.fx.core.app.ApplicationContext;
import org.eclipse.fx.ui.workbench.fx.DefaultJFXApp;
import org.eclipse.fx.ui.workbench.fx.E4Application;
import org.eclipse.fx.ui.workbench.fx.E4MainThreadApplication;
import org.osgi.framework.FrameworkUtil;

import javafx.application.Application;

public class App extends E4MainThreadApplication {
	
	private String labLocation = null;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		// TODO Auto-generated method stub
		String[] args = (String[])context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
		// Lade das model
		return super.start(context);
	}
	
	@Override
	protected void launchE4JavaFxApplication() throws Exception {
		super.launchE4JavaFxApplication();
		return;
	}
	
	@Override
	protected Class<? extends Application> getJfxApplicationClass() {
		return LauncherFX.class;
	}
	
	@Override
	protected IEclipseContext createApplicationContext() {
		IEclipseContext ctx = super.createApplicationContext();
		
		//Select Application Model
		//ctx.set(IWorkbench.XMI_URI_ARG, "bundleclass://");
		
		return ctx;
	}
}
