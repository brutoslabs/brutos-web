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
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Property;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.type.Types;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.ProviderAdapter;

/**
 *
 * @author Afonso Brandao
 */
public class ComplexComponentFactory extends ProviderAdapter {
    
    private ComplexObjectInject inject;
    
    public ComplexComponentFactory( ComplexObjectInject inject ) {
        this.inject = inject;
    }

    public Object getValueInject( ValueInject value ){
        return value.getValue();
        //org.brandao.brutos.type.Type type = Types.getType( value.getTarget() );
        //return type.getValue( null, null, value.getValue() );
    }
    
    private Object getInstanceByConstructor( PicoContainer container ) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{
        Constructor insCons = inject.getType().getConstructor( new Class[]{} );
        return insCons.newInstance( new Object[]{} );
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
            throw new PicoCompositionException( e );
        }
        
        
    }
    
    private Object getInstance( PicoContainer container ) throws Exception{
        Object instance = getInstanceByConstructor( container );
        
        if( inject.isCollection() )
            loadDataCollection( (Collection)instance, container );
        else
        if( inject.isMap() )
            loadDataMap( (Map)instance, container );
        
        return instance;
    }
    
    public Object getComponentKey(){
        return inject.getName();
    }

    public Object getValue( Class<?> tipo, Object value ){
        org.brandao.brutos.type.Type type = Types.getType( (Class)tipo );
        return type.getValue( value );
    }
    
    private void loadDataMap(Map map, PicoContainer container) {
        for( Property prop: inject.getProps() ){
            //Object key   = getValue( inject.getKeyType(), prop.getKey() );
            Object key   = prop.getKey() instanceof ValueInject? 
                        getValue( inject.getKeyType(), ((ValueInject)prop.getKey()).getValue() ) :
                        container.getComponent( prop.getKey().getName() );

            Object value = 
                    prop.getValue() instanceof ValueInject? 
                        getValue( inject.getValueType(), ((ValueInject)prop.getValue()).getValue() ) :
                        container.getComponent( prop.getValue().getName() );
            
            map.put( key, value );
        }
    }

    private void loadDataCollection(Collection collection, PicoContainer container) {
        for( Property prop: inject.getProps() ){
            Object value = 
                    prop.getValue() instanceof ValueInject? 
                        getValue( inject.getValueType(), ((ValueInject)prop.getValue()).getValue() ) :
                        container.getComponent( prop.getValue().getName() );
            
            collection.add( value );
        }
    }

}
