package org.insilico.sbmlsheets.services.provider;

import static org.eclipse.fx.code.editor.Constants.DOCUMENT_URL;

import java.net.URI;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.URIUtil;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.sbmlsheets.core.SheetReader;
import org.insilico.sbmlsheets.core.Spreadsheet;
import org.insilico.sbmlsheets.core.SpreadsheetUtils;
import org.osgi.service.component.annotations.Component;



/**
 * The {@link SBMLDocumentLoader} provides a {@link SBMLDocument} via dependency injection if the
 * current document is a sbml file.
 * 
 * In oder to use this {@link IContextFunction} the context must store the location of a sbml file
 * with the key {@link org.eclipse.fx.code.editor.Constants#DOCUMENT_URL}. This context function
 * will only compute values for the contextKey
 * {@link org.insilico.sbmlsheets.core.Constants#KEY_SBML_DOCUMENT}
 * 
 * @author roman
 *
 */
@Component(service = IContextFunction.class,
        property = {"service.context.key=org.insilico.sbmlsheets.core.Spreadsheet"})
public class SpreadsheetDocumentLoader extends ContextFunction {
    Map<String, Spreadsheet> cache = new WeakHashMap<>();

    @Override
    public Object compute(IEclipseContext context, String contextKey) {
        Object urlVal = context.get(DOCUMENT_URL);

        if (urlVal == null) {
            Object partVal = context.get(MPart.class);
            if (partVal != null && partVal instanceof MPart) {
                MPart part = (MPart) partVal;
                
                urlVal = part.getPersistedState().get(DOCUMENT_URL);
                context.set(DOCUMENT_URL, urlVal);
            }
        }

        if (urlVal != null && urlVal instanceof String) {
            String urlString = (String) urlVal;
            urlString = urlString.replace("%20", " ");
            Spreadsheet doc = cache.get(urlString);

            if (doc == null) {
                try {
                    URI url = URIUtil.fromString(urlString);
                    doc = SheetReader.readSheetFromFile(url.getPath());
                    cache.put(urlString, doc);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Reading failed");
                    return IInjector.NOT_A_VALUE;
                }
            }

            return doc;
        }

        System.out.println("No doc selected");
        return IInjector.NOT_A_VALUE;
    }
}
