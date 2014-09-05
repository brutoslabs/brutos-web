/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.text.MessageFormat;
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

/**
 *
 * @author Brandão
 */
public class JSR303Validator implements Validator{

    private javax.validation.Validator objectValidator;
    private ExecutableValidator executableValidator;
    
    public void configure(Properties config) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.objectValidator = validatorFactory.getValidator();
        this.executableValidator = this.objectValidator.forExecutables();
    }

    public void validate(ConstructorArgBean source, Object value) throws ValidatorException {
        //not apply
    }

    public void validate(ConstructorBean source, Object[] value) throws ValidatorException {
        Set constraintViolations =
            executableValidator
                .validateConstructorParameters(source.getContructor(), value, new Class[]{});
        throwException(constraintViolations);
    }

    public void validate(ConstructorBean source, Object value) throws ValidatorException{
        Set constraintViolations =
            executableValidator
                .validateConstructorReturnValue(source.getContructor(), value, new Class[]{});
        throwException(constraintViolations);
    }
    
    public void validate(PropertyBean source, Object value) throws ValidatorException {
        Set constraintViolations = objectValidator.validate(value, new Class[]{});
        throwException(constraintViolations);
    }

    public void validate(PropertyController source, Object value) throws ValidatorException {
        Set constraintViolations = objectValidator.validate(value, new Class[]{});
        throwException(constraintViolations);
    }

    public void validate(ParameterAction source, Object value) throws ValidatorException {
        //not apply
    }

    public void validate(Action source, Object controller, Object[] value) throws ValidatorException {
        Set constraintViolations =
            executableValidator
                .validateParameters(
                        controller, 
                        source.getMethod(), 
                        value, new Class[]{});
        throwException(constraintViolations);
    }

    public void validate(Action source, Object controller, Object value) throws ValidatorException{
        Set constraintViolations =
            executableValidator
                .validateReturnValue(
                        controller, 
                        source.getMethod(), 
                        value, new Class[]{});
        throwException(constraintViolations);
    }

    protected void throwException(Set constraintViolations) throws ValidatorException{
        
        if(constraintViolations.isEmpty()){
            Object[] cvs = constraintViolations.toArray(new Object[]{});
            
            ValidatorException ex = new ValidatorException();
            for(int i=0;i<cvs.length;i++){
                ConstraintViolation cv = (ConstraintViolation) cvs[0];
                String errMsg = MessageFormat.format(
                        cv.getMessage(),
                        new Object[]{
                        cv.getRootBeanClass(),
                        cv.getPropertyPath().toString(), 
                        cv.getInvalidValue()});
                ValidatorException e = new ValidatorException(errMsg);
                ex.addCause(e);
            }
            throw ex;
        }
    }
    
}
