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

package org.brandao.brutos.programatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.CollectionMapping;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;
import org.brandao.brutos.mapping.MapMapping;
import org.brandao.brutos.mapping.MappingBean;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.*;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Classe usada para contruir um controlador.
 * Com essa classe é possível incluir ações, atribuir multiplas identificações
 * ao controlador, capturar exceções e processa-las, definir uma ação padrão, determinar
 * os interceptadores do controlador, criar mapeamentos de beans, configurar as
 * propriedades do controlador e obter a classe que representa o controlador.
 *
 * <p>A inclusão de uma ação é feita usando o método addAction(...). É possivel
 * definir sua identificação, o método responsável pelo seu processamento,
 * identificação do objeto resultante da ação. Além de ser possível definir a visão
 * e como seu fluxo será direcionado para a visão.</p>
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
 * <p>Para atribuir novas identifiçães ao controlador é usado o método addAlias(...).
 * No exemplo abaixo o controlador estará associado a duas identificações.
 * Essas Identificações são "/index.jbrs" e "/default.jbrs".</p>
 *
 * Ex:
 * <pre>
 * ...
 *   controllerBuilder.addAlias( "/index.jbrs" );
 *   controllerBuilder.addAlias( "/default.jbrs" );
 * ...
 * </pre>
 *
 * A captura de exceções que podem ser lançadas ao executar alguma ação é configurada
 * pelo método addThrowable(...). No exemplo asseguir, se for lançada uma exceção
 * LoginException, o fluxo será alterado para o controlador "/login.jbrs" e
 * possuirá a identificação loginException.
 *
 * Ex:
 * 
 * <pre>
 * controlerBuilder
 *   .addThrowable( LoginException.class, "/login.jbrs", "loginException", DispatcherType.FORWARD );
 * </pre>
 *
 * <p>A ação padrão é definida usando o método setDefaultAction(...). Essa ação é
 * executada se o controlador for chamado sem que seja determinada a execução de alguma ação.
 * A ação deve ser previamente definida.</p>
 *
 * Ex;
 * <pre>
 * controllerBuilder.addAction( "show", "result", "show.jsp",
 *     DispatcherType.INCLUDE, "showPerson" );
 * controlerBuilder.setDefaultAction( "show" );
 * </pre>
 *
 * <p>Para definir os interceptadores do controlador é usando o método addInterceptor(...).
 * Além de determinar os interceptadores é possível fazer uma configuração específica
 * para um determinado controlador.</p>
 *
 * Ex:
 * 
 * <pre>
 * controllerBuilder.addInterceptor( "loginInterceptor" )
 *  .addParameter( "excludeMethods", "index" );
 * </pre>
 *
 * <p>O mapeamento de beans permite injetar os dados obtidos da requisição, sessão,
 * contexto ou de qualquer outro escopo nas propriedades do bean. A configuração do
 * mapeamento é feita usando o método buildMappingBean(...).</p>
 *
 * Ex:
 * 
 * <pre>
 * BeanBuilder beanBuilder = controllerBuilder.buildMappingBean( "myBeanMapping", MyBean.class );
 * beanBuilder
 *   .addProperty( "name-client", "name", ScopeType.REQUEST )
 *   .addProperty( "country-client", "country", ScopeType.REQUEST )
 *   .addProperty( "currentUser", "currentUser", ScopeType.SESSION );
 * </pre>
 *
 * <p>As propriedades do controlador são configuradas pelo método addProperty(...).</p>
 *
 * Ex:
 * <pre>
 * controllerBuilder.addProperty( "user", "user", ScopeType.SESSION );
 * </pre>
 * 
 * @author Afonso Brandao
 */
public class ControllerBuilder {
    
    private Form controller;
    private ControllerManager controllerManager;
    private InterceptorManager interceptorManager;
    private ValidatorProvider validatorProvider;

    /**
     * Constroi um novo controlador.
     * 
     * @param controller 
     * @param controllerManager
     * @param interceptorManager
     * @param validatorProvider
     */
    public ControllerBuilder( Form controller, ControllerManager controllerManager, 
            InterceptorManager interceptorManager, ValidatorProvider validatorProvider ) {
        this.controller = controller;
        this.controllerManager  = controllerManager;
        this.interceptorManager = interceptorManager;
        this.validatorProvider  = validatorProvider;
    }

