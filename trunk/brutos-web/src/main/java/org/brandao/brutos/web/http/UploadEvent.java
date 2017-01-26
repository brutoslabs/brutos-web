

package org.brandao.brutos.web.http;


public interface UploadEvent {

    
    public boolean isMultipart();

    
    public long getContentLength();

    
    public long getBytesRead();

}
