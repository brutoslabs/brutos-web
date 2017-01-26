

package org.brandao.brutos.web.http;


public interface UploadStats {

    
    public double getPercentComplete();

    
    public long estimatedMillisecondsLeft();

    
    public long getElapsedTimeInMilliseconds();

}
