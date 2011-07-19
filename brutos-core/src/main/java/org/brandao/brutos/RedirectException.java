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
 * Lan�ada quando ocorre um redirecionamento.
 * 
 * @author Afonso Brandao
 */
public class RedirectException extends BrutosException{
    
    private String page;
    private String view;
    private DispatcherType dispatcher;

    public RedirectException() {
	super();
    }

    public RedirectException(String view, DispatcherType dispatcher) {
	this.dispatcher = dispatcher;
        this.view = view;
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

    /**
     * @deprecated
     * @return
     */
    public String getPage() {
        return page;
    }

    /**
     * @deprecated
     * @return
     */
    public void setPage(String page) {
        this.page = page;
    }

    public String getView() {
        return view;
    }

    public DispatcherType getDispatcher() {
        return dispatcher;
    }
    
}
