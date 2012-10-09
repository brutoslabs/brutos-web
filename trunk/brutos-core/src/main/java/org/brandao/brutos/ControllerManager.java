/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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
 * Classe usada para configurar controladores.
 * <p>O controlador � respons�vel por receber os dados e determinar qual objeto
 * do modelo e vis�o ser�o usados. Ele tamb�m � respons�vel por converter,
 * validar e filtrar a entrada de dados.</p>
 * <p>O Brutos pode ser usando como controlador ou front controller. Nos dois
 * casos, somente � necess�rio fazer respectivamente o mapeamento do modelo ou
 * controlador. Esse mapeamento nada mais � que, informar ao controlador como os
 * dados de entrada ser�o disponibilizados no modelo (atributos ou par�metros de
 * um m�todo) e como seu resultado ser� exibido (vis�o). Todo o processamento de
 * uma aplica��o segue as seguintes etapas:</p>
 * 
 * <ul>
 * 
 * <li><b>Obter modelo.</b> O controlador recebe a requisi��o, examina-o e extrai a ID
 * do modelo, que � determinado pelo URI, no caso de uma aplica��o web, ou nome 
 * da classe, em uma aplica��o desktop. Essa ID � usada para localizar o modelo. 
 * Se o modelo n�o for encontrado, a requisi��o � finalizada.</li>
 * 
 * <li><b>Gerar manipulador.</b> Nessa etapa � gerado o manipulador da requisi��o. O
 * manipulador � composto por: modelo; a��o e a identifica��o da requisi��o. � 
 * nesse momento que � identificado o controlador e a a��o a ser executada.</li>
 * 
 * <li><b>Processar interceptadores.</b> Ocorre o processamento da pilha de
 * interceptadores. Essa pilha de interceptadores pode ser criada pelo 
 * desenvolvedor. Se a pilha de interceptadores estiver vazia, ir� 
 * automaticamente avan�ar para a pr�xima etapa.</li>
 * 
 * <li><b>Processar valida��es.</b> � nessa etapa que ocorrem as valida��es. Essa
 * valida��o pode ser criada pelo desenvolvedor ou obtida diretamente do 
 * controlador. Os valores s�o validados de acordo com as regras de valida��o 
 * da aplica��o. Se o valor for considerado inv�lido, ser� marcado como inv�lido. 
 * Mesmo existindo um valor inv�lido, a pr�xima etapa, atualizar valores do
 * modelo, ser� executada.</li>
 * 
 * <li><b>Atualizar valores do modelo.</b> Esta � a etapa onde s�o atualizados os
 * valores no lado do servidor, ou seja, a atualiza��o das propriedades do 
 * modelo. Somente as propriedades mapeadas ser�o atualizadas. Se na etapa 
 * anterior, algum valor foi marcado como inv�lido, o controlador 
 * automaticamente ir� para a etapa final, processar vis�o, ignorando a etapa 
 * invocar aplica��o.</li>
 * 
 * <li><b>Invocar aplica��o.</b> Nessa etapa o controlador invoca a aplica��o. Os
 * valores foram convertidos, validados e aplicados no modelo, ent�o estar� 
 * pronto para utilizar suas regras de neg�cio. Se ocorrer um problema, o 
 * controlador automaticamente ir� para a pr�xima etapa. Tanto um 
 * erro (exception) quanto o resultado poder� alterar o fluxo l�gico da 
 * aplica��o.</li>
 * 
 * <li><b>Processar vis�o.</b> Nessa etapa ser� exibida a tela com o resultado obtido
 * do modelo.</li>
 * 
 * </ul>
 *
 * Ex:
 *
 * <pre>
 * public class MyController{
 *   ...
 * }
 *
 * controllerManager.addController( MyController.class );
 * ...
 * controllerManager.addController( "/index.jbrs", MyController.class );
 * </pre>
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
     * Constr�i um novo controlador.
     *
     * @param classtype Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( Class classtype ){
        return addController( null, null, null, classtype, "invoke" );
    }

    /**
     * Constr�i um novo controlador.
     *
     * @param id Identifica��o do controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, Class classType ){
        return addController( id, null, null, classType, "invoke" );
    }
    
    /**
     * Constr�i um novo controlador.
     *
     * @param id Identifica��o do controlador.
     * @param view Vis�o associada ao controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view, Class classType ){
        return addController( id, view, null, classType, "invoke" );
    }
    
    /**
     * Constr�i um novo controlador.
     *
     * @param id Identifica��o do controlador.
     * @param view Vis�o associada ao controlador.
     * @param name Nome do controlador, usado para obter sua inst�ncia no container
     * IOC.
     * @param classType Classe do controlador.
     * @param actionId Nome do par�metro que cont�m a identifica��o da a��o.
     * Normalmente usado em aplica��es web.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view,
           String name, Class classType, String actionId ){
        return addController( id, view, DispatcherType.FORWARD, name, classType, actionId );
    }

    public ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, String actionId ){
            return addController( id, view, dispatcherType, name, classType, actionId, 
                    ActionType.PARAMETER);
    }
    
    /**
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
        
        if( StringUtil.isEmpty(name) )
            name = classType.getSimpleName();
        
        if(ActionType.PARAMETER.equals(actionType) || ActionType.COMPLEMENT.equals(actionType)){
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
        
        getLogger().info(
                String.format(
                    "create controller %s[%s]",
                    new Object[]{
                        classType.getName(),
                        id == null? "?" : id
                         }));
        //Action
        ActionListener ac = new ActionListener();
        ac.setPreAction( getMethodAction( "preAction", controller.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", controller.getClassType() ) );
        controller.setActionListener( ac );
        
        addController( controller.getId(), controller );
        
        controller.setDefaultInterceptorList( interceptorManager.getDefaultInterceptors() );
        
        this.current = createBuilder( controller, this, 
                interceptorManager, validatorProvider, applicationContext );
        
        return this.getCurrent();
    }
    
    protected ControllerBuilder createBuilder(Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorProvider validatorProvider,
            ConfigurableApplicationContext applicationContext){
        return new ControllerBuilder( controller, this, 
                interceptorManager, validatorProvider, applicationContext );
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
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ControllerBuilder.class);
    }
    
}
