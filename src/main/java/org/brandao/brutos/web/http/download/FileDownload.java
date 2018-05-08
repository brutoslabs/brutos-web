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

package org.brandao.brutos.web.http.download;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.brandao.brutos.web.http.Download;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * 
 * @author Brandao
 */
public class FileDownload implements Download{

    private File file;
    private String contentType;
    private Map<String,Object> header;
    
    public FileDownload( File file ){
        this( file, null, null );
        this.header = new HashMap<String,Object>();
        this.header.put( "Content-Disposition" ,
                    "attachment;filename=" + file.getName() + ";" );
    }

    public FileDownload( File file, String contentType, Map<String,Object> header ){
        this.file        = file;
        this.contentType = contentType;
        this.header      = header;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Map<String, Object> getHeader() {
        return this.header;
    }

    public long getContentLength() {
        return file.length();
    }

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
