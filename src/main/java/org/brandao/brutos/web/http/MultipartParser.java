package org.brandao.brutos.web.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.mapping.StringUtil;

public class MultipartParser {

	private static final String PREFIX_TMP_FILE_NAME	= "multpart";
	
	private static final String SUFFIX_TMP_FILE_NAME	= ".tmp";

	private static final byte[] boundaryEndMark = new byte[] {'-','-'};
	
	private Line line;
	
	private InputStream in;
	
	private String charset;
	
	private byte[] boundary;

	private byte[] boundaryEnd;
	
	public MultipartParser(InputStream in, String charset, String boundary) {
		this.boundary = boundary.getBytes();
		this.in = in;
		this.charset = charset;
        this.line =  new Line(new byte[8192], 0, 0);
		this.boundaryEnd = Arrays.copyOf(this.boundary, this.boundary.length + boundaryEndMark.length);
		System.arraycopy(boundaryEndMark, 0, this.boundaryEnd, this.boundary.length, boundaryEndMark.length);
	}
	
    public boolean hasMoreElements() throws IOException{
    	Line line = readLineBytes();
    	return line != null && startsWith(line, boundary) && !startsWith(line, boundaryEnd);
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
		
		while( (line = readLineBytes()) != null && !startsWith(line, boundary) ) {
			builder.append(toString(line, false));
		}
		
		field.setValue(builder.toString());
	}

	private void loadFile(Field field) throws IOException {
		
		Line line;
        UploadedFile f = null;
		FieldHeader contentDisposition = field.getHeader().get("content-disposition");
		Map<String,String> contentDispositionParams = contentDisposition.getParams(); 

        File file = File.createTempFile(PREFIX_TMP_FILE_NAME, SUFFIX_TMP_FILE_NAME);
        file.deleteOnExit();
        f= new UploadedFileImp(file);
        f.setFileName(contentDispositionParams.get("filename"));

        try(FileOutputStream fout = new FileOutputStream(file)){
        	
    		while( (line = readLineBytes()) != null && !startsWith(line, boundary) ) {
                fout.write( line.data, 0, line.len );
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

	private String toString(Line line) throws UnsupportedEncodingException {
		return toString(line, true);
	}
	
	private String toString(Line line, boolean withoutMarks) throws UnsupportedEncodingException {
		
		if(line == null || line.len == 0) {
			return new String();
		}
		
		int max = line.len;
		
		if(withoutMarks) {
			if(max > 0 && line.data[max - 1] == '\n') {
				max--;
			}
			
			if(max > 0 && line.data[max - 1] == '\r') {
				max--;
			}
		}
		
		return new String(line.data, 0, max, charset);
	}

	private String readLine() throws IOException {
		Line line = readLineBytes();
		return toString(line);
	}
	
	private Line readLineBytes() throws IOException {
		
		if(line.len > 0) {
			int len = line.maxLen - line.len;
			
			if(len > 0) {
				System.arraycopy(line.data, line.len, line.data, 0, len);
			}
			
			line.maxLen = len;
			line.len = 0;
		}
		
		while(line.maxLen < line.data.length){
			
			for(int i=0;i<line.maxLen;i++) {
				if(line.data[i] == '\n') {
					line.len = i;
					break;
				}
			}
			
			int r = in.read(line.data, line.maxLen, line.data.length - line.maxLen);
			
			if(r < 0) {
				break;
			}
			
			line.maxLen += r;
		}
		
		if(line.len == 0) {
			line.len = line.maxLen;
		}
		
		return line.len == line.maxLen && line.len == 0? null : line;
	}
	
	private boolean startsWith(Line line, byte[] a) {
		
		if(line == null || line.len < a.length) {
			return false;
		}
		
		byte[] value = line.data;
		
		for(int i=0;i<a.length;i++) {
			if(a[i] != value[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public class Line {
		
		public byte[] data;
		
		public int len;

		public int maxLen;
		
		public Line(byte[] data, int len, int maxLen) {
			this.data = data;
			this.len = len;
			this.maxLen = maxLen;
		}
		
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
