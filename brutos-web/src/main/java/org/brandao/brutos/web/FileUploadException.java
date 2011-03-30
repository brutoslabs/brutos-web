/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
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
