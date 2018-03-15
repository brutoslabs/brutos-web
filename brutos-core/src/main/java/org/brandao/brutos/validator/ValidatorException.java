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

package org.brandao.brutos.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.brandao.brutos.BrutosException;

/**
 * 
 * @author Brandao
 */
public class ValidatorException extends BrutosException implements Serializable {

	private static final long serialVersionUID = -1590829351058138495L;
	
	public Map<String, List<ValidatorException>> mapExceptions = new HashMap<String, List<ValidatorException>>();
	
	public ValidatorException() {
		super();
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

	public void addCause(String path, ValidatorException vex) {
		this.addError(path, vex);
	}

	public void addCause(List<ValidatorException> vex) {
		for(ValidatorException e: vex){
			for(Entry<String,List<ValidatorException>> entry: e.mapExceptions.entrySet()){
				this.addError(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public Map<String, List<ValidatorException>> getCauses() {
		return this.mapExceptions;
	}

	protected void addError(String path, ValidatorException ex){
		
		List<ValidatorException> list = this.mapExceptions.get(path);
		
		if(list == null){
			list = new ArrayList<ValidatorException>();
			this.mapExceptions.put(path, list);
		}
		
		list.add(ex);
	}
	
	protected void addError(String path, List<ValidatorException> ex){
		
		List<ValidatorException> list = this.mapExceptions.get(path);
		
		if(list == null){
			list = new ArrayList<ValidatorException>();
			this.mapExceptions.put(path, list);
		}
		
		list.addAll(ex);
	}
	
}
