

package org.brandao.brutos.web.http.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.brandao.brutos.web.http.Download;


public class StreamDownload implements Download{

    public InputStream input;
    public Map<String,String> header;
    public long contentLength;

    public StreamDownload( InputStream input, Map<String,String> header, long contentLength ){
        this.input = input;
        this.header = header;
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return null;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public long getContentLength() {
        return contentLength;
    }

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
