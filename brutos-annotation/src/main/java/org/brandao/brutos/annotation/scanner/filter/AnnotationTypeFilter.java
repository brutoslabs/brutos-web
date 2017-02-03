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

package org.brandao.brutos.annotation.scanner.filter;

import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.annotation.scanner.TypeFilter;
import org.brandao.brutos.scanner.vfs.Vfs;

/**
 *
 * @author Brandao
 */
public class AnnotationTypeFilter implements TypeFilter {

	private List<Class> annotation;

	public boolean accepts(String resource) {
		try {
			resource = Vfs.toClass(resource);
			Class clazz = ClassUtil.get(resource);
			for (Class an : annotation) {
				return clazz.isAnnotationPresent(an);
			}
			return false;
		} catch (ClassNotFoundException e) {
			throw new BrutosException(e);
		}
	}

	public void setExpression(List<String> value) {
		try {
			this.annotation = new ArrayList<Class>();

			for (String name : value)
				this.annotation.add(ClassUtil.get(name));

		} catch (ClassNotFoundException e) {
			throw new BrutosException(e);
		}
	}

}
