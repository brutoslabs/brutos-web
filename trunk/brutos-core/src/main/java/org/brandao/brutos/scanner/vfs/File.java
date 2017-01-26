

package org.brandao.brutos.scanner.vfs;

import java.io.InputStream;


public interface File {
    
    String getRelativePath();
    
    String getName();
    
    InputStream openInputStream() throws VfsException;
    
}
