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
 * 
 * @author Brandao
 */
public class DispatcherView {

    private DispatcherType dispatcher;

    public DispatcherView(){
        this(null);
    }
    
    public DispatcherView(DispatcherType dispatcher){
        this.dispatcher = dispatcher;
    }

    /**
     * O fluxo é alterado para a visão.
     * @param value Visão.
     */
    public void to( String value ){
        StackRequestElement request = 
                Invoker.getInstance().getStackRequest().getCurrent();
        request.setDispatcherType(dispatcher);
        request.setView(value);
    }

    /**
     * O fluxo é alterado para a visão.
     * @param value Visão.
     */
    public void toRedirectNow( String value ){
        throw new RedirectException( value, dispatcher );
    }

}
