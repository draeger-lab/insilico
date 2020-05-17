package org.insilico.sbmlsheets.services.provider;  

import java.net.URI;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.URIUtil;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.insilico.sbmlsheets.core.SBMLUtils;
import org.osgi.service.component.annotations.Component;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;



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

@Component(
    service = {IContextFunction.class},
    property = {"service.context.key=org.sbml.jsbml.SBMLDocument"}
 )
 public class SBMLDocumentLoader extends ContextFunction {
    Map<String, SBMLDocument> cache = new WeakHashMap<String, SBMLDocument>();

    public Object compute(IEclipseContext context, String contextKey) {
       System.out.println("Compute...");
       System.out.println("Hallo");
       Object urlVal = context.get("documentUrl");
       if (urlVal == null) {
          Object partVal = context.get(MPart.class);
          if (partVal != null && partVal instanceof MPart) {
             MPart part = (MPart)partVal;
             urlVal = part.getPersistedState().get("documentUrl");
             context.set("documentUrl", urlVal);
          }
       }

       if (urlVal != null && urlVal instanceof String) {
          String urlString = (String)urlVal;
          urlString = urlString.replace("%20", " ");
          SBMLDocument doc = (SBMLDocument)this.cache.get(urlString);
          if (doc == null) {
             if (!SBMLUtils.isSBMLFile(urlString)) {
                System.out.println("Not a sbml file");
                return IInjector.NOT_A_VALUE;
             }

             try {
                URI url = URIUtil.fromString(urlString);
                doc = JSBML.readSBMLFromFile(url.getPath());
                this.cache.put(urlString, doc);
             } catch (Exception var7) {
                var7.printStackTrace();
                System.out.println("Reading failed");
                return IInjector.NOT_A_VALUE;
             }
          }

          return doc;
       } else {
          System.out.println("No doc selected");
          return IInjector.NOT_A_VALUE;
       }
    }
 }