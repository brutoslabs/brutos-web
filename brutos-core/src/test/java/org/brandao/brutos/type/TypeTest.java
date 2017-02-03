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

package org.brandao.brutos.type;

import java.util.List;
import junit.framework.TestCase;

public class TypeTest extends TestCase {

	public void testGetClassType() {
		Class type = TypeUtil.getRawType(int.class);
		TestCase.assertEquals(int.class, type);
	}

	public void testgetRawType() {
		GenericTypeImp genericType = new GenericTypeImp(List.class,
				new Class[] { Integer.class });
		Class type = TypeUtil.getRawType(genericType);
		TestCase.assertEquals(List.class, type);
	}

	/*
	 * public void testgetListType(){ GenericTypeImp genericType = new
	 * GenericTypeImp(List.class,new Class[]{Integer.class}); ListType type =
	 * (ListType) (new TypeManagerImp()).getType(genericType);
	 * TestCase.assertEquals(Integer.class,
	 * type.getCollectionType().getClassType()); }
	 */

}
