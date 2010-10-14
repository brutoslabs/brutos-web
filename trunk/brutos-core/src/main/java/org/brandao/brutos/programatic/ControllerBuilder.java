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

import java.lang.reflect.Method;
import java.util.Arrays;
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
                    webFrame.getClassType().getName() );
            
        if( target == null )
            throw new BrutosException( "target is required: " +
                    webFrame.getClassType().getName() );
        
        if( webFrame.getMappingBeans().containsKey( name ) )
            throw new BrutosException( "duplicate mapping name " + name + " in the " + webFrame.getClassType().getName() );
        
        if( Map.class.isAssignableFrom( target ) ||
            Collection.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " + target.getName() );

        MappingBean mappingBean = new MappingBean(webFrame);
        mappingBean.setClassType( target );
        mappingBean.setName( name );
        webFrame.getMappingBeans().put( name, mappingBean );
        BeanBuilder mb = new BeanBuilder( mappingBean, webFrame, this );
        return mb;
    }
    
    public MapBuilder addMappingMap( String name, Class target ){

        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;

        if( name == null )
            throw new BrutosException( "name is required: " +
                webFrame.getClassType().getName() );

        if( target == null )
            throw new BrutosException( "class type is required: " +
                webFrame.getClassType().getName() );

        if( !Map.class.isAssignableFrom( target ) )
            throw new BrutosException( "invalid class type: " + target.getName() );

        if( webFrame.getMappingBeans().containsKey( name ) )
            throw new BrutosException( "duplicate mapping name " + name + 
                ": " + webFrame.getClassType().getName() );

        MapMapping mappingBean = new MapMapping(webFrame);
        mappingBean.setCollectionType(target);
        mappingBean.setName( name );
        webFrame.getMappingBeans().put( name, mappingBean );
        MapBuilder mb = new MapBuilder( mappingBean, webFrame, this );
        return mb;
    }

    public CollectionBuilder addMappingCollection( String name, Class target ){
        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;

        if( name == null )
            throw new BrutosException( "name is required: " +
                webFrame.getClassType().getName() );

        if( target == null )
            throw new BrutosException( "class type is required: " +
                webFrame.getClassType().getName() );

        if( !Collection.class.isAssignableFrom( target ) )
            throw new BrutosException( "invalid class type: " + target.getName() );

        if( webFrame.getMappingBeans().containsKey( name ) )
            throw new BrutosException( "duplicate mapping name " + name + ": " +
                webFrame.getClassType().getName() );

        CollectionMapping mappingBean = new CollectionMapping(webFrame);
        mappingBean.setCollectionType(target);
        mappingBean.setName( name );
        webFrame.getMappingBeans().put( name, mappingBean );
        CollectionBuilder mb = new CollectionBuilder( mappingBean, webFrame, this );
        return mb;
    }

    public MethodBuilder addMethod( String name, String methodName, Class<?> ... parametersType ){
        return addMethod( name, null, null, methodName, parametersType );
    }

    public MethodBuilder addMethod( String name, String methodName, String returnPage, Class<?> ... parametersType ){
        return addMethod( name, null, returnPage, methodName, parametersType );
    }
    
    public MethodBuilder addMethod( String name, String returnIn, String returnPage, String methodName, Class<?> ... parametersType ){
        return addMethod( name, returnIn, returnPage, false, methodName, parametersType );
    }
    public MethodBuilder addMethod( String name, String returnIn, String returnPage, boolean redirect, String methodName, Class<?> ... parametersType ){
        
        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        returnIn =
            returnIn == null || returnIn.replace( " ", "" ).length() == 0?
                null :
                returnIn;

        returnPage =
            returnPage == null || returnPage.replace( " ", "" ).length() == 0?
                null :
                returnPage;

        methodName =
            methodName == null || methodName.replace( " ", "" ).length() == 0?
                null :
                methodName;
        
        if( webFrame.getMethods().containsKey( name ) )
            throw new BrutosException( "duplicate method " + name + ": " +
                webFrame.getClassType().getName() );
     
        MethodForm mp = new MethodForm();
        mp.setName( name );
        mp.setRedirect(redirect);
        
        try{
            Class<?> classType = webFrame.getClassType();
            Method method = classType.getMethod( methodName, parametersType );
            mp.setParametersType( Arrays.asList( method.getParameterTypes() ) );

            Class<?> returnType = method.getReturnType();
            if( returnPage != null ){
                mp.setReturnPage( returnPage );
                mp.setReturnIn( returnIn == null? "result" : returnIn );
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
        
        mp.setForm( webFrame );
        webFrame.getMethods().put( name, mp );
        return new MethodBuilder( mp, webFrame );
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
        
        webFrame.addInterceptor( it );
        return new InterceptorBuilder( it, interceptorManager );
    }


    public PropertyBuilder addProperty( String propertyName, String name, ScopeType scope, EnumerationType enumProperty ){
        return addProperty( propertyName, name, scope, enumProperty, null, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, ScopeType scope, String temporalProperty ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, ScopeType scope, Type type ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    public PropertyBuilder addProperty( String propertyName, String name, EnumerationType enumProperty ){
        return addProperty( propertyName, name, ScopeType.REQUEST, enumProperty, null, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, ScopeType scope ){
        return addProperty( propertyName, name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, String temporalProperty ){
        return addProperty( propertyName, name, ScopeType.REQUEST, EnumerationType.ORDINAL, temporalProperty, null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, Type type ){
        return addProperty( propertyName, name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type );
    }

    public ControllerBuilder addPropertyMapping( String propertyName, String mapping ){
        return addProperty( propertyName, null, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    public PropertyBuilder addPropertyMapping( String propertyName, String name, String mapping ){
        return addProperty( propertyName, name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name ){
        return addProperty( propertyName, name, ScopeType.REQUEST, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null );
    }

    public PropertyBuilder addProperty( String propertyName, String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type ){

        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
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

        if( name == null )
            throw new BrutosException( "name is required: " +
                    webFrame.getClassType().getName() );

        if( propertyName == null )
            throw new BrutosException( "property name is required: " +
                    webFrame.getClassType().getName() );

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();
        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( BrutosContext
                    .getCurrentInstance().getValidatorProvider()
                        .getValidator( validatorConfig ) );

        FieldForm fieldBean = new FieldForm();
        fieldBean.setBean( useBean );
        fieldBean.setName(propertyName);


        BeanInstance bean = new BeanInstance( null, webFrame.getClassType() );

        if( !bean.containProperty(propertyName) )
            throw new BrutosException( "no such property: " +
                webFrame.getClassType().getName() + "." + propertyName );


        if( mapping != null ){
            if( webFrame.getMappingBeans().containsKey( mapping ) )
                useBean.setMapping( webFrame.getMappingBean( mapping ) );
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
                            webFrame.getClassType().getName(),
                            propertyName,
                            e.getMessage() ) );
            }
        }

        if( webFrame.getFields().contains( fieldBean ) )
            throw new BrutosException( "property already defined: " +
                    webFrame.getClassType().getName() + "." + propertyName );

        webFrame.getFields().add( fieldBean );

        return new PropertyBuilder( validatorConfig, webFrame, webFrameManager, interceptorManager );
    }

    public Class<?> getClassType(){
        return webFrame.getClassType();
    }
    
}
