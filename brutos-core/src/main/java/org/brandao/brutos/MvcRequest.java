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
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Brandao
 */
public interface MvcRequest {

	Set<String> getHeadersNames();
	
	Set<String> getParametersNames();

	Set<String> getPropertiesNames();
	
	RequestParserEvent getRequestParserInfo();
    
	RequestParser getRequestParser();
	
	String getEncoding();
	
	String getRequestId();
	
	Throwable getThrowable();
	
	Object getHeader(String value);
	
	Object getParameter(String name);

	List<Object> getParameters(String name);
	
	Object getProperty(String name);

	InputStream getStream() throws IOException;

	DataType getType();

	List<DataType> getAcceptResponse();
	
	ResourceAction getResourceAction();
	
	ApplicationContext getApplicationContext();
	
	Object getResource();
	
	Object[] getParameters();
	
	RequestInstrument getRequestInstrument();

	StackRequestElement getStackRequestElement();
	
}
