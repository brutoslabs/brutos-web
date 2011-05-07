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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.Configuration;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.validator.RestrictionRules;
import org.brandao.brutos.InterceptorManager;
/**
 *
 * @author Afonso Brandao
 */
public class PropertyBuilder extends WebFrameBuilder{

    private Configuration config;
    
    public PropertyBuilder( Configuration config, Controller webFrame, WebFrameManager webFrameManager, InterceptorManager interceptorManager ){
        super( webFrame, webFrameManager, interceptorManager );
        this.config = config;
    }
    
    public PropertyBuilder addRestriction( RestrictionRules ruleId, Object value ){
        config.put( ruleId.toString(), value );
        return this;
    }

    public PropertyBuilder setMessage( String message ){
        config.setProperty("message", message );
        return this;
    }

}
