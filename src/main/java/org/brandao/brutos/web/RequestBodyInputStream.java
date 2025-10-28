package org.brandao.brutos.web;

import java.io.IOException;
import java.io.InputStream;

public class RequestBodyInputStream extends InputStream {

	private InputStream stream;
	
	private StreamCache streamCache;
	
	public RequestBodyInputStream(InputStream stream, StreamCache streamCache) {
		this.streamCache = streamCache;
		this.stream = stream;
	}
	
	@Override
    public int read(byte b[], int off, int len) throws IOException {
		int r = stream.read(b, off, len);
		streamCache.append(b, off, r);
		return r;
	}
	
	@Override
	public int read() throws IOException {
		int read = stream.read();
		streamCache.append(read);
		return read;
	}
	
	public void close() throws IOException {
		try {
			streamCache.flush();
		}
		finally {
			stream.close();
		}
	}
	
}
