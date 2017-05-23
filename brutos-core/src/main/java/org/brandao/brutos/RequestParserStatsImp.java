package org.brandao.brutos;


public class RequestParserStatsImp implements RequestParserStats{

    private RequestParserEvent event;
    private long statedTimeMilliseconds;

    public RequestParserStatsImp( RequestParserEvent event, long statedTimeMilliseconds ){
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
