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

package org.brandao.brutos.type;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.http.Download;

/**
 *
 * @author Afonso Brandao
 */
public class DownloadType implements Type{

    public DownloadType() {
    }

    public Object getValue( HttpServletRequest request, ServletContext context, Object value ) {
        if( value instanceof Download )
            return value;
        else
            return null;
    }

    public void setValue( HttpServletResponse response, ServletContext context, Object value ) throws IOException{
        if( value instanceof Download ){
            Download download = (Download)value;
            Map<String,String> header = download.getHeader();

            if( header != null ){
                Iterator<String> keys = header.keySet().iterator();
                while( keys.hasNext() ){
                    String key = keys.next();
                    response.addHeader( key, header.get( key ) );
                }
            }

            if( download.getContentLength() != -1 )
                response.setContentLength( (int)download.getContentLength() );

            download.write( response.getOutputStream() );
        }
    }
    
    public Class getClassType() {
        return Download.class;
    }
}
