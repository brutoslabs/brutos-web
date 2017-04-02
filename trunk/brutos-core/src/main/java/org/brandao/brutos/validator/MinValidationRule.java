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

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;

/**
 * 
 * @author Brandao
 */
public class MinValidationRule implements ValidationRule {

	private String expected;

	public void validate(Object source, Object value) {
		Type valueType = null;

		if (source instanceof DependencyBean)
			valueType = ((DependencyBean) source).getType();
		else if (source instanceof UseBeanData)
			valueType = ((UseBeanData) source).getType();
		else
			throw new BrutosException("invalid source: " + source);

		Number tmp = (Number) valueType.convert(this.expected);

		if (value != null && ((Number) value).doubleValue() < tmp.doubleValue())
			throw new ValidatorException();
	}

	public void setConfiguration(Properties config) {
		this.expected = config.getProperty(RestrictionRules.MIN.toString());
	}

}
