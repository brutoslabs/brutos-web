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

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.ThrowSafe;

/**
 *
 * @author Brandao
 */
public class ThrowableEntry {
    
    private String view;
    
    private DispatcherType dispatcher;
    
    private Class<? extends Throwable> target;
    
    private String name;
    
    private boolean rendered;
    
    private boolean enabled;

    public ThrowableEntry(){
    }

    public ThrowableEntry(Class<? extends Throwable> target){
        this.target = target;
    }
    
    public ThrowableEntry(ThrowSafe value){
        this.dispatcher = "".equals(value.dispatcher())?
                null :
                DispatcherType.valueOf(value.dispatcher());
        
        this.enabled = value.enabled();
        this.name = "".equals(value.name().trim())?
                null : 
                value.name();
        
        this.rendered = value.rendered();
        this.target = value.target();
        this.view = value.view();
    }
    
    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public DispatcherType getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherType dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Class<? extends Throwable> getTarget() {
        return target;
    }

    public void setTarget(Class<? extends Throwable> target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}
