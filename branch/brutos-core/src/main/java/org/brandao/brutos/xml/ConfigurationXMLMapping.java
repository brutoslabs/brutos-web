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

import java.util.Map;
import org.brandao.brutos.Configuration;

/**
 *
 * @author Afonso Brandao
 */
public class ConfigurationXMLMapping {
    
    private Configuration configuration;
    
    public ConfigurationXMLMapping( Configuration configuration ) {
        this.setConfiguration(configuration);
    }
    
    public void setData( Map<String,Object> data ){
        if( data == null )
            return;
        
        for( String id: data.keySet() ){
            String value = (String) data.get( id );
            getConfiguration().put( id, value );
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
