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

package org.brandao.brutos;

import java.util.Properties;

/**
 * Classe usada na configuração de uma aplicação.
 * 
 * @author Afonso Brandao
 */
public class Configuration extends Properties{
    
    public Configuration() {
        super();
    }

    public Configuration( Properties props ){
        super( props );
    }
    
    public String getProperty(String key, String defaultValue){
        String value = super.getProperty( key, defaultValue );
        value = ( value == null )? System.getProperty( key ) : value;
        
        if( value != null )
            value = getVars( value );
        
        return value;
    }
    
    public String getProperty(String key){
        String value = super.getProperty( key );
        value = ( value == null )? System.getProperty( key ) : value;
        if( value != null )
            value = getVars( value );
        
        return value;
    }
    
    public String getProperty(String key, boolean insert ){
        String value = super.getProperty( key );
        value = ( value == null )? System.getProperty( key ) : value;
        if( value != null && insert )
            value = getVars( value );
        
        return value;
    }
    
    private String getVars( String value ){
        
        int index = value.indexOf( "${" );
        
        while( index != -1 ){
            int end = value.indexOf( "}" );
            
            if( end != -1 ){
                String key = value.substring( index + 2, end );
                String prop = getProperty( key, null );
                if( prop != null )
                    value = value.replace( "${" + key + "}", prop );
            }
            index = value.indexOf( "${" );
        }
        return value;
    }
    
}
