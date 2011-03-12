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

/**
 *
 * @author Brandao
 */
public abstract class AbstractResource implements Resource{

    public static final String FILE_URL_PREFIX = "file:/";
    
    protected String createRelativePath( String path, String relativePath ){

        path = cleanPath( path );
        String[] parts = path.split( "/+" );
        int length = parts.length -1;

        String newPath = "";
        for( int k=0;k<length;k++ ){
            newPath += parts[k] + "/";
        }

        newPath = newPath.substring(0, newPath.length()-1);
        
        newPath += this.cleanPath( relativePath );

        return newPath;
    }

    protected String cleanPath( String path ){
        path = path.replace( "\\" , "/");
        path = path.replaceAll( "/+" , "/");
        return path;
    }

    public boolean isOpen(){
        return false;
    }
    
}
