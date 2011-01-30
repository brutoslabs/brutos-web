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
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Classe usada para configurar controladores ou front controllers.
 * <p>O controlador é responsável por receber os dados e determinar qual objeto
 * do modelo e visão serão usados. Ele também é responsável por converter,
 * validar e filtrar a entrada de dados.</p>
 * <p>O Brutos pode ser usando como controlador ou front controller. Nos dois
 * casos, somente é necessário fazer respectivamente o mapeamento do modelo ou
 * controlador. Esse mapeamento nada mais é que, informar ao controlador como os
 * dados de entrada serão disponibilizados no modelo (atributos ou parâmetros de
 * um método) e como seu resultado será exibido (visão). Todo o processamento de
 * uma aplicação segue as seguintes etapas:</p>
 * 
 * <ul>
 * 
 * <li><b>Obter modelo.</b> O controlador recebe a requisição, examina-o e extrai a ID
 * do modelo, que é determinado pelo URI, no caso de uma aplicação web, ou nome 
 * da classe, em uma aplicação desktop. Essa ID é usada para localizar o modelo. 
 * Se o modelo não for encontrado, a requisição é finalizada.</li>
 * 
 * <li><b>Gerar manipulador.</b> Nessa etapa é gerado o manipulador da requisição. O
 * manipulador é composto por: modelo; ação e a identificação da requisição. É 
 * nesse momento que é identificado o controlador e a ação a ser executada.</li>
 * 
 * <li><b>Processar interceptadores.</b> Ocorre o processamento da pilha de
 * interceptadores. Essa pilha de interceptadores pode ser criada pelo 
 * desenvolvedor. Se a pilha de interceptadores estiver vazia, irá 
 * automaticamente avançar para a próxima etapa.</li>
 * 
 * <li><b>Processar validações.</b> É nessa etapa que ocorrem as validações. Essa
 * validação pode ser criada pelo desenvolvedor ou obtida diretamente do 
 * controlador. Os valores são validados de acordo com as regras de validação 
 * da aplicação. Se o valor for considerado inválido, será marcado como inválido. 
 * Mesmo existindo um valor inválido, a próxima etapa, atualizar valores do
 * modelo, será executada.</li>
 * 
 * <li><b>Atualizar valores do modelo.</b> Esta é a etapa onde são atualizados os
 * valores no lado do servidor, ou seja, a atualização das propriedades do 
 * modelo. Somente as propriedades mapeadas serão atualizadas. Se na etapa 
 * anterior, algum valor foi marcado como inválido, o controlador 
 * automaticamente irá para a etapa final, processar visão, ignorando a etapa 
 * invocar aplicação.</li>
 * 
 * <li><b>Invocar aplicação.</b> Nessa etapa o controlador invoca a aplicação. Os
 * valores foram convertidos, validados e aplicados no modelo, então estará 
 * pronto para utilizar suas regras de negócio. Se ocorrer um problema, o 
 * controlador automaticamente irá para a próxima etapa. Tanto um 
 * erro (exception) quanto o resultado poderá alterar o fluxo lógico da 
 * aplicação.</li>
 * 
 * <li><b>Processar visão.</b> Nessa etapa será exibida a tela com o resultado obtido
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

    private static Logger logger = LoggerProvider
            .getCurrentLoggerProvider()
                .getLogger(ControllerManager.class.getName());
    
    private Map forms;
    private Map revForms;
    private ValidatorProvider validatorProvider;
    private ControllerBuilder current;
    
    private InterceptorManager interceptorManager;
    
    public ControllerManager( InterceptorManager interceptorManager, ValidatorProvider validatorProvider) {
        this.forms              = new HashMap();
        this.revForms           = new HashMap();
        this.interceptorManager = interceptorManager;
        this.validatorProvider  = validatorProvider;
    }

    /**
     * Constrói um novo controlador.
     *
     * @param classtype Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( Class classtype ){
        return addController( null, null, null, classtype, "invoke" );
    }

    /**
     * Constrói um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, Class classType ){
        return addController( id, null, null, classType, "invoke" );
    }
    
    /**
     * Constrói um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param view Visão associada ao controlador.
     * @param classType Classe do controlador.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view, Class classType ){
        return addController( id, view, null, classType, "invoke" );
    }
    
    /**
     * Constrói um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param view Visão associada ao controlador.
     * @param name Nome do controlador, usado para obter sua instância no container
     * IOC.
     * @param classType Classe do controlador.
     * @param actionId Nome do parâmetro que contém a identificação da ação.
     * Normalmente usado em aplicações web.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addController( String id, String view,
           String name, Class classType, String actionId ){
        return addController( id, view, DispatcherType.FORWARD, name, classType, actionId );
    }

    /**
     * Constrói um novo controlador.
     *
     * @param id Identificação do controlador.
     * @param view Visão associada ao controlador.
     * @param dispatcherType Determina como o fluxo deve ser direcionado para a
     * visão.
     * @param name Nome do controlador, usado para obter sua instância no container
     * IOC.
     * @param classType Classe do controlador.
     * @param actionId Nome do parâmetro que contém a identificação da ação.
     * Normalmente usado em aplicações web.
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
        
        Form fr = new Form();
        fr.setUri( id );
        fr.setId( name );
        fr.setPage( view );
        fr.setClassType( classType );
        fr.setScope( ScopeType.REQUEST );
        fr.setMethodId( actionId );
        fr.setRedirect(false);
        fr.setDispatcherType(dispatcherType);
        
        //Action
        Action ac = new Action();
        ac.setPreAction( getMethodAction( "preAction", fr.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", fr.getClassType() ) );
        fr.setAcion( ac );
        
        //forms.put( fr.getUri(), fr );
        //revForms.put( fr.getClassType(), fr );
        addForm( fr.getUri(), fr );
        fr.setDefaultInterceptorList( interceptorManager.getDefaultInterceptors() );
        
        this.current = new ControllerBuilder( fr, this, interceptorManager, validatorProvider );
        
        //for( Interceptor in: interceptorManager.getDefaultInterceptors() )
        //    current.addInterceptor( in.getName() );

        return this.getCurrent();
    }
    
    private Method getMethodAction( String methodName, Class classe ){
        try{
            Method method = classe.getDeclaredMethod( methodName );
            return method;
        }
        catch( Exception e ){
            //throw new BrutosException( e );
            return null;
        }
    }

    /**
     * Verifica se existe um controlador mapeado para uma determinada identificação
     * @param id Identificação.
     * @return Verdadeiro se existir um mapeamento, coso contrário falso.
     */
    public boolean contains( String id ){
        return this.forms.containsKey( id );
    }
    
    /**
     * Obtém o mapeamento de um controlador.
     * @param id Identificação do controlador.
     * @return Mapeamento do controlador.
     */
    public Form getController( String id ){
        return (Form)forms.get( id );
    }

    /**
     * @deprecated
     * @param uri
     * @return .
     */
    public Form getForm( String uri ){
        return (Form)forms.get( uri );
    }

    /**
     * Obtém o mapeamento de um controlador.
     * @param controllerClass Classe do controlador.
     * @return Mapeamento do controlador.
     */
    public Form getController( Class controllerClass ){
        return (Form)revForms.get( controllerClass );
    }

    /**
     * @deprecated 
     * @param controllerClass
     * @return .
     */
    public Form getForm( Class controllerClass ){
        return (Form)revForms.get( controllerClass );
    }

    /**
     * Obtém o mapeamento de todos os controladores.
     * @return Controladores.
     */
    public Map<String, Form> getControllers() {
        return Collections.unmodifiableMap( forms );
    }

    /**
     * @deprecated 
     * @return .
     */
    public Map<String, Form> getForms() {
        return Collections.unmodifiableMap( forms );
    }

    void addForm( String id, Form form ) {
        if( contains(id) )
            throw new BrutosException( String.format("duplicate id: %s", id ) );

        forms.put(id, form);
        revForms.put( form.getClassType(), form);
    }

    public ControllerBuilder getCurrent() {
        return current;
    }
}
