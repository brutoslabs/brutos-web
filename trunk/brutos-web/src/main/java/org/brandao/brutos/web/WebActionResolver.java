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

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.AbstractActionResolver;
import org.brandao.brutos.ActionResolverException;
import org.brandao.brutos.ActionTypeResolver;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DefaultMvcRequest;
import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;
import org.brandao.brutos.web.mapping.RequestMappingEntry;
import org.brandao.brutos.web.mapping.RequestMappingNode;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;
import org.brandao.brutos.web.util.WebUtil;

/**
 * 
 * @author Brandao
 */
@SuppressWarnings("unused")
public class WebActionResolver extends AbstractActionResolver{
    
	private RequestMappingNode root;
	
    public WebActionResolver(){
    	super();
    	this.root = new RequestMappingNode();
    	this.addActionTypeResolver(WebActionType.PARAMETER,  new ParamActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.HIERARCHY,  new HierarchyActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.DETACHED,   new DetachedActionTypeResolver());
    }
    
	public ResourceAction getResourceAction(ControllerManager controllerManager,
			MutableMvcRequest request) throws ActionResolverException{
		
		try{
			WebMvcRequest webRequest = (WebMvcRequest)request; 
			String id = request.getRequestId();
			RequestMappingEntry entry = this.get(request.getRequestId(), webRequest.getRequestMethodType(), request);
			
			if(entry != null){
				if(entry.getAction() == null){
					ActionTypeResolver resolver = 
							this.actionTypeResolver.get(entry.getController().getActionType());
					return resolver.getResourceAction(entry.getController(), request);
				}
				else
					return new WebResourceAction(
							entry.getRequestMethodType(),
							(WebController)entry.getController(), 
							(WebAction)entry.getAction() );
			}
			
			return null;
		}
		catch(Throwable e){
			throw new ActionResolverException(e);			
		}
	}

	public ResourceAction getResourceAction(Controller controller,
			String actionId, MutableMvcRequest request)
			throws ActionResolverException {
		
		WebApplicationContext context = 
				(WebApplicationContext)request.getApplicationContext();
		
		WebActionID wID = new WebActionID(actionId, context.getRequestMethod());
		Action action = controller.getAction(wID);
		return action == null? null : new DefaultResourceAction(controller, action);
	}
	
    public void registry(ControllerID controllerID, Controller controller, 
    		ActionID actionID, Action action) throws ActionResolverException{
    	
    	try{
	    	List<ActionID> list = 
				controller.getActionType()
				.getIDs(controllerID, controller, actionID, action);
	
	    	for(ActionID aID: list){
	    		WebActionID aWID = (WebActionID)aID;
		    	String[] parts   = WebUtil.parserURI(aWID.getId(), true).toArray(new String[0]);
		    	
		    	boolean added =
		    			this.addNode(
	    					this.root, 
	    					new RequestMappingEntry(
	    							aWID.getRequestMethodType(), 
	    							controller, action),
							parts, 0);
		    	
		    	if(!added){
		    		throw new ActionResolverException("action has been added: " + aWID);
		    	}
	    	}
    	}
    	catch(ActionResolverException e){
    		throw e;    		
    	}
    	catch(Throwable e){
    		throw new ActionResolverException(e);    		
    	}
    	
    }

    public RequestMappingEntry get(String value, RequestMethodType methodType, 
    		MutableMvcRequest request) throws MalformedURLException{
    	
    	String[] parts = WebUtil.parserURI(value, false).toArray(new String[0]);
    	
    	if(parts.length == 0){
        	return this.root.getRequestEntry(methodType);
    	}
    	else{
    		return this.getNode(this.root, methodType, request, parts, 0);
    	}
    }
    
    public void remove(ControllerID controllerID, Controller controller, 
    		ActionID actionID, Action action) throws ActionResolverException{
    	
    	try{
	    	List<ActionID> list = 
				controller.getActionType()
				.getIDs(controllerID, controller, actionID, action);
	
	    	for(ActionID aID: list){
	    		WebActionID aWID = (WebActionID)aID;
		    	String[] parts   = WebUtil.parserURI(aWID.getId(), true).toArray(new String[0]);
		    	boolean removed =
	    			this.removeNode(
	    					this.root, 
	    					new RequestMappingEntry(
	    							aWID.getRequestMethodType(), 
	    							controller, 
	    							action),
							parts, 0);
		    	
		    	if(!removed){
		    		throw new ActionResolverException("action not found: " + aWID);
		    	}
	    	}
    	}
    	catch(Throwable e){
    		throw new ActionResolverException(e);    		
    	}
    	
    }
    
    private boolean addNode(RequestMappingNode node,
    		RequestMappingEntry value, String[] parts, int index) throws MalformedURLException{
    	
    	if(index == 0 && parts.length == 0){
    		return node.putRequestEntry(value.getRequestMethodType(), value);
    	}
    	else
    	if(index == parts.length){
    		return node.putRequestEntry(value.getRequestMethodType(), value);
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		if(next == null){
    			next = node.add(parts[index], null);
    		}
    		return this.addNode(next, value, parts, index + 1);
    	}
    	
    }

    private RequestMappingEntry getNode(RequestMappingNode node, 
    		 RequestMethodType methodType, MutableMvcRequest request, String[] parts, int index) throws MalformedURLException{
    	
    	if(index == 0 && parts.length == 0){
    		return node.getRequestEntry(methodType);
    	}
    	else
    	if(index == parts.length){
    		return node.getRequestEntry(methodType);
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		
    		if(next == null){
    			return null;
    		}
    		
    		RequestMappingEntry e = this.getNode(next, methodType, request, parts, index + 1);
    		
    		if(e != null && !next.isStaticValue()){
    			next.updateRequest(request, parts[index]);
    		}
    		
    		return e;
    	}
    	
    }
    
    private boolean removeNode(RequestMappingNode node, 
    		RequestMappingEntry value, String[] parts, int index){
    	
    	if(index == 0 && parts.length == 0){
    		return node.removeRequestEntry(value.getRequestMethodType());
    	}
    	else
    	if(index == parts.length){
    		return node.removeRequestEntry(value.getRequestMethodType());
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		if(next == null){
    			return false;
    		}
    		
			boolean removed = 
				this.removeNode(next, value, parts, index + 1);
			
			if(next.isEmpty()){
				node.remove(parts[index]);
			}
    		
			return removed;
    	}
    	
    }
    
}
