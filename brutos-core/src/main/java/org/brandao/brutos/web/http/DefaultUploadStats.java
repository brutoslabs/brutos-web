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

package org.brandao.brutos.web.http;

/**
 *
 * @author Brandao
 */
public class DefaultUploadStats implements UploadStats{

    private UploadEvent event;
    private long statedTimeMilliseconds;

    public DefaultUploadStats( UploadEvent event, long statedTimeMilliseconds ){
        this.event = event;
        this.statedTimeMilliseconds = statedTimeMilliseconds;
    }
    
    public double getPercentComplete() {
        double total = event.getContentLength();
        double current = event.getBytesRead();
        return (current/total)*100;
    }

    public long estimatedMillisecondsLeft() {

        long bytesProcessed = event.getBytesRead();
        long elapsedTimeInMilliseconds = this.getElapsedTimeInMilliseconds();
        long sizeTotal = event.getContentLength();

        double bytesPerMillisecond =
            bytesProcessed/
            (elapsedTimeInMilliseconds + 0.00001);

        return (long)
            ((sizeTotal - bytesProcessed)/(bytesPerMillisecond + 0.00001));
    }

    public long getElapsedTimeInMilliseconds() {
        return System.currentTimeMillis() - this.statedTimeMilliseconds;
    }

}
