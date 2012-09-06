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


package org.brandao.brutos;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractScanner implements Scanner{

    protected List listClass;
    protected TypeFilter defaultFilter;
    protected List filters;
    protected String basePackage;
    protected boolean useDefaultFilter;
    
    public AbstractScanner(){
        this.listClass = new ArrayList();
        this.filters   = new ArrayList();
    }

    public void setConfiguration(Properties config){
        this.basePackage =  
            config.getProperty("base-package", "");
        this.useDefaultFilter =  
            Boolean.valueOf(config.getProperty("use-default-filter", "true"))
                    .booleanValue();
    }
    
    public void addFilter(TypeFilter filter){
        filters.add(filter);
    }

    public void setDefaultFilter( TypeFilter filter ){
        this.defaultFilter = filter;
    }

    protected boolean accepts(Class classe){
        if(!listClass.contains(classe)){
            Boolean value = 
                this.defaultFilter == null || !this.useDefaultFilter? 
                    Boolean.FALSE : 
                    this.defaultFilter.accepts(classe);
            
            for(int i=0;i<filters.size();i++){
                TypeFilter filter = (TypeFilter)filters.get(i);
                Boolean filterValue = filter.accepts(classe);
                if(filterValue != null){
                    if(!filterValue.booleanValue())
                        return false;
                    else
                        value = Boolean.TRUE;
                }
            }
            
            if(value != null && value.booleanValue())
                return true;
        }
        
        return false;
    }
    
    public List getClassList() {
        return listClass;
    }

}