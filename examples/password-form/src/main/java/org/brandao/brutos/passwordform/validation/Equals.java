package org.brandao.brutos.passwordform.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualsValidator.class)
@Documented
public @interface Equals {

    String message() default "{javax.validation.constraints.Equals.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String first();
    
    String second();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
            @interface List
    {
    	Equals[] value();
    }
    
}
