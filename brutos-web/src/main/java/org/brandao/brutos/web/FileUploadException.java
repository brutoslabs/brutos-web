

package org.brandao.brutos.web;

import org.brandao.brutos.BrutosException;


public class FileUploadException extends BrutosException{

    public FileUploadException() {
	super();
    }

    public FileUploadException(String message) {
	super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

}
