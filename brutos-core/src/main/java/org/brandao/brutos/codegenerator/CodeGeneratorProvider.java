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


package org.brandao.brutos.codegenerator;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.proxy.ProxyFactory;

/**
 *
 * @author Brandao
 */
public abstract class CodeGeneratorProvider {

    public static CodeGeneratorProvider getProvider( Properties properties ){
        String providerName =
                properties
                    .getProperty(
                        "org.brandao.brutos.proxy.provider",
                        null);
        CodeGeneratorProvider provider = null;

        if( providerName == null )
            return null;

        try{
            Class providerClass = Class.forName( providerName, true,
                    Thread.currentThread().getContextClassLoader() );
            provider = (CodeGeneratorProvider)providerClass.newInstance();
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

        return provider;
    }

    public abstract ProxyFactory getProxyFactory( Class<?> classEntity )
            throws BrutosException;
    
}
