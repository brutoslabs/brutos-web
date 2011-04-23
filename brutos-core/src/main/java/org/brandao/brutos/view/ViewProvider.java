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

package org.brandao.brutos.view;

import java.io.IOException;
import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ParameterizedRequest;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.ViewException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Afonso Brandao
 */
public abstract class ViewProvider {
    
    public ViewProvider() {
    }
    
    public static ViewProvider getProvider( Properties properties ){
        String viewProviderName = properties.getProperty("org.brandao.brutos.view.provider");
        ViewProvider view       = null;
        
        if( viewProviderName == null )
            viewProviderName = "org.brandao.brutos.view.JSPViewProvider";

        try{
            Class<?> iocProvider = Class.forName( viewProviderName, true, Thread.currentThread().getContextClassLoader() );
            view = (ViewProvider)iocProvider.newInstance();
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }
        
        view.configure( properties );
        return view;
    }
    
    public abstract void configure( Properties properties );

    /*
     * @deprecated
     * @param page
     * @param request
     * @param response
     * @param context
     * @throws ServletException
     * @throws IOException
     */
    //public abstract void show( String page, ServletRequest request, HttpServletResponse response, ServletContext context ) throws ServletException, IOException;

    /*
     * @deprecated 
     * @param page
     * @param redirect
     * @param request
     * @param response
     * @param context
     * @throws ServletException
     * @throws IOException
     */
    /*public abstract void show( String page, boolean redirect, ServletRequest request,
            HttpServletResponse response, ServletContext context )
                throws ServletException, IOException;
    */
    protected abstract void show( String view, DispatcherType dispatcherType )
                throws IOException;

    public void show( ParameterizedRequest request ) throws IOException,
            ViewException{

        InterceptorHandler handler = request.getHandler();
        
        if( request.isHasViewProcessed() )
            throw new ViewException("view has been processed");

        try{
            Scopes scopes         =
                handler.getContext().getScopes();
            Scope requestScope    =
                scopes.get(ScopeType.REQUEST.toString());
            MethodForm method     =
                handler.getResourceAction().getMethodForm();

            ThrowableSafeData throwableSafeData =
                    request.getThrowableSafeData();

            Object objectThrow = request.getObjectThrow();
            if( throwableSafeData != null ){
                if( throwableSafeData.getParameterName() != null )
                    requestScope.put(throwableSafeData.getParameterName(), objectThrow);

                if( throwableSafeData.getUri() != null ){
                    this.show(
                        throwableSafeData.getUri(),
                        throwableSafeData.getDispatcher());
                    return;
                }
            }

            if( method != null ){

                if( method.getReturnClass() != void.class ){
                    String var =
                        method.getReturnIn() == null?
                            BrutosConstants.DEFAULT_RETURN_NAME :
                            method.getReturnIn();
                    requestScope.put(var, request.getResultAction());
                }

                if( method.getReturnPage() != null ){
                    this.show(method.getReturnPage(),
                            method.getDispatcherType());
                    return;
                }
                else
                if( method.getReturnType() != null ){
                    method.getReturnType().setValue(request.getResultAction());
                    return;
                }
            }

            this.show(request.getController().getPage(),
                    request.getController().getDispatcherType());

        }
        finally{
            request.setHasViewProcessed(true);
        }
    }

}
