package org.draegerlab.insilico.workbench.handler;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StatusBarPart {
    @Inject
    Stage stage;

    @Inject
    IProject project;

    @PostConstruct
    void init(BorderPane parent) {
        stage.setTitle("InSilico - Lab " + project.getFullPath());

        Label l = new Label("Status Bar");
        l.setPrefHeight(48);

        parent.getStyleClass().add("darker");

        parent.setCenter(l);
    }
}
