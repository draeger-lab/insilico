package org.draegerlab.insilico.launcher.splash;

import org.eclipse.fx.core.app.ApplicationContext;
import org.eclipse.fx.ui.services.startup.StartupProgressTrackerService;

public class StartupTracker implements StartupProgressTrackerService {
    private SplashScreen splash;
    private ErrorWindow errorWindow;

    @Override
    public OSGiRV applicationLaunched(ApplicationContext applicationContext) {
        return OSGiRV.CONTINUE;
    }

    @Override
    public void stateReached(ProgressState s) {
        if (s instanceof DefaultProgressState) {
            DefaultProgressState state = (DefaultProgressState) s;

            switch (state) {
                case DI_SYSTEM_INITIALIZED:
                    break;
                case JAVAFX_INITIALIZED:
                    if (splash == null) {
                        splash = new SplashScreen();
                        splash.show();
                    }
                    break;
                case JAVAFX_INITIALIZED_LAUNCHER_THREAD:
                    // Show splash
                    break;
                case LOCATION_CHECK_FAILED:
                    // Stop splash
                    if (splash != null) {
                        splash.hide();
                        splash = null;
                    }
                    // Show error view
                    errorWindow = new ErrorWindow();
                    errorWindow.show();
                    break;
                case POST_CONTEXT_LF_FINISHED:
                    break;
                case WORKBENCH_GUI_SHOWING:
                    break;
                case WORKBENCH_GUI_SHOWN:
                    // Hide splash
                    if (splash != null) {
                        splash.hide();
                        splash = null;
                    }

                    // Check if a lab was specified
                    break;
                default:
                    break;
            }
        }
    }
}
