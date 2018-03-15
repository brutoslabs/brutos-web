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

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.ConstructorBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.ResultAction;

/**
 * 
 * @author Brandao
 */
public class JSR303Validator implements Validator {

	private javax.validation.Validator objectValidator;
	
	private ExecutableValidator executableValidator;
	
	private Properties config;
	
	public void configure(Properties config) {
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();
		this.objectValidator		= validatorFactory.getValidator();
		this.executableValidator	= this.objectValidator.forExecutables();
		this.config 				= config;
	}

	public Properties getConfiguration() {
		return this.config;
	}

	public void validate(ConstructorArgBean source, Object value)
			throws ValidatorException {
	}

	public void validate(ConstructorBean source, Object factoryInstance,
			Object[] value) throws ValidatorException {
		
		Method method = source.getMethod();
		
		Set<ConstraintViolation<Object>> constraintViolations = 
			method == null ? 
				executableValidator.validateConstructorParameters(source.getContructor(), value) : 
				executableValidator.validateParameters(factoryInstance, method, value);
				
		throwException(constraintViolations);
		
	}

	public void validate(ConstructorBean source, Object factoryInstance,
			Object value) throws ValidatorException {
		
		Method method = source.getMethod();
		
		Set<ConstraintViolation<Object>> constraintViolations = 
			method == null ? 
				executableValidator.validateConstructorReturnValue(source.getContructor(), value) : 
				executableValidator.validateReturnValue(factoryInstance, method, value);
				
		throwException(constraintViolations);
	}

	public void validate(PropertyBean source, Object beanInstance, Object value)
			throws ValidatorException {
		
		/*
		Method method = source.getBeanProperty().getSet();

		if (method != null) {
			Set<ConstraintViolation<Object>> constraintViolations = 
				executableValidator.validateParameters(beanInstance, method, new Object[] {value}, this.groups);
			
			throwException(constraintViolations);
		}
		*/
		
	}

	public void validate(PropertyController source, Object controllerInstance,
			Object value) throws ValidatorException {
		
		
		Method method = source.getBeanProperty().getSet();

		if (method != null) {
			Set<ConstraintViolation<Object>> constraintViolations = 
					executableValidator.validateParameters(controllerInstance, method, new Object[] {value});
			
			throwException(constraintViolations);
		}

	}

	public void validate(ParameterAction source, Object controllerInstance,
			Object value) throws ValidatorException {
		// not apply
	}

	public void validate(Action source, Object controller, Object[] value)
			throws ValidatorException {
		
		Method method = source.getMethod();
		
		if (method != null) {
			
			Set<ConstraintViolation<Object>> constraintViolations = 
				executableValidator.validateParameters(controller, method, value);
			
			throwException(constraintViolations);
		}
	}

	@Deprecated
	public void validate(Action source, Object controller, Object value)
			throws ValidatorException {
	}

	public void validate(ResultAction source, Object controller, Object value)
			throws ValidatorException {
		
		Method method = source.getAction().getMethod();

		if (method != null) {
			
			Set<ConstraintViolation<Object>> constraintViolations = 
				executableValidator.validateReturnValue(controller, method, value);
			
			throwException(constraintViolations);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void throwException(Set<ConstraintViolation<Object>> constraintViolations)
			throws ValidatorException {

		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object>[] cvs = constraintViolations.toArray(new ConstraintViolation[0]);

			ValidatorException ex = new ValidatorException();
			
			for (ConstraintViolation<Object> cv: cvs) {
				String path = cv.getPropertyPath().toString();
				String message = cv.getMessage();
				ValidatorException e = new ValidatorException(message);
				ex.addCause(path, e);
			}
			
			throw ex;
		}
	}

}
