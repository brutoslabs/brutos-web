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

package org.brandao.brutos.annotation.configuration;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DataType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ResponseType;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.ResultView;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.mapping.StringUtil;

public class ActionConfig {

	protected ActionEntry actionEntry;

	public ActionConfig(ActionEntry actionEntry) {
		this.actionEntry = actionEntry;
	}
	
	public String getActionId(){
		Action action = actionEntry.getAnnotation(Action.class);
		String[] actionIDs = action == null? null : action.value();
		return actionIDs == null || actionIDs.length == 0? 
				null : 
				StringUtil.adjust(actionIDs[0]);
	}
	
	public String getResultActionName(){
		Result resultAnnotation = actionEntry.getAnnotation(Result.class);
		return resultAnnotation == null ? null : StringUtil.adjust(resultAnnotation.value());
	}
	
	public boolean isResultRenderable(){
		ResultView resultView = actionEntry.getAnnotation(ResultView.class);
		return resultView == null ? false : resultView.rendered();
	}

	public boolean isRenderable(){
		View viewAnnotation = actionEntry.getAnnotation(View.class);
		return viewAnnotation == null ? true : viewAnnotation.rendered();
	}

	public boolean isResolvedView(){
		View viewAnnotation = actionEntry.getAnnotation(View.class);
		boolean resolved = viewAnnotation == null ? false : viewAnnotation.resolved();
		return this.isRenderable() ? resolved : true;
	}

	public String getActionView() {
		View viewAnnotation = actionEntry.getAnnotation(View.class);
		String view = viewAnnotation == null || StringUtil.isEmpty(viewAnnotation.value())? 
				null : 
				StringUtil.adjust(viewAnnotation.value());
		return view;
	}
	
	public String getActionExecutor(){
		return actionEntry.isAbstractAction() ? null : actionEntry.getName();
	}
	
	public DataType[] getRequestTypes(){
		AcceptRequestType requestTypesAnnotation =
				actionEntry.getAnnotation(AcceptRequestType.class);
		
		if(requestTypesAnnotation != null){
			String[] values = requestTypesAnnotation.value();
			DataType[] types = new DataType[values.length];
			for(int i=0;i<values.length;i++){
				types[i] = DataType.valueOf(values[i]);
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
				types[i] = DataType.valueOf(values[i]);
			}
			return types;
		}
		
		return null;
	}
	
	public DispatcherType getDispatcherType(){
		View viewAnnotation = actionEntry.getAnnotation(View.class);
		
		return viewAnnotation == null? 
				null : 
				DispatcherType.valueOf(StringUtil.adjust(viewAnnotation.dispatcher()));
	}
	
	public String[] getAliasName(){
		Action action = actionEntry.getAnnotation(Action.class);
		String[] actionIDs = action == null? null : action.value();
		
		if(actionIDs == null || actionIDs.length <= 1){
			return null;
		}
		
		List<String> result = new ArrayList<String>();
		
		for(int i=1;i<actionIDs.length;i++){
			String actionName = StringUtil.adjust(actionIDs[i]);
			
			if(!StringUtil.isEmpty(actionName)){
				result.add(actionName);
			}
			else{
				throw new BrutosException("invalid action id: "
						+ actionEntry.getControllerClass().getName() + "."
						+ actionEntry.getName());
			}
		}
		
		return result.toArray(new String[0]);
	}
	
}
