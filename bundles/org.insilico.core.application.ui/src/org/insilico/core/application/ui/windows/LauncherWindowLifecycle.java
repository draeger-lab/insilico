package org.insilico.core.application.ui.windows;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.fx.ui.workbench.services.lifecycle.annotation.PreClose;
import org.eclipse.fx.ui.workbench.services.lifecycle.annotation.PreShow;

import javafx.stage.Stage;

public class LauncherWindowLifecycle {
    @PreShow
    void show(MWindow window, Stage stage) {
        stage.setResizable(false);
    }

    @PreClose
    void close() {

    }
}
