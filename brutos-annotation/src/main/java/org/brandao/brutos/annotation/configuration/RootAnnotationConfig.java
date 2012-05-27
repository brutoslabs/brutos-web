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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Configuration.class)
public class RootAnnotationConfig extends AbstractAnnotationConfig{

    private List<AnnotationConfigEntry> root;
    
    public boolean isApplicable(Object source) {
        return ((Class)source).isAnnotationPresent(TypeDef.class) ||
               ((Class)source).isAnnotationPresent(Intercepts.class) ||
               ((Class)source).isAnnotationPresent(Controller.class);
        
    }

    public Object applyConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {
        
        List<Class> classList = (List<Class>)source;
        List<Class> stereotypeClassList = (List<Class>)builder;
        
        createAnnotationTree(stereotypeClassList);
        
        AnnotationConfigEntry rootAnnotationConfigEntry = new AnnotationConfigEntry();
        rootAnnotationConfigEntry.setAnnotationConfig(this);
        rootAnnotationConfigEntry.setNextAnnotationConfig(root);
        this.setConfiguration(rootAnnotationConfigEntry);

        Map<Class,AnnotationConfigEntry> map = new HashMap<Class,AnnotationConfigEntry>();

        for(AnnotationConfigEntry ace: root)
            map.put(ace.getStereotype().target(), ace);
        
        for(Class target: getExecutionOrder()){
            
            if(target == Configuration.class)
                continue;
            
            for(Class clazz: classList){
                AnnotationConfigEntry ace = map.get(target);
                ace.getAnnotationConfig().applyConfiguration(clazz, null, applicationContext);
            }
        }
        
        return source;
    }
    
    protected void createAnnotationTree(List<Class> list) {
        
        Map<Class,AnnotationConfigEntry> configMap = getAnnotationConfigEntry(list);
        root = new LinkedList<AnnotationConfigEntry>();
        
        for(AnnotationConfigEntry ace: configMap.values()){
            Class<? extends Annotation>[] after = ace.getStereotype().executeAfter();
            for(Class<? extends Annotation> an: after){
                AnnotationConfigEntry beforeEntry = configMap.get(an);
                if(beforeEntry == null)
                    throw new BrutosException("not found: " + an.getName());
                
                beforeEntry.getNextAnnotationConfig().add(ace);
            }
            
            if(after.length == 0)
                root.add(ace);
        }
    }
    
    protected Map<Class,AnnotationConfigEntry> getAnnotationConfigEntry(List<Class> list){
        Map<Class,AnnotationConfigEntry> map = 
                new HashMap<Class,AnnotationConfigEntry>();

        for(Class clazz: list){
            Stereotype st = 
                    (Stereotype) clazz.getAnnotation(Stereotype.class);
            
            AnnotationConfigEntry current = map.get(st.target());
            AnnotationConfigEntry newConfig = getAnnotationConfig(clazz);
            
            if(current != null){
                boolean override = 
                    newConfig.getStereotype().majorVersion() >= current.getStereotype().majorVersion() &&
                    newConfig.getStereotype().minorVersion() > current.getStereotype().minorVersion();
                if( override )
                    map.put(st.target(), newConfig);
            }
            else
                map.put(st.target(), newConfig);
        }
        
        return map;
    }
    
    protected AnnotationConfigEntry getAnnotationConfig(Class stereotype) {
        
        try{
            AnnotationConfig ac = (AnnotationConfig) ClassUtil.getInstance(stereotype);
            AnnotationConfigEntry r = new AnnotationConfigEntry();
            r.setAnnotationConfig(ac);
            r.setStereotype((Stereotype)stereotype.getAnnotation(Stereotype.class));
            r.setNextAnnotationConfig(new LinkedList<AnnotationConfigEntry>());

            return r;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }
    
    @Override
    public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{
            TypeDef.class,
            Intercepts.class,
            Controller.class};
    }
    
}
