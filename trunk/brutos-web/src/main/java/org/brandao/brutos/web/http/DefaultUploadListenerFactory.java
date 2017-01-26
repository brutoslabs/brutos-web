

package org.brandao.brutos.web.http;


public class DefaultUploadListenerFactory implements UploadListenerFactory{

    public UploadListener getNewUploadListener(UploadEvent event) {
        return new DefaultUploadListener( event );
    }

}
