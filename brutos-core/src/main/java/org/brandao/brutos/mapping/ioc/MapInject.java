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

/**
 *
 * @author Afonso Brandao
 */
public class MapInject extends ComplexObjectInject{
    
    public MapInject( String name, Class keyType, Class valueType, Class type, String factory, Property[] props ) {
        //super( type == null? java.util.HashMap.class : type, name, props );
        super( name, keyType == null? String.class : keyType, valueType == null? String.class : valueType, java.util.Map.class, factory, props );
        setType( type == null? java.util.HashMap.class : type );
    }
    
}
