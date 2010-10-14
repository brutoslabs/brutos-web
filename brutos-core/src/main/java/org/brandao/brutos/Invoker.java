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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @author Afonso Brandao
 */
public class Invoker {

    private Logger logger;

    public Invoker() {
        this.logger = BrutosContext
                        .getCurrentInstance().getLoggerProvider()
                            .getLogger( Invoker.class.getName() );
    }
    
    public boolean invoke( BrutosContext brutosContext, HttpServletResponse response ) throws IOException{

        //Form form = brutosContext.getController();
        Form form = brutosContext
                .getResolveController()
                    .getController(
                        (WebFrameManager)ContextLoaderListener.currentContext
                            .getAttribute( BrutosConstants.WEBFRAME_MANAGER ),
                        (HttpServletRequest)ContextLoaderListener.currentRequest.get()
                );

        long time = 0;
        if( form == null )
            //response.setStatus( response.SC_NOT_FOUND );
            return false;
        else
            brutosContext
                .getRequest()
                    .setAttribute( BrutosConstants.CONTROLLER , form);


        try{
            if( logger.isDebugEnabled() ){
                logger.debug( "Received a new request" );
            }

            time = System.currentTimeMillis();

            IOCManager iocManager =
                    (IOCManager)brutosContext.getContext()
                        .getAttribute( BrutosConstants.IOC_MANAGER );

            ImpInterceptorHandler ih = new ImpInterceptorHandler();
            ih.setContext( brutosContext.getContext() );
            ih.setRequest( brutosContext.getRequest() );
            ih.setResource( iocManager.getInstance( form.getId() ) );
            ih.setResponse( response );
            ih.setURI( ih.getRequest().getRequestURI() );
            ih.setResourceMethod(
                brutosContext
                    .getMethodResolver()
                        .getResourceMethod( brutosContext.getRequest() ) );

            if( logger.isDebugEnabled() ){
                logger.debug(
                    String.format(
                        "Controller: %s Method: %s",
                        form.getClass().getName() ,
                        ih.getResourceMethod() == null?  "" : ih.getResourceMethod().getMethod().getName() )
                );


            }
            form.proccessBrutosAction( ih );
        }
        finally{
            if( logger.isDebugEnabled() )
                logger.debug(
                        String.format( "Request processed in %d ms",
                            (System.currentTimeMillis()-time) ) );
        }

        return true;
    }
}
