package org.insilico.sbmlsheets.core;

public class SpreadsheetUtils {

	public static final boolean isSheetFile(String uri) {
        return uri.endsWith("csv") || uri.endsWith("tsv") || uri.endsWith("sheets");
    }
}
