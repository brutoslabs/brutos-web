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
