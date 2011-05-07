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

import java.util.List;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 * 
 * @author Afonso Brandao
 */
public class DefaultValidatorProvider extends ValidatorProvider{

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

    public void configure(Properties config) {
    }

    public Validator getValidator(Configuration config) {
        Validator validator = new DefaultValidator();
        validator.configure(config);
        return validator;
    }
    
}
