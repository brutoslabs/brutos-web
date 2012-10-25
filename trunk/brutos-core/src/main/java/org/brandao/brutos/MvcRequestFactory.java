/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.brandao.brutos;

/**
 * F�bria de requisi��es.
 * 
 * @author Afonso Brandao
 */
public abstract class MvcRequestFactory {

    private static ThreadLocal requests = new ThreadLocal();

    /**
     * Obt�m a atual requisi��o.
     * @return Requisi��o.
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
     * Destr�i a requisi��o.
     */
    public void destroyRequest(){
        requests.remove();
    }

    /**
     * Cria uma nova requisi��o.
     * @return Nova requisi��o.
     */
    protected abstract MvcRequest getNewRequest();

}
