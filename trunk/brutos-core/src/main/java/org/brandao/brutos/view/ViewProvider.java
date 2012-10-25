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
package org.brandao.brutos.view;

import java.io.IOException;
import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.ViewException;
import org.brandao.brutos.mapping.Action;
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
            viewProviderName = DefaultViewProvider.class.getName();

        try{
            Class iocProvider = 
                    Class.forName(
                        viewProviderName,
                        true,
                        Thread.currentThread().getContextClassLoader() );
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
    protected abstract void show( RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType )
                throws IOException;

    private void showView( RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType )
                throws IOException{
        requestInstrument.setHasViewProcessed(true);
        show(requestInstrument,view,dispatcherType);
    }

    private void showView( RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement, Action method )
                throws IOException{
        requestInstrument.setHasViewProcessed(true);
        //method.getReturnType().setValue(stackRequestElement.getResultAction());
        method.getReturnType()
            .show(
                stackRequestElement.getHandler().getContext().getMvcResponse(), 
                stackRequestElement.getResultAction());
    }

    public void show( RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement ) throws IOException,
            ViewException{

        if( requestInstrument.isHasViewProcessed() )
            return;

        Scopes scopes         =
            requestInstrument.getContext().getScopes();
        Scope requestScope    =
            scopes.get(ScopeType.REQUEST.toString());
        Action method     =
            stackRequestElement.getAction() == null?
                null :
                stackRequestElement.getAction().getMethodForm();

        ThrowableSafeData throwableSafeData =
                stackRequestElement.getThrowableSafeData();

        Object objectThrow = stackRequestElement.getObjectThrow();

        if( throwableSafeData != null ){
            if( throwableSafeData.getParameterName() != null )
                requestScope.put(
                    throwableSafeData.getParameterName(),
                    objectThrow);

            if( throwableSafeData.getView() != null ){
                this.showView(
                    requestInstrument,
                    throwableSafeData.getView(),
                    throwableSafeData.getDispatcher());
                return;
            }
        }

        if( stackRequestElement.getView() != null ){
            this.showView(requestInstrument, stackRequestElement.getView(),
                stackRequestElement.getDispatcherType());
            return;
        }

        if( method != null ){

            if( method.getReturnClass() != void.class ){
                String var =
                    method.getReturnIn() == null?
                        BrutosConstants.DEFAULT_RETURN_NAME :
                        method.getReturnIn();
                requestScope.put(var, stackRequestElement.getResultAction());
            }

            if( method.getView() != null ){
                this.showView(requestInstrument, method.getView(),
                        method.getDispatcherType());
                return;
            }
        }

        if( stackRequestElement.getController().getView() != null ){
            this.showView(requestInstrument,
                    stackRequestElement.getController().getView(),
                    stackRequestElement.getController().getDispatcherType());
        }
        else
        if( method != null && method.getReturnType() != null )
            this.showView(requestInstrument, stackRequestElement, method);

    }

}
