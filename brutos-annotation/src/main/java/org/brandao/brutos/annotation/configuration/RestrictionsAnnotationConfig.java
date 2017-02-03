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

import org.brandao.brutos.*;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Restriction;
import org.brandao.brutos.annotation.Restrictions;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotation;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.validator.RestrictionRules;

/**
 *
 * @author Brandao
 */
@Stereotype(target = Restrictions.class, executeAfter = Basic.class)
public class RestrictionsAnnotationConfig extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		return (source instanceof ActionParamEntry && ((ActionParamEntry) source)
				.isAnnotationPresent(Restrictions.class))
				|| (source instanceof BeanPropertyAnnotation && ((BeanPropertyAnnotation) source)
						.isAnnotationPresent(Restrictions.class));
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		try {
			return applyConfiguration0(source, builder, componentRegistry);
		} catch (Exception e) {
			throw new BrutosException("can't create validation rules", e);
		}

	}

	public Object applyConfiguration0(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		ParameterBuilder parameterBuilder = builder instanceof ParameterBuilder ? (ParameterBuilder) builder
				: null;

		PropertyBuilder propertyBuilder = builder instanceof PropertyBuilder ? (PropertyBuilder) builder
				: null;

		if (parameterBuilder == null && propertyBuilder == null)
			return builder;

		Restrictions restrictions = this.getAnnotation(source);
		String message = StringUtil.adjust(restrictions.message());
		RestrictionBuilder restrictionBuilder = null;
		Restriction[] rules = restrictions.rules();

		if (rules.length > 0) {
			Restriction r = rules[0];
			String rule = r.rule();
			String value = r.value();

			if (parameterBuilder != null) {
				restrictionBuilder = parameterBuilder.addRestriction(
						RestrictionRules.valueOf(rule), value);
			} else {
				restrictionBuilder = propertyBuilder.addRestriction(
						RestrictionRules.valueOf(rule), value);
			}

			for (int i = 1; i < rules.length; i++) {
				r = rules[i];
				rule = r.rule();
				value = r.value();
				restrictionBuilder.addRestriction(
						RestrictionRules.valueOf(rule), value);
			}
			restrictionBuilder.setMessage(message == null ? r.message()
					: message);
		}

		return restrictionBuilder;
	}

	private Restrictions getAnnotation(Object source) {
		if (source instanceof ActionParamEntry)
			return ((ActionParamEntry) source)
					.getAnnotation(Restrictions.class);
		else
			return ((BeanPropertyAnnotation) source)
					.getAnnotation(Restrictions.class);
	}

}
