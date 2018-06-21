package org.insilico.ui.handlers;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import javafx.scene.Node;

/**
 * Handler which shows the delete dialog.
 * 
 * @author roman
 *
 */
public class ShowDeleteDialogHandler extends SelectedResourceHandler {
    public static class DeleteDialog extends ResourceOperationDialog {
        public DeleteDialog() {
            super("Delete", "Are you sure you want to delete the selected resource?");
        }

        @Override
        protected Node createDialogContent() {
            return null;
        }

        @Override
        protected void performOperation(IResource res) {
            try {
                res.delete(true, null);
            }
            catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected Class<? extends ResourceOperationDialog> getDialogClass() {
        return DeleteDialog.class;
    }
}
