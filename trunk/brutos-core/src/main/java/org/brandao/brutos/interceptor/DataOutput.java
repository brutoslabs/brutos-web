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

package org.brandao.brutos.interceptor;

import java.lang.reflect.Field;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class DataOutput {

	private Scope scope;

	public DataOutput(Scope scope) {
		this.scope = scope;
	}

	public void write(Controller form, Object object) {
		try {
			List<PropertyController> fields = form.getProperties();
			
			for (PropertyController ff: fields) {
				
				if(!ff.canGet()){
					continue;
				}
				
				Object value = ff.getValueFromSource(object);
				this.scope.put(ff.getName(), value);
				/*
				if (value == null){
					ff.getScope().remove(ff.getName());
				}
				else{
					ff.getScope().put(ff.getName(), value);
				}
				*/
			}
		}
		catch (BrutosException e) {
			throw e;
		}
		catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	@Deprecated
	public void writeFields(Controller form, Object object) {
		try {
			Field[] fields = form.getClassType().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				scope.put(f.getName(), f.get(object));
			}
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}
	
}
