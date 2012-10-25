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

import java.util.*;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Classe usada para construir um controlador. Com essa classe � poss�vel incluir
 * a��es, atribuir m�ltiplas identifica��es ao controlador, capturar exce��es e
 * processa-las, definir uma a��o padr�o, determinar os interceptadores do
 * controlador, criar mapeamentos de beans, configurar as propriedades do
 * controlador e obter a classe que representa o controlador.
 *
 * <p>A inclus�o de uma a��o � feita usando o m�todo addAction(...). � possivel
 * definir sua identifica��o, o m�todo respons�vel pelo seu processamento,
 * identifica��o do objeto resultante da a��o. Al�m de ser poss�vel definir a
 * vis�o e como seu fluxo ser� direcionado para a vis�o.</p>
 *
 * Ex:
 *
 * <pre>
 * public class Controller{
 *
 *     public String savePerson( Person o ){
 *         ...
 *         this.person = o;
 *         return o.isPresist()? "OK" :  "ERROR";
 *     }
 * }
 *
 * controllerBuilder.addAction( "addPerson", "result", "showPerson.jsp",
 *     DispatcherType.FORWARD, "savePerson" );
 *
 * ...
 * </pre>
 *
 * <p>Para atribuir novas identifi��es ao controlador � usado o m�todo addAlias(...).
 * No exemplo abaixo o controlador estar� associado a duas identifica��es.
 * Essas Identifica��es s�o "/index.jbrs" e "/default.jbrs".</p>
 *
 * Ex:
 * <pre>
 * ...
 *   controllerBuilder.addAlias( "/index.jbrs" );
 *   controllerBuilder.addAlias( "/default.jbrs" );
 * ...
 * </pre>
 *
 * A captura de exce��es que podem ser lan�adas ao executar alguma a��o � configurada
 * pelo m�todo addThrowable(...). No exemplo asseguir, se for lan�ada uma exce��o
 * LoginException, o fluxo ser� alterado para o controlador "/login.jbrs" e
 * possuir� a identifica��o loginException.
 *
 * Ex:
 * 
 * <pre>
 * controlerBuilder
 *   .addThrowable( LoginException.class, "/login.jbrs", "loginException", DispatcherType.FORWARD );
 * </pre>
 *
 * <p>A a��o padr�o � definida usando o m�todo setDefaultAction(...). Essa a��o �
 * executada se o controlador for chamado sem que seja determinada a execu��o de alguma a��o.
 * A a��o deve ser previamente definida.</p>
 *
 * Ex;
 * <pre>
 * controllerBuilder.addAction( "show", "result", "show.jsp",
 *     DispatcherType.INCLUDE, "showPerson" );
 * controlerBuilder.setDefaultAction( "show" );
 * </pre>
 *
 * <p>Para definir os interceptadores do controlador � usando o m�todo addInterceptor(...).
 * Al�m de determinar os interceptadores � poss�vel fazer uma configura��o espec�fica
 * para um determinado controlador.</p>
 *
 * Ex:
 * 
 * <pre>
 * controllerBuilder.addInterceptor( "loginInterceptor" )
 *  .addParameter( "excludeMethods", "index" );
 * </pre>
 *
 * <p>O mapeamento de beans permite injetar os dados obtidos da requisi��o, sess�o,
 * contexto ou de qualquer outro escopo nas propriedades do bean. A configura��o do
 * mapeamento � feita usando o m�todo buildMappingBean(...).</p>
 *
 * Ex:
 * 
 * <pre>
 * BeanBuilder beanBuilder = controllerBuilder.buildMappingBean( "myBeanMapping", MyBean.class );
 * beanBuilder
 *   .addProperty( "name-client", "name", ScopeType.PARAM )
 *   .addProperty( "country-client", "country", ScopeType.PARAM )
 *   .addProperty( "currentUser", "currentUser", ScopeType.SESSION );
 * </pre>
 *
 * <p>As propriedades do controlador s�o configuradas pelo m�todo addProperty(...).</p>
 *
 * Ex:
 * <pre>
 * controllerBuilder.addProperty( "user", "user", ScopeType.SESSION );
 * </pre>
 * 
 * @author Afonso Brandao
 */
public class ControllerBuilder {
    
    protected final Controller controller;
    protected ControllerManager controllerManager;
    protected InterceptorManager interceptorManager;
    protected ValidatorProvider validatorProvider;
    protected ConfigurableApplicationContext applicationContext;

    public ControllerBuilder(ControllerBuilder builder){
        this( builder.controller, builder.controllerManager,
            builder.interceptorManager, builder.validatorProvider,
            builder.applicationContext );
    }
    
