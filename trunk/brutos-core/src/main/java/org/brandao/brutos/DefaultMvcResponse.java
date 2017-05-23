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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Brandao
 */
public class DefaultMvcResponse implements MutableMvcResponse {

    protected Set<String> headerNames;
    
    protected Map<String, List<Object>> header;
	
    protected MvcRequest request;
    
    protected DataType dataType;
    
    protected Object result;
    
	public DefaultMvcResponse(){
		this.header = new HashMap<String, List<Object>>();
		this.headerNames = new HashSet<String>();
	}
	
	public MvcRequest getRequest() {
		return this.request;
	}

	public void process(Object object) {
	}

	public OutputStream processStream() throws IOException{
		return null;
	}

	public void setHeader(String name, Object value) {
		this.setValue(name, value, this.headerNames, this.header);
	}

	public DataType getType() {
		return this.dataType;
	}

	public Object getResult() {
		return this.result;
	}

	public void setResult(Object value) {
		this.result = value;
	}

	public void setRequest(MvcRequest value) {
		this.request = value;
	}

	public void setType(DataType value) {
		this.dataType = value;
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
	
}
