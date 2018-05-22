package org.insilico.jsbml.core.tests;

import static org.eclipse.fx.code.editor.Constants.DOCUMENT_URL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.insilico.jsbml.core.services.provider.SBMLDocumentLoader;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.sbml.jsbml.SBMLDocument;


@SuppressWarnings("restriction")
public class SBMLLoaderTest {

    private static final Bundle BUNDLE = FrameworkUtil.getBundle(SBMLLoaderTest.class);

    @Test
    public void serviceActive_pass() {
        boolean foundService = false;

        try {
            ServiceReference<?>[] refs = BUNDLE.getBundleContext()
                    .getAllServiceReferences(IContextFunction.class.getName(), null);

            for (ServiceReference<?> ref : refs) {
                System.out.println("KEY: " + ref.getProperty("service.context.key"));

                if (ref.getBundle().equals(FrameworkUtil.getBundle(SBMLDocumentLoader.class))) {
                    foundService = true;
                    break;
                }
            }
        }
        catch (InvalidSyntaxException e) {
            // Fail
            e.printStackTrace();
        }

        assertTrue(foundService);
    }

    @Test
    public void getSBMLDocumentFromContext_pass() {
        String url = Platform.getResourceString(BUNDLE, "res/test.sbml");
        IEclipseContext root =
                EclipseContextFactory.createServiceContext(BUNDLE.getBundleContext());
        root.set(DOCUMENT_URL, url);

        Object doc = root.get(SBMLDocument.class);

        assertNotNull(doc);
        assertTrue(doc instanceof SBMLDocument);
    }

    public void injectSBMLDocument_pass() {
        String url = Platform.getResourceString(BUNDLE, "res/test.sbml");
        IEclipseContext root =
                EclipseContextFactory.createServiceContext(BUNDLE.getBundleContext());
        root.set(DOCUMENT_URL, url);

        InjectionTest test = ContextInjectionFactory.make(InjectionTest.class, root);
        assertNotNull(test);
        assertNotNull(test.doc);
    }

    @Test
    public void getSBMLDocumentFromContext_noSBMLFile() {
        String url = Platform.getResourceString(BUNDLE, "res/test.txt");
        IEclipseContext root =
                EclipseContextFactory.createServiceContext(BUNDLE.getBundleContext());
        root.set(DOCUMENT_URL, url);

        Object doc = root.get(SBMLDocument.class);

        assertNull(doc);
    }


    @Test
    public void getSBMLDocumentFromContext_noDocumentSet() {
        IEclipseContext root =
                EclipseContextFactory.createServiceContext(BUNDLE.getBundleContext());
        Object doc = root.get(SBMLDocument.class);

        assertNull(doc);
    }

    class InjectionTest {
        @Inject
        SBMLDocument doc;
    }
}
