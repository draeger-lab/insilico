package org.insilico.jsbml.core.services.provider;

import org.eclipse.fx.code.editor.services.FileIconProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * OSGi service component which provides a file icon for SBML files. This component provides the
 * {@link FileIconProvider} service.
 * 
 * @author roman
 *
 */
@Component
public class SBMLIconProvider implements FileIconProvider {

    @Override
    public boolean test(String t) {
        return t.endsWith("sbml");
    }

    @Override
    public String getFileIconUri(String uri) {
        Bundle b = FrameworkUtil.getBundle(SBMLIconProvider.class);
        return "platform:/plugin/" + b.getSymbolicName() + "/res/sbml-logo-16.png";
    }

}
