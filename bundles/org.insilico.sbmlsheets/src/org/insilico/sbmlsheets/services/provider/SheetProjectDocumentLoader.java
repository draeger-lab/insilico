package org.insilico.sbmlsheets.services.provider;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.inject.Inject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.insilico.sbmlsheets.core.TableEditor;
import org.insilico.sbmlsheets.core.SheetReader;
import org.insilico.sbmlsheets.core.SpreadsheetUtils;
import org.osgi.service.component.annotations.Component;


@Component(
    service = {IContextFunction.class},
    property = {"service.context.key=org.insilico.sbmlsheets.core.TableEditor"}//.SheetProject"}
 )
 public class SheetProjectDocumentLoader extends ContextFunction {
  
  @Inject
  IWorkspace ws;

  @Inject
  EModelService ms;

  @Inject
  MApplication app;
  
  
    Map<String, TableEditor> cache = new WeakHashMap<String, TableEditor>();

    public Object compute(IEclipseContext context, String contextKey) {
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
          TableEditor doc = (TableEditor)this.cache.get(urlString);
          if (doc == null) {
             if (!SpreadsheetUtils.isSheetFile(urlString)) {
                System.out.println("Not a Spreadsheet file");
                return IInjector.NOT_A_VALUE;
             }

             try {
                URI url = URIUtil.fromString(urlString);
                doc = SheetReader.readProjectFromFile(url.getPath());
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

