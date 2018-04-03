package org.draegerlab.insilico.launcher.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.log.Logger;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class LogoPart {
	@Inject
	Logger logger;
	
	@Inject
	IEclipseContext ctx;
	
	@PostConstruct
	void init(BorderPane parent, Stage primaryStage) {
		// Setup window
		primaryStage.setResizable(false);

		Label label = new Label("InSilico");
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		parent.setCenter(label);


		
		Button b = new Button("Create New");
		b.setOnAction(e -> {
			logger.info("Button pressed");
			
			ECommandService cs = ctx.get(ECommandService.class);
			EHandlerService hs = ctx.get(EHandlerService.class);
			
			Map<String, Object> args = new HashMap<String, Object>();
			
			
			ParameterizedCommand cmd = cs.createCommand("org.draegerlab.insilico.workbench.command.openlab", null);
			hs.executeHandler(cmd, ctx);
		});
		
		parent.setBottom(b);

	}
}
