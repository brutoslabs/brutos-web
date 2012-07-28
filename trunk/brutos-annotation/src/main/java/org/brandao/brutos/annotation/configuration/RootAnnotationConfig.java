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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Configuration.class)
public class RootAnnotationConfig extends AbstractAnnotationConfig{

    public boolean isApplicable(Object source) {
        return ((Class)source).isAnnotationPresent(TypeDef.class) ||
               ((Class)source).isAnnotationPresent(Intercepts.class) ||
               ((Class)source).isAnnotationPresent(Controller.class);
        
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        List<Class> classList = (List<Class>)source;
        
        Map<Class,AnnotationConfigEntry> map = new HashMap<Class,AnnotationConfigEntry>();

        for(AnnotationConfigEntry ace: super.annotation.getNextAnnotationConfig())
            map.put(ace.getStereotype().target(), ace);
        
        for(Class target: getExecutionOrder()){
            
            AnnotationConfigEntry ace = map.get(target);
            AnnotationConfig ac = ace.getAnnotationConfig();
            
            for(Class clazz: classList){
                if(ac.isApplicable(clazz))
                    ac.applyConfiguration(clazz, null, applicationContext);
            }
        }
        
        return source;
    }
    
    @Override
    public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{
            TypeDef.class,
            Intercepts.class,
            Controller.class};
    }
    
}
