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
 * Fábria de requisições.
 * 
 * @author Afonso Brandao
 */
public abstract class MvcRequestFactory {

    private static ThreadLocal requests = new ThreadLocal();

    /**
     * Obtém a atual requisição.
     * @return Requisição.
     */
    public MvcRequest getCurrentRequest(){

        MvcRequest request = (MvcRequest) requests.get();
        
        if( request == null ){
            request = getNewRequest();
            requests.set(request);
        }

        return request;
    }

    /**
     * Destrói a requisição.
     */
    public void destroyRequest(){
        requests.remove();
    }

    /**
     * Cria uma nova requisição.
     * @return Nova requisição.
     */
    protected abstract MvcRequest getNewRequest();

}
