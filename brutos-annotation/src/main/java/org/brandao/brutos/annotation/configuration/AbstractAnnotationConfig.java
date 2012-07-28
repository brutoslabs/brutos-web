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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.AnnotationConfig;

/**
 *
 * @author Brandao
 */
public abstract class AbstractAnnotationConfig implements AnnotationConfig{

    protected AnnotationConfigEntry annotation;
    
    private Converter sourceConverter;
    
    public void setConfiguration(AnnotationConfigEntry annotation){
        this.annotation = annotation;
    }
    
    public Object applyInternalConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        List<AnnotationConfigEntry> list = 
                getOrder(annotation.getNextAnnotationConfig());

        for(int i=0;i<list.size();i++){
            AnnotationConfigEntry next = list.get(i);
            if(next.getAnnotationConfig().isApplicable(source)){
                
                source = 
                        this.sourceConverter == null? 
                            source : 
                            this.sourceConverter.converter(source,applicationContext);
                
                builder = next.getAnnotationConfig()
                        .applyConfiguration(source, builder, applicationContext);
            }
        }
        
        return builder;
    }

    private List<AnnotationConfigEntry> getOrder(List<AnnotationConfigEntry> list){
        
        Class<? extends Annotation>[] order = getExecutionOrder();
        
        if(order.length == 0)
            return list;
        
        Map<Class,AnnotationConfigEntry> map = new HashMap<Class,AnnotationConfigEntry>();
        
        for(AnnotationConfigEntry ace: annotation.getNextAnnotationConfig())
            map.put(ace.getStereotype().target(), ace);
        
        
        List<AnnotationConfigEntry> result = new ArrayList<AnnotationConfigEntry>();
        
        for(Class target: getExecutionOrder()){
            AnnotationConfigEntry ace = map.get(target);
            
            if(ace == null)
                throw new BrutosException("not found target: @" + target.getSimpleName());
            
            result.add(ace);
        }
        
        for(AnnotationConfigEntry ace: annotation.getNextAnnotationConfig()){
            if(!map.containsKey(ace.getStereotype().target()))
                result.add(ace);
        }
            
        return result;
    }
    
    public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{};
    }

    public void setSourceConverter(Converter value){
        this.sourceConverter = value;
    }
    
    public Converter getSourceConverter(){
        return this.sourceConverter;
    }
    
}
