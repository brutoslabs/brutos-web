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
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.Download;

/**
 *
 * @author Afonso Brandao
 */
public class DownloadType implements Type{

    public DownloadType() {
    }
    
    public Class getClassType() {
        return Download.class;
    }

    public Object getValue(Object value) {
        if( value instanceof Download )
            return value;
        else
            return null;
    }

    public void setValue(Object value) throws IOException {
        if( value instanceof Download ){
            ConfigurableApplicationContext app =
                    (ConfigurableApplicationContext)Invoker.getApplicationContext();
            MvcResponse response = app.getMvcResponse();

            Download download = (Download)value;
            Map info = download.getHeader();
            if( info != null ){
                Iterator<String> keys = download.getHeader().keySet().iterator();
                while( keys.hasNext() ){
                    String key = keys.next();
                    info.put( key, info.get( key ) );
                }
            }

            if( download.getContentLength() != -1 )
                response.setLength( (int)download.getContentLength() );

            download.write( response.processStream() );
        }
    }
}
