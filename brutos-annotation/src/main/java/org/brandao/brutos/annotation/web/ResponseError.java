package org.brandao.brutos.annotation.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.web.HttpStatus;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseError {

	int	code() default HttpStatus.INTERNAL_SERVER_ERROR;

	Class<? extends Throwable>[] exception() default Throwable.class;
	
}
