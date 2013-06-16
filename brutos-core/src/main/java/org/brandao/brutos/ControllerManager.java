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

import java.lang.reflect.Method;
import java.util.*;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.ActionListener;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Gerencia os controladores de toda a aplicação.
 * 
 * @author Afonso Brandao
 */
public class ControllerManager {

    private Map mappedControllers;
    private Map classMappedControllers;
    private ValidatorProvider validatorProvider;
    private ControllerBuilder current;
    private ConfigurableApplicationContext applicationContext;
    private InterceptorManager interceptorManager;
    private ControllerManager parent;
    
    public ControllerManager( InterceptorManager interceptorManager, 
            ValidatorProvider validatorProvider, 
            ControllerManager parent,
            ConfigurableApplicationContext applicationContext) {
        this.mappedControllers      = new HashMap();
        this.classMappedControllers = new HashMap();
        this.interceptorManager     = interceptorManager;
        this.validatorProvider      = validatorProvider;
        this.applicationContext     = applicationContext;
        this.parent                 = parent;
    }

    /**
     * Cria um novo controlador.
     *
     * @param classtype Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( Class classtype ){
        return addController( null, null, null, classtype, "invoke" );
    }

    /**
     * Cria um novo controlador atribuindo uma identificação.
     *
     * @param id Identificação do controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, Class classType ){
        return addController( id, null, null, classType, "invoke" );
    }
    
    /**
     * Cria um novo controlador atribuindo uma identificação e uma visão.
     *
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view, Class classType ){
        return addController( id, view, null, classType, "invoke" );
    }
    
    /**
     * Cria um novo controlador atribuindo uma identificação, uma visão e
     * com o nome do parâmetro que identifica a ação.
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param name Identificação do controlador dentro do contexto do conteinerIoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view,
           String name, Class classType, String actionId ){
        return addController( id, view, DispatcherType.FORWARD, name, classType, actionId );
    }

    /**
     * Cria um novo controlador atribuindo uma identificação, uma visão com o tipo
     * de direcionamento de fluxo e nome do parâmetro que identifica a ação.
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param dispatcherType Tipo do direcionamento do fluxo para a visão.
     * @param name Identificação do controlador dentro do contexto do conteinerIoC.
     * @param classType Classe do controlador.
     * @param actionId Parâmetro que identifica a ação.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, String actionId ){
            return addController( id, view, dispatcherType, name, classType, actionId, 
                    ActionType.PARAMETER);
    }
    
    /*
     * Constr�i um novo controlador.
     *
     * @param id Identifica��o do controlador.
     * @param view Vis�o associada ao controlador.
     * @param dispatcherType Determina como o fluxo deve ser direcionado para a
     * vis�o.
     * @param name Nome do controlador, usado para obter sua inst�ncia no container
     * IOC.
     * @param classType Classe do controlador.
     * @param actionId Nome do par�metro que cont�m a identifica��o da a��o.
     * Normalmente usado em aplica��es web.
     * @return Construtor do controlador.
     */
    
