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
import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.scope.Scope;

/**
 * Renderiza as vistas da aplicação.
 * 
 * @author Afonso Brandao
 */
public abstract class ViewProvider {
    
    public ViewProvider() {
    }
    
    /**
     * Obtém uma instância do provedor de vista.
     * @param properties Configuração da aplicação.
     * @return Provedor de vista.
     */
    public static ViewProvider getProvider( Properties properties ){
        String viewProviderName = properties
                .getProperty(
                    BrutosConstants.VIEW_PROVIDER_CLASS,
                    BrutosConstants.DEFAULT_VIEW_PROVIDER_CLASS);
        
        ViewProvider view       = null;
        
        try{
            Class iocProvider = ClassUtil.get(viewProviderName);
            view = (ViewProvider)ClassUtil.getInstance(iocProvider);
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
    
    /**
     * Aplica as configurações da aplicação.
     * 
     * @param properties Configuração da aplicação.
     */
    public abstract void configure( Properties properties );

    /**
     * Renderiza uma determinada vista.
     * 
     * @param requestInstrument Recursos da aplicação.
     * @param view Vista a ser renderizada.
     * @param dispatcherType Tipo de direcionamento do fluxo para a vista.
     * @throws IOException Lançado se ocorrer algum problema ao renderizar a vista.
     */
    protected abstract void show( RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType )
                throws IOException;

    /**
     * Renderiza uma determinada vista.
     * 
     * @param requestInstrument Recursos da aplicação.
     * @param view Vista a ser renderizada.
     * @param dispatcherType Tipo de direcionamento do fluxo para visão.
     * @throws IOException Lançado se ocorrer algum problema ao renderizar a vista.
     */
    private void showView( RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType )
                throws IOException{
        requestInstrument.setHasViewProcessed(true);
        show(requestInstrument,view,dispatcherType);
    }

    /**
     * Renderiza a vista de uma determinada ação.
     * 
     * @param requestInstrument Recursos da aplicação.
     * @param stackRequestElement Informações da requisição.
     * @param action Ação.
     * @throws IOException Lançado se ocorrer algum problema ao renderizar a vista.
     */
    private void showView( RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement, Action action )
                throws IOException{
        requestInstrument.setHasViewProcessed(true);
        action.getReturnType()
            .show(
                stackRequestElement.getHandler().getContext().getMvcResponse(), 
                stackRequestElement.getResultAction());
    }

    /**
     * Renderiza a vista da requisição.
     * 
     * @param requestInstrument Recursos da aplicação.
     * @param stackRequestElement Informações da requisição.
     * @throws IOException Lançado se ocorrer algum problema ao renderizar a vista.
     */
    public void show( RequestInstrument requestInstrument,
            StackRequestElement stackRequestElement ) throws IOException{

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
                
                if(method.isReturnRendered()){
                    this.showView(requestInstrument, stackRequestElement, method);
                    return;
                }
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
