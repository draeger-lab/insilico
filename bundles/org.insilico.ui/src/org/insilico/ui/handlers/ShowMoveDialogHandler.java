package org.insilico.ui.handlers;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.resources.IResource;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Handler which shows the a dialog where the user can enter the new location of the selected item.
 * This handler can only be executed if the selection is either a object of type {@link IResource}
 * or a {@link List} of {@link IResource} containing only 1 item.
 * 
 * @author roman
 *
 */
public class ShowMoveDialogHandler extends SelectedResourceHandler {
    public static class MoveDialog extends ResourceOperationDialog {
        @Inject
        @Log
        Logger LOGGER;
        
        private Label fromLabel;
        private TextField toTextField;
        
        @Inject
        public MoveDialog(MApplication app) {
            super("Move", "Enter the new location for the resource.");
        }
        
        @Override
        protected Node createDialogContent() {
            Label from = new Label("From:");
            Label to = new Label("To:");
            
            Label fromLabel = new Label("NULL");
            this.fromLabel = fromLabel;
            fromLabel.getStyleClass().add("secondary-text");
            TextField toTextField = new TextField("NULL");
            this.toTextField = toTextField;
            
            from.setPrefWidth(40.0);
            from.setAlignment(Pos.CENTER_RIGHT);
            to.setPrefWidth(40.0);
            to.setAlignment(Pos.CENTER_RIGHT);
            
            HBox fromWrapper = new HBox(12.0, from, fromLabel);
            HBox toWrapper = new HBox(12.0, to, toTextField);
            VBox wrapper = new VBox(8.0, fromWrapper, toWrapper);
            
            return wrapper;
        }
        
        @Override
        protected void performOperation(IResource res) {
            String destination = toTextField.getText();
            
            
            try {
                Path src = FileSystems.getDefault().getPath(res.getLocation().toOSString());
                Path des = FileSystems.getDefault().getPath(destination);
                Files.move(src, des);
            }
            catch (Exception e) {
                LOGGER.info("Failed to move " + res.getName() + " to " + destination);
                e.printStackTrace();
            }
        }
        
        @Override
        public void setResource(IResource res) {
            super.setResource(res);
            setFrom(res.getLocation().toOSString());
            setTo(res.getLocation().toOSString());
        }
        
        public void setFrom(String from) {
            this.fromLabel.setText(from);
        }
        
        public void setTo(String to) {
            this.toTextField.setText(to);
        }
    }
    
    @Override
    protected Class<? extends ResourceOperationDialog> getDialogClass() {
        return MoveDialog.class;
    }
}
