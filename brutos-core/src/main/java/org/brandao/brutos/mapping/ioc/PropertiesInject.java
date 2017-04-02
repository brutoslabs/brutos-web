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

package org.brandao.brutos.mapping.ioc;

import java.util.List;

/**
 * 
 * @author Brandao
 */
public class PropertiesInject extends ComplexObjectInject {

	private List props;

	public PropertiesInject(Class type, String name, String factory,
			Property[] props) {
		// super( type, name, ScopeType.REQUEST, false );
		super(name, String.class, String.class, java.util.Properties.class,
				factory, props);
		setSingleton(true);
		setType(type == null ? java.util.Properties.class : type);
		// setConstructor( new ConstructorInject( null, this ) );
	}

	public List getProps() {
		return props;
	}

	public void setProps(List props) {
		this.props = props;
	}

}
