package org.brandao.brutos.annotation.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.web.HttpStatus;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseStatus {

	int	code() default HttpStatus.INTERNAL_SERVER_ERROR;

	String	reason() default "";
	
	int	value() default HttpStatus.INTERNAL_SERVER_ERROR;
	
}
