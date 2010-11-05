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

package org.brandao.brutos.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletResponse;
import org.brandao.brutos.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Afonso Brandao
 */
public class WebResponseDispatcher implements ResponseDispatcher{

    Properties config;

    public WebResponseDispatcher(){
        this.config = new Properties();
    }

    public void process( Object object ){
        String contentType = config.getProperty( "contentType", "text/html" );

        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();

        ServletResponse response = requestInfo.getResponse();
        response.setContentType(contentType);
        
        try{
            PrintWriter out = response.getWriter();
            out.print( String.valueOf( object ) );
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    public void setProperty( String name, Object value ){
        this.config.setProperty(name, String.valueOf( value ) );
    }

    public OutputStream processStream() {
        String contentType = config.getProperty( "contentType", "text/html" );
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        ServletResponse response = requestInfo.getResponse();
        response.setContentType(contentType);

        try{
            return response.getOutputStream();
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
}
