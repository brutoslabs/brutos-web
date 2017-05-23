package org.brandao.brutos;

import java.util.Properties;

public class RequestParserImp extends AbstractRequestParser{

	public void parserContentType(MvcRequest request, DataType dataType,
			Properties config, MutableRequestParserEvent requestParserInfo)
			throws RequestParserException {
		
		ParserContentType parser = this.parsers.get(dataType);
		
		if(parser == null){
			throw new RequestParserException("not found: " + dataType.getName());
		}
		
		parser.parserContentType(request, requestParserInfo, config);
		
	}

}