    /**
     * Atribui uma nova identificação ao controlador.
     * 
     * @param id Nova identificação.
     * @return Construtor do controlador.
     */
    public ControllerBuilder addAlias( String id ){

        id = id == null || id.replace( " ", "" ).length() == 0? null : id;

        if( id == null )
            throw new NullPointerException();

        controller.addAlias(id);
        controllerManager.addForm(id, controller);
        return this;
    }

    /**
     * Intercepta e atribui uma identificação a uma determinada exceção.
     *
     * @param target Exceção alvo.
     * @param id Identificação.
     * @return Contrutos do controlador.
     */
    public ControllerBuilder addThrowable( Class target, String id ){
        return addThrowable( target, null, id, DispatcherType.FORWARD );
    }

    /**
     * Intercepta e atribui uma identificação a uma determinada exceção. O
     * objeto resultante da exceção pode ser usando na visão.
     *
     * @param target Exceção alvo.
     * @param view Visão. Se omitido, será usado a visão do controlador.
     * @param id Identificação.
     * @param dispatcher Modo como será direcionado o fluxo para a visão.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder addThrowable( Class target, String view, String id, DispatcherType dispatcher ){

        view =
            view == null || view.replace( " ", "" ).length() == 0?
                null :
                view;
        
        id =
            id == null || id.replace( " ", "" ).length() == 0?
                null :
                id;

        if( target == null )
            throw new BrutosException( "target is required: " + controller.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setUri(view);
        thr.setRedirect( false );
        thr.setDispatcher( dispatcher );
        controller.setThrowsSafe(thr);
        return this;
    }

    /**
     * Define um ação a ser executado caso o controlador seja chamado sem que seja 
     * determinada a execução de alguma ação.
     * 
     * @param id Identificação da ção.
     * @return Contrutor do controlador.
     */
    public ControllerBuilder setDefaultAction( String id ){

        id =
            id == null || id.replace( " ", "" ).length() == 0?
                null :
                id;
        
        if( id != null ){
            /*
             * Agora é permitido que exista uma acao sem um metodo
            if( !action.getMethods().containsKey( name ) )
                throw new BrutosException( "method " + name + " not found: " +
                        webFrame.getClassType().getName() );
            else
             */
                controller.setDefaultMethodName( id );
        }
        return this;
    }

    /**
     * Gera o mapeamento de um bean. Esse mapeamento é usado para converter e injetar os
     * dado da requisição ou de qualquer outro escopo nas propriedades do bean.
     * 
     * @param name Nome do mapeamento.
     * @param target Alvo do mapeamento.
     * @return Construtor do mapeamento.
     * @throws java.lang.NullPointerException Lançado se target for igual a null.
     * @throws org.brandao.brutos.BrutosException Lançado se o target for uma
     * coleção ou um mapeamento.
     */
    public BeanBuilder buildMappingBean( String name, Class target ){

        if( target == null )
            throw new NullPointerException();

        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        
        if( name == null )
            throw new BrutosException( "name is required: " +
                    controller.getClassType().getName() );
            
        if( target == null )
            throw new BrutosException( "target is required: " +
                    controller.getClassType().getName() );
        
        if( controller.getMappingBeans().containsKey( name ) )
            throw new BrutosException( "duplicate mapping name " + name + " in the " + controller.getClassType().getName() );

        /*
        if( Map.class.isAssignableFrom( target ) ||
            Collection.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " + target.getName() );
        */
        
        MappingBean mappingBean;

        if( Map.class.isAssignableFrom(target) )
            mappingBean = new MapMapping(controller);
        else
        if( List.class.isAssignableFrom(target) )
            mappingBean = new CollectionMapping(controller);
        else
            mappingBean = new MappingBean(controller);
        
        mappingBean.setClassType( target );
        mappingBean.setName( name );
        controller.getMappingBeans().put( name, mappingBean );
        BeanBuilder mb = new BeanBuilder( mappingBean, controller, this, validatorProvider );
        return mb;
    }

