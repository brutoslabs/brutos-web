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
 * @author Afonso Brandao
 */
public abstract class MvcResponseFactory {

    private static ThreadLocal responses = new ThreadLocal();

    public MvcResponse getCurrentResponse(){

        MvcResponse request = (MvcResponse) responses.get();
        
        if( request == null ){
            request = getNewResponse();
            responses.set(request);
        }

        return request;
    }

    public void destroyResponse(){
        responses.remove();
    }
    
    protected abstract MvcResponse getNewResponse();

}
