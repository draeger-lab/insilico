package org.insilico.core.application.ui.services.provider;

import org.eclipse.fx.core.app.ApplicationContext;
import org.eclipse.fx.ui.dialogs.MessageDialog;
import org.eclipse.fx.ui.services.startup.StartupProgressTrackerService;
import org.osgi.service.component.annotations.Component;

@Component
@SuppressWarnings("restriction")
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
                    // Failed
                    MessageDialog.openErrorDialog(null, "Already Running",
                            "There is already a running instance of InSilico.");
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
