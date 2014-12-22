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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.configuration.converters.InterceptorStackConverter;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.annotation.scanner.filter.ControllerFilter;
import org.brandao.brutos.annotation.scanner.filter.InterceptorFilter;
import org.brandao.brutos.annotation.scanner.filter.TypeTypeFilter;
import org.brandao.brutos.xml.FilterEntity;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Configuration.class, 
        sourceConverter=InterceptorStackConverter.class)
public class RootAnnotationConfig extends AbstractAnnotationConfig{

    public static final org.brandao.brutos.annotation.scanner.TypeFilter[] DEFAULT_FILTERS =
            new org.brandao.brutos.annotation.scanner.TypeFilter[]{
                new ControllerFilter(),
                new InterceptorFilter(),
                new TypeTypeFilter()
            };
    
    private static final List<Class> defaultAnnotationConfig = new ArrayList<Class>();
    
    static{
        //defaultAnnotationConfig.add(RootAnnotationConfig.class);
        defaultAnnotationConfig.add(ActionAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptsStackAnnotationConfig.class);
        defaultAnnotationConfig.add(BeanAnnotationConfig.class);
        defaultAnnotationConfig.add(KeyCollectionAnnotationConfig.class);
        defaultAnnotationConfig.add(ElementCollectionAnnotationConfig.class);
        defaultAnnotationConfig.add(ControllerAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptedByAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptsAnnotationConfig.class);
        defaultAnnotationConfig.add(RestrictionAnnotationConfig.class);
        defaultAnnotationConfig.add(RestrictionsAnnotationConfig.class);
        defaultAnnotationConfig.add(ThrowSafeAnnotationConfig.class);
        defaultAnnotationConfig.add(ThrowSafeListAnnotationConfig.class);
        defaultAnnotationConfig.add(TypeDefAnnotationConfig.class);
        defaultAnnotationConfig.add(IdentifyAnnotationConfig.class);
        defaultAnnotationConfig.add(ExtendedScopeAnnotationConfig.class);
    }
    
    public boolean isApplicable(Object source) {
        return true;
    }

    public Object applyConfiguration(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
        
        try{
            return applyConfiguration0(source, builder, componentRegistry);
        }
        catch(Throwable e){
            throw new BrutosException("failed to load configuration", e);
        }
        
    }
    
    public Object applyConfiguration0(Object source, Object builder, 
            ComponentRegistry componentRegistry) 
            throws InstantiationException, IllegalAccessException {
        
        ConfigurationEntry startConfig = (ConfigurationEntry)source;
        
        List<Class> classList = 
            startConfig.getAllClazz() != null && !startConfig.getAllClazz().isEmpty()?
                startConfig.getAllClazz() :
                this.loadConfiguration(
                        startConfig, 
                        componentRegistry, 
                        startConfig.getConfigClass());
        
        classList.addAll(defaultAnnotationConfig);
        
        AnnotationUtil
                .createAnnotationTree(
                        this,
                        this.applicationContext, 
                        Arrays.asList( new Class[]{RootAnnotationConfig.class}));
        
        Map<Class,AnnotationConfigEntry> map = new HashMap<Class,AnnotationConfigEntry>();

        for(AnnotationConfigEntry ace: super.annotation.getNextAnnotationConfig())
            map.put(ace.getStereotype().target(), ace);
        
        for(Class target: getExecutionOrder()){
            
            AnnotationConfigEntry ace = map.get(target);
            
            if(ace == null)
                throw new BrutosException("configuration not found: " + target);
            
            AnnotationConfig ac = ace.getAnnotationConfig();
            
            for(Object item: classList){
                if(ac.isApplicable(item))
                    ac.applyConfiguration(item, null, componentRegistry);
            }
        }
        
        return source;
    }
    
    @Override
    public Class<? extends Annotation>[] getExecutionOrder(){
        return new Class[]{
            TypeDef.class,
            Intercepts.class,
            InterceptsStack.class,
            Controller.class};
    }
    
    private List<Class> loadConfiguration(
            ConfigurationEntry startConfig,
            ComponentRegistry componentRegistry, List<Class> configurationClassList) 
            throws InstantiationException, IllegalAccessException{
        
        Set<Class> allClass = new HashSet<Class>();
        
        Scanner scanner = AnnotationUtil.createScanner(startConfig, DEFAULT_FILTERS);
        
        allClass.addAll(scanner.getClassList());
        
        if(configurationClassList == null)
            return new ArrayList<Class>();
        
        for(Class clazz: configurationClassList){
            
            if(clazz.isAnnotationPresent(ComponentScan.class)){
                ComponentScan componentScan = 
                        (ComponentScan) clazz.getAnnotation(ComponentScan.class);

                ConfigurationEntry configurationEntry = 
                        createConfigurationEntry(componentScan);

                scanner = AnnotationUtil.createScanner(configurationEntry, DEFAULT_FILTERS);
                allClass.addAll(scanner.getClassList());
            }
            
            if(Configurer.class.isAssignableFrom(clazz)){
                Configurer configurer = 
                        (Configurer) ClassUtil.getInstance(clazz);
                
                configurer.addScopes(componentRegistry);
                configurer.addTypes(componentRegistry);
                configurer.addInterceptors(componentRegistry);
                configurer.addControllers(componentRegistry);
            }
            
        }
        
        return new ArrayList<Class>(allClass);
    }
    
    private ConfigurationEntry createConfigurationEntry(ComponentScan componentScan){
        
        Class[] basePackageClass = componentScan.basePackage();
        String[] basePackage = componentScan.value();
        TypeFilter[] excludeFilter = componentScan.excludeFilters();
        TypeFilter[] includeFilters = componentScan.includeFilters();
        boolean useDefaultFilters = componentScan.useDefaultFilters();
        Class scannerClass = componentScan.scanner();

        ConfigurationEntry result = new ConfigurationEntry();
        result.setUseDefaultfilter(useDefaultFilters);
        result.setScannerClassName(scannerClass == Scanner.class? null : scannerClass.getName());
        
        List<String> basePackageList = new ArrayList<String>();
        
        for(Class c: basePackageClass)
            basePackageList.add(c.getPackage().getName());
        
        basePackageList.addAll(Arrays.asList(basePackage));
        
        result.setBasePackage(basePackageList);
        
        List<FilterEntity> excludeFilterList = new ArrayList<FilterEntity>();
        result.setExcludeFilters(excludeFilterList);
        for(TypeFilter e: excludeFilter){
            excludeFilterList.add(AnnotationUtil.toFilterEntity(e));
        }

        List<FilterEntity> includeFilterslist = new ArrayList<FilterEntity>();
        result.setIncludeFilters(includeFilterslist);
        for(TypeFilter e: includeFilters){
            includeFilterslist.add(AnnotationUtil.toFilterEntity(e));
        }
        
        return result;
    }
    
}
