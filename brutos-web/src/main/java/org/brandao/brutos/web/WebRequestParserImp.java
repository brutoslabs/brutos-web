package org.brandao.brutos.web;

import org.brandao.brutos.web.parser.AllParserContentType;
import org.brandao.brutos.web.parser.JsonParserContentType;
import org.brandao.brutos.web.parser.MultipartFormDataParserContentType;
import org.brandao.brutos.web.parser.WWWFormUrlEncodedParserContentType;

public class WebRequestParserImp 
	extends org.brandao.brutos.AbstractRequestParser{

	public WebRequestParserImp(){
		this.registerParser(
			MediaType.ALL, new AllParserContentType());
		this.registerParser(
			MediaType.APPLICATION_JSON,	new JsonParserContentType());
		this.registerParser(
			MediaType.MULTIPART_FORM_DATA, new MultipartFormDataParserContentType());
		this.registerParser(
			MediaType.APPLICATION_X_WWW_FORM_URLENCODED, new WWWFormUrlEncodedParserContentType());
	}
	
}
