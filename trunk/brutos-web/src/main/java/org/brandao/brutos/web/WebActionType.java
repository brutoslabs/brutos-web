package org.brandao.brutos.web;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.util.WebUtil;

public class WebActionType extends ActionType{

	public static final WebActionType PARAMETER = new WebActionType() {
		
		public String id(){
			return "PARAMETER";
		}

		public String name(){
			return "Parameter";
		}
		
		public boolean isValidControllerId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public boolean isValidActionId(String value){
			return true;
		}

		public ActionID getActionID(Controller controller, Action action){
			WebAction webAction = (WebAction)action;
			return new WebActionID(webAction.getName(), webAction.getRequestMethod());
		}
		
	};

	public static final WebActionType HIERARCHY = new WebActionType() {
		
		public String id(){
			return "HIERARCHY";
		}

		public String name(){
			return "Hierarchy";
		}

		public boolean isValidControllerId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}
		
		public boolean isValidActionId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public ActionID getActionID(Controller controller, Action action){
			WebAction webAction = (WebAction)action;
			return new WebActionID(controller.getId() + webAction.getName(), webAction.getRequestMethod());
		}
		
	};

	public static final WebActionType DETACHED = new WebActionType() {

		public String id(){
			return "DETACHED";
		}

		public String name(){
			return "Detached";
		}

		public boolean isValidControllerId(String value){
			return false;
		}
		
		public boolean isValidActionId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public ActionID getActionID(Controller controller, Action action){
			WebAction webAction = (WebAction)action;
			return new WebActionID(webAction.getName(), webAction.getRequestMethod());
		}
		
	};

	private final static Map<String, WebActionType> defaultTypes = 
			new HashMap<String, WebActionType>();
	
	static {
		defaultTypes.put(PARAMETER.id(),	PARAMETER);
		defaultTypes.put(HIERARCHY.id(),	HIERARCHY);
		defaultTypes.put(DETACHED.id(),		DETACHED);
	}

	public static WebActionType valueOf(String value) {
		if (value == null)
			return null;
		else
			return defaultTypes.get(value.toUpperCase());
	}
	
}
