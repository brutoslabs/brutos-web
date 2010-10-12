/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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
package org.brandao.brutos.xml;

import java.util.List;
import java.util.Map;
import org.brandao.brutos.ClassType;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brand�o
 */
public class TypeXMLMapping {

    private List<Map<String,String>> mappingTypes;

    public TypeXMLMapping(){
    }

    public void processData( List<Map<String,String>> data ) throws ClassNotFoundException{
        this.mappingTypes = data;
        if( data != null )
            processData0( data );
    }

    private void processData0( List<Map<String,String>> data ) throws ClassNotFoundException{
        for( Map<String,String> type: data ){
            createOrUpdateType( type.get( "class-type" ), type.get( "factory" ) );
        }
    }
    
    private void createOrUpdateType( String type, String factory ) throws ClassNotFoundException{
        Class typeClass    = ClassType.get( type );
        Class factoryClass = ClassType.get( factory );
        Types.setType(typeClass, factoryClass);
    }

}
