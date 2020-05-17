package org.insilico.ui.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.ui.controls.dialog.TitleAreaDialog;
import org.eclipse.fx.ui.services.dialog.LightWeightDialogService;
import org.eclipse.fx.ui.services.dialog.LightWeightDialogService.ModalityScope;

import javafx.scene.Node;

// @SuppressWarnings("restriction") //added by marietta 15032020
public abstract class SelectedResourceHandler {
    public abstract static class ResourceOperationDialog extends TitleAreaDialog {
        @Log
        @Inject
        Logger LOGGER;
        
        private IResource resource;
        private String operationName;
        
        public ResourceOperationDialog(String operationName, String message) {
            this(null, operationName, message);
        } 
        
        
        public ResourceOperationDialog(IResource resource, String operationName, String message) {
            super(operationName, operationName, message);
            this.operationName = operationName;
            this.resource = resource;
                    
            setClientArea(createDialogContent());
            addDefaultButtons();
        }
        
        @Override
        protected void handleOk() {
            IResource res = getResource();
            
            if(res != null)
                performOperation(res);
            
            super.handleOk();
        }
        
        @Override
        public DialogButton createOKButton() {
            DialogButton b = super.createOKButton();
            b.setLabel(operationName);
            return b;
        }

        public String getOperationName() {
            return this.operationName;
        }
        
        public void setOperationName(String operation) {
            this.operationName = operation;
        }
        
        public IResource getResource() {
            return this.resource;
        }
        
        public void setResource(IResource res) {
            this.resource = res;
        }
        
        abstract protected Node createDialogContent();
        abstract protected void performOperation(IResource res);
    }
    
    @Execute
    public void invoke(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection, LightWeightDialogService dialogService) {
        IResource res = getResourceFromSelection(selection);
        ResourceOperationDialog dialog = dialogService.openDialog(getDialogClass(), ModalityScope.WINDOW);
        dialog.setResource(res);
    }
    
    abstract protected Class<? extends ResourceOperationDialog> getDialogClass();
    
    @CanExecute
    public Boolean canMove(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
        return getResourceFromSelection(selection) != null;
    }
    
    private IResource getResourceFromSelection(Object selection) {
        if (selection == null)
            return null;
        
        if (selection instanceof List) {
            List<?> c = (List<?>) selection;

            if (c.size() == 1 && c.get(0) instanceof IResource)
                return (IResource) c.get(0);
        }
        else if (selection instanceof IResource) {
            return (IResource) selection;
        }
        
        return null;
    }
}
