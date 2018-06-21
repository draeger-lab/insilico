package org.insilico.core.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;

public class CreateEmptyFileHandler extends CreateFileHandler {
    @Execute
    void invoke(IProject parent, IPath path, @Optional IProgressMonitor pm) throws CoreException {
        InputStream stream = new ByteArrayInputStream(new byte[0]);
        int flags = 0;
        
        createFile(parent, path, stream, flags, pm);
    }
}
