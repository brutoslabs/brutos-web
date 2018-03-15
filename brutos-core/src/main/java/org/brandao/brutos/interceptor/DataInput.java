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

import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
@Deprecated
public class DataInput {

	private Scope scope;

	public DataInput(Scope requestScope) {
		this.scope = requestScope;
	}

	public void read(Controller controller, Object object) {
		/*
		try {
			List<PropertyController> fields = controller.getProperties();
			
			for (PropertyController ff : fields) {
				if(ff.canSet()){
					ff.setValue(object);
				}
			}
			
			scope.put(BrutosConstants.WEBFRAME, object);
			scope.put(BrutosConstants.CONTROLLER, object);
		}
		catch (BrutosException e) {
			throw e;
		}
		catch (Exception e) {
			throw new BrutosException(e);
		}
		*/
	}

}
