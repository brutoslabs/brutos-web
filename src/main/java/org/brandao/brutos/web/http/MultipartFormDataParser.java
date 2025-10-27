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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.mapping.StringUtil;

public class MultipartFormDataParser {

	private static final String PREFIX_TMP_FILE_NAME	= "multpart";
	
	private static final String SUFFIX_TMP_FILE_NAME	= ".tmp";

	private static final byte[] boundaryMark = new byte[] {'-','-'};
	
	private Line line;
	
	private InputStream in;
	
	private String charset;
	
	private byte[] boundary;

	private byte[] boundaryStart;
	
	private byte[] boundaryEnd;
	
	private MutableRequestParserEvent event;
	
	private long maxLength;
	
	public MultipartFormDataParser(InputStream in, String charset, String boundary, long maxLength, MutableRequestParserEvent event) {
		this.boundary = boundary.getBytes();
		this.in = in;
		this.charset = charset;
		this.event = event;
		this.maxLength = maxLength;
        this.line =  new Line(new byte[8192], 0, 0, 0);
        
		this.boundaryStart = new byte[this.boundary.length + 2];
		System.arraycopy(boundaryMark,  0, this.boundaryStart,                   0, boundaryMark.length);
		System.arraycopy(this.boundary, 0, this.boundaryStart, boundaryMark.length, this.boundary.length);
        
		this.boundaryEnd = Arrays.copyOf(this.boundaryStart, this.boundaryStart.length + boundaryMark.length);
		System.arraycopy(boundaryMark, 0, this.boundaryEnd, this.boundaryStart.length, boundaryMark.length);
	}
	
    public boolean hasMoreElements() throws IOException{
    	Line line = event.getBytesRead() == 0? readLineBytes() : this.line;
    	return line != null && (!startsWith(line, boundaryEnd) && startsWith(line, boundaryStart));
    }
	
	public Field nextElement() throws IOException {
		Field field = new Field();
		loadHeader(field);
		loadData(field);
		return field;
	}
	
	private void loadHeader(Field field) throws IOException {
		
		String line;
		
		while((line = readLine()) != null) {
			FieldHeader header = parseFieldHeader(line);
			field.getHeader().put(header.getName().toLowerCase(), header);
		}
		
	}

	private void loadData(Field field) throws IOException {
		
		FieldHeader contentDisposition = field.getHeader().get("content-disposition");
		
		if(contentDisposition.getParams().get("filename") == null) {
			loadValue(field);
		}
		else {
			loadFile(field);
		}
		
	}

	private void loadValue(Field field) throws IOException {
		
		Line line;
		StringBuilder builder = new StringBuilder();
		
		while( (line = readLineBytes()) != null && !startsWith(line, boundaryStart) ) {
			
			if(builder.length() > 0) {
				builder.append(System.getProperty("line.separator"));
			}
			
			builder.append(toString(line));
			
		}
		
		field.setValue(builder.toString());
	}

	private void loadFile(Field field) throws IOException {
		
		Line line = null;
        UploadedFile f = null;
		FieldHeader contentDisposition = field.getHeader().get("content-disposition");
		Map<String,String> contentDispositionParams = contentDisposition.getParams(); 

        File file = File.createTempFile(PREFIX_TMP_FILE_NAME, SUFFIX_TMP_FILE_NAME);
        file.deleteOnExit();
        f = new UploadedFileImp(file);
        f.setFileName(contentDispositionParams.get("filename"));

        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){

    		while( (line = readLineBytes()) != null && !startsWith(line, boundaryStart) ) {
    			System.out.print(new String(line.data, line.start, 1 + (line.end - line.start)));
                //fout.write( line.data, line.start, 1 + (line.end - line.start) );
    			raf.write(line.data, line.start, 1 + (line.end - line.start));
    		}
    		
        	raf.setLength(raf.length() - 2);
        	
        }

