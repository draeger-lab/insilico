package org.insilico.ui.handlers;

import java.nio.file.Path;

/**
 * Contributes a new Rich Client Platform feature to inSilico platform. It is
 * not desired to accept single plug-ins.
 * 
 * @author mzakharc
 *
 */
public class AddFeatureHandler extends AddModuleHandler {

	@Override
	public void copyModule(Path src) {
		copyFolder(src, concatToPlatformPath("/features/"));
	}
	
}
