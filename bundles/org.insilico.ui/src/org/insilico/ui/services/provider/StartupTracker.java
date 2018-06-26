package org.insilico.ui.services.provider;

import org.eclipse.fx.core.app.ApplicationContext;
import org.eclipse.fx.ui.services.startup.StartupProgressTrackerService;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

@Component(service = StartupProgressTrackerService.class, property= {Constants.SERVICE_RANKING + ":Integer=1"})
public class StartupTracker implements StartupProgressTrackerService {

    @Override
    public OSGiRV applicationLaunched(ApplicationContext applicationContext) {
        applicationContext.applicationRunning();
        return OSGiRV.CONTINUE;
    }

    @Override
    public void stateReached(ProgressState s) {
        if (s instanceof DefaultProgressState) {
            DefaultProgressState state = (DefaultProgressState) s;

            switch (state) {
                case DI_SYSTEM_INITIALIZED:
                    // Starting

                    break;
                case JAVAFX_INITIALIZED:
                    break;
                case JAVAFX_INITIALIZED_LAUNCHER_THREAD:
                    break;
                case LOCATION_CHECK_FAILED:
                    // Stop framework
                    try {
                        FrameworkUtil.getBundle(StartupTracker.class).getBundleContext()
                                .getBundle(0).stop();
                       
                        javafx.application.Platform.exit();
                        System.exit(0);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case POST_CONTEXT_LF_FINISHED:
                    break;
                case WORKBENCH_GUI_SHOWING:
                    break;
                case WORKBENCH_GUI_SHOWN:
                    // Done

                    break;
                default:
                    break;
            }
        }
    }
}
