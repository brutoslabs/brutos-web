/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.validator;

import java.util.Properties;
import junit.framework.TestCase;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.type.IntegerType;
import org.brandao.brutos.type.StringType;

/**
 *
 * @author Brandao
 */
public class ValidatorProviderTest extends TestCase{

    public void testEqualRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());
        
        Properties config = new Properties();
        config.setProperty(RestrictionRules.EQUAL.toString(), "100");

        Validator v = vp.getValidator(config);
        v.validate(d, new Integer(100));
    }

    public void testNotEqualRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.EQUAL.toString(), "100");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, new Integer(10));
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }

    public void testMinRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MIN.toString(), "10");

        Validator v = vp.getValidator(config);
        v.validate(d, new Integer(10));
    }

    public void testNotMinRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.EQUAL.toString(), "10");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, new Integer(9));
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }

    public void testMinLengthRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MINLENGTH.toString(), "10");

        Validator v = vp.getValidator(config);
        v.validate(d, "AAAAAAAAAA");
    }

    public void testNotMinLengthRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MINLENGTH.toString(), "10");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, "AAAAAAAAA");
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }

    public void testMaxRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MAX.toString(), "4");

        Validator v = vp.getValidator(config);
        v.validate(d, new Integer(4));
    }

    public void testNotMaxRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new IntegerType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MAX.toString(), "4");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, new Integer(10));
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }

    public void testMaxLengthRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MAXLENGTH.toString(), "4");

        Validator v = vp.getValidator(config);
        v.validate(d, "AAAA");
    }

    public void testNotMaxLengthRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MAXLENGTH.toString(), "4");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, "AAAAAA");
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }


    public void testMatchesRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MATCHES.toString(), "\\d+");

        Validator v = vp.getValidator(config);
        v.validate(d, "10");
    }

    public void testNotMatchesRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.MATCHES.toString(), "\\d+");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, "AA");
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){

        }
    }

    public void testRequiredRule(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.REQUIRED.toString(), "true");

        Validator v = vp.getValidator(config);
        v.validate(d, "10");
    }

    public void testRequiredRuleError(){
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(new Properties());

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.REQUIRED.toString(), "true");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, null);
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){
        }
    }


    public void testCustomRule(){
        Properties configProvider = new Properties();
        configProvider.setProperty(
            "org.brandao.brutos.validator.rules.range",
            CustomValidationRule.class.getName());
        
        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(configProvider);

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty("range", "10-20");

        Validator v = vp.getValidator(config);
        v.validate(d, new Integer(10));
        v.validate(d, new Integer(20));
        v.validate(d, new Integer(15));
    }

    public void testErrorCustomRule(){
        Properties configProvider = new Properties();
        configProvider.setProperty(
            "org.brandao.brutos.validator.rules.range",
            CustomValidationRule.class.getName());

        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(configProvider);

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty("range", "10-20");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, new Integer(9));
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){
        }

        try{
            v.validate(d, new Integer(21));
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){
        }

    }

    public void testMessage(){
        Properties configProvider = new Properties();

        ValidatorProvider vp =
                ValidatorProvider.getValidatorProvider(configProvider);

        DependencyBean d = new DependencyBean(new Bean(new Controller(null)));
        d.setType(new StringType());

        Properties config = new Properties();
        config.setProperty(RestrictionRules.EQUAL.toString(), "teste");
        config.setProperty("message", "expected: ${equal} found: ${value}");

        Validator v = vp.getValidator(config);
        try{
            v.validate(d, "AA");
            fail("expected ValidatorException");
        }
        catch( ValidatorException e ){
            assertEquals("expected: teste found: AA",e.getMessage());
        }

    }

    public static class CustomValidationRule implements ValidationRule{

        private Integer start;
        
        private Integer end;
        
        public void validate(Object source, Object value)
                throws ValidatorException {

            if( !(value instanceof Number) )
                throw new ValidatorException("invalid type: " +  value.getClass());

            Integer val = new Integer(((Number)value).intValue());
            if( val.intValue() < start.intValue() || val.intValue() > end.intValue() )
                throw new ValidatorException();
        }

        public void setConfiguration(Properties config) {
            String range = config.getProperty("range");
            
            if( !range.matches("\\d+-\\d+") )
                throw new ValidatorException("invalid range syntax: " +  range );

            String[] vals = range.split("-");
            this.start = Integer.valueOf(vals[0]);
            this.end = Integer.valueOf(vals[1]);
        }

    }
}
