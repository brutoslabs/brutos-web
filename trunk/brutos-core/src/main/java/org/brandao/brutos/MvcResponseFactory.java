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
 * F�brica de respostas.
 *
 * @author Afonso Brandao
 */
public abstract class MvcResponseFactory {

    private static ThreadLocal responses = new ThreadLocal();

    /**
     * Obt�m a atual resposta.
     * @return Resposta.
     */
    public MvcResponse getCurrentResponse(){

        MvcResponse request = (MvcResponse) responses.get();
        
        if( request == null ){
            request = getNewResponse();
            responses.set(request);
        }

        return request;
    }

    /**
     * Destr�i a resposta.
     */
    public void destroyResponse(){
        responses.remove();
    }

    /**
     * Obt�m uma nova resposta.
     * @return Nova resposta.
     */
    protected abstract MvcResponse getNewResponse();

}
