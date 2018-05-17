package org.insilico.jsbml.core.editor;

import javax.annotation.PostConstruct;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class SBMLEditor {
    @PostConstruct
    void init(BorderPane parent) {
        Label l = new Label("Hello SBML!");
        parent.setCenter(l);
    }
}
