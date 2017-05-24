package org.brandao.brutos;

public class MutableRequestParserEventImp implements MutableRequestParserEvent{

	private long contentLength;
	
	private long bytesRead;
	
    private long statedTimeMilliseconds;

    private MvcRequest request;
    
    private MvcResponse response;
    
	public long getContentLength() {
		return this.contentLength;
	}

	public long getBytesRead() {
		return this.bytesRead;
	}

	public void setContentLength(long value) {
		this.contentLength = value;
	}

	public void setBytesRead(long value) {
		this.bytesRead = value;
		
	}

	public void addBytesRead(long value) {
		this.bytesRead += value;
	}

    public double getPercentComplete() {
        double total = getContentLength();
        double current = getBytesRead();
        return (current/total)*100;
    }

    public long estimatedMillisecondsLeft() {

        long bytesProcessed = getBytesRead();
        long elapsedTimeInMilliseconds = this.getElapsedTimeInMilliseconds();
        long sizeTotal = getContentLength();

        double bytesPerMillisecond =
            bytesProcessed/
            (elapsedTimeInMilliseconds + 0.00001);

        return (long)
            ((sizeTotal - bytesProcessed)/(bytesPerMillisecond + 0.00001));
    }

    public long getElapsedTimeInMilliseconds() {
        return System.currentTimeMillis() - this.statedTimeMilliseconds;
    }

	public void setStart(long value) {
		this.statedTimeMilliseconds = value;
	}

	public MvcRequest getRequest() {
		return this.request;
	}

	public MvcResponse getResponse() {
		return this.response;
	}

	public void setRequest(MvcRequest value) {
		this.request = value;
	}

	public void setResponse(MvcResponse value) {
		this.response = value;
	}

}
