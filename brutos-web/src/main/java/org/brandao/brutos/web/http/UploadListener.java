

package org.brandao.brutos.web.http;

import java.io.Serializable;
import java.util.EventListener;


public interface UploadListener extends EventListener,Serializable{

    
    public UploadEvent getUploadEvent();

    
    public void uploadStarted();

    
    public void uploadFinished();

    public UploadStats getUploadStats();
    
}
