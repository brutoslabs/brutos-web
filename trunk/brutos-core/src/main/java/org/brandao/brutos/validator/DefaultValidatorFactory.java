

package org.brandao.brutos.validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ValidatorFactory;


public class DefaultValidatorFactory implements ValidatorFactory{
    
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
            
            if(ruleId.equals(RestrictionRules.CUSTOM))
                continue;
            
            Class rule = getClass(ruleId.toString(), false);
            rules.put(ruleId.toString(), rule);
        }

        Iterator keys = config.stringPropertyNames().iterator();

        while( keys.hasNext() ){
            String key = (String) keys.next();
            if( key.startsWith(PREFIX_NAME) ){
                String name = key.substring(PREFIX_NAME.length(),key.length());
                Class rule = getClass(config.getProperty(key), true);
                rules.put(name.toLowerCase(), rule);
            }
        }
    }

    private Class getClass(String name, boolean resolved){
        try{
            String className = resolved? name : getClassName(name);
            return ClassUtil.get(className);
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

    public void destroy() {
    }
    
}
