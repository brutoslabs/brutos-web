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

package org.brandao.brutos.web.http.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.brandao.brutos.web.http.Download;

/**
 *
 * @author Afonso Brandao
 */
public class StreamDownload implements Download{

    public InputStream input;
    public Map<String,String> header;
    public long contentLength;

    public StreamDownload( InputStream input, Map<String,String> header, long contentLength ){
        this.input = input;
        this.header = header;
        this.contentLength = contentLength;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        try{
            byte[] data = new byte[2048];
            int l = 0;
            while( (l = input.read(data, 0, data.length) ) != -1 ){
                out.write( data, 0, l);
            }
        }
        finally{
            if( input != null )
                input.close();
        }
    }

}
