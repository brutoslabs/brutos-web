package org.brandao.brutos.scanner.vfs;

import org.brandao.brutos.BrutosException;


public class VfsException extends BrutosException{
    
    public VfsException() {
	super();
    }

    public VfsException(String message) {
	super(message);
    }

    public VfsException(String message, Throwable cause) {
        super(message, cause);
    }

    public VfsException(Throwable cause) {
        super(cause);
    }
    
}
