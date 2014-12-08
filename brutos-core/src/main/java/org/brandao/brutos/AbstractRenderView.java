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

import java.io.IOException;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.Type;

/**
 * Classe base de um renderizador de vista.
 * 
 * @author Brandao
 */
public abstract class AbstractRenderView implements RenderView{
    
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
            StackRequestElement stackRequestElement, Type type )
                throws IOException{
        requestInstrument.setHasViewProcessed(true);
        type
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

        Scopes scopes = requestInstrument.getContext().getScopes();
        Scope requestScope = scopes.get(ScopeType.REQUEST.toString());
        
        Action method = 
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
                
                if(method.isReturnRendered() || method.getReturnType().isAlwaysRender()){
                    this.showView(requestInstrument, stackRequestElement, method.getReturnType());
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
            this.showView(requestInstrument, stackRequestElement, method.getReturnType());

    }
    
}
