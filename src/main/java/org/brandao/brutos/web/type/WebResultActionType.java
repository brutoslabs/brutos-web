package org.brandao.brutos.web.type;

import java.util.Map;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.ResultActionType;
import org.brandao.brutos.web.ConfigurableWebResultAction;
import org.brandao.brutos.web.MutableWebMvcRequest;
import org.brandao.brutos.web.MutableWebMvcResponse;
import org.brandao.brutos.web.WebResultActionImp;
import org.brandao.brutos.web.WebStackRequestElement;

public class WebResultActionType 
	extends ResultActionType{

	public Object convert(Object value) {
		return new WebResultActionImp();
	}
	
	public void show(MvcResponse r, Object value){
		
		MutableWebMvcRequest request             = (MutableWebMvcRequest)r.getRequest();
		MutableWebMvcResponse response           = (MutableWebMvcResponse)r;
		ConfigurableWebResultAction resultAction = (ConfigurableWebResultAction)value;
		Map<String, String> header               = resultAction.getHeader();
		int responseStatus                       = resultAction.getResponseStatus();
		String reason                            = resultAction.getReason();
		
		if(responseStatus != 0 && reason != null){
			try{
				response.sendError(responseStatus, reason);
				return;
			}
			catch(Throwable e){
				throw new BrutosException(e);
			}
		}
		
		if(resultAction.getContent() == null){
			WebStackRequestElement stackRequestElement = 
					(WebStackRequestElement) request.getStackRequestElement();
			stackRequestElement.setResponseStatus(responseStatus);
			stackRequestElement.setReason(reason);
		}
		else
		if(responseStatus != 0){
			response.setStatus(responseStatus);			
		}
		
		for (String key : header.keySet()) {
			response.setHeader(key, header.get(key));
		}
	
		super.show(r, value);
	}
	
}
