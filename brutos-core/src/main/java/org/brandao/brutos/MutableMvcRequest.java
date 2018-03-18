package org.brandao.brutos;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MutableMvcRequest extends MvcRequest{

	void setEncoding(String value) throws UnsupportedEncodingException;
	
	void setRequestId(String requestId);
	
    void setRequestParserInfo(RequestParserEvent value);
    
    void setRequestParser(RequestParser value);
	
	void setThrowable(Throwable value);
	
	void setHeader(String name, Object value);
	
    void setParameter(String name, String value);
    
    void setParameters(String name, String[] values);

    void setParameter(String name, Object value);
    
    void setParameters(String name, Object[] value);

    void setParameters(Object[] value);
    
	void setProperty(String name, Object value);

	void setAcceptResponse(List<DataType> value);
	
	void setType(DataType value);

	void setResourceAction(ResourceAction value);
	
	void setApplicationContext(ApplicationContext value);
	
	void setResource(Object value);
	
	void setRequestInstrument(RequestInstrument value);

	void setStackRequestElement(StackRequestElement value);
	
}
