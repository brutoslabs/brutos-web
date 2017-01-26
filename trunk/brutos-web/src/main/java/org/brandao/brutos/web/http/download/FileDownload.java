

package org.brandao.brutos.web.http.download;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.brandao.brutos.web.http.Download;
import java.io.FileInputStream;
import java.util.HashMap;


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

    public String getContentType() {
        return this.contentType;
    }

    public Map<String, String> getHeader() {
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
