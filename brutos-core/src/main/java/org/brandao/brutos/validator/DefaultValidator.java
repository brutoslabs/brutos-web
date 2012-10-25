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
