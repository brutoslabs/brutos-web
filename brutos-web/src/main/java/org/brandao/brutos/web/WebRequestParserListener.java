package org.brandao.brutos.web;

import java.util.Map;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.RequestParserEvent;
import org.brandao.brutos.RequestParserListenerImp;
import org.brandao.brutos.RequestParserStats;
import org.brandao.brutos.scope.Scope;

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