    /**
     * Cria um novo controlador atribuindo uma identificação, uma visão com o tipo
     * de direcionamento de fluxo, com o nome do parâmetro que identifica a ação
     * e seu tipo de mapeamento.
     * @param id Identificação do controlador.
     * @param view Visão do controlador.
     * @param dispatcherType
     * @param name
     * @param classType
     * @param actionId
     * @param actionType
     * @return 
     */
    public ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, String actionId, ActionType actionType ){

        id       = StringUtil.adjust(id);
        view     = StringUtil.adjust(view);
        actionId = StringUtil.adjust(actionId);
        name     = StringUtil.adjust(name);
        
        if(actionType == null){
            Properties config = applicationContext.getConfiguration();
            String strategyName =
                    config.getProperty(
                        BrutosConstants.ACTION_TYPE,
                        BrutosConstants.DEFAULT_ACTION_TYPE_NAME);
            actionType = ActionType.valueOf(strategyName.toUpperCase());
        }
        
        if(classType == null)
            throw new IllegalArgumentException("invalid class type: " + classType);
        
        if( StringUtil.isEmpty(actionId) )
            actionId = BrutosConstants.DEFAULT_ACTION_ID;
        
        if(ActionType.PARAMETER.equals(actionType) || ActionType.HIERARCHY.equals(actionType)){
            if(StringUtil.isEmpty(id))
                throw new IllegalArgumentException("controller id is required: " + classType.getName() );
        }
        else
        if(!ActionType.DETACHED.equals(actionType))
            throw new IllegalArgumentException("invalid class type: " + classType);
            
        Controller controller = new Controller();
        controller.setId( id );
        controller.setName( name );
        controller.setView( view );
        controller.setClassType( classType );
        controller.setActionId( actionId );
        controller.setDispatcherType(dispatcherType);
        controller.setActionType(actionType);
        
        printCreateController(controller);
        
        //Action
        ActionListener ac = new ActionListener();
        ac.setPreAction( getMethodAction( "preAction", controller.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", controller.getClassType() ) );
        controller.setActionListener( ac );
        
        addController( controller.getId(), controller );
        
        controller.setDefaultInterceptorList( interceptorManager.getDefaultInterceptors() );
        
        this.current = new ControllerBuilder( controller, this, 
                interceptorManager, validatorProvider, applicationContext );
        
        return this.getCurrent();
    }
    
    private Method getMethodAction( String methodName, Class classe ){
        try{
            Method method = classe.getDeclaredMethod( methodName, new Class[]{} );
            return method;
        }
        catch( Exception e ){
            //throw new BrutosException( e );
            return null;
        }
    }

    /**
     * Verifica se existe um controlador mapeado para uma determinada identifica��o
     * @param id Identifica��o.
     * @return Verdadeiro se existir um mapeamento, coso contr�rio falso.
     */
    public boolean contains( String id ){
        boolean result = this.mappedControllers.containsKey( id );
        return !result && parent != null? parent.mappedControllers.containsKey( id ) : result;
    }
    
    /**
     * Obt�m o mapeamento de um controlador.
     * @param id Identifica��o do controlador.
     * @return Mapeamento do controlador.
     */
    public Controller getController( String id ){
        Controller controller = (Controller)mappedControllers.get( id );
        
        if(controller == null && parent != null)
            return parent.getController(id);
        else
            return controller;
        
    }

    /**
     * Obt�m o mapeamento de um controlador.
     * @param controllerClass Classe do controlador.
     * @return Mapeamento do controlador.
     */
    public Controller getController( Class controllerClass ){
        Controller controller = (Controller)classMappedControllers.get( controllerClass );
        
        if(controller == null && parent != null)
            return parent.getController(controllerClass);
        else
            return controller;
    }

    /**
     * Obtém o mapeamento de todos os controladores.
     * @return Controladores.
     */
    public List getControllers() {
        List tmp;
        
        tmp = new LinkedList(classMappedControllers.values());
        
        if(parent != null)
            tmp.addAll(parent.classMappedControllers.values());
            
        return Collections.unmodifiableList( tmp );
    }

    public Iterator getAllControllers(){
        return new Iterator(){
            
            private Iterator currentIterator;
            private Iterator parentIterator;
            private int index;
            private int maxSize;
            {
                index = 0;
                currentIterator = 
                        classMappedControllers.values().iterator();
                parentIterator = 
                        parent != null? 
                            parent.classMappedControllers.values().iterator() : 
                            null;
                maxSize = 
                        classMappedControllers.size();
                
            }
            
            public boolean hasNext() {
                if(index < maxSize)
                    return currentIterator.hasNext();
                else
                    return parentIterator != null? parentIterator.hasNext() : false;
            }

            public Object next() {
                try{
                    if(index < maxSize)
                        return currentIterator.next();
                    else
                        return parentIterator != null? parentIterator.next() : null;
                }
                finally{
                    index++;
                }
            }

            public void remove() {
                if(index < maxSize)
                    currentIterator.remove();
                else
                if(parentIterator != null)
                    parentIterator.remove();
                
                index--;
            }
            
        };
    }
    
    void addController( String id, Controller form ) {
        
        if( id != null){
             if(contains(id))
                throw new BrutosException( String.format("duplicate id: %s", new Object[]{id} ) );
            else
                mappedControllers.put(id, form);
        }
        
        classMappedControllers.put( form.getClassType(), form);
    }

    public ControllerBuilder getCurrent() {
        return current;
    }
    
    void setParent( ControllerManager parent ){
        this.parent = parent;
    }
    
    public ControllerManager getParent(){
        return this.parent;
    }
    
    private void printCreateController(Controller controller){
        
        ActionType type = controller.getActionType();
        
        if(type.equals(ActionType.PARAMETER)){
            String log = 
                controller + 
                "[" +
                controller.getId() +
                "]";
            getLogger()
                .info(log);
        }
        
    }
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ControllerBuilder.class);
    }
    
}
