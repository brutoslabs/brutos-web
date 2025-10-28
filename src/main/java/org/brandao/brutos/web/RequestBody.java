package org.brandao.brutos.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestBody {

	public static final String REQUEST_BODY_PROPERTY = "@RequestBody:CONTENT";
	
	private StreamCache streamCache;

	private String charset;
	
	public RequestBody(StreamCache streamCache, String charset) {
		this.streamCache = streamCache;
		this.charset = charset;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(streamCache.getInputStream(),charset))){
			char[] b = new char[2048];
			int l;
			while((l = br.read(b, 0, b.length)) != -1) {
				sb.append(b, 0, l);
			}
		}
		catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		return sb.toString();
	}
	
	public InputStream getInputStream() throws IOException {
		return streamCache.getInputStream();
	}
	
}
