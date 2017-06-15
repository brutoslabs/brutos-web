package org.brandao.brutos.annotation.configuration.web;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DataType;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.ResponseType;
import org.brandao.brutos.annotation.configuration.ActionConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.ResponseStatus;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.RequestMethodType;

public class WebActionConfig extends ActionConfig{

	public WebActionConfig(ActionEntry actionEntry) {
		super(actionEntry);
	}

	public RequestMethodType getRequestMethodType(){
		
		RequestMethod requestMethod = 
				actionEntry.isAnnotationPresent(RequestMethod.class)?
					actionEntry.getAnnotation(RequestMethod.class) :
					actionEntry.getControllerClass().getAnnotation(RequestMethod.class);

		String[] requestMethodTypes = 
				requestMethod == null || requestMethod.value().length == 0? 
					null : 
					requestMethod.value();
	
		return
				requestMethodTypes == null? 
					null : 
					RequestMethodType.valueOf(StringUtil.adjust(requestMethodTypes[0]));
		
	}
	
	public DataType[] getRequestTypes(){
		AcceptRequestType requestTypesAnnotation =
				actionEntry.getAnnotation(AcceptRequestType.class);
		
		if(requestTypesAnnotation != null){
			String[] values = requestTypesAnnotation.value();
			DataType[] types = new DataType[values.length];
			for(int i=0;i<values.length;i++){
				types[i] = MediaType.valueOf(values[i]);
			}
			return types;
		}
		
		return null;
	}

	public DataType[] getResponseTypes(){
		ResponseType responseTypeAnnotation = 
				actionEntry.getAnnotation(ResponseType.class);
		
		if(responseTypeAnnotation != null){
			String[] values = responseTypeAnnotation.value();
			DataType[] types = new DataType[values.length];
			for(int i=0;i<values.length;i++){
				types[i] = MediaType.valueOf(values[i]);
			}
			return types;
		}
		
		return null;
	}
	
	public int getResponseStatus(){
		ResponseStatus responseStatus = 
				actionEntry.isAnnotationPresent(ResponseStatus.class)?
					actionEntry.getAnnotation(ResponseStatus.class) :
					actionEntry.getControllerClass().getAnnotation(ResponseStatus.class);
		
		return responseStatus == null? 0 : responseStatus.value();
	}
	
	public RequestMethodType[] getRequestMethodTypeAlias(){
		
		RequestMethod requestMethod = 
				actionEntry.isAnnotationPresent(RequestMethod.class)?
					actionEntry.getAnnotation(RequestMethod.class) :
					actionEntry.getControllerClass().getAnnotation(RequestMethod.class);

		String[] requestMethodTypes = 
				requestMethod == null? 
					null : 
					requestMethod.value();
	
		if(requestMethodTypes == null || requestMethodTypes.length <= 1){
			return null;
		}
		
		List<RequestMethodType> result = new ArrayList<RequestMethodType>();
		
		for(int i=1;i<requestMethodTypes.length;i++){
			String requestMethodTypeName = StringUtil.adjust(requestMethodTypes[i]);
			RequestMethodType requestMethodType = 
					RequestMethodType.valueOf(requestMethodTypeName);
			if(requestMethodType != null){
				result.add(requestMethodType);
			}
			else{
				throw new BrutosException("invalid request method type: "
						+ actionEntry.getControllerClass().getName() + "."
						+ actionEntry.getName());
			}
		}
		
		return result.toArray(new RequestMethodType[0]);
	}
	
}
