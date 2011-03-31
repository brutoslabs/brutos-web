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

package org.brandao.brutos.ioc.picocontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.mapping.ioc.ConstructorInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.PropertyInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.ProviderAdapter;

/**
 *
 * @author Afonso Brandao
 */
public class ObjectComponentFactory extends ProviderAdapter {
    
    private Injectable inject;
    
    public ObjectComponentFactory( Injectable inject ) {
        
        this.inject = inject;
    }

    public Object getValueInject( ValueInject value ){
        return value.getValue();
    }
    
    private void registerInstance( Object instance ) throws PicoCompositionException {
        Scope scope = null;

        if( inject.isSingleton() )
            scope = PicoContainerScopes.get( "singleton" );
        else
            scope = PicoContainerScopes.get( inject.getScope().toString() );

        scope.put( inject.getName(), instance );
    }

    private Object getInstance() throws PicoCompositionException {
        Scope scope = null;

        if( inject.isSingleton() )
            scope = PicoContainerScopes.get( "singleton" );
        else
            scope = PicoContainerScopes.get( inject.getScope().toString() );

        return scope.get( inject.getName() );

    }
    
    private Object getInstanceByConstructor( PicoContainer container ) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        ConstructorInject cons = inject.getConstructor();
        List<Object> values    = new ArrayList();
        List<Class<?>> types   = new ArrayList();
        
        if( cons != null ){
            List<Injectable> args = cons.getArgs();
            for( Injectable arg: args ){
                if( arg instanceof ValueInject ){
                    values.add( getValueInject( (ValueInject)arg ) );
                    types.add( arg.getTarget() );
                }
                else{
                    values.add( container.getComponent( arg.getName() ) );
                    types.add( arg.getTarget() );
                }
            }
        }
        //Constructor insCons = inject.getTarget().getConstructor( types.toArray( new Class[]{} ) );
        ConstructorInject conInject = inject.getConstructor();
        if( conInject.isConstructor() ){
            Constructor insCons = inject.getConstructor().getContructor();
            return insCons.newInstance( values.toArray( new Object[]{} ) );
        }
        else{
            Object factory = 
                inject.getFactory() != null?
                    container.getComponent(inject.getFactory()) :
                    null;

            Method method = inject.getConstructor().getMethod( factory );
            return method.invoke( 
                    factory == null?
                        inject.getTarget() :
                        factory,
                    values.toArray( new Object[]{} ) );
        }
    }
    
    
    public Object provide(){
        return null;
    }
    
    public Object getComponentInstance(PicoContainer container, Type into) throws PicoCompositionException {
        try{
            Object instance = getInstance();
            
            if( instance != null )
                return instance;

            instance = getInstance( container );

            registerInstance( instance );
            
            return instance;
        }
        catch( PicoCompositionException e ){
            throw e;
        }
        catch( Exception e ){
            throw new PicoCompositionException( "error when instantiating " + inject.getName(), e );
        }
        
        
    }
    
    private Object getInstance( PicoContainer container ) throws Exception{
        Object instance = getInstanceByConstructor( container );

        //ConstructorInject cons = inject.getConstructor();
        List<PropertyInject> property = inject.getProperties();
        //List<Class<?>> valueArgs = new ArrayList();

        if( property != null ){

            for( PropertyInject prop: property ){
                Injectable arg = prop.getProperty();
                Object value = null;
                if( arg instanceof ValueInject )
                    value = getValueInject( (ValueInject)arg );
                else
                    value = container.getComponent( arg.getName() );

                Method m = prop.getMethod();
                m.invoke( instance, value );

            }
        }
        return instance;
    }
    
    public Object getComponentKey(){
        return inject.getName();
    }
}