    /**
     * Adiciona uma nova ação ao controlador.
     *
     * @param id Identificação da ação.
     * @return Contrutor da ação.
     */
    public ActionBuilder addAction( String id ){
        return addAction( id, null, null, DispatcherType.FORWARD, null );
    }

    /**
     * Adiciona uma nova ação ao controlador.
     *
     * @param id Identificação da ação.
     * @param executor Nome do método que processa a ação.
     * @return Contrutor da ação.
     */
    public ActionBuilder addAction( String id, String executor ){
        return addAction( id, null, null, DispatcherType.FORWARD, executor );
    }

    /**
     * Adiciona uma nova ação ao controlador.
     *
     * @param id Identificação da ação.
     * @param executor Nome do método que processa a ação.
     * @param view Visão. Se omitido, será usado a visão do controldor.
     * @return Contrutor da ação.
     */
    public ActionBuilder addAction( String id, String executor, String view ){
        return addAction( id, null, view, DispatcherType.FORWARD, executor );
    }
    
    /**
     * Adiciona uma nova ação ao controlador.
     *
     * @param id Identificação da ação.
     * @param executor Nome do método que processa a ação.
     * @param view Visão. Se omitido, será usado a visão do controldor.
     * @param resultId Identificação do resultado da ação. Essa identificação
     * serve para se obter o resultado na visão.
     * @return Contrutor da ação.
     */
    public ActionBuilder addAction( String id, String resultId, String view, String executor ){
        return addAction( id, resultId, view, DispatcherType.FORWARD, executor );
    }
    /**
     * Adiciona uma nova ação ao controlador.
     *
     * @param id Identificação da ação.
     * @param executor Nome do método que processa a ação.
     * @param view Visão. Se omitido, será usado a visão do controldor.
     * @param dispatcher Modo como será alterado o fluxo para a visão.
     * @param resultId Identificação do resultado da ação. Essa identificação
     * serve para se obter o resultado na visão.
     * @return Contrutor da ação.
     */
    public ActionBuilder addAction( String id, String resultId, String view, DispatcherType dispatcher, String executor ){
        
        id =
            id == null || id.replace( " ", "" ).length() == 0?
                null :
                id;
        resultId =
            resultId == null || resultId.replace( " ", "" ).length() == 0?
                null :
                resultId;

        view =
            view == null || view.replace( " ", "" ).length() == 0?
                null :
                view;

        executor =
            executor == null || executor.replace( " ", "" ).length() == 0?
                null :
                executor;
        
        if( controller.getMethods().containsKey( id ) )
            throw new BrutosException( "duplicate action " + id + ": " +
                controller.getClassType().getName() );
     
        MethodForm mp = new MethodForm();
        mp.setName( id );
        mp.setRedirect(false);
        mp.setDispatcherType(dispatcher);
        mp.setReturnPage(view);
        mp.setMethodName(executor);
        mp.setReturnIn( resultId == null? "result" : resultId );
        /*
        try{
            Class<?> classType = controller.getClassType();
            Method method = classType.getMethod( methodName, parametersType );
            mp.setParametersType( Arrays.asList( method.getParameterTypes() ) );

            Class<?> returnType = method.getReturnType();
            if( viewResult != null ){
                mp.setReturnPage( viewResult );
                mp.setReturnIn( resultId == null? "result" : resultId );
            }
            else
            if( returnType != void.class )
                mp.setReturnType( Types.getType( returnType ) );
            
            mp.setMethod( method );
            mp.setReturnClass( returnType );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
        */
        
        mp.setForm( controller );
        controller.getMethods().put( id, mp );
        return new ActionBuilder( mp, controller, validatorProvider );
    }

