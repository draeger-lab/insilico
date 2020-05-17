package org.insilico.sbmlsheets.services; 

import org.sbml.jsbml.SBMLDocument;

/**
 * A {@link SBMLAdapterBuilderProvider} provides a builder which can be used to create a product of
 * type P. The builder is used to form a adapter.
 * 
 * @author robert
 *
 * @param <P>
 */
public interface SBMLAdapterBuilderProvider<P> {
    public boolean canBuildProductFrom(SBMLDocument doc);

    public P getBuilder();
}
