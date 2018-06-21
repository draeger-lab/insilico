package org.insilico.ui.services;

import java.util.List;

import org.insilico.ui.services.ToolbarService.ToolbarContribution;

public interface ToolbarContributionLookup {
    public List<ToolbarContribution> getContributions(Object toolbar, Object activePart);
}
