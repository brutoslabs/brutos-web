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
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;

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
					return new WebResourceAction( (WebController)entry.getController(), (WebAction)entry.getAction() );
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
	
    public void registry(String controllerID, Controller controller, 
    		String actionID, Action action) throws ActionResolverException{
    	
    	try{
	    	List<ActionID> list = 
				controller.getActionType()
				.getIDs(controllerID, controller, actionID, action);
	
	    	for(ActionID aID: list){
	    		WebActionID aWID = (WebActionID)aID;
		    	String[] parts   = this.parser(aWID.getId()).toArray(new String[0]);
		    	this.addNode(this.root, aWID.getRequestMethodType(),
		    			new RequestMappingEntry(controller, action), parts, 0);
	    	}
    	}
    	catch(Throwable e){
    		throw new ActionResolverException(e);    		
    	}
    	
    }

    public RequestMappingEntry get(String value, RequestMethodType methodType, 
    		MutableMvcRequest request) throws MalformedURLException{
    	String[] parts = value.split("/");
    	
    	if(parts.length == 0){
        	return this.root.getRequestEntry(methodType);
    	}
    	else{
    		return this.getNode(this.root, methodType, request, parts, 1);
    	}
    }
    
    public void remove(String controllerID, Controller controller, 
    		String actionID, Action action) throws ActionResolverException{
    	
    	try{
	    	List<ActionID> list = 
				controller.getActionType()
				.getIDs(controllerID, controller, actionID, action);
	
	    	for(ActionID aID: list){
	    		WebActionID aWID = (WebActionID)aID;
		    	String[] parts       = this.parser(aWID.getId()).toArray(new String[0]);
		    	this.removeNode(this.root, aWID.getRequestMethodType(), 
		    			new RequestMappingEntry(controller, action), parts, 0);
	    	}
    	}
    	catch(Throwable e){
    		throw new ActionResolverException(e);    		
    	}
    	
    }
    
    private void addNode(RequestMappingNode node, RequestMethodType methodType,
    		RequestMappingEntry value, String[] parts, int index) throws MalformedURLException{
    	
    	if(index == 0 && parts.length == 0){
    		node.putRequestEntry(methodType, value);
    	}
    	else
    	if(index == parts.length){
    		node.putRequestEntry(methodType, value);
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		if(next == null){
    			next = node.add(parts[index], null);
    		}
    		this.addNode(next, methodType, value, parts, index + 1);
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
    
    private void removeNode(RequestMappingNode node, RequestMethodType methodType, 
    		RequestMappingEntry value, String[] parts, int index){
    	
    	if(index == 0 && parts.length == 0){
    		node.remove(null);
    	}
    	else
    	if(index == parts.length){
    		node.removeRequestEntry(methodType);
    	}
    	else{
    		RequestMappingNode next = node.getNext(parts[index]);
    		if(next != null){
    			this.removeNode(next, methodType, value, parts, index + 1);
    			if(next.isEmpty()){
    				node.remove(parts[index]);
    			}
    		}
    	}
    	
    }
    
    private List<String> parser(String value){
    	List<String> result = new ArrayList<String>();
    	
    	for(int i=0;i<value.length();i++){
    		char c = value.charAt(i);
    				
    		if(c == '/'){
    			int start = i + 1;
    			int end   = -1;
    			int region = 0;
    			
    			for(i = i+1;i<value.length();i++){
    				c = value.charAt(i);
    				if(c == '{'){
    					region++;
    				}
    				
    				if(c == '}'){
    					region--;
    				}
    				
    	    		if(c == '/' && region == 0){
    	    			end = i;
    	    			i -= 1;
    	    			break;
    	    		}
    	    		
    			}
    			
    			if(end == -1){
    				//result.add(value.substring(start, value.length()));
    				end = value.length();
    			}
    			
    			if(end > start){
    				result.add(value.substring(start, end));
    			}
    		}
    		
    	}
    	
    	return result;
    	
    }
    
    private static class RequestMappingNode{
    	
    	private String value;

    	private StringPattern pattern;
    	
    	private boolean staticValue;
    	
    	private Map<String, RequestMappingNode> staticNext;
    	
    	private Set<RequestMappingNode> dynamicNext;
    	
    	private Map<RequestMethodType, RequestMappingEntry> requestMethodTypes;
    	
    	public RequestMappingNode(){
    		this.dynamicNext = new HashSet<RequestMappingNode>();
    		this.staticNext  = new HashMap<String, RequestMappingNode>();
    		this.pattern     = null;
    	}
    	
    	public void updateRequest(MutableMvcRequest request, String value){
    		Map<String,List<String>> params = this.pattern.getParameters(value);

            for(String key: params.keySet() ){
            	for(String v: params.get(key)){
            		request.setParameter(key, v);
            	}
            }
    		
    	}
    	
    	public RequestMappingNode add(String value, 
    			RequestMappingEntry requestEntry) throws MalformedURLException{
    		
    		RequestMappingNode node = new RequestMappingNode();
    		node.setValue(value);
    		node.setStaticValue(value == null || value.indexOf("{") == -1);
    		
    		if(node.isStaticValue()){
    			this.staticNext.put(value, node);
    		}
    		else{
    			node.setPattern(new StringPattern(value));
    			this.dynamicNext.add(node);
    		}
    		
    		return node;
    	}

    	public void remove(String value){
    		
    		RequestMappingNode node = new RequestMappingNode();
    		node.setValue(value);
    		
    		if(value == null || value.startsWith("{")){
    			this.staticNext.remove(value);
    		}
    		else{
    			this.dynamicNext.remove(node);
    		}
    		
    	}

    	public RequestMappingNode getNextNode(String value){
    		
    		RequestMappingNode next = this.staticNext.get(value);
    		
    		if(next != null){
    			return next;
    		}
    		
    		for(RequestMappingNode dynamicNode: this.dynamicNext){
    			if(dynamicNode.value.equals(value)){
    				return dynamicNode;
    			}
    		}
    		
    		return null;
    	}
    	
    	public RequestMappingNode getNext(String value){
    		
    		if(!this.staticNext.isEmpty()){
    			RequestMappingNode next = this.staticNext.get(value);
    			if(next != null){
    				return next;
    			}
    		}
    		
    		for(RequestMappingNode dynamicNode: this.dynamicNext){
    			if(dynamicNode.pattern.matches(value)){
    				return dynamicNode;
    			}
    		}
    		
    		return null;
    	}

    	public boolean isEmpty(){
    		return this.dynamicNext.isEmpty() && this.staticNext.isEmpty();
    	}
    	
    	public StringPattern getPattern() {
    		return pattern;
    	}

    	public void setPattern(StringPattern pattern) {
    		this.pattern = pattern;
    	}

    	public RequestMappingEntry getRequestEntry(RequestMethodType value) {
    		return requestMethodTypes.get(value);
    	}

    	public void putRequestEntry(RequestMethodType requestMethodType, 
    			RequestMappingEntry value) {
    		if(this.requestMethodTypes == null){
    			this.requestMethodTypes = 
    					new HashMap<RequestMethodType, RequestMappingEntry>();
    		}
    		
    		this.requestMethodTypes.put(requestMethodType, value);
    	}

    	public void removeRequestEntry(RequestMethodType value) {
    		
    		if(requestMethodTypes == null){
    			return;
    		}
    		
    		this.requestMethodTypes.remove(value);
    	}
    	
    	public String getValue() {
    		return value;
    	}

    	public void setValue(String value) {
    		this.value = value;
    	}

    	public boolean isStaticValue() {
    		return staticValue;
    	}

    	public void setStaticValue(boolean staticValue) {
    		this.staticValue = staticValue;
    	}

    	public Map<String, RequestMappingNode> getStaticNext() {
    		return staticNext;
    	}

    	public void setStaticNext(Map<String, RequestMappingNode> staticNext) {
    		this.staticNext = staticNext;
    	}

    	public Set<RequestMappingNode> getDynamicNext() {
    		return dynamicNext;
    	}

    	public void setDynamicNext(Set<RequestMappingNode> dynamicNext) {
    		this.dynamicNext = dynamicNext;
    	}

    	@Override
    	public int hashCode() {
    		final int prime = 31;
    		int result = 1;
    		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    		RequestMappingNode other = (RequestMappingNode) obj;
    		if (value == null) {
    			if (other.value != null)
    				return false;
    		} else if (!value.equals(other.value))
    			return false;
    		return true;
    	}
    	
    }
    
    private static class RequestMappingEntry{
    	
    	private Controller controller;
    	
    	private Action action;
    	
    	public RequestMappingEntry(Controller controller, Action action) {
    		this.controller = controller;
    		this.action = action;
    	}

    	public Controller getController() {
    		return controller;
    	}

    	public Action getAction() {
    		return action;
    	}

    }
    
}
