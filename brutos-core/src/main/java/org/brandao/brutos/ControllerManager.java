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

import java.util.Iterator;
import java.util.List;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.mapping.Controller;

/**
 * Gerencia os controladores de toda a aplicação.
 * 
 * @author Brandao
 */
public interface ControllerManager {

    /**
     * Cria um novo controlador.
     *
     * @param classtype Classe do controlador.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( Class classtype );

    /**
     * Cria um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, Class classType );
    
    /**
     * Cria um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param classType Classe do controlador.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, String view, boolean resolvedView, 
            Class classType );
    
    /**
     * Cria um novo controlador.
     * 
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param name Identificação do controlador dentro do contexto do conteinerIoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, String view,
           boolean resolvedView, String name, Class classType, String actionId );

    /**
     * Cria um novo controlador.
     * 
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param dispatcherType Tipo do direcionamento do fluxo para a visão.
     * @param name Identificação do controlador dentro do contexto do conteiner IoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class classType, String actionId );
    
    /**
     * Cria um novo controlador.
     * 
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param dispatcherType Tipo do direcionamento do fluxo para a visão.
     * @param name Identificação do controlador dentro do contexto do contêiner IoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @param actionType Estratégia de mapeamento de ação.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, String view, 
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class classType, String actionId, ActionType actionType );

    /**
     * Cria um novo controlador.
     * 
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param dispatcherType Tipo do direcionamento do fluxo para a visão.
     * @param name Identificação do controlador dentro do contexto do contêiner IoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @param actionType Estratégia de mapeamento de ação.
     * @return Construtor do controlador.
     */
    ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            boolean resolvedView, String name, Class classType, String actionId, ActionType actionType );
    
    /**
     * Verifica a existência de um controlador com uma determinada identificação.
     * @param id Identificação.
     * @return Verdadeiro se existir, coso contrário falso.
     */
    boolean contains( String id );
    
    /**
     * Obtém um controlador a partir de sua identificação.
     * @param id Identificação do controlador.
     * @return Mapeamento do controlador.
     */
    Controller getController( String id );

    /**
     * Obtém o mapeamento de um controlador a partir de sua classe.
     * @param controllerClass Classe do controlador.
     * @return Mapeamento do controlador.
     */
    Controller getController( Class controllerClass );

    /**
     * Obtém o mapeamento de todos os controladores.
     * @return Controladores.
     */
    List getControllers();

    /**
     * Obtém o mapeamento de todos os controladores.
     * @return Controladores.
     */
    Iterator getAllControllers();
    
    /**
     * Obtém o controlador que está atualmente está sendo construido.
     * @return Contrutor do controlador.
     */
    ControllerBuilder getCurrent();
    
    /**
     * Define o gestor parente.
     * @param parent Gestor parente.
     */
    void setParent( ControllerManager parent );
    
    /**
     * Obtém o gestor parente.
     * @return Gestor parente.
     */
    ControllerManager getParent();
    
    /**
     * Obtém o objeto que auxilia a geração de log.
     * @return Objeto que auxilia a geração de log
     */
    Logger getLogger();

    /**
     * Obtém o gerenciador dos interceptadrores.
     * @return Gerenciador dos interceptadrores
     */
    InterceptorManager getInterceptorManager();
   
    /**
     * Define o gerenciador dos interceptadrores
     * @param interceptorManager Gerenciador dos interceptadrores.
     */
    void setInterceptorManager(InterceptorManager interceptorManager);
    
    /**
     * Obtém a fábrica de validadores.
     * @return Provedor dos validadores..
     */
    ValidatorFactory getValidatorFactory();
    
    /**
     * Define a fábrica de validadores.
     * @param validatorFactory Provedor dos validadores. 
     */
    void setValidatorFactory(ValidatorFactory validatorFactory);

    /**
     * Obtém o contexto da aplicação.
     * @return Contexto da aplicação.
     */
    ConfigurableApplicationContext getApplicationContext();
    
    /**
     * Define o contexto da aplicação.
     * @param applicationContext Contexto da aplicação.
     */
    void setApplicationContext(ConfigurableApplicationContext applicationContext);

    void removeController(Class clazz);
    
    void removeController(String name);
    
    public static interface InternalUpdate{
        
        void addControllerAlias( Controller controller, String alias );
        
        void removeControllerAlias( Controller controller, String alias );
        
    }
    
}
