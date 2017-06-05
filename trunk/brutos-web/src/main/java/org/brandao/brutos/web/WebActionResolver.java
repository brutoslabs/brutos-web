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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.AbstractActionResolver;
import org.brandao.brutos.ActionResolverException;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DefaultResourceAction;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebController;

/**
 * 
 * @author Brandao
 */
public class WebActionResolver extends AbstractActionResolver{
    
	private RequestNode root;
	
	public static void main(String[] s){
		WebActionResolver r = new WebActionResolver();
		WebController c = new WebController(null);
		c.setId(null);
		c.setActionType(WebActionType.DETACHED);
		
		WebAction action = new WebAction();
		action.setController(c);
		action.setName("/value/value2/value3");
		action.setId(c.getActionType().getActionID(c, action));
		r.registry(c, action);
		
		RequestEntry e = r.get("/value");
	}
	
    public WebActionResolver(){
    	super();
    	this.addActionTypeResolver(WebActionType.PARAMETER,  new ParamActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.HIERARCHY,  new HierarchyActionTypeResolver());
    	this.addActionTypeResolver(WebActionType.DETACHED,   new DetachedActionTypeResolver());
    }
    
    /*
     * /                      []
     * /value/value2/value3   [value,  value2, value3]
     * /{regex}/value2/value3 [regex,  value2, value3]
     * /value2/{regex}/value3 [value2, regex,  value3]
     * /value2/value3/{regex} [valu2,  value3, regex]
     * 
     */
    
	public ResourceAction getResourceAction(ControllerManager controllerManager,
			MutableMvcRequest request) throws ActionResolverException{
		String id = request.getRequestId();
		RequestEntry entry = this.get(id);
		
		if(id != null){
			return new DefaultResourceAction( entry.getController(), entry.getAction() );
		}
		
		return null;
	}
    
    public void registry(Controller controller, Action action){
    	WebActionID actionID = (WebActionID)action.getId();
    	String id            = actionID.getId();
    	String[] parts       = this.parser(id).toArray(new String[0]);
    	
    	this.addNode(this.root, new RequestEntry(controller, action), parts, 0);
    }

    public RequestEntry get(String value){
    	String[] parts       = value.split("/");
    	return this.getNode(this.root, parts, 0);
    }
    
    public void remove(Controller controller, Action action){
    	WebActionID actionID = (WebActionID)action.getId();
    	String id            = actionID.getId();
    	String[] parts       = this.parser(id).toArray(new String[0]);
    	
    	this.removeNode(this.root, new RequestEntry(controller, action), parts, 0);
    }
    
    private void addNode(RequestNode node, RequestEntry value, String[] parts, int index){
    	
    	if(index == 0 && parts.length == 0){
    		node.setRequestEntry(value);
    	}
    	else
    	if(index == parts.length - 1){
    		node.setRequestEntry(value);
    	}
    	else{
    		RequestNode next = node.getNext(parts[index]);
    		if(next == null){
    			next = node.add(parts[index], null);
    		}
    		this.addNode(next, value, parts, index++);
    	}
    	
    }

    private RequestEntry getNode(RequestNode node, String[] parts, int index){
    	
    	if(index == 0 && parts.length == 0){
    		return node.getRequestEntry();
    	}
    	else
    	if(index == parts.length - 1){
    		return node.getRequestEntry();
    	}
    	else{
    		RequestNode next = node.getNext(parts[index]);
    		if(next == null){
    			next = node.add(parts[index], null);
    		}
    		return this.getNode(next, parts, index++);
    	}
    	
    }
    
    private void removeNode(RequestNode node, RequestEntry value, String[] parts, int index){
    	
    	if(index == 0 && parts.length == 0){
    		node.remove(null);
    	}
    	else
    	if(index == parts.length - 1){
    		node.setRequestEntry(null);
    	}
    	else{
    		RequestNode next = node.getNext(parts[index]);
    		if(next != null){
    			this.removeNode(next, value, parts, index++);
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
    				result.add(value.substring(start, value.length()));
    			}
    			else
    			if(end > start){
    				result.add(value.substring(start, end));
    			}
    		}
    		
    	}
    	
    	return result;
    	
    }
    
}

class RequestEntry{
	
	private Controller controller;
	
	private Action action;
	
	public RequestEntry(Controller controller, Action action) {
		this.controller = controller;
		this.action = action;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
}

/*
 * /value/value2/value3
 * /{regex}/value2/value2
 * /value2/{regex}/value2
 * /value2/value2/{regex}
 * 
 */
 
class RequestNode{
	
	private String value;

	private boolean staticValue;
	
	private Map<String, RequestNode> staticNext;
	
	private Set<RequestNode> dynamicNext;
	
	private RequestEntry requestEntry;
	
	public RequestNode(){
		this.dynamicNext = new HashSet<RequestNode>();
		this.staticNext  = new HashMap<String, RequestNode>();
	}
	
	public RequestNode add(String value, RequestEntry requestEntry){
		
		RequestNode node = new RequestNode();
		node.setValue(value);
		node.setRequestEntry(requestEntry);
		node.setStaticValue(value == null || !value.startsWith("{"));
		
		if(node.isStaticValue()){
			this.staticNext.put(value, node);
		}
		else{
			this.dynamicNext.add(node);
		}
		
		return node;
	}

	public void remove(String value){
		
		RequestNode node = new RequestNode();
		node.setValue(value);
		
		if(value == null || value.startsWith("{")){
			this.staticNext.remove(value);
		}
		else{
			this.dynamicNext.remove(node);
		}
		
	}

	public RequestNode getNextNode(String value){
		
		RequestNode next = this.staticNext.get(value);
		
		if(next != null){
			return next;
		}
		
		for(RequestNode dynamicNode: this.dynamicNext){
			if(dynamicNode.value.equals(value)){
				return dynamicNode;
			}
		}
		
		return null;
	}
	
	public RequestNode getNext(String value){
		
		if(!this.staticNext.isEmpty()){
			RequestNode next = this.staticNext.get(value);
			if(next != null){
				return next;
			}
		}
		
		for(RequestNode dynamicNode: this.dynamicNext){
			if(value.matches(dynamicNode.value)){
				return dynamicNode;
			}
		}
		
		return null;
	}

	public boolean isEmpty(){
		return this.dynamicNext.isEmpty() && this.staticNext.isEmpty();
	}
	
	public RequestEntry getRequestEntry() {
		return requestEntry;
	}

	public void setRequestEntry(RequestEntry requestEntry) {
		this.requestEntry = requestEntry;
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

	public Map<String, RequestNode> getStaticNext() {
		return staticNext;
	}

	public void setStaticNext(Map<String, RequestNode> staticNext) {
		this.staticNext = staticNext;
	}

	public Set<RequestNode> getDynamicNext() {
		return dynamicNext;
	}

	public void setDynamicNext(Set<RequestNode> dynamicNext) {
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
		RequestNode other = (RequestNode) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
