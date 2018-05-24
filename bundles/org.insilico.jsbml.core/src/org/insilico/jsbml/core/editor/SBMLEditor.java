package org.insilico.jsbml.core.editor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.sbml.jsbml.SBMLDocument;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SBMLEditor {
    @Inject
    SBMLDocument doc;

    @PostConstruct
    void init(BorderPane parent) {
        Label l = new Label("Hello SBML!");
        parent.setCenter(l);
    }
}
