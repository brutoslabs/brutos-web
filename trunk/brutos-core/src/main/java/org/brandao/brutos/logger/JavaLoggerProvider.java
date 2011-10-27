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

package org.brandao.brutos.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Afonso Brandao
 */
public class JavaLoggerProvider extends LoggerProvider{

    private Map cacheLoggers;

    public JavaLoggerProvider(){
        this.cacheLoggers = new HashMap();
    }

    public void configure( Properties config ){
    }

    public Logger getLogger( Class clazz ){
        return getLogger( clazz.getSimpleName() );
    }

    public Logger getLogger( String name ){
        if( !cacheLoggers.containsKey(name) ){
            java.util.logging.Logger jLogger =
                java.util.logging.Logger.getLogger( name );

            Logger logger = new JavaLogger( jLogger );
            cacheLoggers.put(name, logger );
            return logger;
        }
        else
            return (Logger) cacheLoggers.get(name);
    }

    public void destroy() {
        cacheLoggers.clear();
    }
    
}
