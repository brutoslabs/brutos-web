package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnyKeyCollection {

	Basic metaBean();
	
	Class<?> metaType();

	MetaValue[] metaValues() default {};

	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;
	
}
