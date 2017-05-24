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

/**
 * 
 * @author Brandao
 *
 */
public interface MutableRequestParserEvent extends RequestParserEvent{

	void setStart(long value);
	
    void setContentLength(long value);

    void setBytesRead(long value);
	
    void addBytesRead(long value);
    
    void setRequest(MvcRequest value);

    void setResponse(MvcResponse value);
    
}
