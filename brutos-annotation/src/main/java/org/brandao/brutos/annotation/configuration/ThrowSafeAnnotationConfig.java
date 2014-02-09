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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.ThrowSafe;

/**
 *
 * @author Brandao
 */
@Stereotype(target=ThrowSafe.class,executeAfter={Action.class,Controller.class})
public class ThrowSafeAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof ThrowableEntry;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
    
        try{
            return applyConfiguration0(source, builder, applicationContext);
        }
        catch(Exception e){
            throw 
                new BrutosException(
                        "can't create mapping exception",
                        e );
        }
        
    }
    
    public Object applyConfiguration0(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        if(builder instanceof ActionBuilder)
            addThrowSafe((ActionBuilder)builder, applicationContext, (ThrowableEntry)source);
        else
            addThrowSafe((ControllerBuilder)builder, applicationContext, (ThrowableEntry)source);
        
        return builder;
    }
    
    protected void addThrowSafe(ActionBuilder actionBuilder, 
            ConfigurableApplicationContext applicationContext, ThrowableEntry throwSafe){
        
        if(throwSafe.isEnabled()){
            actionBuilder
                    .addThrowable(
                        throwSafe.getTarget(), 
                        throwSafe.isRendered()? 
                            getView(actionBuilder, applicationContext, throwSafe) : 
                            null,
                        throwSafe.getName(), 
                        throwSafe.getDispatcher());
        }
    }
    
    protected void addThrowSafe(ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, ThrowableEntry throwSafe){
        
        if(throwSafe.isEnabled()){
            controllerBuilder
                    .addThrowable(
                        throwSafe.getTarget(), 
                        throwSafe.isRendered()? 
                            getView(controllerBuilder, applicationContext, throwSafe) : 
                            null,
                        throwSafe.getName(), 
                        throwSafe.getDispatcher());
        }
        
    }
    
    protected String getView(ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, ThrowableEntry throwSafe){
        return 
            throwSafe.isResolved()? 
                throwSafe.getView() : 
                applicationContext.
                        getViewResolver()
                        .getView(
                                controllerBuilder, 
                                null, 
                                throwSafe.getTarget(), 
                                throwSafe.getView());
    }

    protected String getView(ActionBuilder actionBuilder, 
            ConfigurableApplicationContext applicationContext, ThrowableEntry throwSafe){
        return
            throwSafe.isResolved()?
                throwSafe.getView() : 
                applicationContext.
                        getViewResolver()
                        .getView(
                                actionBuilder.getControllerBuilder(), 
                                actionBuilder, 
                                throwSafe.getTarget(), 
                                throwSafe.getView());
    }
    
}
