/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2025 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import org.brandao.brutos.MutableRequestParserEvent;

public class MultipartFormDataParserLine {
	
	private byte[] data;
	
	private int start;

	private int end;
	
	private int maxLen;
	
	private int minLineLength;
	
	private String charset;
	
	public MultipartFormDataParserLine(byte[] data, int start, int end, int maxLen, int minLineLength, String charset) {
		this.data = data;
		this.start = start;
		this.end = end;
		this.maxLen = maxLen;
		this.charset = charset;
		this.minLineLength = minLineLength + 2;
		
		if(this.data.length < this.minLineLength * 4) {
			throw new IllegalArgumentException("data.length < minLineLength * 4");
		}
		
	}
	
	public boolean foundLine() {
		return !(end == maxLen && end == 0);			
	}
	
	public void adjustMinLengthLine(boolean force) {
		
		if(force || (data.length - start <= minLineLength)) {
			System.arraycopy(data, start, data, 0, data.length - start);
			maxLen = maxLen - start;
			end = end - start;
			start = 0;
		}
		
	}
	
	public boolean isNeededMoreDataToMakeLine() {
		return maxLen < data.length || end - start == 0;			
	}
	
	public boolean existNewLine() {
		
		for(int i=start;i<maxLen;i++) {
			if(data[i] == '\n') {
				end = i;
				return true;
			}
		}
	
		return false;
	}
	
	public void adjustToNextLine() {
		start = end == 0? 0 : end + 1;
		end = start;
	}

	public void resetLineBuffer() {
		start = 0;
		end = 0;
		maxLen = 0;
	}
	
	public int read(InputStream in, MutableRequestParserEvent event) throws IOException {
		int maxRead = data.length - maxLen;
		int read = in.read(data, maxLen, maxRead);
		
		if(read > 0) {
			maxLen += read;
	        event.addBytesRead(read);
		}
		
        return read;
	}
	
	public void write(RandomAccessFile raf) throws IOException {
		int len = 1 + (end - start);
		try {
			raf.write(data, start, len);
		}
		catch(Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public void adjustEndToMaxLengthData() {
		if(start == end) {
			end = maxLen - 1;
		}
	}
	
	public boolean startsWith(byte[] a) {
		
		int len = 1 + (end-start);
		
		if(len < a.length) {
			return false;
		}
		
		byte[] value = data;
		int startLine = start;
		
		for(int i=0;i<a.length;i++) {
			
			if(a[i] != value[startLine]) {
				return false;
			}
			
			startLine++;
		}
		
		return true;
	}
	
	public String toString(boolean withoutMarks) throws UnsupportedEncodingException {
		
		int len = 1 + (end - start);
		
		if(end - start == 0) {
			return new String();
		}
		
		int max = end;
		
		if(withoutMarks) {
			if(max >= 0 && data[max] == '\n') {
				max--;
			}
			
			if(max >= 0 && data[max] == '\r') {
				max--;
			}
		}
		
		len = 1 + (max - start);
		
		return len <= 0? null : (charset == null? new String(data, start, len) : new String(data, start, len, charset));
	}
		
}
