package org.draegerlab.insilico.launcher.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProjectListPart {
	
	@Inject
	IWorkspace workspace;

	@PostConstruct
	public void init(BorderPane parent) {
		
		
		ListView<IProject> list = new ListView<IProject>();
		list.getItems().addAll(workspace.getRoot().getProjects());
		list.getStyleClass().add("darker");

		workspace.addResourceChangeListener(e -> {
			list.getItems().clear();
			list.getItems().addAll(workspace.getRoot().getProjects());
		});

		
		parent.setCenter(list);
	}
}
