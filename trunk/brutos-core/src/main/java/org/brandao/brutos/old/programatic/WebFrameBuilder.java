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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.programatic.InterceptorBuilder;
import org.brandao.brutos.programatic.InterceptorManager;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
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
public class WebFrameBuilder {
    
    private Form webFrame;
    private WebFrameManager webFrameManager;
    private InterceptorManager interceptorManager;
    
    public WebFrameBuilder( Form webFrame, WebFrameManager webFrameManager, InterceptorManager interceptorManager ) {
        this.webFrame = webFrame;
        this.webFrameManager  = webFrameManager;
        this.interceptorManager = interceptorManager;
    }

    public WebFrameBuilder addAlias( String viewId ){

        viewId = viewId == null || viewId.replace( " ", "" ).length() == 0? null : viewId;

        if( viewId == null )
            throw new BrutosException( "not allowed alias: " + viewId );

        webFrame.addAlias(viewId);
        webFrameManager.addForm(viewId, webFrame);
        return this;
    }

    public WebFrameBuilder addThrowable( Class target, String parameterName ){
        return addThrowable( target, null, parameterName, false );
    }

    public WebFrameBuilder addThrowable( Class target, String viewId, String parameterName, boolean redirect ){

        viewId =
            viewId == null || viewId.replace( " ", "" ).length() == 0?
                null :
                viewId;
        
        parameterName =
            parameterName == null || parameterName.replace( " ", "" ).length() == 0?
                null :
                parameterName;

        if( target == null )
            throw new BrutosException( "target is required: " + webFrame.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(parameterName);
        thr.setTarget(target);
        thr.setUri(viewId);
        thr.setRedirect( redirect );
        webFrame.setThrowsSafe(thr);
        return this;
    }
    
    public WebFrameBuilder setDefaultMethodName( String name ){

        name =
            name == null || name.replace( " ", "" ).length() == 0?
                null :
                name;
        
        if( name != null ){
            if( !webFrame.getMethods().containsKey( name ) )
                throw new BrutosException( "method " + name + " not found: " +
                        webFrame.getClassType().getName() );
            else
                webFrame.setDefaultMethodName( name );
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
        
        webFrame.addInterceptor( new Interceptor[]{it} );
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

    public WebFrameBuilder addPropertyMapping( String propertyName, String mapping ){
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
                    .getCurrentApplicationContext().getValidatorProvider()
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
