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

import org.brandao.brutos.ControllerManager.InternalUpdate;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.AnyType;
import org.brandao.brutos.type.NullType;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

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
    
    protected ValidatorFactory validatorFactory;
    
    protected ConfigurableApplicationContext applicationContext;
    
    protected InternalUpdate internalUpdate;

    public ControllerBuilder(ControllerBuilder builder, InternalUpdate internalUpdate){
        this( builder.controller, builder.controllerManager,
            builder.interceptorManager, builder.validatorFactory,
            builder.applicationContext, internalUpdate );
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
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext,InternalUpdate internalUpdate ) {
        this.controller = controller;
        this.controllerManager  = controllerManager;
        this.interceptorManager = interceptorManager;
        this.validatorFactory   = validatorFactory;
        this.applicationContext = applicationContext;
        this.internalUpdate     = internalUpdate;
    }

    /**
     * Atribui uma nova identifica��o ao controlador.
     * 
     * @param id Nova identifica��o.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addAlias( String id ){
        
        if(ActionType.DETACHED.equals(controller.getActionType())){
                throw new MappingException(
                    "alias not allowed: " + controller.getClassType().getName() );
        }
        
        id = StringUtil.adjust(id);
        
        if( StringUtil.isEmpty(id) )
            throw new MappingException("invalid alias");

        getLogger().info(
                String.format(
                "adding alias %s on controller %s",
                new Object[]{id,controller.getClassType().getSimpleName()}));
        
        controller.addAlias(id);
        internalUpdate.addControllerAlias(controller,id);
        return this;
    }

    /**
     * Remove uma das identificações do controlador.
     * 
     * @param id Identificação.
     * @return Construtor do controlador.
     */
    public ControllerBuilder removeAlias( String id ){
        
        if(ActionType.DETACHED.equals(controller.getActionType())){
                throw new MappingException(
                    "alias not allowed: " + controller.getClassType().getName() );
        }
        
        id = StringUtil.adjust(id);
        
        if( StringUtil.isEmpty(id) )
            throw new MappingException("invalid alias");

        getLogger().info(
                String.format(
                "removing alias %s on controller %s",
                new Object[]{id,controller.getClassType().getSimpleName()}));
        
        internalUpdate.removeControllerAlias(controller,id);
        return this;
    }
    
    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o.
     *
     * @param target Exce��o alvo.
     * @param id Identifica��o.
     * @return Contrutos do controlador.
     */
    public ControllerBuilder addThrowable( Class<?> target, String id ){
        return addThrowable( target, null, 
                !"true".equals(applicationContext.getConfiguration()
                .getProperty(BrutosConstants.VIEW_RESOLVER_AUTO)),
                id, DispatcherType.FORWARD );
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
    public ControllerBuilder addThrowable( Class<?> target, String view, 
            boolean resolvedView, String id, DispatcherType dispatcher){
        return this.addThrowable( target, view, id, dispatcher,
                resolvedView);
    }
    
    /**
     * Intercepta e atribui uma identifica��o a uma determinada exce��o. O
     * objeto resultante da exce��o pode ser usando na vis�o.
     *
     * @param target Exce��o alvo.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controlador.
     * @param id Identifica��o.
     * @param dispatcher Modo como ser� direcionado o fluxo para a vis�o.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder addThrowable( Class<?> target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){

        view = StringUtil.adjust(view);
        
        view = 
            resolvedView? 
                view : 
                applicationContext.
                        getViewResolver()
                        .getView(
                                this, 
                                null, 
                                target, 
                                view);
        
        id = StringUtil.adjust(id);

        if( target == null )
            throw new MappingException( "target is required: " + controller.getClassType().getSimpleName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new MappingException( "target is not allowed: " +target.getSimpleName() );

        if(this.controller.getThrowsSafe(target) != null)
            throw new MappingException( "the exception has been added on controller: " + target.getSimpleName() );
        	
        if(dispatcher == null)
            dispatcher = BrutosConstants.DEFAULT_DISPATCHERTYPE;
        
        getLogger().info(
                String.format(
                "adding exception %s on controller %s",
                new Object[]{
                    target.getSimpleName(),
                    controller.getClassType().getSimpleName()}));
        
        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setView(view);
        thr.setRedirect( false );
        thr.setDispatcher( dispatcher );
        this.controller.setThrowsSafe(thr);
        return this;
    }

    /**
     * Define uma a��o a ser executado caso o controlador seja chamado sem que seja
     * determinada a execu��o de alguma a��o.
     * 
     * @param id Identifica��o da ��o.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder setDefaultAction(String id){

        id = StringUtil.adjust(id);
        
        if(this.controller.getAction(id) == null)
        	throw new MappingException("action not found: \"" + id + "\"");
        
        if( id != null ){
            getLogger().info(
                String.format(
                    "adding default action %s on controller %s",
                    new Object[]{id,controller.getClassType().getSimpleName()}));

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
     * @throws org.brandao.brutos.MappingException Lan�ado se o target for uma
     * cole��o.
     */
    public BeanBuilder buildMappingBean( String name, Class<?> target ){
        return this.buildMappingBean(name, null, target);
    }
    
    public BeanBuilder buildMappingBean( String name, 
            String parentBeanName, Class<?> target ){

        if( target == null )
            throw new MappingException("invalid target class");

        name = StringUtil.adjust(name);
        
        if( name == null || !name.matches("[a-zA-Z0-9_#]+"))
            throw new MappingException( "invalid bean name: \"" +
                    name + "\"");
            
        if( controller.getBean( name ) != null )
            throw new MappingException( "duplicate bean name: \"" + name + "\"" );

        getLogger().info(
            String.format(
                "adding bean %s[%s]",
                new Object[]{name,target.getSimpleName()}));
        
        Bean parentBean = 
                parentBeanName == null?
                null :
                this.controller.getBean(parentBeanName);
        
        Bean mappingBean;

        if( Map.class.isAssignableFrom(target) )
            mappingBean = new MapBean(controller, parentBean);
        else
        if( Collection.class.isAssignableFrom(target) )
            mappingBean = new CollectionBean(controller, parentBean);
        else
            mappingBean = new Bean(controller, parentBean);

        ConstructorBean constructor = mappingBean.getConstructor();
        
        constructor.setValidator(
                this.validatorFactory.getValidator(new Configuration()));
        
        mappingBean.setClassType( target );
        mappingBean.setName( name );
        controller.addBean( name, mappingBean );
        BeanBuilder mb = new BeanBuilder( mappingBean, controller, 
                this, validatorFactory, applicationContext );
        return mb;
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id ){
        return addAction( id, null, null, 
                !"true".equals(applicationContext.getConfiguration()
                .getProperty(BrutosConstants.VIEW_RESOLVER_AUTO)), 
                DispatcherType.FORWARD, null );
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String executor ){
        return addAction( id, null, null, 
                !"true".equals(applicationContext.getConfiguration()
                .getProperty(BrutosConstants.VIEW_RESOLVER_AUTO)), 
                DispatcherType.FORWARD, executor );
    }

    /**
     * Adiciona uma nova a��o ao controlador.
     *
     * @param id Identifica��o da a��o.
     * @param executor Nome do m�todo que processa a a��o.
     * @param view Vis�o. Se omitido, ser� usado a vis�o do controldor.
     * @return Contrutor da a��o.
     */
    public ActionBuilder addAction( String id, String executor, String view,
            boolean resolvedView ){
        return addAction( id, null, view, 
                resolvedView, DispatcherType.FORWARD, executor );
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
    public ActionBuilder addAction( String id, String resultId, String view, 
             boolean resolvedView, String executor ){
        return addAction( id, resultId, view, 
                resolvedView, DispatcherType.FORWARD, executor );
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
    public ActionBuilder addAction( String id, String resultId, String view, 
             boolean resolvedView, DispatcherType dispatcher, String executor ){
        return addAction( id, resultId, false, view, resolvedView, dispatcher, executor );
    }

    public ActionBuilder addAction( String id, String resultId, boolean resultRendered, String view, 
             boolean resolvedView, DispatcherType dispatcher, String executor ){
        return this.addAction(id, resultId, resultRendered, view, dispatcher, resolvedView, executor);
    }
    
    public ActionBuilder addAction( String id, String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
        
        id = StringUtil.adjust(id);
        
        if(StringUtil.isEmpty(id))
            throw new MappingException("action id cannot be empty");
        
        resultId = StringUtil.adjust(resultId);

        view = StringUtil.adjust(view);

        executor = StringUtil.adjust(executor);
        
        
        if(StringUtil.isEmpty(view) && StringUtil.isEmpty(executor))
            throw new MappingException("view must be informed in abstract actions: " + id);

        if( controller.getAction( id ) != null )
            throw new MappingException( "duplicate action: " + id );
     
        Action action = new Action();
        action.setCode(Action.getNextId());
        action.setController( controller );
        action.setResolvedView(resolvedView);
        action.setResultValidator(validatorFactory.getValidator(new Configuration()));
        action.setParametersValidator(validatorFactory.getValidator(new Configuration()));
        controller.addAction( id, action );

        ActionBuilder actionBuilder = 
                new ActionBuilder( action, controller, validatorFactory, 
                    this, this.applicationContext );

        actionBuilder
                .setName( id )
                .setDispatcherType(dispatcher)
                .setExecutor(executor)
                .setResult(resultId)
                .setResultRendered(resultRendered)
                .setView(view, resolvedView);
        
        getLogger().info(
                String.format(
                    "adding action %s on controller %s",
                    new Object[]{action.getId(), this.controller.getClassType().getSimpleName()}));
        
        return actionBuilder;
    }

    public ControllerBuilder addActionAlias(String id, ActionBuilder parent){
        
        id = StringUtil.adjust(id);
        
        if( StringUtil.isEmpty(id) )
            throw new MappingException("action id cannot be empty");
        
        if( controller.getAction( id ) != null )
            throw new MappingException( "duplicate action: " + id);
        
        Action action = parent.action;
        action.addAlias(id);
        controller.addAction( id, parent.action );
        
        return this;
    }
    
    public ControllerBuilder removeActionAlias(String id, ActionBuilder parent){
        
        id = StringUtil.adjust(id);
        
        if( StringUtil.isEmpty(id) )
            throw new MappingException("invalid alias");
        
        if(controller.getAction(id) == null || !controller.getAction(id).equals(parent.action))
            throw new MappingException( "invalid action " + id + ": " +
                controller.getClassType().getName() );

        
        controller.removeAction(id);
        return this;
    }
    
    /**
     * Define um novo interceptador do controlador. Se o interceptador for
     * definido como "default", ser� lan�ada uma exce��o. O interceptador dever�
     * ser previamente criado.
     * @param name Nome do interceptador. Se n�o encontrado, ser� lan�ada uma exce��o.
     * @return Construtor do interceptador.
     */
    public InterceptorBuilder addInterceptor(String name){

    	name = StringUtil.adjust(name);
    	
    	if(StringUtil.isEmpty(name))
    		throw new MappingException("interceptor name must be informed");
    	
    	if(!this.interceptorManager.containsInterceptor(name))
    		throw new MappingException("interceptor not found: " + name);
    	
        Interceptor parent = interceptorManager.getInterceptor( name );
        
        if(parent.isDefault())
    		throw new MappingException("interceptor already intercept this controller: " + name);
        	
        if(this.controller.isInterceptedBy(parent))
    		throw new MappingException("interceptor already intercept this controller: " + name);
        
        Interceptor it;
        
        if( parent instanceof InterceptorStack )
            it = new InterceptorStack( (InterceptorStack) parent );
        else
            it = new Interceptor( parent );

        
        it.setProperties( new HashMap<String, Object>() );
        
        Set<String> keys = parent.getProperties().keySet();
        Iterator<String> iKeys = keys.iterator();
        
        while( iKeys.hasNext() ){
            String key = iKeys.next();
            Object value = parent.getProperties().get(key);
            it.getProperties().put(key, value);
        }
        
        getLogger().info(
            String.format(
                "adding interceptor %s on controller %s",
                new Object[]{name, this.controller.getClassType().getSimpleName()}));
        
        controller.addInterceptor(it);
        
        return new InterceptorBuilder(it, interceptorManager);
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

    public PropertyBuilder addGenericProperty(String propertyName, String id, Class<?> classType){
    	return
			this.addProperty( propertyName, id, 
		            BrutosConstants.DEFAULT_SCOPETYPE, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
		            BrutosConstants.DEFAULT_TEMPORALPROPERTY, 
		            null, null, false, true, classType, null );
    }

    public PropertyBuilder addGenericProperty(String propertyName, String id){
    	return
			this.addProperty( propertyName, id, 
		            BrutosConstants.DEFAULT_SCOPETYPE, BrutosConstants.DEFAULT_ENUMERATIONTYPE, 
		            BrutosConstants.DEFAULT_TEMPORALPROPERTY, 
		            null, null, false, true, null, null );
    }
    
    public PropertyBuilder addProperty( String propertyName, String id, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            String mapping, Object value, boolean nullable, Object classType, Type type ){
    	return this.addProperty(propertyName, id, scope, enumProperty, temporalProperty, 
                mapping, value, nullable, false, classType, type );
    }
    
    public PropertyBuilder addProperty( String propertyName, String id, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            String mapping, Object value, boolean nullable, boolean generic, Object classType, Type type ){

        id                 = StringUtil.adjust(id);
        propertyName       = StringUtil.adjust(propertyName);
        temporalProperty   = StringUtil.adjust(temporalProperty);
        mapping            = StringUtil.adjust(mapping);
        BeanInstance bean  = this.controller.getBeanInstance();
        Object genericType = classType == null? bean.getGenericType(propertyName) : classType;
        Class<?> rawType   = TypeUtil.getRawType(genericType);
        
        if( propertyName == null )
            throw new MappingException("property name is required: " +
                    controller.getClassType().getName());

        if( controller.containsProperty(propertyName) )
            throw new MappingException("property already added: " +
                    controller.getClassType().getName() + "." + propertyName);
        
        if(scope == null)
        	throw new MappingException("invalid scope");
        
        PropertyController property = new PropertyController();
        property.setName( id );
        property.setScopeType( scope );
        property.setValidate( this.validatorFactory.getValidator( new Configuration() ) );
        property.setStaticValue( value );
        property.setNullable(nullable);
        property.setPropertyName(propertyName);
        property.setController(this.controller);
        
        try{
            property.setBeanProperty(bean.getProperty(propertyName));
        }
        catch(Throwable e){
            throw new MappingException( "no such property: " +
                controller.getClassType().getName() + "." + propertyName );
        }

        if(type == null){
        	if(nullable){
        		if(classType == null)
                	throw new MappingException("type must be informed");
        			
            	type = new NullType((Class<?>)classType);
        	}
        	else{
	            try{
	                type = 
		        		this.applicationContext.getTypeManager()
		                		.getType(genericType, enumProperty, temporalProperty );
	            }
	            catch( UnknownTypeException e ){
	                throw new MappingException(e);
	            }
        	}
        }
        
        /*
        if(!(type instanceof DateTimeType) && temporalProperty != null)
        	throw new MappingException("not a temporal type: " + propertyName);
        */
        
        property.setType(type);
        
        if(generic){
        	MetaBean metaBean = new MetaBean(controller);
        	metaBean.setClassType(rawType);
        	metaBean.setType(type);
        	property.setMetaBean(metaBean);
        }
        else
        if( mapping != null ){
            if( controller.getBean(mapping) != null )
                property.setMapping( controller.getBean( mapping ) );
            else
                throw new MappingException( "mapping not found: " + mapping );
        }
        else{
            Type definedType = property.getType();
            
            if(definedType.getClass() == ObjectType.class && rawType != Object.class)
            	throw new MappingException("unknown type: " + rawType.getSimpleName());
        }

        controller.addProperty( property );

        getLogger().info(
                String.format(
                    "adding property %s on controller %s",
                    new Object[]{propertyName, this.controller.getClassType().getSimpleName()}));
        
        return new PropertyBuilder(property, this);
    }

    /**
     * Constr�i uma propriedade do controlador.
     * @param propertyName Nome da propriedade.
     * @param clazz Tipo da propriedade.
     * @return Construtor da propriedade.
     */
    public BeanBuilder buildProperty( String propertyName, Class<?> clazz ){
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

    public Class<?> getClassType(){
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
    
    public ControllerBuilder setView(String view, boolean resolvedView){
        view = StringUtil.adjust(view);
        
        view = 
            resolvedView? 
                view : 
                applicationContext.
                        getViewResolver()
                        .getView(
                                this, 
                                null, 
                                null, 
                                view);
        
        controller.setView(view);
        
        return this;
    }

    public String getView(){
        return controller.getView();
    }
    
    public ControllerBuilder setActionId(String value){
    	
        if(StringUtil.isEmpty(value))
            value = BrutosConstants.DEFAULT_ACTION_ID;
        else
        if(!value.matches("[a-zA-Z0-9_#]+"))
        	throw new MappingException("invalid action id: " + value);
        
        controller.setActionId(value);
        
        getLogger().info(
                String.format(
                    "override the action id to %s on controller %s",
                    new Object[]{value, this.controller.getClassType().getSimpleName()}));
        
        return this;
    }
    
    public String getActionId(){
        return controller.getActionId();
    }
    
    public ControllerBuilder setDispatcherType(String value){
        value = StringUtil.adjust(value);
        
        if(StringUtil.isEmpty(value))
            throw new MappingException("invalid dispatcher type");
        
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
    
    public ControllerBuilder setActionType(ActionType actionType){
        this.controller.setActionType(actionType);
        return this;
    }
    
    public ActionType getActionType(){
        return this.controller.getActionType();
    }

    public PropertyBuilder getProperty(String name){
        PropertyController property = (PropertyController) controller.getProperty(name);
        return property == null? null : new PropertyBuilder(property, this);
    }

    public boolean isResolvedView(){
        return this.controller.isResolvedView();
    }
    
}
