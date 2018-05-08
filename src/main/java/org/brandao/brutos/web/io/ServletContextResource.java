/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;
import org.brandao.brutos.io.AbstractResource;
import org.brandao.brutos.io.Resource;

/**
 * 
 * @author Brandao
 */
public class ServletContextResource extends AbstractResource{

    private ServletContext servletContext;
    
    private String path;
    
    public ServletContextResource(ServletContext servletContext, String path) {
        this.servletContext = servletContext;

        path = !path.startsWith("/")?
            "/" + path:
            path;
        
        this.path = this.cleanPath(path);
    }

    public URL getURL() throws IOException {
        URL url = this.servletContext.getResource(this.path);
        if (url == null) {
                throw new FileNotFoundException(
                                this.path + " URL does not exist");
        }
        return url;
    }

    public Resource getRelativeResource(String relativePath) throws IOException {
        return new ServletContextResource(
                servletContext,
                this.createRelativePath(this.path, relativePath) );
    }

    public boolean exists() {
        try {
            URL url = this.servletContext.getResource(this.path);
            return (url != null);
        }
        catch (MalformedURLException ex) {
            return false;
        }
    }

    public InputStream getInputStream() throws IOException {
        InputStream is = this.servletContext.getResourceAsStream(this.path);
        
        if (is == null)
            throw new FileNotFoundException(
                    "Could not open " + this.path );

        return is;
    }

    public boolean equals( Object e ){
        return e instanceof ServletContextResource?
            ((ServletContextResource)e).path.equals( this.path ) :
            false;
    }

    public String getName() {
        return this.path;
    }

}
