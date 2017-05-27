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

package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;

/**
 * 
 * @author Brandao
 */
public class ActionType {

	private final static Map<String, ActionType> defaultTypes = 
			new HashMap<String, ActionType>();

	public static final ActionType PARAMETER = new ActionType(){
		
		public String id(){
			return "PARAMETER";
		}

		public String name(){
			return "Parameter";
		}
		
		public boolean isValidControllerId(String value){
			return value != null;
		}

		public boolean isValidActionId(String value){
			return value != null;
		}
		
		public ActionID getActionID(Controller controller, Action action){
			return new ActionID(controller.getId() + "-" + action.getId());
		}
		
	};
	
	public static ActionType valueOf(String value) {
		if (value == null)
			return null;
		else
			return (ActionType) defaultTypes.get(value.toUpperCase());
	}

	public String id(){
		throw new UnsupportedOperationException();
	}

	public String name(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isValidControllerId(String value){
		throw new UnsupportedOperationException();
	}

	public boolean isValidActionId(String value){
		throw new UnsupportedOperationException();
	}
	
	public ActionID getActionID(Controller controller, Action action){
		throw new UnsupportedOperationException();
	}
	
}
