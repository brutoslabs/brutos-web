package org.brandao.brutos.web.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import org.brandao.brutos.MutableRequestParserEvent;

public class WWWFormUrlEncodedParser {

	private InputStream in;
	
	private MutableRequestParserEvent event;
	
	private long maxRequestBodyLength;
	
	private int bufferLength;
	
	private String charset;
	
	private String[] fields;
	
	private int fieldIndex;
	
	public WWWFormUrlEncodedParser(InputStream in, String charset, long maxRequestBodyLength, int bufferLength, MutableRequestParserEvent event) {
		
		if(bufferLength < 1024) {
			throw new IllegalArgumentException("bufferLength: bufferLength < 1024");
		}

		if(bufferLength/2 < 256) {
			throw new IllegalArgumentException("maxHeaderLineLength: bufferLength/2 < 256");
		}
		
		this.in = in;
		this.event = event;
		this.maxRequestBodyLength = maxRequestBodyLength;
		this.charset = charset;
		this.bufferLength = bufferLength;
		this.fields = null;
		this.fieldIndex = -1;
	}
	
    public boolean hasMoreElements() throws IOException{
    	
    	if(fields == null) {
    		parseFields();
    	}
    	
    	fieldIndex++;
    	
    	return fieldIndex < fields.length;
    }
	
	public Field nextElement() throws IOException {
		
		String[] field = fields[fieldIndex].split("\\=");
		String name = URLDecoder.decode(field[0], "UTF-8");
		String value = field.length < 2? null : URLDecoder.decode(field[1], "UTF-8");
		
		return new Field(name, value);
	}
	
	private void parseFields() throws IOException {
	
    	StringBuilder builder = new StringBuilder();
    	
		try(InputStreamReader reader = new InputStreamReader(in, charset)){

	    	char[] buf = new char[bufferLength];
	    	int l;
	    	while((l = reader.read(buf, 0, buf.length)) != -1){
	    		
		        event.addBytesRead(l);

		        if(this.maxRequestBodyLength > 0 && event.getBytesRead() > this.maxRequestBodyLength) {
		            throw new IOException( "data too large" );
		        }
		        
	    		builder.append(buf, 0, l);
		        
	    	}
		
	    	
		}
		
    	String data = builder.toString();
    	fields = data.split("\\&");
		
	}

	public class Field {
		
		private String name;
		
		private String value;

		public Field(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
		
		
	}
}
