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

package org.brandao.brutos.mapping.ioc;

import java.util.List;

/**
 *
 * @author Afonso Brandao
 */
public class PropertiesInject extends ComplexObjectInject{
    
    private List props;
    
    public PropertiesInject( Class type, String name, String factory, Property[] props ) {
        //super( type, name, ScopeType.REQUEST, false );
        super( name, String.class, String.class, java.util.Properties.class, factory, props );
        setSingleton( true );
        setType( type == null? java.util.Properties.class : type );
        //setConstructor( new ConstructorInject( null, this ) );
    }
    
    public List getProps() {
        return props;
    }

    public void setProps(List props) {
        this.props = props;
    }
    
}
