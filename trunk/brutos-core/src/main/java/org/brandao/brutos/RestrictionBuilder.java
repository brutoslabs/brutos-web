

package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.validator.RestrictionRules;


public class RestrictionBuilder{

    private Properties config;
    
    public RestrictionBuilder(Properties config){
        this.config = config;
    }

    
    public RestrictionBuilder addRestriction( RestrictionRules ruleId, Object value ){
        config.put( ruleId.toString(), value );
        return this;
    }

    
    
    public RestrictionBuilder setMessage( String message ){
        config.setProperty("message", message );
        return this;
    }

}
