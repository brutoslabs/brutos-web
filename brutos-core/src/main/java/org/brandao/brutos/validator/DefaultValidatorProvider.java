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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 * 
 * @author Afonso Brandao
 */
public class DefaultValidatorProvider extends ValidatorProvider{

    /*
    public static class DefaultValidator implements Validator{

        private Type integerType = Types.getType(Integer.class);

        private Properties config;

        public void configure(Properties config) {
            this.config = config;
        }

        public void validate(Object source, Object value) {
            Type valueType = null;

            if( source instanceof DependencyBean )
                valueType = ((DependencyBean)source).getType();
            else
            if( source instanceof UseBeanData )
                valueType = ((UseBeanData)source).getType();
            else
                throw new BrutosException( "invalid source: " + source );

            String message = config.getProperty( "message" );
            if( message != null ){
                List rules = RestrictionRules.getRestrictionRules();
                int size = rules.size();
                for( int i=0;i<size;i++ ){
                    RestrictionRules r = (RestrictionRules)rules.get(i);
                    String name = r.toString();
                    String val  = String.valueOf(config.get( name ));
                    message = message.replace( "${"+name+"}" , val );
                }
                message = message.replace( "${value}" , String.valueOf(value) );
            }
            else
                message = "";

            if( config.containsKey( RestrictionRules.REQUIRED.toString() ) ){
                if( value == null )
                    throw new ValidatorException( message );
            }

            if( config.containsKey( RestrictionRules.EQUAL.toString() ) ){
                Object tmp = valueType
                                .getValue(
                                config.get(RestrictionRules.EQUAL.toString()));

                if( tmp != null && !tmp.equals( value ) )
                    throw new ValidatorException( message );
            }

            if( value instanceof Number ){
                if( config.containsKey( RestrictionRules.MAX.toString() ) ){
                    Number tmp = (Number) valueType
                                    .getValue(
                                    config.get(RestrictionRules.MAX.toString()));

                    if( ((Number)value).doubleValue() > tmp.doubleValue() )
                        throw new ValidatorException( message );
                }
                
                if( config.containsKey( RestrictionRules.MIN.toString() ) ){
                    Number tmp = (Number) valueType
                                    .getValue(
                                    config.get(RestrictionRules.MIN.toString()));

                    if( ((Number)value).doubleValue() < tmp.doubleValue() )
                        throw new ValidatorException( message );
                }
            }

            if( value instanceof String ){
                if( config.containsKey( RestrictionRules.MAX_LENGTH.toString() ) ){
                    Number tmp = (Number) integerType
                                    .getValue(
                                    config.get(RestrictionRules.MAX_LENGTH.toString()));

                    if( ((String)value).length() > tmp.intValue() )
                        throw new ValidatorException( message );
                }

                if( config.containsKey( RestrictionRules.MIN_LENGTH.toString() ) ){
                    Number tmp = (Number) integerType
                                    .getValue(
                                    config.get(RestrictionRules.MIN_LENGTH.toString()));

                    if( ((String)value).length() < tmp.intValue() )
                        throw new ValidatorException( message );
                }

                if( config.containsKey( RestrictionRules.MATCHES.toString() ) ){
                    String tmp = (String) valueType
                                    .getValue(
                                    config.get(RestrictionRules.MATCHES.toString()));

                    if( !((String)value).matches(tmp) )
                        throw new ValidatorException( message );
                }

            }
            
        }

    }
    */

    public static final String PREFIX_NAME =
                                "org.brandao.brutos.validator.rules.";

    private Map rules;

    public void configure(Properties config){
        rules = new HashMap();
        load(config);
    }

    private void load(Properties config){
        List staticRules = RestrictionRules.getRestrictionRules();
        int size = staticRules.size();
        for( int i=0;i<size;i++ ){
            RestrictionRules ruleId = (RestrictionRules) staticRules.get(i);
            ValidationRule rule = getInstance(getClassName(ruleId.toString()));
            rules.put(ruleId.toString(), rule);
        }

        Iterator keys = config.stringPropertyNames().iterator();

        while( keys.hasNext() ){
            String key = (String) keys.next();
            if( key.startsWith(PREFIX_NAME) ){
                String name = key.substring(PREFIX_NAME.length(),key.length());
                ValidationRule rule = getInstance(config.getProperty(key));
                rules.put(name.toLowerCase(), rule);
            }
        }
    }

    private ValidationRule getInstance(String name){
        try{
            return (ValidationRule)ClassUtil.getInstance(ClassUtil.get(name));
            /*
            Class clazz = Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            return (ValidationRule)clazz.newInstance();
            */
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }

    private String getClassName(String name){
        return "org.brandao.brutos.validator." +
                getCanonicalName(name) + "ValidationRule";
    }
    
    private String getCanonicalName(String name){
        return
            Character.toString(name.charAt(0)).toUpperCase() +
            name.subSequence(1, name.length());
    }

    public Validator getValidator(Properties config) {
        Validator validator = new DefaultValidator(this.rules);
        validator.configure(config);
        return validator;
    }

}
