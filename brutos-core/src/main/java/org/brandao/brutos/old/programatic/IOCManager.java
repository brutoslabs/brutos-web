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

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.ListInject;
import org.brandao.brutos.mapping.ioc.MapInject;
import org.brandao.brutos.mapping.ioc.PropertiesInject;
import org.brandao.brutos.mapping.ioc.SetInject;

/**
 *
 * @author Afonso Brandao
 */
public class IOCManager {
    
    private IOCProvider provider;
    
    public IOCManager() {
    }
    
    public IOCManager( IOCProvider provider ) {
        this.provider = provider;
    }
    
    public Bean addBean( String name, Class classType ){
        return addBean( name, classType, ScopeType.valueOf( "prototype" ), false, null );
    }
    
    public Bean addBean( String name, Class classType, ScopeType scope ){
        return addBean( name, classType, scope, false, null );
    }
    
    public Bean addSingleton( String name, String factory ){
        return addBean( name, null, null, true, factory );
    }
    
    public Bean addSingleton( String name, Class classType ){
        return addBean( name, classType, null, true, null );
    }
    
    public Bean addBean( String name, Class classType, ScopeType scope, boolean singleton, String factory ){
        
        if( provider == null )
            throw new BrutosException( "IOC/DI provider not configured!" );
        
        if( classType == null && factory == null )
            throw new BrutosException( "the class type is null factory is required!" );
        
        Bean oldBean = provider.getBeanDefinition( name );
        
        if( oldBean != null && oldBean.getInjectable().getTarget() != classType )
            throw new BeanExistException( name );
        else
        if( oldBean != null )
            provider.removeBeanDefinition( oldBean );
        
        Injectable injectable = new Injectable( classType, name, scope, singleton, factory );
        Bean bean = new Bean( injectable, this );
        provider.addBeanDefinition( bean );
        return bean;
    }
    
    public CollectionBean addCollection( String name, Class classType, Class valueType, String factory ){
        
        if( provider == null )
            throw new BrutosException( "IOC/DI provider not configured!" );
        
        Bean oldBean = provider.getBeanDefinition( name );
        
        if( oldBean != null && ((ComplexObjectInject)oldBean.getInjectable()).getType() != classType )
            throw new BeanExistException( name );
        else
        if( oldBean != null )
            provider.removeBeanDefinition( oldBean );
        
        ComplexObjectInject inject;
        
        if( java.util.List.class.isAssignableFrom( classType ) )
            inject = new ListInject( name, valueType, classType, factory );
        else
        if( java.util.Set.class.isAssignableFrom( classType ) )
            inject = new SetInject( name, valueType, classType, factory );
        else
            throw new BrutosException( "invalid type" );
        
        CollectionBean bean = new CollectionBean( inject, this );
        provider.addBeanDefinition( bean );
        return bean;
    }
    
    public MapBean addMap( String name, Class classType, Class keyType, Class valueType, String factory ){
        
        if( provider == null )
            throw new BrutosException( "IOC/DI provider not configured!" );
        
        Bean oldBean = provider.getBeanDefinition( name );
        
        if( oldBean != null && ((ComplexObjectInject)oldBean.getInjectable()).getType() != classType )
            throw new BeanExistException( name );
        else
        if( oldBean != null )
            provider.removeBeanDefinition( oldBean );
        
        ComplexObjectInject inject;
        
        if( java.util.Map.class.isAssignableFrom( classType ) && !java.util.Properties.class.isAssignableFrom( classType ) )
            inject = new MapInject( name, keyType, valueType, classType, factory );
        else
            throw new BrutosException( "invalid type" );
        
        MapBean bean = new MapBean( inject, this );
        provider.addBeanDefinition( bean );
        return bean;
    }
    
    public PropertiesBean addProperties( String name, Class classType ){
        return addProperties( name, classType, null );
    }
    
    public PropertiesBean addProperties( String name, Class classType, String factory ){
        if( provider == null )
            throw new BrutosException( "IOC/DI provider not configured!" );

        Bean oldBean = provider.getBeanDefinition( name );
        
        if( oldBean != null && ((ComplexObjectInject)oldBean.getInjectable()).getType() != classType )
            throw new BeanExistException( name );
        else
        if( oldBean != null )
            provider.removeBeanDefinition( oldBean );
        
        ComplexObjectInject inject;
        inject = new PropertiesInject( classType, name, factory );
        
        PropertiesBean bean = new PropertiesBean( inject, this );
        provider.addBeanDefinition( bean );
        return bean;
    }
    
    public Object getInstance( String name ){
         return provider.getBean( name );
    }
    
    public Bean getBean( String name ){
        return provider.getBeanDefinition( name );
    }

    public IOCProvider getProvider() {
        return provider;
    }

    public void setProvider(IOCProvider provider) {
        this.provider = provider;
    }
    
}
