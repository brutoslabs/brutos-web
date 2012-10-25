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
public class CollectionBean extends Bean{
    
    public CollectionBean( Injectable inject, IOCManager manager ) {
        super( inject, manager );
    }
    
    public CollectionBean addBean( String name ){
        ComplexObjectInject coi = (ComplexObjectInject)getInjectable();

        Bean refBean = manager.getBean( name );

        if( refBean == null )
            throw new BeanNotFoundException( name );

        coi.getProps().add( new Property( null, refBean.bean ) );
        return this;
    }
    
    public CollectionBean addValue( Object value ){
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
