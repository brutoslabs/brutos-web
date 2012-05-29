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

package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.annotation.configuration.*;

/**
 *
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends AbstractApplicationContext{
    
    private Class[] allClazz;
    private List<Class> annotationConfig;
    private List<Class> compositeClassList;
    
    public AnnotationApplicationContext(Class[] clazz) {
        this.allClazz = clazz;
    }

    public AnnotationApplicationContext(Class[] clazz,
            AbstractApplicationContext parent) {
        super(parent);
        this.allClazz = clazz;
    }

    @Override
    public void configure( Properties config ) {
        super.configure(config);
        postConfigure();
    }

    private void postConfigure(){
        
        loadDefaultAnnotationConfig();
        
        compositeClassList = new ArrayList<Class>();
        
        compositeClassList.addAll(this.annotationConfig);
        compositeClassList.addAll(Arrays.asList(this.allClazz));
        
        AnnotationConfig rootAnnotationConfig = getRootAnnotationConfig();
        
        rootAnnotationConfig
                .applyConfiguration(Arrays.asList(this.allClazz), 
                    compositeClassList, this);
    }
    
    @Override
    public void destroy(){
    }
 
    protected AnnotationConfig getRootAnnotationConfig(){
        Class<AnnotationConfig> rootConfigClass = null;
        
        for(Class clazz: this.compositeClassList){
            Stereotype newSt = (Stereotype) clazz.getAnnotation(Stereotype.class);
            if(newSt != null && newSt.target() == Configuration.class){
                        
                if(rootConfigClass != null){
                    Stereotype st = (Stereotype) rootConfigClass.getAnnotation(Stereotype.class);
                    boolean override = 
                        newSt.majorVersion() >= st.majorVersion() &&
                        newSt.minorVersion() > st.minorVersion();
                    
                    if(override)
                        rootConfigClass = clazz;
                }
                else
                    rootConfigClass = clazz;
            }
        }
        
        if(rootConfigClass == null)
            throw new BrutosException(
                    "not found: " + Configuration.class.getSimpleName());
        
        try{
            AnnotationConfig rootAnnotationConfig = 
                    (AnnotationConfig)ClassUtil.getInstance(rootConfigClass);
            return rootAnnotationConfig;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }
    
    protected void loadDefaultAnnotationConfig(){
        annotationConfig = new ArrayList<Class>();
        
        annotationConfig.add(RootAnnotationConfig.class);
        annotationConfig.add(ActionAnnotationConfig.class);
        annotationConfig.add(ActionParamAnnotationConfig.class);
        annotationConfig.add(BeanAnnotationConfig.class);
        annotationConfig.add(ControllerAliasAnnotationConfig.class);
        annotationConfig.add(ControllerAnnotationConfig.class);
        annotationConfig.add(InterceptedByAnnotationConfig.class);
        annotationConfig.add(InterceptsAnnotationConfig.class);
        annotationConfig.add(PropertyAnnotationConfig.class);
        annotationConfig.add(RestrictionAnnotationConfig.class);
        annotationConfig.add(RestrictionsAnnotationConfig.class);
        annotationConfig.add(ThrowableSafeAnnotationConfig.class);
        annotationConfig.add(TypeDefAnnotationConfig.class);
    }
}
