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

import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.Property;
import org.brandao.brutos.mapping.ioc.ValueInject;

/**
 *
 * @author Afonso Brandao
 */
public class MapBean<K,T> extends Bean{
    
    public MapBean( Injectable inject, IOCManager manager ) {
        super( inject, manager );
    }
    
    public MapBean<K,T> add( K valueKey, String keyRef, T value, String valueRef ){
        ComplexObjectInject coi = (ComplexObjectInject)getInjectable();
        
        Injectable key;
        Injectable val;
        
        if( valueKey != null )
            key = new ValueInject( coi.getKeyType(), String.valueOf( valueKey ) );
        else{
            Bean bean = manager.getBean( keyRef );

            if( bean == null )
                throw new BeanNotFoundException( keyRef );
            
            key = bean.bean;
        }
        
        if( value != null )
            val = new ValueInject( coi.getValueType(), String.valueOf( value ) );
        else{
            Bean bean = manager.getBean( valueRef );

            if( bean == null )
                throw new BeanNotFoundException( valueRef );
            
            val = bean.bean;
        }
        
        coi.getProps().add( new Property( key, val ) );
        return this;
    }
    
    public MapBean<K,T> addBean( K key, String name ){
        return add( key, null, null, name );
    }
    
    public MapBean<K,T> addValue( K key, T value ){
        return add( key, null, value, null );
    }
    
}
