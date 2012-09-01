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

package org.brandao.brutos.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
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

    public static final String 
        AnnotationApplicationContext = "org.brandao.brutos.annotation.AnnotationApplicationContext";
    
    public static final String 
        DefaultScannerFilter = "org.brandao.brutos.annotation.AnnotationFilter";
    
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

        loadAnnotationDefinition( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_ANNOTATION_CONFIG ) );
    }

    private void loadAnnotationDefinition( Element cp ){
        if( cp != null ){
            ApplicationContext aac = createApplicationContext(getDetectedClass());
            handler.setParent(aac);
            aac.configure(aac.getConfiguration());
        }
    }

    private List getDetectedClass(){
        Scanner scanner = new Scanner();
        scanner.load(Thread.currentThread().getContextClassLoader());
        scanner.setFilter(getFilter());
        return scanner.getClasses();
    }
    
    private ScannerFilter getFilter(){
        try{
            Class scannerFilterClass = ClassUtil.get(DefaultScannerFilter);
            return (ScannerFilter)ClassUtil.getInstance(scannerFilterClass);
        }
        catch(Exception e){
            throw new BrutosException(
                "can't initialize the scanner: " + DefaultScannerFilter,e);
        }
    }
    private ApplicationContext createApplicationContext(List detectedClass){

        Class clazz = getApplicationContextClass();

        if(ApplicationContext.class.isAssignableFrom(clazz)){
            try{
                ApplicationContext app =
                    (ApplicationContext) clazz
                        .getConstructor(new Class[]{Class[].class})
                            .newInstance(new Object[]{detectedClass.toArray()});
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
                TypeManager.register(type, (TypeFactory)ClassUtil.getInstance(factory));
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

}
