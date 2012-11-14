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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.scanner.DefaultScanner;
import org.brandao.brutos.scanner.Scanner;
import org.brandao.brutos.scanner.TypeFilter;
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

    private XMLParseUtil parseUtil;

    private ApplicationContext parent;
    
    public static final String 
        AnnotationApplicationContext = "org.brandao.brutos.annotation.AnnotationApplicationContext";
    
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
    
    public ContextDefinitionReader( ConfigurableApplicationContext handler,
            List blackList, ResourceLoader resourceLoader ){
        super( handler, resourceLoader );
        this.parseUtil = new XMLParseUtil();
    }

    public Element validate( Resource resource ){
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

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

    public void build( Element document ){

        loadContextParams(
            parseUtil.getElement(
                document, 
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAMS ) );

        loadTypes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_TYPES ) );

        Scanner scanner = 
                getScanner(parseUtil.getElement(
                    document,
                    XMLBrutosConstants.XML_BRUTOS_COMPONENT_SCAN ) );
        
        loadAnnotationDefinition( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_ANNOTATION_CONFIG ), scanner );
    }

    private Scanner getScanner(Element element){
        
        if(element == null)
            return null;
        
        String basePackage = element.getAttribute("base-package");
        boolean useDefaultfilter = "true".equals(element.getAttribute("use-default-filters"));
        
        Scanner scanner = new DefaultScanner();
        
        if(useDefaultfilter)
            loadDefaultFilters(scanner);
        
        Properties prop = new Properties();
        prop.setProperty("base-package", basePackage == null? "" : basePackage);
        prop.setProperty("use-default-filters", String.valueOf(useDefaultfilter));
        
        scanner.setConfiguration(prop);
        
        NodeList list = parseUtil.getElements(element, "exclude-filter");
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            TypeFilter filter = 
                getTypeFilter(expression, false, filterClassName, true);
            
            if(filter != null)
                scanner.addFilter(filter);
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
                scanner.addFilter(filter);
        }
        
        scanner.scan();
        
        return scanner;
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
    
    private void loadDefaultFilters(Scanner scanner){
        for(int i=0;i<DefaultFilters.length;i++){
            String filterName = DefaultFilters[i];
            TypeFilter filter = 
                getTypeFilter(null, true, filterName, 
                    !filterName.startsWith(BaseScannerAnnotationClass));
            
            
            if(filter != null)
                scanner.addFilter(filter);
        }
    }
    private void loadAnnotationDefinition( Element cp, Scanner scanner ){
        if( cp != null ){
            List classList = scanner == null? new ArrayList() : scanner.getClassList();
            parent = createApplicationContext(classList);
           parent.configure(handler.getConfiguration());
        }
    }

    private TypeFilter getTypeFilter(String expression, boolean include, 
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
    
    private ApplicationContext createApplicationContext(List detectedClass){

        Class clazz = getApplicationContextClass();

        if(ApplicationContext.class.isAssignableFrom(clazz)){
            try{
                Class[] arrayClass = new Class[detectedClass.size()];
                
                for(int i=0;i<detectedClass.size();i++)
                    arrayClass[i] = (Class)detectedClass.get(i);
                
                ApplicationContext app =
                    (ApplicationContext) clazz
                        .getConstructor(new Class[]{Class[].class})
                            .newInstance(new Object[]{arrayClass});
                return app;
            }
            catch( Exception e ){
                throw new BrutosException("unable to create instance: " +
                        clazz.getName(),e);
            }
        }
        else
            throw new BrutosException("annotation application is not valid:"+
                    clazz.getName());
    }

    private Class getApplicationContextClass(){
        return this.getContextClass(AnnotationApplicationContext);
    }

    private Class getContextClass( String contextClassName ){
        try {
            return Thread.currentThread().getContextClassLoader()
                    .loadClass(contextClassName);
        } catch (ClassNotFoundException ex) {
            throw new BrutosException( "Failed to load: " + contextClassName, ex );
        }
    }

    private void loadContextParams( Element cp ){

        if( cp == null )
            return;
        
        Properties config = handler.getConfiguration();

        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAM );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String name  = parseUtil.getAttribute(c, "name" );
            String value = parseUtil.getAttribute(c,"value");

            value = value == null? c.getTextContent() : value;
            
            config.setProperty(name, value);
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
            String name  = parseUtil.getAttribute(c,"class-type" );
            String value = parseUtil.getAttribute(c,"factory");

            value = value == null? c.getTextContent() : value;

            Class type;
            Class factory;

            try{
                type = ClassUtil.get(name);
                factory = ClassUtil.get(value);
                TypeManager.register(type, (TypeFactory)ClassUtil.getInstance(factory/*,super.handler*/));
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }

        }

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
        Resource resource = this.resourceLoader.getResource(location);
        this.loadDefinitions(resource);
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public ApplicationContext getParent() {
        return parent;
    }

}
