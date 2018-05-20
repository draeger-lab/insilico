package org.insilico.jsbml.core.services;

import org.eclipse.fx.code.editor.fx.e4.EditorClassURLProvider;
import org.insilico.jsbml.core.editor.SBMLEditor;

public class SBMLEditorProvider implements EditorClassURLProvider {

    @Override
    public boolean test(String t) {
        return t.endsWith("sbml");
    }

    @Override
    public String getBundleClassURI(String uri) {
        return "bundleclass://org.insilico.jsbml.core/" + SBMLEditor.class.getName();
    }

}
