package org.insilico.ui.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ShowNewFileDialogHandler extends SelectedResourceHandler {
    public static class NewFileDialog extends ResourceOperationDialog {
        
        private TextField tfName;
        
        public NewFileDialog() {
            super("Create File", "Enter the name for the new file.");
        }
        
        
        
        @Override
        protected Node createDialogContent() {
            Label lName = new Label("Name:");

            TextField tfName = new TextField("NULL");
            this.tfName = tfName;
            lName.setAlignment(Pos.CENTER_RIGHT);

            HBox wrapper = new HBox(12.0, lName, tfName);

            return wrapper;
        }

        @Override
        protected void performOperation(IResource res) {
            String name = tfName.getText();
            
            if (name == null || name.isEmpty())
                return;
            
            if (res instanceof IContainer) {
                IContainer c = (IContainer) res;
                performOperation(c, name);
            }
            else {
                performOperation(res.getParent(), name);
            }
        }
        
        protected void performOperation(IContainer parent, String name) {
            IFile file = parent.getFile(new Path(name));
            
            try {
                InputStream in = new ByteArrayInputStream(new byte[0]);
                file.create(in, 0, null);
            }
            catch(CoreException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected Class<? extends ResourceOperationDialog> getDialogClass() {
        return NewFileDialog.class;
    }
}
