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

package org.brandao.brutos.mapping;

import org.brandao.brutos.BrutosConstants;

/**
 * 
 * @author Brandao
 */
public class CollectionBean extends Bean {

	protected DependencyBean collection;

	protected int maxItens;
	
	public CollectionBean(Controller controller, Bean parent) {
		super(controller, parent);
		this.maxItens = BrutosConstants.COLLECTION_MAX_ITENS;
	}

	public int getMaxItens() {
		return maxItens;
	}

	public void setMaxItens(int maxItens) {
		this.maxItens = maxItens;
	}

	public void setCollection(DependencyBean collection) {
		this.collection = collection;
	}

	public DependencyBean getCollection() {
		return this.collection;
	}
	
	public boolean isBean() {
		return false;
	}

	public boolean isCollection() {
		return true;
	}

	public boolean isMap() {
		return false;
	}

}