    /**
     * Constr�i um novo controlador.
     * 
     * @param controller 
     * @param controllerManager
     * @param interceptorManager
     * @param validatorProvider
     */
    public ControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorProvider validatorProvider,
            ConfigurableApplicationContext applicationContext ) {
        this.controller = controller;
        this.controllerManager  = controllerManager;
        this.interceptorManager = interceptorManager;
        this.validatorProvider  = validatorProvider;
        this.applicationContext = applicationContext;
    }

    /**
     * Atribui uma nova identifica��o ao controlador.
     * 
     * @param id Nova identifica��o.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addAlias( String id ){
        
        if(ActionType.DETACHED.equals(controller.getActionType())){
                throw new IllegalArgumentException(
                    "alias not allowed: " + controller.getClassType().getName() );
        }
        
        id = StringUtil.adjust(id);
        
        if( StringUtil.isEmpty(id) )
            throw new BrutosException("invalid alias");

        getLogger().info(
                String.format(
                "added alias: %s => %s",
                new Object[]{id,controller.getClassType().getName()}));
        
        controller.addAlias(id);
        controllerManager.addController(id, controller);
        return this;
    }

    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o.
     *
     * @param target Exce��o alvo.
     * @param id Identifica��o.
     * @return Contrutos do controlador.
     */
    public ControllerBuilder addThrowable( Class target, String id ){
        return addThrowable( target, null, id, DispatcherType.FORWARD );
    }

    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o. O
     * objeto resultante da exce��o pode ser usando na vis�o.
     *
     * @param target Exce��o alvo.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controlador.
     * @param id Identifica��o.
     * @param dispatcher Modo como ser� direcionado o fluxo para a vis�o.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder addThrowable( Class target, String view, String id, DispatcherType dispatcher ){

        view = StringUtil.adjust(view);
        
        id = StringUtil.adjust(id);

        if( target == null )
            throw new BrutosException( "target is required: " + controller.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        if(dispatcher == null)
            dispatcher = BrutosConstants.DEFAULT_DISPATCHERTYPE;
        
        getLogger().info(
                String.format(
                "%s => %s",
                new Object[]{
                    target.getName(),
                    view == null?
                        (controller.getView() == null? "empty" : controller.getView()) :
                        view
                    }));
        
        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setView(view);
        thr.setRedirect( false );
        thr.setDispatcher( dispatcher );
        controller.setThrowsSafe(thr);
        return this;
    }

    /**
     * Define uma a��o a ser executado caso o controlador seja chamado sem que seja
     * determinada a execu��o de alguma a��o.
     * 
     * @param id Identifica��o da ��o.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder setDefaultAction( String id ){

        id = StringUtil.adjust(id);
        
        if( id != null ){
            getLogger().info(
                String.format(
                    "default action defined %s: %s",
                    new Object[]{id,controller.getClassType().getName()}));

                controller.setDefaultAction( id );
        }
        return this;
    }

    /**
     * Cria o mapeamento de um bean. Esse mapeamento � usado para converter e injetar os
     * dados da requisi��o ou de qualquer outro escopo nas propriedades do bean.
     * 
     * @param name Nome do mapeamento.
     * @param target Alvo do mapeamento.
     * @return Construtor do mapeamento.
     * @throws java.lang.NullPointerException Lan�ado se target for igual a null.
     * @throws org.brandao.brutos.BrutosException Lan�ado se o target for uma
     * cole��o.
     */
    public BeanBuilder buildMappingBean( String name, Class target ){

        if( target == null )
            throw new NullPointerException();

        name = StringUtil.adjust(name);
        
        if( name == null )
            throw new BrutosException( "name is required: " +
                    controller.getClassType().getName() );
            
        if( target == null )
            throw new BrutosException( "target is required: " +
                    controller.getClassType().getName() );
        
        if( controller.getBean( name ) != null )
            throw new BrutosException( "duplicate mapping name " + name + " in the " + controller.getClassType().getName() );

        getLogger().info(
            String.format(
                "create bean %s[%s]",
                new Object[]{name,target.getName()}));
        
        Bean mappingBean;

        if( Map.class.isAssignableFrom(target) )
            mappingBean = new MapBean(controller);
        else
        if( Collection.class.isAssignableFrom(target) )
            mappingBean = new CollectionBean(controller);
        else
            mappingBean = new Bean(controller);


        mappingBean.setClassType( target );
        mappingBean.setName( name );
        controller.addBean( name, mappingBean );
        BeanBuilder mb = new BeanBuilder( mappingBean, controller, 
                this, validatorProvider, applicationContext );
        return mb;
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id ){
        return addAction( id, null, null, DispatcherType.FORWARD, null );
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String executor ){
        return addAction( id, null, null, DispatcherType.FORWARD, executor );
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controldor.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String executor, String view ){
        return addAction( id, null, view, DispatcherType.FORWARD, executor );
    }
    
    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controldor.
     * @param resultId Identifica��o do resultado da a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String resultId, String view, String executor ){
        return addAction( id, resultId, view, DispatcherType.FORWARD, executor );
    }
    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controldor.
     * @param dispatcher Modo como ser� alterado o fluxo para a vis�o.
     * @param resultId Identifica��o do resultado da a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String resultId, String view, DispatcherType dispatcher, String executor ){
        
        id = StringUtil.adjust(id);
        
        if(id == null)
            throw new IllegalArgumentException("invalid action id: " + id);
        
        ActionType type = controller.getActionType();
        
        if(type.equals(ActionType.COMPLEMENT))
            id = controller.getId() + id;
        
        resultId = StringUtil.adjust(resultId);

        view = StringUtil.adjust(view);

        executor = StringUtil.adjust(executor);
        
        if( controller.getAction( id ) != null )
            throw new BrutosException( "duplicate action " + id + ": " +
                controller.getClassType().getName() );
     
        Action action = new Action();
        action.setController( controller );
        controller.addAction( id, action );

        ActionBuilder actionBuilder = 
                new ActionBuilder( action, controller, validatorProvider, this );

        actionBuilder
                .setName( id )
                .setDispatcherType(dispatcher)
                .setView(view)
                .setDispatcherType(dispatcher)
                .setExecutor(executor)
                .setResult(resultId);
        
        printCreateAction(action);
        
        return actionBuilder;
    }

    /**
     * Define um novo interceptador do controlador. Se o interceptador for
     * definido como "default", ser� lan�ada uma exce��o. O interceptador dever�
     * ser previamente criado.
     * @param name Nome do interceptador. Se n�o encontrado, ser� lan�ada uma exce��o.
     * @return Construtor do interceptador.
     */
    public InterceptorBuilder addInterceptor( String name ){
        Interceptor parent = interceptorManager.getInterceptor( name );
        Interceptor it;
        
        if( parent instanceof InterceptorStack )
            it = new InterceptorStack( (InterceptorStack) parent );
        else
            it = new Interceptor( parent );
        
        it.setProperties( new HashMap() );
        
        Set keys = parent.getProperties().keySet();
        Iterator iKeys = keys.iterator();
        while( iKeys.hasNext() ){
            String key = (String) iKeys.next();
            Object value = parent.getProperties().get( key );
            it.getProperties().put( key, value );
        }
        
        getLogger().info(
            String.format(
                "%s intercepted by %s",
                new Object[]{this.controller.getClassType().getName(),name}));
        
        controller.addInterceptor( new Interceptor[]{it} );
        return new InterceptorBuilder( it, interceptorManager );
    }


    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param scope Escopo.
     * @param enumProperty Usado na configura��o de propriedades do tipo enum.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, EnumerationType enumProperty ){
        return addProperty( propertyName, id, scope, enumProperty, null, null, 
                null, false, null );
    }

    /**
     * Configura uma propriedade do controlador que não possui valor.
     *
     * @param propertyName Nome da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addNullProperty( String propertyName ){
        return addProperty( propertyName, null, null, null, null, null,
                null, true, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param scope Escopo.
     * @param temporalProperty Usado na configura��o de datas.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, String temporalProperty ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL,
                temporalProperty, null, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param scope Escopo.
     * @param type Faz o processamento da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, Type type ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, false, type );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param enumProperty Usado na configura��o de propriedades do tipo enum.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, EnumerationType enumProperty ){
        return addProperty( propertyName, id, ScopeType.PARAM, enumProperty,
                null, null, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param scope Escopo.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param temporalProperty Usado na configura��o de datas.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, String temporalProperty ){
        return addProperty( propertyName, id, ScopeType.PARAM,
                EnumerationType.ORDINAL, temporalProperty, null, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param type Faz o processamento da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, Type type ){
        return addProperty( propertyName, id, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, false, type );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param mapping Mapeamento customizado.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addPropertyMapping( String propertyName, String mapping ){
        return addProperty( propertyName, null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @param mapping Mapeamento customizado.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addPropertyMapping( String propertyName, String id, String mapping ){
        return addProperty( propertyName, id, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identifica��o da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id ){
        return addProperty( propertyName, id, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, false, null );
    }

    /**
     * Configura uma propriedade do controlador com valor est�tico.
     * 
     * @param propertyName Nome da propriedade.
     * @param value Valor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addStaticProperty( String propertyName, Object value ){
        return addProperty( propertyName, null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, value, false, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade.
     * @param id Identificação da propriedade.
     * @param scope Escopo.
     * @param enumProperty Usado na configuração de propriedades do tipo enum.
     * @param mapping Mapeamento customizado.
     * @param temporalProperty Usado na configuração de datas.
     * @param type Faz o processamento da propriedade.
     * @param value Valor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            String mapping, Object value, boolean nullable, Type type ){
        return addProperty(propertyName,id,scope,enumProperty,temporalProperty, 
            mapping,value,nullable,null,type);
    }
    
    public PropertyBuilder addProperty( String propertyName, String id, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            String mapping, Object value, boolean nullable, Object classType, Type type ){

        id               = StringUtil.adjust(id);
        propertyName     = StringUtil.adjust(propertyName);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping          = StringUtil.adjust(mapping);
        
        if( propertyName == null )
            throw new BrutosException( "property name is required: " +
                    controller.getClassType().getName() );

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();
        useBean.setNome( id );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );
        useBean.setStaticValue( value );
        useBean.setNullable(nullable);
        
        PropertyController property = new PropertyController();
        property.setBean( useBean );
        property.setName(propertyName);


        BeanInstance bean = new BeanInstance( null, controller.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                controller.getClassType().getName() + "." + propertyName );


        Object genericType = classType == null? bean.getGenericType(propertyName) : classType;
        
        if( mapping != null ){
            if( controller.getBean(mapping) != null )
                useBean.setMapping( controller.getBean( mapping ) );
            else
                throw new BrutosException( "mapping not found: " + mapping );

        }
        else
        if( type != null )
            useBean.setType( type );
        else{
            try{
                useBean.setType(
                        TypeManager.getType(
                            genericType,
                            enumProperty,
                            temporalProperty ) );
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            new Object[]{
                                controller.getClassType().getName(),
                                propertyName,
                                e.getMessage()} ) );
            }
        }

        if( controller.containsProperty( propertyName ) )
            throw new BrutosException( "property already defined: " +
                    controller.getClassType().getName() + "." + propertyName );

        controller.addProperty( property );

        return new PropertyBuilder( property );
    }

    /**
     * Constr�i uma propriedade do controlador.
     * @param propertyName Nome da propriedade.
     * @param clazz Tipo da propriedade.
     * @return Construtor da propriedade.
     */
    public BeanBuilder buildProperty( String propertyName, Class clazz ){
        String beanName = this.controller.getName() + "Controller#" + propertyName;

        BeanBuilder beanBuilder =
                buildMappingBean(beanName, clazz);

        this.addPropertyMapping(propertyName, beanName);

        return beanBuilder;
    }

    /**
     * Obt�m a classe do controlador.
     * @return Classe do controlador.
     */

    public Class getClassType(){
        return controller.getClassType();
    }
    
    public Bean getBean(String name){
        return controller.getBean(name);
    }
    
    public ControllerBuilder setId(String value){
        
        value = StringUtil.adjust(value);
        
        this.controller.setId(value);
        return this;
    }
    
    public String getId(){
        return controller.getId();
    }

    public ControllerBuilder setName(String value){
        value = StringUtil.adjust(value);
        
        if(value == null)
            value = controller.getClassType().getSimpleName();
        
        controller.setName(value);
        
        return this;
    }

    public String getName(){
        return controller.getName();
    }
    
    public ControllerBuilder setView(String value){
        value = StringUtil.adjust(value);
        controller.setView(value);
        return this;
    }

    public String getView(){
        return controller.getView();
    }
    
    public ControllerBuilder setActionId(String value){
        value = StringUtil.adjust(value);
        if(value == null)
            value = BrutosConstants.DEFAULT_ACTION_ID;
        
        controller.setActionId(value);
        
        return this;
    }
    
    public String getActionId(){
        return controller.getActionId();
    }
    
    public ControllerBuilder setDispatcherType(String value){
        value = StringUtil.adjust(value);
        
        if(StringUtil.isEmpty(value))
            throw new BrutosException("invalid dispatcher type");
        
        this.setDispatcherType(DispatcherType.valueOf(value));
        
        return this;
    }

    public ControllerBuilder setDispatcherType(DispatcherType value){
        this.controller.setDispatcherType(value);
        return this;
    }

    public DispatcherType getDispatcherType(){
        return this.controller.getDispatcherType();
    }
    
    protected Logger getLogger(){
        return LoggerProvider.getCurrentLoggerProvider()
                .getLogger(ControllerBuilder.class);
    }
    
    public void setActionType(ActionType actionType){
        this.controller.setActionType(actionType);
    }
    
    public ActionType getActionType(){
        return this.controller.getActionType();
    }

    public PropertyBuilder getProperty(String name){
        PropertyController property = (PropertyController) controller.getProperty(name);
        return property == null? null : new PropertyBuilder( property );
    }

    private void printCreateAction(Action action){
        
        ActionType type = controller.getActionType();
        String log;
        
        if(type.equals(ActionType.PARAMETER)){
            log = 
                controller + 
                "[" +
                controller.getId() + 
                "?" + 
                controller.getActionId() + 
                "=" +
                action.getName() + 
                "]";
        }
        else{
            log = 
                controller + 
                "[" +
                action.getName() + 
                "]";
            
        }
        
        getLogger()
            .info(log);
    }
    
}
