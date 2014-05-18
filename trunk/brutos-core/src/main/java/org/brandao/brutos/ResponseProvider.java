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
public class ResponseProvider {

    private static final ThreadLocal responses;

    static{
        responses = new ThreadLocal();
    }
    
    private MvcResponseFactory factory;
    
    public MvcResponse start(){
        MvcResponse current = (MvcResponse) responses.get();
        MvcResponse response = this.factory.getResponse();
        responses.set(response);
        return current;
    }
    
    public static MvcResponse getResponse(){
        return (MvcResponse) responses.get();
    }

    public void destroy(MvcResponse old){
        if(old == null)
            responses.remove();
        else
            responses.set(old);
    }

    /**
     * @return the factory
     */
    public MvcResponseFactory getFactory() {
        return factory;
    }

    /**
     * @param factory the factory to set
     */
    public void setFactory(MvcResponseFactory factory) {
        this.factory = factory;
    }

}
