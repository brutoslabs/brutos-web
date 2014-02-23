/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.web;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManagerImp;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.util.WebUtil;

/**
 *
 * @author Brandao
 */
public class WebControllerManager extends ControllerManagerImp{
 
    public WebControllerManager(){
        super();
    }
    
    public ControllerBuilder addController( String id, String view, 
            DispatcherType dispatcherType, String name, Class classType, 
            String actionId, ActionType actionType ){
        
        
        ControllerBuilder builder =  
            super.addController( id, view,
                dispatcherType, name, classType, actionId, actionType );
        
        ActionType type = builder.getActionType();
        
        if(ActionType.PARAMETER.equals(type) || ActionType.HIERARCHY.equals(type))
            WebUtil.checkURI(builder.getId(), true);
        
        WebUtil.checkURI(builder.getView(), false);
        
        return new WebControllerBuilder(builder, this.internalUpdate);
    }
    
}
