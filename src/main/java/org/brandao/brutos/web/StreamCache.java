package org.brandao.brutos.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCache {

	private byte[] cache;

	private int cacheOff;
	
	private int length;
	
	private File cacheFile;
	
	private OutputStream out;

	public StreamCache(int memoryLength) {
		this.cache = new byte[memoryLength];
		this.cacheOff = 0;
		this.length = 0;
	}
	
	public void append(int b) throws IOException {
		if(b < 0) {
			return;
		}
		
		append(new byte[] {(byte)b}, 0, 1);
	}
	
	public void append(byte[] b, int o, int l) throws IOException {
		
		l = o + l > b.length? b.length - o : l;
		
		if(l > cache.length) {
			appendCacheToFile();
			appendFile(b, o, l);
		}
		else {
			if(cacheOff + l > cache.length) {
				appendCacheToFile();
			}
			
			System.arraycopy(b, o, cache, cacheOff, l);
			cacheOff += l;
		}
		
		this.length += l;
		
	}
	
	private void appendCacheToFile() throws IOException {
		if(cacheOff > 0) {
			appendFile(cache, 0, cacheOff);
			cacheOff = 0;
		}
	}
	
	private void appendFile(byte[] b, int o, int l) throws IOException {
		if(cacheFile == null) {
			cacheFile = File.createTempFile("request", "tmp");
			cacheFile.deleteOnExit();
			out = new FileOutputStream(cacheFile);
		}
		
		out.write(b, o, l);
	}
	
	public InputStream getInputStream() throws IOException {
		if(this.length > this.cache.length) {
			appendCacheToFile();
			return new FileInputStream(cacheFile);
		}
		else {
			return new ByteArrayInputStream(cache, 0, length);
		}	
	}
}
