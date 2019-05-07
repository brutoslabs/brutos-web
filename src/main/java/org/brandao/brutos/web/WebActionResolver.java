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

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import org.brandao.brutos.web.mapping.RequestEntry;
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
	
	private SimpleResourceCache cache;
	
    public WebActionResolver(){
    	super();
    	this.root = new RequestMappingNode();
    	this.addActionTypeResolver(WebActionType.PARAMETER,  new ParamActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.HIERARCHY,  new HierarchyActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.DETACHED,   new DetachedActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.HEADER,     new HeaderActionTypeResolver());
    	this.cache = new SimpleResourceCache(300);
    }
    
	public ResourceAction getResourceAction(Action action, MutableMvcRequest request) throws ActionResolverException {
		WebAction webAction = (WebAction)action;
		WebController webController = (WebController)action.getController();
		return new WebResourceAction(
					webAction.getRequestMethod(), 
					webController, 
					webAction
				);
	}
    
	public ResourceAction getResourceAction(ControllerManager controllerManager,
			MutableMvcRequest request) throws ActionResolverException{
		
		try{
			WebMvcRequest webRequest     = (WebMvcRequest)request; 
			String id                    = request.getRequestId();
			RequestMethodType methodType = webRequest.getRequestMethodType();
			ResourceKey key              = new ResourceKey(id, methodType);
			
			RequestEntry entry = this.cache.get(key);
			
			if(entry != null){
				
				if(entry instanceof EmptyWebResourceAction){
					return null;
				}
				
			}
			else{
				entry = this.get(request.getRequestId(), webRequest.getRequestMethodType(), request);
				this.cache.put(key, entry == null? emptyWebResourceAction : entry);
			}
			
			if(entry != null){
				
				Map<String, List<String>> params = entry.getParameters();
				
				if(params != null){
			        for(String k: params.keySet() ){
			        	for(String v: params.get(k)){
			        		request.setParameter(k, v);
			        	}
			        }
				}
				
				if(entry.getRequestMappingEntry().getAction() == null){
					ActionTypeResolver resolver = 
						this.actionTypeResolver.get(
						entry.getRequestMappingEntry().getController().getActionType());
					return
						resolver.getResourceAction(
								entry.getRequestMappingEntry().getController(), request);
				}
				else{
					return new WebResourceAction(
							entry.getRequestMappingEntry().getRequestMethodType(),
							(WebController)entry.getRequestMappingEntry().getController(), 
							(WebAction)entry.getRequestMappingEntry().getAction() );
				}
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
		
		return this.getResourceAction(controller, context.getRequestMethod(), actionId, request);
	}
	
	public ResourceAction getResourceAction(Controller controller,
			RequestMethodType requestMethodType, String actionId, MutableMvcRequest request)
			throws ActionResolverException {
		
		WebApplicationContext context = 
				(WebApplicationContext)request.getApplicationContext();
		
		WebActionID wID = new WebActionID(actionId, context.getRequestMethod());
		Action action = controller.getAction(wID);
		return 
			action == null? 
				null : 
				new WebResourceAction(
						wID.getRequestMethodType(), 
						(WebController)controller, 
						(WebAction)action);
	}
	
    public void registry(ControllerID controllerID, Controller controller, 
    		ActionID actionID, Action action) throws ActionResolverException{
    	
    	try{
	    	List<ActionID> list = 
				controller.getActionType()
				.getIDs(controllerID, controller, actionID, action);
	
	    	if(list == null){
	    		return;
	    	}
	    	
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

    public RequestEntry get(String value, RequestMethodType methodType, 
    		MutableMvcRequest request) throws MalformedURLException{
    	
    	String[] parts = WebUtil.parserURI(value, false).toArray(new String[0]);
    	
    	if(parts.length == 0){
        	return new RequestEntry(this.root.getRequestEntry(methodType), null);
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
    		RequestMappingNode next = node.getNextToAdd(parts[index]);
    		if(next == null){
    			next = node.add(parts[index], null);
    		}
    		return this.addNode(next, value, parts, index + 1);
    	}
    	
    }

    private RequestEntry getNode(RequestMappingNode node, 
    		 RequestMethodType methodType, MutableMvcRequest request, String[] parts, int index) throws MalformedURLException{
    	
    	if(index == 0 && parts.length == 0){
    		RequestMappingEntry rme = node.getRequestEntry(methodType);
    		return rme == null? 
    				null :
					new RequestEntry(
						node.getRequestEntry(methodType), 
						node.getRequestParameters(request, parts[index-1]));
    	}
    	else
    	if(index == parts.length){
    		RequestMappingEntry rme = node.getRequestEntry(methodType);
    		return rme == null? 
    				null : 
					new RequestEntry(
							node.getRequestEntry(methodType), 
							node.getRequestParameters(request, parts[index-1]));
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		
    		if(next == null){
    			return null;
    		}
    		
    		RequestEntry e = this.getNode(next, methodType, request, parts, index + 1);
    		
    		if(e != null && !node.isStaticValue()){
    			Map<String, List<String>> params     =  e.getParameters();
    			Map<String, List<String>> nodeParams = node.getRequestParameters(request, parts[index-1]);
    			
    			if(params != null){
    				
    				if(nodeParams != null){
    					params.putAll(nodeParams);
    				}
    				
    			}
    			else
    			if(nodeParams != null){
    				e.setParameters(nodeParams);
    			}
    			
    			//e.setParameters(next.getRequestParameters(request, parts[index]));
    			//next.updateRequest(request, parts[index]);
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
    
    /* cache */
    
    private static final EmptyWebResourceAction emptyWebResourceAction = new EmptyWebResourceAction();
    
    private static class EmptyWebResourceAction extends RequestEntry {

		public EmptyWebResourceAction() {
			super(null, null);
		}
    	    	
    }
    
    private static class SimpleResourceCache extends LinkedHashMap<ResourceKey, RequestEntry>{
        
		private static final long serialVersionUID = 7702944317773359399L;
		
		private int maxSize;
        
    	public SimpleResourceCache(int capacity) {
            super(capacity, 0.75f, true);
            this.maxSize = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<ResourceKey, RequestEntry> eldest) {
            return super.size() > maxSize;
        }
    }
    
    private static class ResourceKey implements Serializable{
    	
		private static final long serialVersionUID = -5567615660615057030L;

		public String resource;
    	
    	public RequestMethodType methodType;

		public ResourceKey(String resource, RequestMethodType methodType) {
			this.resource = resource;
			this.methodType = methodType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((methodType == null) ? 0 : methodType.hashCode());
			result = prime * result
					+ ((resource == null) ? 0 : resource.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResourceKey other = (ResourceKey) obj;
			if (methodType == null) {
				if (other.methodType != null)
					return false;
			} else if (!methodType.equals(other.methodType))
				return false;
			if (resource == null) {
				if (other.resource != null)
					return false;
			} else if (!resource.equals(other.resource))
				return false;
			return true;
		}
    	
    }
}
