package org.insilico.jsbml.core.services;

import org.eclipse.fx.code.editor.services.FileIconProvider;

@SuppressWarnings("restriction")
public class SBMLIconProvider implements FileIconProvider {

    @Override
    public boolean test(String t) {
        return t.endsWith("sbml");
    }

    @Override
    public String getFileIconUri(String uri) {

        return "platform:/plugin/org.insilico.jsbml.core/res/sbml-logo-16.png";
    }

}
