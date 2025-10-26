package org.brandao.brutos.web.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.mapping.StringUtil;

public class MultipartParser {

	private static final String PREFIX_TMP_FILE_NAME	= "multpart";
	
	private static final String SUFFIX_TMP_FILE_NAME	= ".tmp";
	
	private InputStream in;
	
	private String charset;
	
	private byte[] boundary;
	
	public MultipartParser(InputStream in, String charset, String boundary) {
		this.boundary = boundary.getBytes();
		this.in = in;
		this.charset = charset;
	}
	
	private Field parseField() throws IOException {
		Field field = new Field();
		loadHeader(field);
		loadData(field);
	}
	
	private void loadHeader(Field field) throws UnsupportedEncodingException {
		
		String line;
		
		while(!(line = readLine()).isEmpty()) {
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

	private void loadValue(Field field) throws UnsupportedEncodingException {
		
		byte[] line;
		StringBuilder builder = new StringBuilder();
		
		while( (line = readLineBytes()) != null && !startsWith(line, boundary) ) {
			builder.append(toString(line, false));
		}
		
		field.setValue(builder.toString());
	}

	private void loadFile(Field field) throws IOException {
		
		byte[] line;
        UploadedFile f = null;
		FieldHeader contentDisposition = field.getHeader().get("content-disposition");
		Map<String,String> contentDispositionParams = contentDisposition.getParams(); 

        File file = File.createTempFile(PREFIX_TMP_FILE_NAME, SUFFIX_TMP_FILE_NAME);
        file.deleteOnExit();
        f= new UploadedFileImp(file);
        f.setFileName(contentDispositionParams.get("filename"));

        try(FileOutputStream fout = new FileOutputStream(file)){
        	
    		while( (line = readLineBytes()) != null && !startsWith(line, boundary) ) {
                fout.write( line, 0, line.length );
    		}
        	
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
			f = parts[0].split("\\=");
			name = StringUtil.adjust(f[0]);
			value = f[1].substring(1, f[1].length() - 1);
			params.put(name, value);
		}
		
		return fh;
	}

	private String toString(byte[] line) throws UnsupportedEncodingException {
		return toString(line, true);
	}
	
	private String toString(byte[] line, boolean withoutMarks) throws UnsupportedEncodingException {
		
		if(line == null || line.length == 0) {
			return new String();
		}
		
		int max = line.length;
		
		if(withoutMarks) {
			if(max > 0 && line[max - 1] == '\n') {
				max--;
			}
			
			if(max > 0 && line[max - 1] == '\r') {
				max--;
			}
		}
		
		return new String(line, 0, max, charset);
	}

	private String readLine() throws UnsupportedEncodingException {
		byte[] lineData = readLineBytes();
		return toString(lineData);
	}
	
	private byte[] readLineBytes() {
		return null;
	}
	
	private boolean startsWith(byte[] value, byte[] a) {
		
		if(value == null || value.length < a.length) {
			return false;
		}
		
		for(int i=0;i<a.length;i++) {
			if(a[i] != value[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public class Field {
		
		private Map<String,FieldHeader> header;
		
		private Object value;

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
