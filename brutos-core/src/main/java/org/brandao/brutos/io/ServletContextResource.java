/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;

/**
 *
 * @author Brandao
 */
public class ServletContextResource extends AbstractResource{

    private ServletContext servletContext;
    
    private String path;
    
    public ServletContextResource(ServletContext servletContext, String path) {
        this.servletContext = servletContext;

        path = path.startsWith("/")?
            path.substring(1,path.length()):
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

}
