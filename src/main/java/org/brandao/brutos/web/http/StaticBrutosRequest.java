/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.web.http;

import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class StaticBrutosRequest extends BrutosRequestWrapper{

    public StaticBrutosRequest(ServletRequest request){
        super( getBrutosRequest(request) );
    }

    private static BrutosRequest getBrutosRequest( ServletRequest request ){
        try{
            BrutosRequest brutosRequest =
                    createBrutosRequest(
                        ContextLoader.getCurrentWebApplicationContext(),
                        request );
            return brutosRequest;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private static BrutosRequest createBrutosRequest(WebApplicationContext app,
            ServletRequest servletRequest){

        Class<?> clazz = getBrutosRequestClass(app);

        if(BrutosRequest.class.isAssignableFrom(clazz)){
            try{
                BrutosRequest request =
                    (BrutosRequest)clazz
                        .getConstructor(ServletRequest.class)
                            .newInstance(servletRequest);
                return request;
            }
            catch( Exception e ){
                throw new BrutosException("unable to create instance: " +
                        clazz.getName(),e);
            }
        }
        else
            throw new BrutosException("request is not valid:"+
                    clazz.getName());
    }

    private static Class<?> getBrutosRequestClass(WebApplicationContext app){
        String brutosRequestClassName =
                app.getConfiguration().getProperty(
                    "org.brandao.brutos.web.request",
                    BrutosRequestImp.class.getName()
                );

        return getBrutosRequestClass(brutosRequestClassName);
    }

    private static Class<?> getBrutosRequestClass( String contextClassName ){
        try {
            return Thread.currentThread().getContextClassLoader()
                    .loadClass(contextClassName);
        } catch (ClassNotFoundException ex) {
            throw new BrutosException( "Failed to load: " + contextClassName, ex );
        }
    }

}
