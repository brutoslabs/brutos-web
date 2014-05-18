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

import org.brandao.brutos.BrutosConstants;
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
import org.brandao.brutos.xml.XMLBrutosConstants;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
        scanner.scan();
        componentConfigurer.setComponentList(scanner.getClassList());
        componentConfigurer.init(this.componenetRegistry);
    }
    
    
    protected void build( Element document ){
        super.build(document);

        localAnnotationConfig(parseUtil.getElement(
                    document,
                    XMLBrutosConstants.XML_BRUTOS_COMPONENT_SCAN ) );
    }
    
    private void localAnnotationConfig(Element element){
        
        if(element == null)
            return;
        
        if(this.scanner != null)
            throw new BrutosException("scanner has been defined");
            
        try{
            String scannerClassName = element.getAttribute("scanner-class");
            this.scanner = 
                StringUtil.isEmpty(scannerClassName)? 
                    new DefaultScanner() : 
                    (Scanner)ClassUtil.getInstance(scannerClassName);
        }
        catch(Throwable e){
            throw new BrutosException("failed to create scanner instance", e);
        }
                
        String basePackageText = element.getAttribute("base-package");
        
        this.scanner.setBasePackage(
                StringUtil.isEmpty(basePackageText)?
                    new String[]{} :
                StringUtil.getArray(basePackageText, BrutosConstants.COMMA)
        );
        
        boolean useDefaultfilter = "true".equals(element.getAttribute("use-default-filters"));
        
        if(useDefaultfilter){
            TypeFilter[] filters = loadDefaultFilters();
            for(TypeFilter filter: filters){
                this.scanner.addIncludeFilter(filter);
            }
        }
        
        NodeList list = parseUtil.getElements(element, "exclude-filter");
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            TypeFilter filter = 
                getTypeFilter(expression, filterClassName);
            
            this.scanner.addExcludeFilter(filter);
        }
        
        list = parseUtil.getElements(element, "include-filter");
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            TypeFilter filter = 
                getTypeFilter(expression, filterClassName);
            
            this.scanner.addIncludeFilter(filter);
        }
        
    }
    
    private String getFilterClassName(String expression, String type){
        if(XMLBrutosConstants.XML_BRUTOS_CUSTOM_FILTER.equals(type))
            return expression;
        else{
            String name = 
                type.length() > 1?
                Character.toUpperCase(type.charAt(0)) + type.substring(1) :
                type;
            return "org.brandao.brutos.annotation.scanner.filter." + name + "TypeFilter";
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
