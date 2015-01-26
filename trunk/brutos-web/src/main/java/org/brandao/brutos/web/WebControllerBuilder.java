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

import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.util.WebUtil;

/**
 *
 * @author Brandao
 */
public class WebControllerBuilder extends ControllerBuilder{
    
    public WebControllerBuilder(ControllerBuilder builder, ControllerManager.InternalUpdate internalUpdate){
        super( builder, internalUpdate );
    }
    
    public WebControllerBuilder( Controller controller, ControllerManager controllerManager,
            InterceptorManager interceptorManager, ValidatorFactory validatorFactory,
            ConfigurableApplicationContext applicationContext, ControllerManager.InternalUpdate internalUpdate ){
        super( controller, controllerManager, interceptorManager, 
                validatorFactory, applicationContext, internalUpdate );
    }
    
    public ControllerBuilder addAlias( String id ){
        WebUtil.checkURI(id,true);
        return super.addAlias(id);
    }
    
    public ActionBuilder addAction( String id, String resultId, boolean resultRendered, String view, 
            DispatcherType dispatcher, boolean resolvedView, String executor ){
        
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type)){
            WebUtil.checkURI(id, true);
            
            if(resolvedView && view != null)
                WebUtil.checkURI(view, true);
        }
        
        
        ActionBuilder builder =
            super.addAction(id, resultId, resultRendered, view, 
            dispatcher, resolvedView, executor);
        
        WebUtil.checkURI(builder.getView(), false);
        
        return new WebActionBuilder(builder);
    }
    
    public ControllerBuilder addThrowable( Class target, String view, String id, 
            DispatcherType dispatcher, boolean resolvedView ){
        
        ActionType type = this.controller.getActionType();
        
        if(!ActionType.PARAMETER.equals(type)){
            WebUtil.checkURI(id, true);
            
            if(resolvedView && view != null)
                WebUtil.checkURI(view, true);
        }

        return super.addThrowable( target, view, id, dispatcher, resolvedView );
    }
    
    public ControllerBuilder setDefaultAction( String id ){
        WebUtil.checkURI(id,true);
        return super.setDefaultAction(id);
    }
    
    public ControllerBuilder setId(String value){
        WebUtil.checkURI(value,true);
        return super.setId(value);
    }
    
    public ControllerBuilder setView(String value, boolean resolvedView){
        
        if(this.controller.isResolvedView())
            WebUtil.checkURI(value,true);
     
        return super.setView(value, resolvedView);
    }
    
}
