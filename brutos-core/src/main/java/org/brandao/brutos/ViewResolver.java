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
 * Resolutor de vista. Resolve as vistas dos controladores
 * ações e exceções.
 * 
 * @author Brandao
 */
public interface ViewResolver {
    
    /**
     * Resolve a vista de um componenete da aplicação.
     * 
     * @param controllerBuilder Construtor do controlador.
     * @param actionBuilder Construtor da ação.
     * @param exceptionType Exceção.
     * @param view Nome da vista.
     * @return Vista.
     */
    String getView(ControllerBuilder controllerBuilder, ActionBuilder actionBuilder, 
            Class<?> exceptionType, String view);

    /**
     * Define o contexto do resolutor de vista.
     * @param context
     */
    void setApplicationContext(ApplicationContext context);
    
    /**
     * Obtém o prefixo das vistas.
     * 
     * @return Prefixo.
     */
    String getPrefix();
    
    /**
     * Obtém o sufixo das vistas.
     * @return Sufixo.
     */
    String getSuffix();
    
    /**
     * Obtém o nome padrão de um componente.
     * @return
     */
    String getIndexName();
    
    /**
     * Obtém o separador usado para compor uma vista.
     * @return
     */
    String getSeparator();
    
    /**
     * Obtém a vista de um controlador.
     * 
     * @param controllerType Controlador. 
     * @param view Nome da vista.
     * @return Vista.
     */
    String getControllerView(Class<?> controllerType, String view);
    
    /**
     * Obtém a vista de uma ação.
     * 
     * @param controllerType Controlador.
     * @param actionExecutor Nome do método executor da ação.
     * @param view Nome da vista.
     * @return Vista.
     */
    String getActionView(Class<?> controllerType, String actionExecutor, String view);
    
    /**
     * Obtém a vista de uma exeção mapeada em nível de ação.
     * @param controllerType Controlador.
     * @param actionExecutor Nome do método executor da ação.
     * @param exceptionType Exceção.
     * @param view Noma da vista.
     * @return Vista.
     */
    String getExceptionView(Class<?> controllerType, String actionExecutor, Class<?> exceptionType, String view);
    
    /**
     * Obtém a vista de uma exeção mapeada em nível de controlador.
     * @param controllerType Controlador.
     * @param exceptionType Exceção.
     * @param view Nome da vista.
     * @return Vista.
     */
    String getExceptionView(Class<?> controllerType, Class<?> exceptionType, String view);
    
}
