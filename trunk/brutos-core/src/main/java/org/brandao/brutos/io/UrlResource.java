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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Brandao
 */
public class UrlResource extends AbstractResource{

    private String path;

    private URL url;
    
    public UrlResource( String path ) throws MalformedURLException{
        this.path = path;
        this.url = new URL( path );
    }
    
    public UrlResource( URL url ) throws MalformedURLException{
        this.url = url;
        this.path = url.toString();
    }

    public URL getURL() throws IOException {
        return new URL(this.path);
    }

    public Resource getRelativeResource(String relativePath) throws IOException {
        return new UrlResource(
                new URL(this.url, relativePath) );
    }

    public boolean exists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        con.setUseCaches(false);
        return con.getInputStream();
    }

    public boolean equals( Object e ){
        return e instanceof UrlResource?
            ((UrlResource)e).path.equals( this.path ) :
            false;
    }

    public String getName() {
        return this.path;
    }

}
