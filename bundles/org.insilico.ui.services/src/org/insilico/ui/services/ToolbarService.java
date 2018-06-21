package org.insilico.ui.services;

public interface ToolbarService {
    public interface ToolbarContribution {
        public void getGroups();
        public void getItems();
    }
    
    public ToolbarContribution[] getContributions(Object toolbarContext, Object activePart);
}
