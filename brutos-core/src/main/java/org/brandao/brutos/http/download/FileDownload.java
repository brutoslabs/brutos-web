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

package org.brandao.brutos.http.download;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.brandao.brutos.http.Download;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 *
 * @author Afonso Brandao
 */
public class FileDownload implements Download{

    private File file;
    private String contentType;
    private Map<String,String> header;
    
    public FileDownload( File file ){
        this( file, null, null );
        this.header = new HashMap<String,String>();
        this.header.put( "Content-Disposition" ,
                    "attachment;filename=" + file.getName() + ";" );
    }

    public FileDownload( File file, String contentType, Map<String,String> header ){
        this.file        = file;
        this.contentType = contentType;
        this.header      = header;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public Map<String, String> getHeader() {
        return this.header;
    }

    @Override
    public long getContentLength() {
        return file.length();
    }

    @Override
    public void write(OutputStream out) throws IOException {
        FileInputStream fin = null;
        try{
            fin = new FileInputStream( file );
            byte[] data = new byte[2048];
            int l = 0;
            while( (l = fin.read(data, 0, data.length) ) != -1 ){
                out.write( data, 0, l);
            }
        }
        finally{
            if( fin != null )
                fin.close();
        }
    }

}
