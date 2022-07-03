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
import org.brandao.brutos.web.util.WebUtil;
import org.brandao.brutos.web.util.WebUtil.ViewContext;

/**
 * 
 * @author Brandao
 *
 */
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
		String view                              = resultAction.getView();
		String viewContext                       = null;
		
		if(responseStatus != 0 && reason != null){
			try{
				response.sendError(responseStatus, reason);
				return;
			}
			catch(Throwable e){
				throw new BrutosException(e);
			}
		}

    	ViewContext vc = WebUtil.toViewContext(view);
    	
		view = vc == null? null : vc.getView();
		viewContext = vc == null? null : vc.getContext();
		
		resultAction.setView(view);
		
		WebStackRequestElement stackRequestElement = 
				(WebStackRequestElement) request.getStackRequestElement();
		stackRequestElement.setViewContext(viewContext);
		stackRequestElement.setResponseStatus(responseStatus);
		stackRequestElement.setReason(reason == null || reason.trim().length() != 0? null : reason);
		stackRequestElement.setDispatcherType(resultAction.getDispatcher());
		
		/*
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
		*/
		
		for (String key : header.keySet()) {
			response.setHeader(key, header.get(key));
		}
	
		super.show(r, value);
	}
	
}
