

package org.brandao.brutos.web.http;


public class DefaultUploadListener implements UploadListener{

    private UploadEvent event;
    private long start;

    public DefaultUploadListener( UploadEvent event ){
        this.event = event;
    }

    public UploadEvent getUploadEvent() {
        return this.event;
    }

    public void uploadStarted() {
        this.start = System.currentTimeMillis();
    }

    public void uploadFinished() {
    }

    public UploadStats getUploadStats() {
        return new DefaultUploadStats( this.event, this.start );
    }

}
