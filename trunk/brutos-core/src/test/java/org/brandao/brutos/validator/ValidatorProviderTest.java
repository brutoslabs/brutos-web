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
import junit.framework.TestCase;
import org.brandao.brutos.ValidatorFactory;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.type.IntegerType;
import org.brandao.brutos.type.StringType;

public class ValidatorProviderTest extends TestCase {

	public void testEqualRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.EQUAL.toString(), "100");

		Validator v = vp.getValidator(config);
		v.validate(d, null, new Integer(100));
	}

	public void testNotEqualRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.EQUAL.toString(), "100");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, new Integer(10));
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testMinRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MIN.toString(), "10");

		Validator v = vp.getValidator(config);
		v.validate(d, null, new Integer(10));
	}

	public void testNotMinRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.EQUAL.toString(), "10");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, new Integer(9));
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testMinLengthRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MINLENGTH.toString(), "10");

		Validator v = vp.getValidator(config);
		v.validate(d, null, "AAAAAAAAAA");
	}

	public void testNotMinLengthRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MINLENGTH.toString(), "10");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, "AAAAAAAAA");
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testMaxRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MAX.toString(), "4");

		Validator v = vp.getValidator(config);
		v.validate(d, null, new Integer(4));
	}

	public void testNotMaxRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new IntegerType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MAX.toString(), "4");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, new Integer(10));
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testMaxLengthRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MAXLENGTH.toString(), "4");

		Validator v = vp.getValidator(config);
		v.validate(d, null, "AAAA");
	}

	public void testNotMaxLengthRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MAXLENGTH.toString(), "4");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, "AAAAAA");
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testMatchesRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MATCHES.toString(), "\\d+");

		Validator v = vp.getValidator(config);
		v.validate(d, null, "10");
	}

	public void testNotMatchesRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.MATCHES.toString(), "\\d+");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, "AA");
			fail("expected ValidatorException");
		} catch (ValidatorException e) {

		}
	}

	public void testRequiredRule() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.REQUIRED.toString(), "true");

		Validator v = vp.getValidator(config);
		v.validate(d, null, "10");
	}

	public void testRequiredRuleError() {
		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(new Properties());
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.REQUIRED.toString(), "true");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, null);
			fail("expected ValidatorException");
		} catch (ValidatorException e) {
		}
	}

	public void testCustomRule() {
		Properties configProvider = new Properties();
		configProvider.setProperty("org.brandao.brutos.validator.rules.range",
				CustomValidationRule.class.getName());

		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(configProvider);
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty("range", "10-20");

		Validator v = vp.getValidator(config);
		v.validate(d, null, new Integer(10));
		v.validate(d, null, new Integer(20));
		v.validate(d, null, new Integer(15));
	}

	public void testErrorCustomRule() {
		Properties configProvider = new Properties();
		configProvider.setProperty("org.brandao.brutos.validator.rules.range",
				CustomValidationRule.class.getName());

		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(configProvider);
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty("range", "10-20");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, new Integer(9));
			fail("expected ValidatorException");
		} catch (ValidatorException e) {
		}

		try {
			v.validate(d, null, new Integer(21));
			fail("expected ValidatorException");
		} catch (ValidatorException e) {
		}

	}

	public void testMessage() {
		Properties configProvider = new Properties();

		ValidatorFactory vp = new DefaultValidatorFactory();
		vp.configure(configProvider);
		PropertyBean d = new PropertyBean(new Bean(new Controller(null)));
		d.setType(new StringType());

		Properties config = new Properties();
		config.setProperty(RestrictionRules.EQUAL.toString(), "teste");
		config.setProperty("message", "expected: ${equal} found: ${value}");

		Validator v = vp.getValidator(config);
		try {
			v.validate(d, null, "AA");
			fail("expected ValidatorException");
		} catch (ValidatorException e) {
			assertEquals("expected: teste found: AA", e.getMessage());
		}

	}

}
