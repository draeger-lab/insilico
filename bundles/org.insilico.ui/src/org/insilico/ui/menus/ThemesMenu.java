package org.insilico.ui.menus;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.ItemType;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.ui.services.theme.Theme;
import org.eclipse.fx.ui.services.theme.ThemeManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class ThemesMenu {
    
    public static class ThemeMenuItem {
        @Inject
        ThemeManager themeManager;
        
        @Execute
        void invoke(MDirectMenuItem menuItem) {
            String themeID = menuItem.getPersistedState().get("themeID");
            if(themeID != null)
                themeManager.setCurrentThemeId(themeID);
        }
    }
    
    @Log
    @Inject
    Logger LOGGER;
    
    @Inject
    ThemeManager themeManager;
    
    //MDynamicMenuContribution model;
    
    /*
    @Inject
    public ThemesMenu(MDynamicMenuContribution model) {
        this.model = model;
    }*/
    
    
    @PostConstruct
    void init(MMenu parent, EModelService ms) {
        Bundle b = FrameworkUtil.getBundle(ThemesMenu.class);
        BundleContext ctx = b.getBundleContext();

        try {
            Collection<ServiceReference<Theme>> themes = ctx.getServiceReferences(Theme.class, null);
            
            for(ServiceReference<Theme> themeRef: themes) {
                Theme theme = ctx.getService(themeRef);
                MDirectMenuItem item = ms.createModelElement(MDirectMenuItem.class);//factory.createDirectMenuItem();
                item.setType(ItemType.RADIO);
                item.setLabel(theme.getName());
                item.setSelected(theme.getId() == themeManager.getCurrentTheme().getId());
                item.getPersistedState().put("themeID", theme.getId());
                item.setContributionURI("bundleclass://" + b.getSymbolicName() + "/" + ThemeMenuItem.class.getName());
                
                parent.getChildren().add(item);
            }
        }
        catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }
}
