package org.brandao.brutos;

public interface MutableMvcRequest extends MvcRequest{

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

	void setType(DataType value);

	void setResourceAction(ResourceAction value);
	
	void setApplicationContext(ApplicationContext value);
	
	void setResource(Object value);
	
	void setRequestInstrument(RequestInstrument value);

	void setStackRequestElement(StackRequestElement value);
	
}
