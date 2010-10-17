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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;
import org.brandao.brutos.mapping.MappingBean;
import org.brandao.brutos.mapping.MethodForm;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.*;
/**
 *
 * @author Afonso Brandao
 */
public class ControllerBuilder {
    
    private Form controller;
    private ControllerManager controllerManager;
    private InterceptorManager interceptorManager;
    
    public ControllerBuilder( Form controller, ControllerManager controllerManager, InterceptorManager interceptorManager ) {
        this.controller = controller;
        this.controllerManager  = controllerManager;
        this.interceptorManager = interceptorManager;
    }

    public ControllerBuilder addAlias( String id ){

        id = id == null || id.replace( " ", "" ).length() == 0? null : id;

        if( id == null )
            throw new NullPointerException();

        controller.addAlias(id);
        controllerManager.addForm(id, controller);
        return this;
    }

    public ControllerBuilder addThrowable( Class target, String id ){
        return addThrowable( target, null, id, DispatcherType.FORWARD );
    }

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
    
    public ControllerBuilder setDefaultAction( String name ){

        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        
        if( name != null ){
            /*
             * Agora é permitido que exista uma acao sem um metodo
            if( !action.getMethods().containsKey( name ) )
                throw new BrutosException( "method " + name + " not found: " +
                        webFrame.getClassType().getName() );
            else
             */
                controller.setDefaultMethodName( name );
        }
        return this;
    }
    
    public BeanBuilder addMappingBean( String name, Class target ){

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
        
        if( Map.class.isAssignableFrom( target ) ||
            Collection.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " + target.getName() );

        MappingBean mappingBean = new MappingBean(controller);
        mappingBean.setClassType( target );
        mappingBean.setName( name );
        controller.getMappingBeans().put( name, mappingBean );
        BeanBuilder mb = new BeanBuilder( mappingBean, webFrame, this );
        return mb;
    }

    public ActionBuilder addMethod( String name ){
        return addAction( name, null, null, null );
    }
    
    public ActionBuilder addMethod( String name, String methodName ){
        return addAction( name, null, null, methodName );
    }

    public ActionBuilder addMethod( String name, String methodName, String viewResult ){
        return addAction( name, null, viewResult, methodName );
    }
    
    public ActionBuilder addMethod( String name, String resultId, String viewResult, String methodName ){
        return addAction( name, resultId, viewResult, false, methodName );
    }
    public ActionBuilder addAction( String name, String resultId, String viewResult, DispatcherType dispatcherType, String methodName ){
        
        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        resultId =
            resultId == null || resultId.replace( " ", "" ).length() == 0?
                null :
                resultId;

        viewResult =
            viewResult == null || viewResult.replace( " ", "" ).length() == 0?
                null :
                viewResult;

        methodName =
            methodName == null || methodName.replace( " ", "" ).length() == 0?
                null :
                methodName;
        
        if( controller.getMethods().containsKey( name ) )
            throw new BrutosException( "duplicate action " + name + ": " +
                controller.getClassType().getName() );
     
        MethodForm mp = new MethodForm();
        mp.setName( name );
        mp.setRedirect(false);
        mp.setDispatcherType(dispatcherType);
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
        controller.getMethods().put( name, mp );
        return new ActionBuilder( mp, controller );
    }
    
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
        
        controller.addInterceptor( it );
        return new InterceptorBuilder( it, interceptorManager );
    }


    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, EnumerationType enumProperty ){
        return addProperty( propertyName, id, scope, enumProperty, null, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, String temporalProperty ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope, Type type ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    public PropertyBuilder addProperty( String propertyName, String id, EnumerationType enumProperty ){
        return addProperty( propertyName, id, ScopeType.REQUEST, enumProperty, null, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id, ScopeType scope ){
        return addProperty( propertyName, id, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id, String temporalProperty ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id, Type type ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    public PropertyBuilder addPropertyMapping( String propertyName, String mapping ){
        return addProperty( propertyName, null, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    public PropertyBuilder addPropertyMapping( String propertyName, String id, String mapping ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    public PropertyBuilder addProperty( String propertyName, String id ){
        return addProperty( propertyName, id, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

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
        useBean.setValidate( BrutosContext
                    .getCurrentInstance().getValidatorProvider()
                        .getValidator( validatorConfig ) );

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

        return new PropertyBuilder( validatorConfig, controller, controllerManager, interceptorManager );
    }

    public Class<?> getClassType(){
        return controller.getClassType();
    }
    
}
