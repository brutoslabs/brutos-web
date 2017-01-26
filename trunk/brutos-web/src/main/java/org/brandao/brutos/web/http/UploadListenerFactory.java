

package org.brandao.brutos.web.http;


public interface UploadListenerFactory {

    public UploadListener getNewUploadListener( UploadEvent event );
    
}
