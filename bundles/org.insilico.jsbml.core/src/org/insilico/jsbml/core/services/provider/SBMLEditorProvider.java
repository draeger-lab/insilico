package org.insilico.jsbml.core.services.provider;

import org.eclipse.fx.code.editor.fx.e4.EditorClassURLProvider;
import org.insilico.jsbml.core.editor.DocumentTreeView;
import org.osgi.service.component.annotations.Component;

/**
 * OSGi Service Component which provides a editor part for SBML files. The editor is provides via
 * the {@link EditorClassURLProvider} service.
 * 
 * @author roman
 *
 */
@Component
public class SBMLEditorProvider implements EditorClassURLProvider {

    @Override
    public boolean test(String t) {
        return t.endsWith("sbml");
    }

    @Override
    public String getBundleClassURI(String uri) {
        return "bundleclass://org.insilico.jsbml.core/" + DocumentTreeView.class.getName();
    }
}
