package org.brandao.brutos.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.ConstructorBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.PropertyController;

public class DefaultValidator implements Validator {

	private Properties config;
	private Map mappedRules;
	private Map rules;
	private boolean initialized;

	public DefaultValidator(Map rules) {
		this.mappedRules = rules;
	}

	public void configure(Properties config) {
		this.config = config;
		this.initialized = false;
	}

	private synchronized void init() {
		try {
			this.rules = new HashMap();

			Iterator keys = config.stringPropertyNames().iterator();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				if (!key.equals("message")) {
					Class rule = key.equalsIgnoreCase(RestrictionRules.CUSTOM
							.toString()) ? ClassUtil.get(config
							.getProperty(key)) : (Class) mappedRules.get(key);

					if (rule != null) {
						ValidationRule ruleInstance = getInstance(rule);
						ruleInstance.setConfiguration(this.config);
						rules.put(key, ruleInstance);
					}
				}
			}
			this.initialized = true;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	private ValidationRule getInstance(Class clazz) {
		try {
			return (ValidationRule) ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected String getMessage(Object value, Properties config) {
		String message = config.getProperty("message");
		if (message != null) {
			Iterator r = rules.keySet().iterator();

			while (r.hasNext()) {
				String key = (String) r.next();
				String val = String.valueOf(config.get(key));
				message = message.replace("${" + key + "}", val);
			}
			message = message.replace("${value}", String.valueOf(value));
		} else
			message = "";

		return message;
	}

	public void innerValidate(Object source, Object value)
			throws ValidatorException {

		if (!this.initialized)
			this.init();

		Iterator c = rules.values().iterator();

		try {
			while (c.hasNext()) {
				ValidationRule rule = (ValidationRule) c.next();
				rule.validate(source, value);
			}
		} catch (ValidatorException e) {
			throw new ValidatorException(getMessage(value, config), e);
		}
	}

	public Properties getConfiguration() {
		return this.config;
	}

	public void validate(ConstructorArgBean source, Object value)
			throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(ConstructorBean source, Object factoryInstance,
			Object[] value) throws ValidatorException {
	}

	public void validate(ConstructorBean source, Object factoryInstance,
			Object value) throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(PropertyBean source, Object beanInstance, Object value)
			throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(PropertyController source, Object beanInstance,
			Object value) throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(ParameterAction source, Object controllerInstance,
			Object value) throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(Action source, Object controller, Object[] value)
			throws ValidatorException {
	}

	public void validate(Action source, Object controller, Object value)
			throws ValidatorException {
		this.innerValidate(source, value);
	}

	public void validate(Method source, Object instance, Object[] value)
			throws ValidatorException {
	}

	public void validate(Method source, Object instance, Object value)
			throws ValidatorException {
		this.innerValidate(source, value);
	}

}
