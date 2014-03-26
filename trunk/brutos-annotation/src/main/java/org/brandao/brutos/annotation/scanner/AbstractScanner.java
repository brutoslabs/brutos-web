/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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


package org.brandao.brutos.annotation.scanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractScanner implements Scanner{

    protected Set listClass;
    protected Set filters;
    private String[] basePackage;
    
    public AbstractScanner(){
        this.listClass = new HashSet();
        this.filters   = new HashSet();
    }

    public void addFilter(TypeFilter filter){
        filters.add(filter);
    }

    public void removeFilter(TypeFilter filter){
        filters.remove(filter);
    }
    
    protected boolean accepts(String resource){
        if(!listClass.contains(resource)){
            
            Boolean value = null;
            
            Iterator i = filters.iterator();
            while(i.hasNext()){
                TypeFilter filter = (TypeFilter)i.next();
                Boolean filterValue = filter.accepts(resource);
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
        return new ArrayList(this.listClass);
    }

    public String[] getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String[] basePackage) {
        this.basePackage = basePackage;
    }

}