    /**
     * Adiciona um novo interceptador ao controlador. Se o interceptador for
     * definido como "default" será lançada uma exceção. O interceptador deverá
     * ser previamente criado.
     * @param name Nome do interceptador. Se não encontrado, será lançada uma exceção.
     * @return Construtor do interceptador.
     */
    public InterceptorBuilder addInterceptor( String name ){
        Interceptor parent = interceptorManager.getInterceptor( name );
        Interceptor it = null;
        
        if( parent instanceof InterceptorStack )
            it = new InterceptorStack( (InterceptorStack) parent );
        else
            it = new Interceptor( parent );
        
        it.setProperties( new HashMap() );
        
        Set<String> keys = parent.getProperties().keySet();
        
        for( String key: keys ){
            Object value = parent.getProperties().get( key );
            it.getProperties().put( /*parent.getName() + "." +*/ key, value );
        }
        
        controller.addInterceptor( new Interceptor[]{it} );
        return new InterceptorBuilder( it, interceptorManager );
    }


    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param scope Escopo do volor da propriedade.
     * @param enumProperty Usado na configuração de propriedades do tipo enum.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, EnumerationType enumProperty ){
        return addProperty( propertyName, id, scope, enumProperty, null, null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param scope Escopo do volor da propriedade.
     * @param temporalProperty Usado na configuração de datas.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, String temporalProperty ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param scope Escopo do volor da propriedade.
     * @param type Faz o processamento do valor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, Type type ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param enumProperty Usado na configuração de propriedades do tipo enum.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, EnumerationType enumProperty ){
        return addProperty( propertyName, id, ScopeType.REQUEST, enumProperty, null, null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param scope Escopo do volor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param temporalProperty Usado na configuração de datas.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, String temporalProperty ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param type Faz o processamento do valor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, Type type ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param mapping nome do mapeamento do valor da propriedade. Esse mapemanto
     * deve ser previamente criado com o método buildMappingBean(...).
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addPropertyMapping( String propertyName, String mapping ){
        return addProperty( propertyName, null, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param mapping nome do mapeamento do valor da propriedade. Esse mapemanto
     * deve ser previamente criado com o método buildMappingBean(...).
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addPropertyMapping( String propertyName, String id, String mapping ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

    /**
     * Configura uma propriedade do controlador.
     *
     * @param propertyName Nome da propriedade no controlador.
     * @param id Identificação da propriedade.
     * @param scope Escopo do volor da propriedade.
     * @param enumProperty Usado na configuração de propriedades do tipo enum.
     * @param mapping nome do mapeamento do valor da propriedade. Esse mapemanto
     * deve ser previamente criado com o método buildMappingBean(...).
     * @param temporalProperty Usado na configuração de datas.
     * @param type Faz o processamento do valor da propriedade.
     * @return Contrutor da propriedade.
     */
    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type ){

        id =
            id == null || id.replace( " ", "" ).length() == 0?
                null :
                id;
        propertyName =
            propertyName == null || propertyName.replace( " ", "" ).length() == 0?
                null :
                propertyName;

        temporalProperty =
            temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0?
                null :
                temporalProperty;

        mapping =
            mapping == null || mapping.replace( " ", "" ).length() == 0?
                null :
                mapping;

        if( id == null )
            throw new BrutosException( "name is required: " +
                    controller.getClassType().getName() );

        if( propertyName == null )
            throw new BrutosException( "property name is required: " +
                    controller.getClassType().getName() );

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();
        useBean.setNome( id );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );

        FieldForm fieldBean = new FieldForm();
        fieldBean.setBean( useBean );
        fieldBean.setName(propertyName);


        BeanInstance bean = new BeanInstance( null, controller.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                controller.getClassType().getName() + "." + propertyName );


        if( mapping != null ){
            if( controller.getMappingBeans().containsKey( mapping ) )
                useBean.setMapping( controller.getMappingBean( mapping ) );
            else
                throw new BrutosException( "mapping not found: " + mapping );

        }
        else
        if( type != null )
            useBean.setType( type );
        else{
            try{
                useBean.setType(
                        Types.getType(
                            bean.getGenericType(propertyName),
                            enumProperty,
                            temporalProperty ) );
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException(
                        String.format( "%s.%s : %s" ,
                            controller.getClassType().getName(),
                            propertyName,
                            e.getMessage() ) );
            }
        }

        if( controller.getFields().contains( fieldBean ) )
            throw new BrutosException( "property already defined: " +
                    controller.getClassType().getName() + "." + propertyName );

        controller.getFields().add( fieldBean );

        return new PropertyBuilder( validatorConfig );
    }

    /**
     * Obtém a classe do controlador.
     * @return Classe do controlador.
     */

    public Class getClassType(){
        return controller.getClassType();
    }
    
}
