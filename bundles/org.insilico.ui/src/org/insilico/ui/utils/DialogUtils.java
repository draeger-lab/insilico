package org.insilico.ui.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Utility class to deal with JFX Dialogs.
 * */
@Creatable
@Singleton
public class DialogUtils {

	/**
	 * Shows the confirmation dialog and waits for the user response
	 */
	public void showConfirmationDialog(String title, String header, String content, AlertType alertType) {
		Alert alert = createAlert(title, header, content, alertType);
		alert.showAndWait();
	}
	
	/**
	 * Shows the confirmation dialog with boolean option
	 * */
	public void showConfirmationDialog(String title, String header, String content, AlertType alertType, Consumer<Boolean> booleanConsumer) {
		Alert alert = createAlert(title, header, content, alertType);
		Optional<ButtonType> result = alert.showAndWait();
		booleanConsumer.accept(result.get() == ButtonType.OK);
	}

	/**
	 * Shows the confirmation dialog with stack trace in separate TextArea and waits
	 * for the user response.
	 */
	public void showConfirmationDialog(String title, String header, String content, AlertType alertType, Exception e) {
		Alert alert = createAlert(title, header, content, alertType);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();
		Label label = new Label("The exception stacktrace was:");
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}


	/**
	 * Returns configured Alert
	 */
	private Alert createAlert(String title, String header, String content, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
}
