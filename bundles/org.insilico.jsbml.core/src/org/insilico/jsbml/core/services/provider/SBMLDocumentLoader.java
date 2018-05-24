package org.insilico.jsbml.core.services.provider;

import static org.eclipse.fx.code.editor.Constants.DOCUMENT_URL;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.URIUtil;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.jsbml.core.SBMLUtils;
import org.osgi.service.component.annotations.Component;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;



/**
 * The {@link SBMLDocumentLoader} provides a {@link SBMLDocument} via dependency injection if the
 * current document is a sbml file.
 * 
 * In oder to use this {@link IContextFunction} the context must store the location of a sbml file
 * with the key {@link org.eclipse.fx.code.editor.Constants#DOCUMENT_URL}. This context function
 * will only compute values for the contextKey
 * {@link org.insilico.jsbml.core.Constants#KEY_SBML_DOCUMENT}
 * 
 * @author roman
 *
 */
@SuppressWarnings("restriction")
@Component(service = IContextFunction.class,
        property = {"service.context.key=org.sbml.jsbml.SBMLDocument"})
public class SBMLDocumentLoader extends ContextFunction {
    // Stores weak reference to already loaded documents.
    Map<String, SBMLDocument> cache = new WeakHashMap<>();

    @Override
    public Object compute(IEclipseContext context, String contextKey) {
        System.out.println("Compute...");
        Object urlVal = context.get(DOCUMENT_URL);

        if (urlVal == null) {
            Object partVal = context.get(MPart.class);
            if (partVal != null && partVal instanceof MPart) {
                MPart part = (MPart) partVal;
                urlVal = part.getPersistedState().get(DOCUMENT_URL);
                context.set(DOCUMENT_URL, urlVal);
            }
        }

        // Check if a input file exists
        if (urlVal != null && urlVal instanceof String) {
            String urlString = (String) urlVal;
            urlString = urlString.replace("%20", " ");
            // Check cache
            SBMLDocument doc = cache.get(urlString);

            if (doc == null) {
                // Check if the document is a sbml file.
                if (!SBMLUtils.isSBMLFile(urlString)) {
                    System.out.println("Not a sbml file");
                    return IInjector.NOT_A_VALUE;
                }

                // Load if needed
                try {
                    URI url = URIUtil.fromString(urlString);
                    doc = SBMLReader.read(new File(url));
                    cache.put(urlString, doc);
                }
                catch (Exception e) {
                    // Not a sbml file?
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
