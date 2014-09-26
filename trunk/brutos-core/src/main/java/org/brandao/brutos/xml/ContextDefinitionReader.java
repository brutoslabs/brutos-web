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

package org.brandao.brutos.xml;

import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeFactory;
import org.brandao.brutos.type.TypeManager;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Brandao
 */
public class ContextDefinitionReader 
    extends AbstractXMLDefinitionReader{

    protected XMLParseUtil parseUtil;

    private String scannerClassName;
    
    private String[] basePackage;
    
    private boolean useDefaultfilter;
    
    private List excludeFilters;
    
    private List includeFilters;
    
    public ContextDefinitionReader(ComponentRegistry componenetRegistry){
        super(componenetRegistry);
        this.scannerClassName = null;
    }
    
    public void loadDefinitions(Resource resource) {
        Element document = this.buildDocument(resource, 
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_SCHEMA);
        this.buildComponents(document, resource);
    }
    
    protected void buildComponents(Element document, Resource resource){
        loadTypes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_TYPES ) );
        
        loadContextParams(
            parseUtil.getElement(
                document, 
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAMS ) );
        
        localAnnotationConfig(parseUtil.getElement(
                    document,
                    XMLBrutosConstants.XML_BRUTOS_COMPONENT_SCAN ) );       
    }

    private void loadContextParams( Element cp ){

        if( cp == null )
            return;
        
        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAM );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String name  = parseUtil.getAttribute(c, "name" );
            String value = parseUtil.getAttribute(c,"value");

            value = value == null? c.getTextContent() : value;
            
            super.componenetRegistry.registerProperty(name, value);
        }
        
    }
    
    private void loadTypes( Element cp ){

        if( cp == null )
            return;
        
        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_TYPE );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            //String name  = parseUtil.getAttribute(c,"class-type" );
            String value = parseUtil.getAttribute(c,"factory");

            value = value == null? c.getTextContent() : value;

            //Class type;
            Class factory;

            try{
                factory = ClassUtil.get(value);
                TypeManager.register((TypeFactory)ClassUtil.getInstance(factory/*,super.handler*/));
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }

        }
    }

    private void localAnnotationConfig(Element element){
        
        if(element == null)
            return;
        
        if(this.getScannerClassName() != null)
            throw new BrutosException("scanner has been defined");
            
        this.setScannerClassName(element.getAttribute("scanner-class"));
                
        String basePackageText = element.getAttribute("base-package");
        
        this.setBasePackage(
                StringUtil.isEmpty(basePackageText)?
                    new String[]{""} :
                StringUtil.getArray(basePackageText, BrutosConstants.COMMA)
        );
        
        this.setUseDefaultfilter(
                "true".equals(element.getAttribute("use-default-filters")));
        
        NodeList list = parseUtil.getElements(element, "exclude-filter");
        
        List excludeFilterList = new ArrayList();
        this.setExcludeFilters(excludeFilterList);
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            excludeFilterList.add(new FilterEntity(filterClassName, expression));
        }
        
        list = parseUtil.getElements(element, "include-filter");
        
        List includeFilterList = new ArrayList();
        this.setIncludeFilters(includeFilterList);
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            includeFilterList.add(new FilterEntity(filterClassName, expression));
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
    
    public String getScannerClassName() {
        return scannerClassName;
    }

    public void setScannerClassName(String scannerClassName) {
        this.scannerClassName = scannerClassName;
    }

    public String[] getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String[] basePackage) {
        this.basePackage = basePackage;
    }

    public boolean isUseDefaultfilter() {
        return useDefaultfilter;
    }

    public void setUseDefaultfilter(boolean useDefaultfilter) {
        this.useDefaultfilter = useDefaultfilter;
    }

    public List getExcludeFilters() {
        return excludeFilters;
    }

    public void setExcludeFilters(List excludeFilters) {
        this.excludeFilters = excludeFilters;
    }

    public List getIncludeFilters() {
        return includeFilters;
    }

    public void setIncludeFilters(List includeFilters) {
        this.includeFilters = includeFilters;
    }
    
}
