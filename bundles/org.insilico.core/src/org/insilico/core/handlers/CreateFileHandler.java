package org.insilico.core.handlers;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Abstract base class for Handlers which wish to create a new file. Subclasses can override
 * {@link CreateFileHandler#getUpdateFlags()} and {@link CreateFileHandler#getContent()} to provide
 * the update flags and the content of the new file.
 * 
 * @author roman
 *
 */
abstract public class CreateFileHandler {
    
    protected void createFile(IProject parent, IPath path, InputStream content, int flags, IProgressMonitor pm) throws CoreException {
        IFile file = parent.getFile(path);
        createFile(file, content, flags, pm);
    }
    
    protected void createFile(IFile file, InputStream content, int flags, IProgressMonitor pm) throws CoreException {
        file.create(content, flags, pm);
    }
}
