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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Classe usada para configurar controladores ou front controllers.
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

    private Map forms;
    private Map revForms;
    private ValidatorProvider validatorProvider;
    private ControllerBuilder current;
    private AbstractApplicationContext applicationContext;
    private InterceptorManager interceptorManager;
    
    public ControllerManager( InterceptorManager interceptorManager, 
            ValidatorProvider validatorProvider, AbstractApplicationContext applicationContext) {
        this.forms              = new HashMap();
        this.revForms           = new HashMap();
        this.interceptorManager = interceptorManager;
        this.validatorProvider  = validatorProvider;
        this.applicationContext = applicationContext;
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
            String name, Class classType, String actionId ){

        id = id == null || id.replace( " ", "" ).length() == 0? null : id;
        view = view == null || view.replace( " ", "" ).length() == 0 ? null : view;

        if( actionId == null )
            actionId = "invoke";
        
        if( name == null || name.length() == 0 )
            name = classType.getSimpleName();
        
        Controller fr = new Controller();
        fr.setUri( id );
        fr.setId( name );
        fr.setPage( view );
        fr.setClassType( classType );
        fr.setScope( ScopeType.PARAM );
        fr.setMethodId( actionId );
        fr.setRedirect(false);
        fr.setDispatcherType(dispatcherType);
        
        getLogger().info(
                String.format(
                    "create controller %s[%s]",
                    new Object[]{
                        classType.getName(),
                        id == null? "?" : id
                         }));
        //Action
        Action ac = new Action();
        ac.setPreAction( getMethodAction( "preAction", fr.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", fr.getClassType() ) );
        fr.setAcion( ac );
        
        //forms.put( fr.getUri(), fr );
        //revForms.put( fr.getClassType(), fr );
        addForm( fr.getUri(), fr );
        fr.setDefaultInterceptorList( interceptorManager.getDefaultInterceptors() );
        
        this.current = new ControllerBuilder( fr, this, 
                interceptorManager, validatorProvider, applicationContext );
        
        //for( Interceptor in: interceptorManager.getDefaultInterceptors() )
        //    current.addInterceptor( in.getName() );

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
        return this.forms.containsKey( id );
    }
    
    /**
     * Obt�m o mapeamento de um controlador.
     * @param id Identifica��o do controlador.
     * @return Mapeamento do controlador.
     */
    public Controller getController( String id ){
        return (Controller)forms.get( id );
    }

    /**
     * @deprecated
     * @param uri
     * @return .
     */
    public Controller getForm( String uri ){
        return (Controller)forms.get( uri );
    }

    /**
     * Obt�m o mapeamento de um controlador.
     * @param controllerClass Classe do controlador.
     * @return Mapeamento do controlador.
     */
    public Controller getController( Class controllerClass ){
        return (Controller)revForms.get( controllerClass );
    }

    /**
     * @deprecated 
     * @param controllerClass
     * @return .
     */
    public Controller getForm( Class controllerClass ){
        return (Controller)revForms.get( controllerClass );
    }

    /**
     * Obt�m o mapeamento de todos os controladores.
     * @return Controladores.
     */
    public Map getControllers() {
        return Collections.unmodifiableMap( forms );
    }

    /**
     * @deprecated 
     * @return .
     */
    public Map getForms() {
        return Collections.unmodifiableMap( forms );
    }

    void addForm( String id, Controller form ) {
        if( contains(id) )
            throw new BrutosException( String.format("duplicate id: %s", new Object[]{id} ) );

        forms.put(id, form);
        revForms.put( form.getClassType(), form);
    }

    public ControllerBuilder getCurrent() {
        return current;
    }
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ControllerBuilder.class);
    }
    
}
