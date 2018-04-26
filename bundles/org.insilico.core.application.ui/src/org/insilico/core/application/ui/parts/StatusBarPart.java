package org.insilico.core.application.ui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

/**
 * <h1>Status Bar Part</h1>
 * 
 * This part builds the status bar in the bottom of a lab window.
 * 
 * <h2>How to use</h2>
 * <h3>Show Message</h3>
 * <h3>Show Progress</h3>
 * <h3>Show Indicator</h3>
 * 
 * @author roman
 * @see org.insilico.core.application.ui
 */
public class StatusBarPart {

    @PostConstruct
    void init(MWindow window) {


        // Store this part in the window context
        window.getContext().set(StatusBarPart.class, this);
    }
}
