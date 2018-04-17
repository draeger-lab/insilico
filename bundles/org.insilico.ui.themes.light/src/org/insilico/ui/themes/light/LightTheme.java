package org.insilico.ui.themes.light;

import java.io.File;
import java.net.URL;

import org.eclipse.fx.ui.theme.AbstractTheme;

public class LightTheme extends AbstractTheme {
	public static final String ID = "org.draegerlab.insilico.ui.themes.light";
	
	public LightTheme() {
		super(LightTheme.ID, "Light Theme", LightTheme.class.getClassLoader().getResource("css/light.css"));
	}
}
