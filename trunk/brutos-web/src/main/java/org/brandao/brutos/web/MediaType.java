package org.brandao.brutos.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.DataType;

public class MediaType extends DataType{

	public static final MediaType ALL = 
		new MediaType("*/*", "*", "*");

	public static final MediaType APPLICATION_ATOM_XML = 
		new MediaType("application/atom+xml", "application", "atom+xml");

	public static final MediaType APPLICATION_X_WWW_FORM_URLENCODED = 
			new MediaType("application/x-www-form-urlencoded", "application", "x-www-form-urlencoded");

	public static final MediaType APPLICATION_JSON = 
			new MediaType("application/json", "application", "json");

	public static final MediaType APPLICATION_OCTET_STREAM = 
			new MediaType("application/octet-stream", "application", "octet-stream");

	public static final MediaType APPLICATION_PDF = 
			new MediaType("application/pdf", "application", "pdf");

	public static final MediaType APPLICATION_RSS_XML = 
			new MediaType("application/rss+xml", "application", "rss+xml");

	public static final MediaType APPLICATION_XHTML_XML = 
			new MediaType("application/xhtml+xml", "application", "xhtml+xml");
	
	public static final MediaType APPLICATION_XML = 
			new MediaType("application/xml", "application", "xml");

	public static final MediaType IMAGE_GIF = 
			new MediaType("image/gif", "image", "gif");

	public static final MediaType IMAGE_JPEG = 
			new MediaType("image/jpeg", "image", "jpeg");

	public static final MediaType IMAGE_PNG = 
			new MediaType("image/png", "image", "png");

	public static final MediaType MULTIPART_FORM_DATA = 
			new MediaType("multipart/form-data", "multipart", "form-data");

	public static final MediaType TEXT_EVENT_STREAM = 
			new MediaType("text/event-stream", "text", "event-stream");

	public static final MediaType TEXT_HTML = 
			new MediaType("text/html", "text", "html");

	public static final MediaType TEXT_MARKDOWN = 
			new MediaType("text/markdown", "text", "markdown");

	public static final MediaType TEXT_PLAIN = 
			new MediaType("text/plain", "text", "plain");

	public static final MediaType TEXT_XML = 
			new MediaType("text/xml", "text", "xml");
	
	private static final HashMap<String, MediaType> defaultTypes =
			new HashMap<String, MediaType>();
	
	static{
		defaultTypes.put(ALL.getName(), 								ALL);
		defaultTypes.put(APPLICATION_ATOM_XML.getName(),				APPLICATION_ATOM_XML);  
		defaultTypes.put(APPLICATION_X_WWW_FORM_URLENCODED.getName(),	APPLICATION_X_WWW_FORM_URLENCODED); 
		defaultTypes.put(APPLICATION_JSON.getName(),					APPLICATION_JSON);
		defaultTypes.put(APPLICATION_OCTET_STREAM.getName(),			APPLICATION_OCTET_STREAM);  
		defaultTypes.put(APPLICATION_PDF.getName(),						APPLICATION_PDF);
		defaultTypes.put(APPLICATION_RSS_XML.getName(),					APPLICATION_RSS_XML); 
		defaultTypes.put(APPLICATION_XHTML_XML.getName(),				APPLICATION_XHTML_XML); 
		defaultTypes.put(APPLICATION_XML.getName(),						APPLICATION_XML);
		defaultTypes.put(IMAGE_GIF.getName(),							IMAGE_GIF);
		defaultTypes.put(IMAGE_JPEG.getName(),							IMAGE_JPEG);
		defaultTypes.put(IMAGE_PNG.getName(),							IMAGE_PNG);
		defaultTypes.put(MULTIPART_FORM_DATA.getName(),					MULTIPART_FORM_DATA); 
		defaultTypes.put(TEXT_EVENT_STREAM.getName(),					TEXT_EVENT_STREAM);
		defaultTypes.put(TEXT_HTML.getName(),							TEXT_HTML);
		defaultTypes.put(TEXT_MARKDOWN.getName(),						TEXT_MARKDOWN);
		defaultTypes.put(TEXT_PLAIN.getName(),							TEXT_PLAIN);
		defaultTypes.put(TEXT_XML.getName(),							TEXT_XML);
	}
	
	public static MediaType valueOf(String value){
		
		if(value == null){
			throw new NullPointerException();
		}
		
		MediaType r = defaultTypes.get(value);

		return r == null? create(value) : r;
	}
	
	private static MediaType create(String value){
		String[] p = value.split("\\;");
		
		String[] types = p[0].split("\\/");
		
		Map<String,String> params = new HashMap<String, String>();
		
		for(int i=1;i<p.length;i++){
			String part = p[i];
			String[] keyValue = part.split("\\=");
			
			if(keyValue.length != 2){
				return null;
			}
			
			String key = prepareValue(keyValue[0]);
			String val = prepareValue(keyValue[1]);
			
			params.put(key, val);
		}
		
		return new MediaType(value, types[0], types[1], params);
	}
	
	private static String prepareValue(String value){
		return value == null? null : value.trim();
	}
	
	private final String type;
	
	private final String subType;
	
	private final Map<String,String> params;
	
	@SuppressWarnings("unchecked")
	public MediaType(String name, String type, String subType) {
		this(name, type, subType, Collections.EMPTY_MAP);
	}

	public MediaType(String name, String type, String subType, Map<String,String> params) {
		super(type + "/" + subType);
		this.type    = type;
		this.subType = subType;
		this.params  = params;
	}
	
	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean match(MediaType value){
		String type    = value.getType();
		String subtype = value.getSubType();
		
		boolean typeMath =
				type.equals("*") || type.equals(this.type);
		
		boolean subtypeMath =
				subtype.equals("*") || this.subType.equals("*") || this.subType.equals(subtype);
		
		return typeMath && subtypeMath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataType other = (DataType) obj;
		if (this.getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.getName().equals(other.getName()))
			return false;
		return true;
	}
	
}
