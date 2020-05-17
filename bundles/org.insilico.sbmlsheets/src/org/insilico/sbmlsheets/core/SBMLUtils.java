package org.insilico.sbmlsheets.core;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;

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
    
    public static double convertToDbl(String s) {
      if (s.matches("\\d+")){
        return Double.parseDouble(s+".0");
      } else if (s.matches("\\d+\\.\\d+")) {
        return Double.parseDouble(s);
      } else {
        return 0.0;
      }
    }
    
    public static int convertToInt(String s) {
      if (s.matches("\\d+")){
        return Integer.parseInt(s);
      } else if (s.matches("\\d+\\.\\d+")) {
        return (int) (Math.round(Double.parseDouble(s)));
      } else {
        return 0;
      }
    }
    
    public static boolean convertToBool(String s) {
      if ((s.equals("true")) || (s.equals("True")) || (s.equals("1")) || (s.equals("1.0"))) {
        return true;
      }else {
        return false;
      }
    }

    public static String convertToSBO(String value) {
      if (!value.matches("SBO:[0-9]{7}|\\d{1,7}")) {
        value = "SBO:0000001";
      }else {
        if (value.matches("\\d{1,7}")){
          String zero = "0";
          value = "SBO:"+IntStream.range(0, 7-value.length()).mapToObj(i -> zero).collect(Collectors.joining(""))+value;
        }
      }
      return value;
    }

    public static Kind convertToUnit(String value) {
      for (Unit.Kind c : Unit.Kind.values())
        if (c.getName().equals(value.toUpperCase())) {
          return c;
        }
      return Kind.DIMENSIONLESS;
    }
    
    public static boolean isValidUnit(String value) {
      for (Unit.Kind c : Unit.Kind.values())
        if (c.getName().equals(value.toUpperCase())) {
          return true;
        }
      return false;
    }
}
