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


package org.brandao.brutos.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
//import javax.persistence.EntityManager;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.ThrowableSafeData;
import org.brandao.brutos.programatic.InterceptorBuilder;
import org.brandao.brutos.programatic.BeanBuilder;
import org.brandao.brutos.programatic.MethodBuilder;
import org.brandao.brutos.programatic.WebFrameBuilder;
import org.brandao.brutos.programatic.WebFrameManager;
import org.brandao.brutos.type.Type;

/**
 *
 * @author Afonso Brandao
 */
@Deprecated
public class WebFrameAnnotationMapping {
    
    private WebFrameManager webFrameManager;
    
    public WebFrameAnnotationMapping( WebFrameManager webFrameManager ) {
        this.webFrameManager = webFrameManager;
    }
    
    public void setClassBeans( List<Class> list ){
        for( Object cla: list ){
            if( ((Class)cla).isAnnotationPresent( Frame.class ) )
                mapping0( (Class)cla );
        }
    }
    
    private void mapping0( Class<?> classe ){
        if( classe.isAnnotationPresent( org.brandao.brutos.annotation.Frame.class ) ){
            throw new UnsupportedOperationException(
                    "@Form deprecated use @WebFrame in " + classe.getName() );
        }
        else
        if( classe.isAnnotationPresent( Controller.class ) ){
            /*
            WebFrame aFrame = classe.getAnnotation( WebFrame.class );
            webFrameManager
                .addWebFrame( 
                    aFrame.uri()[0],
                    aFrame.page(), 
                    aFrame.name(),
                    (Class)classe, 
                    ScopeType.REQUEST,
                    aFrame.methodParameterName() 
                );
            */
            
            mappingUseBeans();
            fieldForm();
            mappingMethods();
            interceptedBy();
            //webFrameManager.getCurrent().setDefaultMethodName( aFrame.defaultMethodName() );
        }
    }
    
    private void interceptedBy(){
        WebFrameBuilder wfb = webFrameManager.getCurrent();
        Class classType = wfb.getClassType();
        
        if( classType.isAnnotationPresent( InterceptedBy.class ) ){
            InterceptedBy ib = 
                    (InterceptedBy) classType.getAnnotation( InterceptedBy.class );
            
            Intercept[] intercepts = ib.value();
            
            for( Intercept intercept: intercepts )
                addInterceptor( intercept );
        }
    }
    
    private void addInterceptor( Intercept intercept ){
        WebFrameBuilder wfb = webFrameManager.getCurrent();
        Param[] params = intercept.params();
        
        InterceptorBuilder ib = wfb.addInterceptor( intercept.name() );
        
        for( Param param: params )
            ib.addParameter( param.name(), param.value() );
    }
    
    private ThrowableSafeData getThrowableSafe( ThrowableSafe an ){
        if( an != null ){
            ThrowableSafeData tsd = new ThrowableSafeData();
            //tsd.setJspPage( an.jsp() );
            tsd.setParameterName( an.name() );
            //tsd.setTargets( Arrays.asList( an.target() ) );
            
            if( tsd.getParameterName().equals( "" ) )
                throw new MappingException( "Invalid ThrowableSafe.name(): " + tsd.getParameterName() );
            else
                return tsd;
        }
        else
            return null;
    }
    
    private void mappingMethods(){
        try{
            WebFrameBuilder wfb = webFrameManager.getCurrent();
            
            Class<?> classe = wfb.getClassType();
            Method[] methods = classe.getDeclaredMethods();
            for( Method m: methods ){
                if( m.isAnnotationPresent( MethodMapping.class ) ){
                    throw new java.lang.UnsupportedOperationException("change @MethodMapping by @Method in the " + wfb.getClassType().getName() );
                }
                else
                if( m.isAnnotationPresent( org.brandao.brutos.annotation.Action.class ) ){
                    org.brandao.brutos.annotation.Action mb =
                        m.getAnnotation( org.brandao.brutos.annotation.Action.class );
                    
                    MethodBuilder mBuilder = 
                        wfb.addMethod( 
                            mb.id(),
                            null/*mb.returnIn()*/,
                            null/*mb.returnPage()*/ ,
                            m.getName(),
                            m.getParameterTypes()
                        );
                    
                    if( m.getParameterTypes().length != mb.parameters().length )
                        throw new MappingException( "method.parameters.length != @Method.parameters.length" );
                    /*
                    for( UseBean useBean: mb.parameters() ){
                        mBuilder.addParameter( 
                            useBean.name(), 
                            useBean.scope(), 
                            useBean.enumProperty(),
                            useBean.temporalProperty(),
                            useBean.mappingName(),
                            useBean.factory() == Type.class? null : useBean.factory().newInstance()
                        );
                    }
                     */
                    //mf.setThrowsSafe( getThrowableSafe( m.getAnnotation( ThrowableSafe.class ) ) );
                }
            }
        }
        catch( MappingException e ){
            throw e;
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
        
    }
    
    private void mappingUseBeans(){
        Class<?> classe = webFrameManager.getCurrent().getClassType();
        
        if( classe.isAnnotationPresent( MappingUseBeans.class ) ){
            for( MappingUseBean m: classe.getAnnotation( MappingUseBeans.class ).value() )
                mappingUseBean( m );
        }
        else
        if( classe.isAnnotationPresent( MappingUseBean.class ) ){
            mappingUseBean( classe.getAnnotation( MappingUseBean.class ) );
        }
    }
    
    private void fieldForm(){
        try{
            WebFrameBuilder wfb = webFrameManager.getCurrent();
            
            Class<?> classe = wfb.getClassType();
            Field[] fields = classe.getDeclaredFields();
            for( Field f: fields ){
                if( f.isAnnotationPresent( UseBean.class ) ){
                    UseBean useBean = f.getAnnotation( UseBean.class );
                    /*
                    wfb.addProperty( 
                            f.getName(), 
                            useBean.name(),
                            useBean.scope(),
                            useBean.enumProperty(),
                            useBean.temporalProperty(),
                            useBean.mappingName(),
                            useBean.factory() == Type.class? null : useBean.factory().newInstance()
                    );
                     */
                }
                else
                if( f.isAnnotationPresent( Request.class ) )
                    throw new java.lang.UnsupportedOperationException("removed");
                else
                if( f.isAnnotationPresent( Response.class ) )
                    throw new java.lang.UnsupportedOperationException("removed");
            }
            
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }
    
    private void processPersistenceContext( Field f ){
        Class<?> classType = null;
        try{
            classType = f.getType();
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
        
//        if( classType != EntityManager.class )
//            throw new MappingException( "Invalid field type. Expected javax.persistence.EntityManager" );
        
    }
    
    private void mappingUseBean( MappingUseBean mapping ){
        WebFrameBuilder wfb = webFrameManager.getCurrent();
        BeanBuilder mbb = wfb.addMappingBean( mapping.name(), mapping.target() );
        mappingFieldBean( mbb, mapping.properties() );
    }
    
    private void mappingFieldBean( BeanBuilder mbb, PropertyBean[] props ){
        try{
            for( PropertyBean prop: props ){
                String name    = prop.name();
                String atrName = prop.propertyName();
                /*
                mbb.addProperty( 
                        name, 
                        atrName, 
                        prop.enumProperty(), 
                        prop.temporalProperty(),
                        prop.mappingName(),
                        prop.factory() == Type.class? null : prop.factory().newInstance()
                );
                 */
            }
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }
    
    private Method getMethodAction( String methodName, Class<?> classe ){
        try{
            Method method = classe.getDeclaredMethod( methodName );
            return method;
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }
    
}
