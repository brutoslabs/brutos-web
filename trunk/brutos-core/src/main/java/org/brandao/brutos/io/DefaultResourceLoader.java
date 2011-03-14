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

package org.brandao.brutos.io;

import java.net.MalformedURLException;
import java.net.URL;
import org.brandao.brutos.BrutosException;

/**
 *
 * @author Brandao
 */
public class DefaultResourceLoader implements ResourceLoader{

    private ClassLoader classLoader;

    public DefaultResourceLoader(){
        this( Thread.currentThread().getContextClassLoader() );
    }

    public DefaultResourceLoader( ClassLoader classLoader ){
        this.classLoader = classLoader;
    }

    public Resource getResource(String path) {
        if (path.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(
                    getClassloader(),
                    path.substring(CLASSPATH_URL_PREFIX.length()));
         }
         else {
             try {
                URL url = new URL(path);
                 return new UrlResource(url);
             }
             catch (MalformedURLException e) {
                return getContextResource( path );
             }
         }
    }

    protected Resource getContextResource( String path ){
        throw new BrutosException( "not found: " + path );
    }

    public ClassLoader getClassloader() {
        return this.classLoader;
    }

}
