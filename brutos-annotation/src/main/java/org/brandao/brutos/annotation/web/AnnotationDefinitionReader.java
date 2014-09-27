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

package org.brandao.brutos.annotation.web;

import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.ComponentConfigurer;
import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.annotation.scanner.TypeFilter;
import org.brandao.brutos.annotation.scanner.filter.ControllerFilter;
import org.brandao.brutos.annotation.scanner.filter.InterceptorFilter;
import org.brandao.brutos.annotation.scanner.filter.TypeTypeFilter;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.xml.FilterEntity;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;

/**
 *
 * @author Brandão
 */
public class AnnotationDefinitionReader 
    extends XMLComponentDefinitionReader {

    private static final TypeFilter[] DEFAULT_FILTERS =
            new TypeFilter[]{
                new ControllerFilter(),
                new InterceptorFilter(),
                new TypeTypeFilter()
            };
    private ComponentConfigurer componentConfigurer;
    
    private Scanner scanner;

    public AnnotationDefinitionReader(ConfigurableApplicationContext applicationContext, 
            ComponentRegistry componentRegistry) {
        super(componentRegistry);
        this.componentConfigurer = new ComponentConfigurer(applicationContext);
        this.scanner             = null;
    }

    public void loadDefinitions(){
        this.localConfig();
        this.scanner.scan();
        this.componentConfigurer.setComponentList(scanner.getClassList());
        this.componentConfigurer.init(this.componenetRegistry);
    }
    
    private void localConfig(){
        
        try{
            String scannerClassName = super.getScannerClassName();
            this.scanner = 
                StringUtil.isEmpty(scannerClassName)? 
                    new DefaultScanner() : 
                    (Scanner)ClassUtil.getInstance(scannerClassName);
        }
        catch(Throwable e){
            throw new BrutosException("failed to create scanner instance", e);
        }
                
        String[] basePackage = super.getBasePackage();
        
        this.scanner.setBasePackage(basePackage);
        
        if(super.isUseDefaultfilter()){
            TypeFilter[] filters = loadDefaultFilters();
            for(TypeFilter filter: filters){
                this.scanner.addIncludeFilter(filter);
            }
        }
        
        List<FilterEntity> excludeFilter = super.getExcludeFilters();
        
        for(FilterEntity filterDef: excludeFilter){
            TypeFilter filter = 
                getTypeFilter(filterDef.getExpression(), filterDef.getClassName());
            this.scanner.addExcludeFilter(filter);
        }
        
        List<FilterEntity> includeFilter = super.getIncludeFilters();
        
        for(FilterEntity filterDef: includeFilter){
            TypeFilter filter = 
                getTypeFilter(filterDef.getExpression(), filterDef.getClassName());
            this.scanner.addIncludeFilter(filter);
        }
        
    }
    
    protected TypeFilter[] loadDefaultFilters(){
        return DEFAULT_FILTERS;
    }
    
    private TypeFilter getTypeFilter(String expression, String className){
        try{
            Class scannerFilterClass;
            scannerFilterClass = ClassUtil.get(className);
            TypeFilter filter = (TypeFilter)ClassUtil.getInstance(scannerFilterClass);
            filter.setExpression(expression);
            return filter;
        }
        catch (Throwable ex) {
                throw new BrutosException(
                    "can't initialize the scanner: " + className,ex);
        }
    }

}
