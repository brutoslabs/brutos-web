/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos;

/**
 * Lançada quando ocorre um redirecionamento.
 * 
 * @author Afonso Brandao
 */
public class RedirectException extends BrutosException{
    
    private String page;
    
    public RedirectException() {
	super();
    }

    public RedirectException(String message) {
	super(message);
    }

    public RedirectException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedirectException(Throwable cause) {
        super(cause);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