        field.setValue(f);
        
	}
	
	private FieldHeader parseFieldHeader(String line){
		
		String[] parts = line.split("\\;");
		String[] f = parts[0].split("\\:");
		String name = f[0];
		String value = StringUtil.adjust(f[1]);
		
		Map<String,String> params = parts.length > 1? new HashMap<>() : null;
		
		FieldHeader fh = new FieldHeader(name, value, params);
		
		for(int i=1;i<parts.length;i++) {
			f = parts[i].split("\\=");
			name = StringUtil.adjust(f[0]);
			value = f[1].substring(1, f[1].length() - 1);
			params.put(name, value);
		}
		
		return fh;
	}

	private String toString(Line line) throws UnsupportedEncodingException {
		return toString(line, true);
	}
	
	private String toString(Line line, boolean withoutMarks) throws UnsupportedEncodingException {
		
		int len = 1 + (line.end - line.start);
		
		if(line == null || line.end - line.start == 0) {
			return new String();
		}
		
		int max = line.end;
		
		if(withoutMarks) {
			if(max > 0 && line.data[max] == '\n') {
				max--;
			}
			
			if(max > 0 && line.data[max] == '\r') {
				max--;
			}
		}
		
		len = 1 + (max - line.start);
		
		return len <= 0? null : new String(line.data, line.start, len, charset);
	}

	private String readLine() throws IOException {
		Line line = readLineBytes();
		return toString(line);
	}
	
	private Line readLineBytes() throws IOException {
		
		line.start = line.end == 0? 0 : line.end + 1;
		line.end = line.start;
		
		while(line.maxLen < line.data.length || line.end - line.start == 0){
			
			int mark = getLineMark(line);
			
			if(mark > 0) {
				line.end = mark;
				break;
			}
			
			if(line.data.length == line.maxLen) {
				line.start = 0;
				line.end = 0;
				line.maxLen = 0;
			}
			
			int maxRead = line.data.length - line.maxLen;
			
			int read = in.read(line.data, line.maxLen, maxRead);
			
			if(read <= 0) {
				break;
			}
			
			System.out.println(new String(line.data, line.maxLen, read));
			
	        event.addBytesRead(read);

	        if(this.maxLength > 0 && event.getBytesRead() > this.maxLength)
	            throw new IOException( "data too large" );
			
			line.maxLen += read;
		}
		
		if(line.start == line.end) {
			line.end = line.maxLen;
		}
		
		return line.end == line.maxLen && line.end == 0? null : line;
	}
	
	private int getLineMark(Line line) {
		
		for(int i=line.start;i<line.maxLen;i++) {
			if(line.data[i] == '\n') {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean startsWith(Line line, byte[] a) {
		
		if(line == null || line.end - line.start < a.length) {
			return false;
		}
		
		byte[] value = line.data;
		int startLine = line.start;
		
		//System.out.println(new String(line.data, line.start, a.length));
		//System.out.println(new String(a, 0, a.length));
		
		for(int i=0;i<a.length;i++) {
			
			if(a[i] != value[startLine]) {
				return false;
			}
			
			startLine++;
		}
		
		return true;
	}
	
	public class Line {
		
		public byte[] data;
		
		public int start;

		public int end;
		
		public int maxLen;
		
		public Line(byte[] data, int start, int end, int maxLen) {
			this.data = data;
			this.start = start;
			this.end = end;
			this.maxLen = maxLen;
		}
		
	}
	
	public class Field {
		
		private Map<String,FieldHeader> header;
		
		private Object value;

		public Field() {
			this.header = new HashMap<>();
		}
		
		public Map<String, FieldHeader> getHeader() {
			return header;
		}

		public void setHeader(Map<String, FieldHeader> header) {
			this.header = header;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
	}
	
	public class FieldHeader {
		
		private String name;
		
		private String value;
		
		private Map<String,String> params;

		public FieldHeader(String name, String value, Map<String, String> params) {
			this.name = name;
			this.value = value;
			this.params = params;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public Map<String, String> getParams() {
			return params;
		}
		
	}
}
