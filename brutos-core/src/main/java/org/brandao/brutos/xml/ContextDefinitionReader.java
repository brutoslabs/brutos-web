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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeFactory;
import org.brandao.brutos.type.TypeManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Brandao
 */
public class ContextDefinitionReader extends AbstractDefinitionReader{

    protected XMLParseUtil parseUtil;

    private String[] basePackage;
    
    private String[] includeFilters;
    
    private String[] excludeFilters;

    private boolean useDefaultFilters;
    
    public static final String 
        BaseScannerClass = "org.brandao.brutos.scanner.";

    public static final String 
        BaseScannerAnnotationClass = "org.brandao.brutos.annotation.scanner.";
    
    public static final String[] DefaultFilters = new String[]{
        BaseScannerClass + "ControllerFilter",
        BaseScannerClass + "InterceptorFilter",
        BaseScannerClass + "TypeTypeFilter",
        BaseScannerAnnotationClass + "AnnotationControllerFilter",
        BaseScannerAnnotationClass + "AnnotationInterceptorFilter",
        BaseScannerAnnotationClass + "AnnotationTypeTypeFilter"
    }; 
    
    public ContextDefinitionReader(ComponentRegistry componenetRegistry){
        super(componenetRegistry);
    }
    
    protected Element validate( Resource resource ){
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        URL schemaURL = Thread.currentThread()
            .getContextClassLoader()
                .getResource( XMLBrutosConstants.XML_BRUTOS_CONTEXT_SCHEMA );

        try{
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setValidating(true);
            documentBuilderFactory.setAttribute(
                    XMLBrutosConstants.JAXP_SCHEMA_LANGUAGE,
                    XMLBrutosConstants.W3C_XML_SCHEMA
            );

            documentBuilderFactory.setAttribute(
                    XMLBrutosConstants.JAXP_SCHEMA_SOURCE,
                    schemaURL.toString()
            );
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputStream in = resource.getInputStream();
            Document xmlDocument =
                documentBuilder
                    .parse(new InputSource(in));
            return xmlDocument.getDocumentElement();
        }
        catch (BrutosException ex) {
            throw ex;
        }
        catch (SAXParseException ex) {
            throw new BrutosException(
                     "Line " + ex.getLineNumber() + " in XML document from "
                     + resource + " is invalid", ex);
        }
        catch (SAXException ex) {
             throw new BrutosException("XML document from " + resource +
                     " is invalid", ex);
        }
        catch (ParserConfigurationException   ex) {
             throw new BrutosException("Parser configuration exception parsing "
                     + "XML from " + resource, ex);
        }
        catch (IOException ex) {
             throw new BrutosException("IOException parsing XML document from "
                     + resource, ex);
        }
        catch (Throwable ex) {
             throw new BrutosException("Unexpected exception parsing XML document "
                     + "from " + resource, ex);
        }
    }

    protected void build( Element document ){
        loadTypes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_TYPES ) );
        
        localAnnotationConfig(parseUtil.getElement(
                    document,
                    XMLBrutosConstants.XML_BRUTOS_COMPONENT_SCAN ) );
        
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
                //type = ClassUtil.get(name);
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
        
        String basePackageText = element.getAttribute("base-package");
        basePackageText = basePackageText == null? "" : basePackageText;
        
        this.useDefaultFilters = "true".equals(element.getAttribute("use-default-filters"));
        
        NodeList list = parseUtil.getElements(element, "exclude-filter");
        this.excludeFilters = new String[list.getLength()];
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            this.excludeFilters[i] = expression;
            Object filter = 
                getTypeFilter(expression, false, filterClassName, true);
            
            if(filter != null)
                filterList.add(filter);
        }
        
        list = parseUtil.getElements(element, "include-filter");
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            TypeFilter filter = 
                getTypeFilter(expression, true, filterClassName, true);
            
            if(filter != null)
                getFilters().add(filter);
        }

        this.basePackage         = StringUtil.getArray(basePackageText, ",");
        
    }
    
    private String getFilterClassName(String expression, String type){
        if(XMLBrutosConstants.XML_BRUTOS_CUSTOM_FILTER.equals(type))
            return expression;
        else{
            String name = 
                type.length() > 1?
                Character.toUpperCase(type.charAt(0)) + type.substring(1) :
                type;
            return BaseScannerClass + name + "TypeFilter";
        }
    }
    
    private List loadDefaultFilters(){
        
        List filters = new ArrayList();
        
        for(int i=0;i<DefaultFilters.length;i++){
            String filterName = DefaultFilters[i];
            TypeFilter filter = 
                getTypeFilter(null, true, filterName, 
                    !filterName.startsWith(BaseScannerAnnotationClass));
            
            
            if(filter != null)
                filters.add(filter);
        }
        
        return filters;
    }
    
    private String getTypeFilter(String expression, boolean include, 
        String className, boolean required){
        
        Properties prop = new Properties();
        prop.setProperty("filter-type", include? "include" : "exclude");
        
        if(expression != null)
            prop.setProperty("expression", expression);
        
        try{
            
            Class scannerFilterClass;
            scannerFilterClass = ClassUtil.get(className);
            TypeFilter filter = (TypeFilter)ClassUtil.getInstance(scannerFilterClass);
            filter.setConfiguration(prop);
            return filter;
        }
        catch (ClassNotFoundException ex) {
            if(required)
                throw new BrutosException(
                    "class not found: " + className,ex);
        } 
        catch (InstantiationException ex) {
            if(required)
                throw new BrutosException(
                    "can't initialize the scanner: " + className,ex);
        } 
        catch (IllegalAccessException ex) {
            if(required)
                throw new BrutosException(
                    "can't initialize the scanner: " + className,ex);
        }
        
        return null;
    }
    
    public void loadDefinitions(Resource resource) {
        Element document = this.validate(resource);
        this.build(document);
    }

    public void loadDefinitions(Resource[] resource) {
        if( resource != null )
            for( int i=0;i<resource.length;i++ )
                this.loadDefinitions(resource[i]);
    }

    public void loadDefinitions(String[] locations) {
        if( locations != null )
            for( int i=0;i<locations.length;i++ )
                this.loadDefinitions(locations[i]);
    }

    public void loadDefinitions(String location) {
        Resource resource = this.componenetRegistry.getResource(location);
        this.loadDefinitions(resource);
    }

    public ResourceLoader getResourceLoader() {
        return this.componenetRegistry;
    }

    /**
     * @return the basePackage
     */
    public String[] getBasePackage() {
        return basePackage;
    }

    /**
     * @return the filters
     */
    public String[] getFilters() {
        return filters;
    }

    /**
     * @return the includeFilters
     */
    public String[] getIncludeFilters() {
        return includeFilters;
    }

    /**
     * @return the excludeFilters
     */
    public String[] getExcludeFilters() {
        return excludeFilters;
    }

    /**
     * @return the useDefaultFilters
     */
    public boolean isUseDefaultFilters() {
        return useDefaultFilters;
    }

}
