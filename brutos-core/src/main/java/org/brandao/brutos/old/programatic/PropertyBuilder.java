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
