package org.insilico.sbmlsheets.services.provider;

import org.eclipse.fx.code.editor.services.FileIconProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * OSGi service component which provides a file icon for SBML files. This component provides the
 * {@link FileIconProvider} service.
 * 
 * @author robert
 *
 */
@Component
public class SBMLIconProvider implements FileIconProvider {
   public boolean test(String t) {
      return t.endsWith("sbml");
   }

   public String getFileIconUri(String uri) {
      Bundle b = FrameworkUtil.getBundle(SBMLIconProvider.class);
      return "platform:/plugin/" + b.getSymbolicName() + "/res/sbml-logo-16.png";
   }
}
