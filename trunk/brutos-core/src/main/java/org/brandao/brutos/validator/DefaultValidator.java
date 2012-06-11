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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Brandao
 */
public class DefaultValidator implements Validator{

    private Properties config;
    private Map mappedRules;
    private Map rules;
    private boolean initialized;

    public DefaultValidator(Map rules){
        this.mappedRules = rules;
    }

    public void configure(Properties config) {
        this.config      = config;
        this.initialized = false;
    }

    private void init(){
        this.rules = new HashMap();

        Iterator keys = config.stringPropertyNames().iterator();

        while( keys.hasNext() ){
            String key = (String) keys.next();
            if( !key.equals("message") ){
                ValidationRule rule = (ValidationRule) mappedRules.get(key);

                if( rule != null )
                    rules.put(key, rule);
            }
        }
        this.initialized = true;
    }

    protected String getMessage(Object value, Properties config){
        String message = config.getProperty( "message" );
        if( message != null ){
            Iterator r = rules.keySet().iterator();
            
            while( r.hasNext() ){
                String key = (String) r.next();
                String val  = String.valueOf(config.get( key ));
                message = message.replace( "${"+key+"}" , val );
            }
            message = message.replace( "${value}" , String.valueOf(value) );
        }
        else
            message = "";

        return message;
    }

    public void validate(Object source, Object value) throws ValidatorException {

        if( !this.initialized )
            this.init();
        
        Iterator c = rules.values().iterator();

        try{
            while( c.hasNext() ){
                ValidationRule rule = (ValidationRule) c.next();
                rule.validate(config, source, value);
            }
        }
        catch( ValidatorException e ){
            throw new ValidatorException( getMessage(value,config), e );
        }
    }

    public Properties getConfiguration() {
        return this.config;
    }

}
