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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.MutableRequestParserEvent;
import org.brandao.brutos.mapping.StringUtil;

public class MultipartFormDataParser implements Closeable{

	private static final String PREFIX_TMP_FILE_NAME	= "multpart";
	
	private static final String SUFFIX_TMP_FILE_NAME	= ".tmp";

	private static final byte[] boundaryMark = new byte[] {'-','-'};
	
	private MultipartFormDataParserLine line;
	
	private InputStream in;
	
	private byte[] boundary;

	private byte[] boundaryStart;
	
	private byte[] boundaryEnd;
	
	private MutableRequestParserEvent event;
	
	private long maxRequestBodyLength;
	
	private int maxHeaderLineLength;
	
	public MultipartFormDataParser(InputStream in, String charset, String boundary, long maxRequestBodyLength, int bufferLength, MutableRequestParserEvent event) {
		
		if(bufferLength < boundary.length()*2) {
			throw new IllegalArgumentException("bufferLength: bufferLength < boundary.length*2");
		}

		if(bufferLength/2 < 256) {
			throw new IllegalArgumentException("maxHeaderLineLength: bufferLength/2 < 256");
		}
		
		this.in = in;
		this.event = event;
		this.maxRequestBodyLength = maxRequestBodyLength;
		
		this.boundary = boundary.getBytes();
		this.maxHeaderLineLength = bufferLength/2;
		
		this.boundaryStart = new byte[this.boundary.length + 2];
		System.arraycopy(boundaryMark,  0, this.boundaryStart,                   0, boundaryMark.length);
		System.arraycopy(this.boundary, 0, this.boundaryStart, boundaryMark.length, this.boundary.length);
        
		this.boundaryEnd = Arrays.copyOf(this.boundaryStart, this.boundaryStart.length + boundaryMark.length);
		System.arraycopy(boundaryMark, 0, this.boundaryEnd, this.boundaryStart.length, boundaryMark.length);
		
        this.line =  new MultipartFormDataParserLine(new byte[bufferLength], 0, 0, 0, this.boundaryEnd.length, charset);
		
	}
	
    public boolean hasMoreElements() throws IOException{
    	MultipartFormDataParserLine line = event.getBytesRead() == 0? readLineBytes() : this.line;
    	return line != null && (!line.startsWith(boundaryEnd) && line.startsWith(boundaryStart));
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
			
	        if(line.length() > maxHeaderLineLength) {
	            throw new IOException( "header line too large: " + line );
	        }
			
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
		
		MultipartFormDataParserLine line;
		StringBuilder builder = new StringBuilder();
		
		while((line = readLineBytes()) != null) {
			
			if(line.startsWith(boundaryStart)) {
				break;
			}
			
			builder.append(line.toString(false));
			
		}
		
		field.setValue(builder.substring(0, builder.length() - 2));
	}

	private void loadFile(Field field) throws IOException {
		
		MultipartFormDataParserLine line = null;
        UploadedFile f = null;
		FieldHeader contentDisposition = field.getHeader().get("content-disposition");
		Map<String,String> contentDispositionParams = contentDisposition.getParams(); 

        File file = File.createTempFile(PREFIX_TMP_FILE_NAME, SUFFIX_TMP_FILE_NAME);
        file.deleteOnExit();
        f = new UploadedFileImp(file);
        f.setFileName(contentDispositionParams.get("filename"));

        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){

    		while((line = readLineBytes()) != null) {
    			
    			if(line.startsWith(boundaryStart)) {
    				break;
    			}
    			
    			line.write(raf);
    			
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

	private String readLine() throws IOException {
		MultipartFormDataParserLine line = readLineBytes();
		return line == null? null : line.toString(true);
	}
	
	private MultipartFormDataParserLine readLineBytes() throws IOException {

		line.adjustToNextLine();
		line.adjustMinLengthLine(false);
		
		while(line.isNeededMoreDataToMakeLine()){
			
			if(line.existNewLine()) {
				break;
			}
			
			line.adjustMinLengthLine(true);
			
			if(line.read(in, event) <= 0) {
				break;
			}
			
	        if(this.maxRequestBodyLength > 0 && event.getBytesRead() > this.maxRequestBodyLength) {
	            throw new IOException( "data too large" );
	        }
			
		}
		
		line.adjustEndToMaxLengthData();
		
		return line.foundLine()? line : null;
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

	@Override
	public void close() throws IOException {
		in.close();
	}
	
}
