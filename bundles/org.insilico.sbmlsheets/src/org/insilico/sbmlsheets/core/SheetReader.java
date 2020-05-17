package org.insilico.sbmlsheets.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

public class SheetReader {
  private static final char DEFAULTQUOTE = '\"';

  
  public static Spreadsheet readSheetFromFile(final String uri) {
    return new Spreadsheet(uri);
  }
  
  public static TableEditor readProjectFromFile(final String uri) {
      final List<String> args = new ArrayList<String>();
      try {
          Throwable t = null;
          try {
              final Scanner s = new Scanner(new FileReader(uri));
              try {
                  final List<String> paths = new ArrayList<String>();
                  final List<String> names = new ArrayList<String>();
                  if (!s.hasNextLine()) {
                      return new TableEditor(uri);
                  }
                  while (s.hasNextLine()) {
                      final String[] line = s.nextLine().split("=");
                      if (!line[0].equals("SHEETS")) {
                          args.add(line[1]);
                      }
                      else {
                          for (String namePathPair = s.nextLine(); !namePathPair.equals("}"); namePathPair = s.nextLine()) {
                              paths.add(namePathPair.split(":")[0]);
                              names.add(namePathPair.split(":")[1]);
                          }
                      }
                  }
                  s.close();
                  return new TableEditor(uri, args.get(0), args.get(1), paths, names);
              }
              finally {
                  if (s != null) {
                      s.close();
                  }
              }
          } finally {
              if (t == null) {
                  final Throwable exception = null;
                  t = exception;
              }
              else {
                  final Throwable exception = null;
                  if (t != exception) {
                      t.addSuppressed(exception);
                  }
              }
          }
      }
      catch (IOException e) {
          System.err.println("Error while parsing csv or tsv file: Inputerror");
          e.printStackTrace();
      }
      catch (NoSuchElementException ex) {
          System.err.println("Error while parsing csv or tsv file: File empty");
      }
      return new TableEditor(uri);
  }
 
}
