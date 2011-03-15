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

import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.Property;
import org.brandao.brutos.mapping.ioc.ValueInject;

/**
 *
 * @author Afonso Brandao
 */
public class CollectionBean<T> extends Bean{
    
    public CollectionBean( Injectable inject, IOCManager manager ) {
        super( inject, manager );
    }
    
    public CollectionBean<T> addBean( String name ){
        ComplexObjectInject coi = (ComplexObjectInject)getInjectable();

        Bean refBean = manager.getBean( name );

        if( refBean == null )
            throw new BeanNotFoundException( name );

        coi.getProps().add( new Property( null, refBean.bean ) );
        return this;
    }
    
    public CollectionBean<T> addValue( T value ){
        ComplexObjectInject coi = (ComplexObjectInject)getInjectable();
        coi.getProps().add( 
                new Property( 
                    null, 
                    new ValueInject( 
                        coi.getValueType(), 
                        String.valueOf( value ) 
                    ) 
                ) 
         );
        return this;
    }
    
}
