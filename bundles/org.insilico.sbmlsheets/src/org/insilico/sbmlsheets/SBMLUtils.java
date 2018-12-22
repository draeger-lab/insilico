package org.insilico.sbmlsheets;

/**
 * Util class for JSBML.
 * 
 * @author robert
 *
 */
public final class SBMLUtils {
    /**
     * Checks if the file at the given uri is a valid SBML file.
     * 
     * @param uri
     * @return <code>true</code> if the file is a sbml file.
     */
    public static final boolean isSBMLFile(String uri) {
        return uri.endsWith("sbml");
    }
}
