package org.insilico.sbmlsheets.core;

public class HelpContent {

  public static String getStartText() {
    String s = "** InSilico: SBTabSheets - Plugin **\n" + 
        "\n" + 
        "* FUNCTIONALITY:\n" + 
        "* Start Table Editor via .sheets file\n" + 
        "* Auto-Import of .tsv, .csv, .tab to table editor\n" + 
        "* Manual import of custom table file\n" + 
        "* Modify and create SBTab or custom tables\n" + 
        "    * Compound Table\n" + 
        "    * Compartment Table\n" + 
        "    * Reaction Table\n" + 
        "    * Custom Table\n" + 
        "* Conversion and export to SBTab file\n" + 
        "* Conversion and export to SBML file\n" + 
        "* Browsing SBTab definitions for\n" + 
        "    * Compound Table\n" + 
        "    * Compartment Table\n" + 
        "    * Reaction Table";
    return s;
  }

}
