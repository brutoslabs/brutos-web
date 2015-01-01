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
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.TypeFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Brandao
 */
public class ContextDefinitionReader 
    extends AbstractXMLDefinitionReader{

    private final XMLParseUtil parseUtil;

    private String scannerClassName;
    
    private String[] basePackage;
    
    private boolean useDefaultfilter;
    
    private List excludeFilters;
    
    private List includeFilters;
    
    protected Element rootElement;
    
    public ContextDefinitionReader(ComponentRegistry componenetRegistry){
        super(componenetRegistry);
        this.scannerClassName = null;
        this.parseUtil = new XMLParseUtil(XMLBrutosConstants.XML_BRUTOS_CONTEXT_NAMESPACE);
        this.useDefaultfilter = true;
        this.excludeFilters = new ArrayList();
        this.includeFilters = new ArrayList();
        this.basePackage = new String[]{""};
    }
    
    public void loadDefinitions(Resource resource) {
        Element document = this.buildDocument(resource, 
                new String[]{
                    ResourceLoader.CLASSPATH_URL_PREFIX + 
                            XMLBrutosConstants.XML_BRUTOS_CONTEXT_SCHEMA});
        this.buildComponents(document, resource);
    }
    
    protected void buildComponents(Element document, Resource resource){
        loadTypes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_TYPES ) );

        loadScopes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_EXTENDED_SCOPES ) );
        
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
            
            super.componentRegistry.registerProperty(name, value);
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
            String value = parseUtil.getAttribute(c,"factory");

            value = value == null? c.getTextContent() : value;

            Class factory;

            try{
                factory = ClassUtil.get(value);
                this.componentRegistry.registerType(
                    (TypeFactory)ClassUtil.getInstance(factory));
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }

        }
    }

    private void loadScopes( Element cp ){

        if( cp == null )
            return;
        
        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_EXTENDED_SCOPE );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String name      = parseUtil.getAttribute(c,"name");
            String className = parseUtil.getAttribute(c,"class");

            className = className == null? c.getTextContent() : className;

            try{
                Class scope = ClassUtil.get(className);
                this.componentRegistry.registerScope(name, 
                        (Scope) ClassUtil.getInstance(scope));
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
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            excludeFilters.add(
                    new FilterEntity(type, 
                           Arrays.asList( 
                                StringUtil.getArray(expression, BrutosConstants.COMMA))));
        }
        
        list = parseUtil.getElements(element, "include-filter");
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            includeFilters.add(
                    new FilterEntity(type, 
                           Arrays.asList( 
                                StringUtil.getArray(expression, BrutosConstants.COMMA))));
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
