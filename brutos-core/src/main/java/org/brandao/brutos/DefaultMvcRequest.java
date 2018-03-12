/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Brandao
 */
public class DefaultMvcRequest implements MutableMvcRequest {

	protected String encoding;
	
    protected Set<String> propertyNames;
    
    protected Map<String, List<Object>> properties;
	
    protected Set<String> headerNames;
    
    protected Map<String, List<Object>> header;
	
    protected Set<String> parameterNames;
    
    protected Map<String, List<Object>> parameters;
    
    protected RequestParserEvent requestParserInfo;
    
    protected RequestParser requestParser;
    
    protected String requestId;
    
    protected Throwable throwable;
    
    protected DataType dataType;
    
    protected ResourceAction resourceAction;
    
    protected ApplicationContext applicationContext;
    
    protected Object resource;
    
    protected Object[] actionParameters;
    
    protected RequestInstrument requestInstrument;
    
    protected StackRequestElement stackRequestElement;
    
    protected List<DataType> acceptResponse;
    
    public DefaultMvcRequest(){
		this.header         = new HashMap<String, List<Object>>();
		this.headerNames    = new LinkedHashSet<String>();
		this.properties     = new HashMap<String, List<Object>>();
		this.propertyNames  = new LinkedHashSet<String>();
		this.parameters     = new HashMap<String, List<Object>>();
		this.parameterNames = new LinkedHashSet<String>();
		this.acceptResponse = new ArrayList<DataType>();
    }
    
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Throwable getThrowable() {
		return this.throwable;
	}

	public Object getHeader(String value) {
		return this.getValue(value, this.header);
	}

	public Object getParameter(String name) {
		return this.getValue(name, this.parameters);
	}

	public List<Object> getParameters(String name) {
		return this.getValues(name, this.parameters);
	}
	
	public Object getProperty(String name) {
		return this.getValue(name, this.properties);
	}

	public InputStream getStream() throws IOException {
		return null;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Set<String> getPropertiesNames(){
		return this.propertyNames;
	}

	public Set<String> getHeadersNames(){
		return this.headerNames;
	}
	
	public Set<String> getParametersNames(){
		return this.parameterNames;
	}
	
	public DataType getType() {
		return this.dataType;
	}

	public ResourceAction getResourceAction() {
		return this.resourceAction;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public Object getResource() {
		return this.resource;
	}

	public Object[] getParameters() {
		return this.actionParameters;
	}

	public RequestInstrument getRequestInstrument() {
		return this.requestInstrument;
	}

	public StackRequestElement getStackRequestElement() {
		return this.stackRequestElement;
	}

	public void setThrowable(Throwable value) {
		this.throwable = value;
	}

	public void setHeader(String name, Object value) {
		this.setValue(name, value, this.headerNames, this.header);
	}

	public void setParameter(String name, String value) {
		this.setValue(name, value, this.parameterNames, this.parameters);
	}

	public void setParameters(String name, String[] values) {
		for(String s: values){
			this.setValue(name, s, this.parameterNames, this.parameters);
		}
	}

	public void setParameter(String name, Object value) {
		this.setValue(name, value, this.parameterNames, this.parameters);
	}

	public void setParameters(String name, Object[] value) {
		for(Object s: value){
			this.setValue(name, s, this.parameterNames, this.parameters);
		}
	}

	public void setParameters(Object[] value) {
		this.actionParameters = value;
	}

	public void setProperty(String name, Object value) {
		this.setValue(name, value, this.propertyNames, this.properties);
	}

	public void setType(DataType value) {
		this.dataType = value;
	}

	public void setResourceAction(ResourceAction value) {
		this.resourceAction = value;
	}

	public void setApplicationContext(ApplicationContext value) {
		this.applicationContext = value;
	}

	public void setResource(Object value) {
		this.resource = value;
	}

	public void setRequestInstrument(RequestInstrument value) {
		this.requestInstrument = value;
	}

	public void setStackRequestElement(StackRequestElement value) {
		this.stackRequestElement = value;
	}

	public void setRequestParserInfo(RequestParserEvent value) {
		this.requestParserInfo = value;
	}

	public void setRequestParser(RequestParser value) {
		this.requestParser = value;
	}
	
	public RequestParserEvent getRequestParserInfo() {
		return this.requestParserInfo;
	}

	public RequestParser getRequestParser() {
		return this.requestParser;
	}
	
    private void setValue(String name, Object value, Set<String> names, Map<String, List<Object>> map) {
        if( value != null ){
            List<Object> values = map.get( name );
            if( values == null ){
            	names.add(name);
                values = new ArrayList<Object>();
                map.put( name, values );
            }

            values.add( value );
        }
    }
	
    private Object getValue(String value, Map<String, List<Object>> map){
        List<Object> values = map.get( value );
        return values == null || values.isEmpty()? null : values.get( 0 );
    }
	
	private List<Object> getValues(String name, Map<String, List<Object>> map){
        List<Object> values = (List<Object>) map.get( name );
        
        return values == null || values.isEmpty()? null : values;
    }

	public void setAcceptResponse(List<DataType> value){
		this.acceptResponse = value;
	}
    
	public List<DataType> getAcceptResponse() {
		return this.acceptResponse;
	}
    
}
