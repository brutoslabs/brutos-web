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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.Property;
import org.brandao.brutos.mapping.ioc.ValueInject;

/**
 *
 * @author Afonso Brandao
 */
public class MapBean extends Bean{
    
    public MapBean( Injectable inject, IOCManager manager ) {
        super( inject, manager );
    }
    
    public MapBean add( Object valueKey, String keyRef, Object value, String valueRef ){
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
    
    public MapBean addBean( Object key, String name ){
        return add( key, null, null, name );
    }
    
    public MapBean addValue( Object key, Object value ){
        return add( key, null, value, null );
    }
    
}
