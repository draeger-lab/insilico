package org.insilico.sbmlsheets.core;

import java.io.FileWriter;
import java.io.IOException;

public class SheetWriter {
   public static void writeSheetToFile(String uri, String out) {
      try {
         FileWriter writer = new FileWriter(uri, false);
         writer.write(out);
         writer.close();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static void newCSV(String filePath) {
      writeSheetToFile(filePath, "");
   }
}
