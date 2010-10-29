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
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.programatic.ControllerManager;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;

/**
 *
 * @author Afonso Brandao
 */
public class Invoker {

    private static Logger logger = LoggerProvider.getCurrentLoggerProvider().getLogger(Invoker.class.getName());
    private ControllerResolver controllerResolver;
    private IOCProvider iocProvider;
    private ControllerManager controllerManager;
    private MethodResolver methodResolver;
    private ApplicationContext applicationContext;
    
    public Invoker() {
    }

    public Invoker( ControllerResolver controllerResolver, IOCProvider iocProvider, 
            ControllerManager controllerManager, MethodResolver methodResolver, ApplicationContext applicationContext ){
        this.controllerResolver = controllerResolver;
        this.iocProvider        = iocProvider;
        this.controllerManager  = controllerManager;
        this.methodResolver     = methodResolver;
        this.applicationContext = applicationContext;
    }

    /**
     * @deprecated 
     * @param brutosContext
     * @param response
     * @return
     * @throws IOException
     */
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

    public boolean invoke( String requestId ) throws IOException{

        Scope paramScope = Scopes.get(ScopeType.PARAM.toString());
        Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        ih.setRequestId(requestId);
        
        Form form = controllerResolver.getController(controllerManager, ih);


        if( form == null )
            return false;

        long time = System.currentTimeMillis();
        try{
            requestScope.put( BrutosConstants.IOC_PROVIDER , this.iocProvider );
            requestScope.put( BrutosConstants.APPLICATION_CONTEXT, this.applicationContext );
            
            if( logger.isDebugEnabled() )
                logger.debug( "Received a new request: " + requestId );

            ih.setResource( iocProvider.getBean(form.getId()) );
            
            ih.setResourceMethod( methodResolver.getResourceMethod(form, paramScope) );

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
            requestScope.remove( BrutosConstants.IOC_PROVIDER );
            if( logger.isDebugEnabled() )
                logger.debug(
                        String.format( "Request processed in %d ms",
                            (System.currentTimeMillis()-time) ) );
        }

        return true;
    }

}
