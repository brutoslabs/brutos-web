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

import java.util.Properties;
import org.brandao.brutos.BrutosException;


/**
 * 
 * @author Afonso Brandao
 */
public abstract class LoggerProvider {

    public static LoggerProvider getProvider( Properties config ){

        String loggerName = config.getProperty(
                                "org.brandao.brutos.logger.provider",
                                "org.brandao.brutos.logger.JavaLoggerProvider" );
        
        LoggerProvider logger       = null;

        try{
            Class<?> loggerClass = Class.forName( loggerName, true, Thread.currentThread().getContextClassLoader() );
            logger = (LoggerProvider)loggerClass.newInstance();
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }

        logger.configure( config );
        return logger;
    }

    public abstract void configure( Properties config );

    public abstract void destroy();

    public abstract Logger getLogger( String name );
    
}
