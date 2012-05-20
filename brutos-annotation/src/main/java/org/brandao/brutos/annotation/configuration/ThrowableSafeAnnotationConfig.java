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

import java.lang.reflect.Method;
import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.CustomAnnotation;
import org.brandao.brutos.annotation.ThrowableSafe;

/**
 *
 * @author Brandao
 */
@CustomAnnotation(target=ThrowableSafe.class,depends=Action.class)
public class ThrowableSafeAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return source instanceof Method && 
               ((Method)source).isAnnotationPresent( ThrowableSafe.class );
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        Method method = (Method)source;
        ActionBuilder actionBuilder = (ActionBuilder)builder;
        ThrowableSafe throwableSafe = (ThrowableSafe)method.getAnnotation(ThrowableSafe.class);
        
        DispatcherType dispatcher = DispatcherType.valueOf(throwableSafe.dispatcher());
        String name = throwableSafe.name();
        Class<? extends Throwable> target = throwableSafe.target();
        String view = throwableSafe.view();
        
        actionBuilder = actionBuilder.addThrowable(target, view, name, dispatcher);
        
        return actionBuilder;
    }
    
}
