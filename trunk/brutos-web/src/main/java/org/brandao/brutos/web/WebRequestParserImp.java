package org.brandao.brutos.web;

import org.brandao.brutos.web.parser.JsonParserContentType;
import org.brandao.brutos.web.parser.MultipartFormDataParserContentType;

public class WebRequestParserImp 
	extends org.brandao.brutos.AbstractRequestParser{

	public WebRequestParserImp(){
		this.registryParser(MediaType.APPLICATION_JSON,    new JsonParserContentType());
		this.registryParser(MediaType.MULTIPART_FORM_DATA, new MultipartFormDataParserContentType());
	}
	
}
