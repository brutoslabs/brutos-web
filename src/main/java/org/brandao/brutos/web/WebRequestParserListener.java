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

package org.brandao.brutos.web;

import java.util.Map;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.RequestParserEvent;
import org.brandao.brutos.RequestParserListenerImp;
import org.brandao.brutos.RequestParserStats;
import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 *
 */
public class WebRequestParserListener extends RequestParserListenerImp{

	private static final long serialVersionUID = -3900704450724831129L;

	public WebRequestParserListener() {
	}

	@SuppressWarnings("unchecked")
	public void started(RequestParserEvent value) {
        super.started(value);
        
        ApplicationContext applicationContext = 
        		Invoker.getCurrentApplicationContext();
        
        Scope scope = applicationContext.getScopes().get(WebScopeType.SESSION);
        Map<String,RequestParserStats> mappedUploadStats = 
        		(Map<String,RequestParserStats>)scope.get(BrutosWebConstants.SESSION_UPLOAD_STATS);
        
        if(mappedUploadStats != null){
            mappedUploadStats.put(
            		value.getRequest().getRequestId(), 
            		value);
        }
        
	}

	@SuppressWarnings("unchecked")
	public void finished(RequestParserEvent value) {
		super.finished(value);
		
        ApplicationContext applicationContext = 
        		Invoker.getCurrentApplicationContext();
        
        Scope scope = applicationContext.getScopes().get(WebScopeType.SESSION);
        Map<String,RequestParserStats> mappedUploadStats = 
        		(Map<String,RequestParserStats>)scope.get(BrutosWebConstants.SESSION_UPLOAD_STATS);
        
        if(mappedUploadStats != null){
            mappedUploadStats.remove(
            		value.getRequest().getRequestId());
        }
		
	}
	
}
