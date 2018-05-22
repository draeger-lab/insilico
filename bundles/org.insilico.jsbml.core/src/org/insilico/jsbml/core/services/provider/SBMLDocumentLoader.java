package org.insilico.jsbml.core.services.provider;

import static org.eclipse.fx.code.editor.Constants.DOCUMENT_URL;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
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
        // Check if a input file exists
        if (context.containsKey(DOCUMENT_URL)) {
            String url = (String) context.get(DOCUMENT_URL);

            // Check cache
            SBMLDocument doc = cache.get(url);

            if (doc == null) {
                // Check if the document is a sbml file.
                if (!SBMLUtils.isSBMLFile(url)) {
                    System.out.println("Not a sbml file");
                    return IInjector.NOT_A_VALUE;
                }

                // Load if needed
                try {
                    doc = SBMLReader.read(new File(url));
                    cache.put(url, doc);
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
