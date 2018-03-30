package org.brandao.brutos.passwordform.validation;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsValidator 
	implements ConstraintValidator<Equals, Object>{
	
    private String firstFieldName;
    
    private String secondFieldName;

    private String template;
    
    @Override
    public void initialize(Equals constraintAnnotation){
        firstFieldName 	= constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        template 		= constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context){
        try{
        	Class<?> clazz = value.getClass();
        	Field firstField  = clazz.getDeclaredField(firstFieldName);
        	Field secondField = clazz.getDeclaredField(secondFieldName);
        	
        	firstField.setAccessible(true);
        	secondField.setAccessible(true);
        	
            Object firstObj  = firstField.get(value);
            Object secondObj = secondField.get(value);
            
            boolean isValid = 
            		firstObj == null?
        				secondObj == null :
    					firstObj.equals(secondObj);
            
            if(!isValid){
            	context.disableDefaultConstraintViolation();
                context
                	.buildConstraintViolationWithTemplate(this.template)
                	.addPropertyNode(this.firstFieldName)
                	.addConstraintViolation();            	
            }
            
            return isValid;
        }
        catch(Exception e){
        	throw new IllegalStateException(e);
        }
    }

}
