package org.insilico.ui.themes.light;

import org.eclipse.fx.ui.services.theme.MultiURLStylesheet;
import org.eclipse.fx.ui.services.theme.Stylesheet;
import org.eclipse.fx.ui.services.theme.Theme;
import org.eclipse.fx.ui.theme.AbstractTheme;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(service = Theme.class)
public class LightTheme extends AbstractTheme {
    public static final String ID = "org.insilico.ui.themes.light";

    public LightTheme() {
        super(LightTheme.ID, "Light Theme",
                LightTheme.class.getClassLoader().getResource("css/light.css"));
    }

    @Override
    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void registerStylesheet(Stylesheet stylesheet) {
        super.registerStylesheet(stylesheet);
    }

    @Override
    public void unregisterStylesheet(Stylesheet stylesheet) {
        super.unregisterStylesheet(stylesheet);
    }

    @Override
    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void registerMultiURLStylesheet(MultiURLStylesheet stylesheet) {
        super.registerMultiURLStylesheet(stylesheet);
    }

    @Override
    public void unregisterMultiURLStylesheet(MultiURLStylesheet stylesheet) {
        super.unregisterMultiURLStylesheet(stylesheet);
    }
}
