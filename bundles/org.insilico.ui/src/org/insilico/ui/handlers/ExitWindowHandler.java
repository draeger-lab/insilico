package org.insilico.ui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class ExitWindowHandler {
  @Execute
  public void execute(IWorkbench workbench) {
    //workbench.close();
    System.exit(0);
  }
  
  @CanExecute
  public boolean canExecute() {
    if (true) {
      // to things
      return true;
    }
    //return false;
    return false;
  }
}
