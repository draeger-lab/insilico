package org.insilico.sbmlsheets.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TableEditor {
   private ObservableList<String> paths;
   private ObservableMap<String, String> names;
   private String uri;

   public TableEditor(String uri) {
     
     System.out.println(uri);
      this.setUri(uri);
      this.paths = FXCollections.observableArrayList();
      this.names = FXCollections.observableHashMap();
   }

   public TableEditor(String uri, String name, String specification, List<String> paths, List<String> names) {
      this.uri = uri;
      Iterator<String> var7 = paths.iterator();

      while(var7.hasNext()) {
         String path = (String)var7.next();
         (new StringBuilder(String.valueOf(this.getUri().replace(".sheets", "")))).append(path).toString();
      }

      this.paths = FXCollections.observableArrayList(paths);
      this.names = FXCollections.observableHashMap();

      for(int i = 0; i < paths.size(); ++i) {
         this.addNameToPath((String)paths.get(i), (String)names.get(i));
      }

   }

   private void addNameToPath(String path, String name) {
      this.names.put(path, name);
   }

   public List<String> readFilesInDir(File dir) {
      File[] files = dir.listFiles();
      List<String> paths = new ArrayList<String>();
      File[] var7 = files;
      int var6 = files.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         File file = var7[var5];
         if (file.getAbsolutePath().endsWith("csv")) {
            paths.add(file.getPath());
         }
      }

      return paths;
   }


   public void removePath(int index) {
      this.paths.remove(index);
   }

   public void removePath(String path) {
      this.paths.remove(path);
   }

   public void addPath(String path) {
      this.paths.add(path);
   }

   public void addPath(String path, int index) {
      this.paths.add(index, path);
   }

   public ObservableList<String> getPaths() {
      return this.paths;
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public ObservableMap<String, String> getNames() {
      return this.names;
   }

   public void setNames(ObservableMap<String, String> names) {
      this.names = names;
   }

   public String getName(String path) {
      return (String)this.names.get(path);
   }

   public void addPathNameType(String path, String name, String type) {
      if (!this.paths.contains(name) && !this.names.containsValue(name)) {
         this.addPath(path);
         this.addNameToPath(path, name);
      }

   }
}
