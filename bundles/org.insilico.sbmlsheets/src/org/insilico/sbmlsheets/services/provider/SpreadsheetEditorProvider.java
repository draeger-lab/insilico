package org.insilico.sbmlsheets.services.provider;

import org.eclipse.fx.code.editor.fx.e4.EditorClassURLProvider;
import org.insilico.sbmlsheets.editor.SpreadsheetView;
import org.osgi.service.component.annotations.Component;

@Component
public class SpreadsheetEditorProvider implements EditorClassURLProvider {

    @Override
    public boolean test(String t) {
        return t.endsWith("csv") || t.endsWith("tsv");
    }

    @Override
    public String getBundleClassURI(String uri) {
        return "bundleclass://org.insilico.sbmlsheets/" + SpreadsheetView.class.getName();
    }
}
