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
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.RestrictionRules;
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
public class ControllerDefinitionReader extends AbstractDefinitionReader{

    private XMLParseUtil parseUtil;
    private List blackList;
    
    public ControllerDefinitionReader( ConfigurableApplicationContext handler,
            List blackList, ResourceLoader resourceLoader ){
        super( handler, resourceLoader );
        this.parseUtil = new XMLParseUtil();
        this.blackList = blackList;
    }

    public Element validate(Resource resource){
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        URL schemaURL = Thread.currentThread()
            .getContextClassLoader()
                .getResource( XMLBrutosConstants.XML_BRUTOS_CONTROLLER_SCHEMA );

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

            Document xmlDocument =
                documentBuilder
                    .parse(new InputSource(resource.getInputStream()));
            
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
        catch (ParserConfigurationException ex) {
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


    public void build(Element document, Resource resource){
        
        loadInterceptors( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_INTERCEPTORS ) );

        loadControllers( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_CONTROLLERS ) );

        loadControllers( parseUtil.getElements(
                document,
                XMLBrutosConstants.XML_BRUTOS_CONTROLLER ) );

        loadImporters( parseUtil.getElements(
                document,
                XMLBrutosConstants.XML_BRUTOS_IMPORTER ), resource );
        
    }

    private void loadImporters( NodeList list, Resource resource ){

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String dependencyName = parseUtil.getAttribute(c, "resource" );

            if( dependencyName != null && dependencyName.length() != 0 ){

                try{
                    Resource dependencyResource =
                            resource.getRelativeResource(dependencyName);
                    
                    if( blackList.contains(dependencyResource) )
                        continue;
                    else
                        blackList.add(dependencyResource);

                    this.loadDefinitions( dependencyResource );
                }
                catch( BrutosException ex ){
                    throw ex;
                }
                catch( Throwable ex ){
                    throw new BrutosException( ex );
                }

            }
                
        }

    }

    private void loadInterceptors( Element e ){

        if( e == null )
            return;
        
        NodeList list = parseUtil
            .getElements(
                e,
                XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR );

        loadInterceptor( list );

        NodeList listStack = parseUtil
            .getElements(
                e,
                XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_STACK );

        loadInterceptorStack( listStack );

    }

    private void loadInterceptor( NodeList list ){

        if( list == null )
            return;
        
        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);

            String name       = parseUtil.getAttribute(c,"name" );
            String clazzName  = parseUtil.getAttribute(c,"class" );
            Boolean isDefault = Boolean.valueOf(parseUtil.getAttribute(c,"default" ));
            Class clazz;

            try{
                clazz = Class.forName(
                            clazzName,
                            true,
                            Thread.currentThread().getContextClassLoader() );
            }
            catch( Exception ex ){
                throw new BrutosException( ex );
            }

            InterceptorManager interceptorManager =
                handler.getInterceptorManager();

            InterceptorBuilder interceptorBuilder =
                interceptorManager.addInterceptor(
                    name,
                    clazz,
                    isDefault.booleanValue());

            NodeList listParam = parseUtil
                .getElements(
                    c,
                    XMLBrutosConstants.XML_BRUTOS_PARAM );

            for( int k=0;k<listParam.getLength();k++ ){
                Element paramNode = (Element) listParam.item(k);

                String paramName  = parseUtil.getAttribute(paramNode,"name" );
                String paramValue = parseUtil.getAttribute(paramNode,"value" );

                paramValue =
                    paramValue == null?
                        paramNode.getTextContent() :
                        paramValue;

                interceptorBuilder.addParameter(paramName, paramValue);
            }

        }
    }

    private void loadInterceptorStack( NodeList list ){

        if( list == null )
            return;
        
        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);

            String name       = parseUtil.getAttribute(c,"name" );
            Boolean isDefault = Boolean.valueOf(parseUtil.getAttribute(c,"default" ));

            InterceptorManager interceptorManager =
                handler.getInterceptorManager();

            InterceptorStackBuilder interceptorStackBuilder =
                interceptorManager.addInterceptorStack(
                    name,
                    isDefault.booleanValue());

            NodeList listInterceptorRef = parseUtil
                .getElements(
                    c,
                    XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_REF );

            for( int j=0;j<listInterceptorRef.getLength();j++ ){
                Element interceptorRefNode =
                    (Element) listInterceptorRef.item(j);

                String interceptorRefName = 
                        parseUtil.getAttribute(interceptorRefNode,  "name" );

                interceptorStackBuilder.addInterceptor( interceptorRefName );

                NodeList listParam = parseUtil
                    .getElements(
                        interceptorRefNode,
                        XMLBrutosConstants.XML_BRUTOS_PARAM );

                for( int k=0;k<listParam.getLength();k++ ){
                    Element paramNode = (Element) listParam.item(k);

                    String paramName  = parseUtil.getAttribute(paramNode,"name" );
                    String paramValue = parseUtil.getAttribute(paramNode,"value" );

                    paramValue =
                        paramValue == null?
                            paramNode.getTextContent() :
                            paramValue;

                    interceptorStackBuilder.addParameter(paramName, paramValue);
                }

            }
        }
    }

    private void loadControllers( Element controllersNode ){

        if( controllersNode == null )
            return;
        
        NodeList controllers = parseUtil
            .getElements(
                controllersNode,
                XMLBrutosConstants.XML_BRUTOS_CONTROLLER );


        loadControllers( controllers );
    }

    private void loadControllers( NodeList controllers ){
        for( int i=0;i<controllers.getLength();i++ ){
            Element controllerNode = (Element) controllers.item(i);
            loadController( controllerNode );
        }
    }
    private void loadController( Element controller ){

        String id = parseUtil.getAttribute(controller,"id" );
        
        ActionType actionType = 
            ActionType.valueOf( parseUtil.getAttribute(controller,"action-type" ) );
        
        DispatcherType dispatcher =
            DispatcherType.valueOf( parseUtil.getAttribute(controller,"dispatcher" ) );
        String view = parseUtil.getAttribute(controller,"view" );
        String name = parseUtil.getAttribute(controller,"name" );
        String clazzName = parseUtil.getAttribute(controller,"class" );
        //ScopeType scope =
        //    ScopeType.valueOf( controller.parseUtil.getAttribute(c,  "scope" ) );
        String actionId = parseUtil.getAttribute(controller,"action-id" );
        String defaultAction = parseUtil.getAttribute(controller,"default-action" );

        Class clazz = null;
        try{
            clazz = ClassUtil.get(clazzName);
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }
        ControllerManager controllerMaanger = handler.getControllerManager();

        ControllerBuilder controllerBuilder =
            controllerMaanger.addController(
                id,
                view,
                dispatcher,
                name,
                clazz,
                actionId,
                actionType);

        controllerBuilder.setDefaultAction(defaultAction);

        loadAliasController(
            parseUtil
                .getElements(
                    controller,
                    XMLBrutosConstants.XML_BRUTOS_ALIAS ),
            controllerBuilder );

        addInterceptorController(
            parseUtil
                .getElements(
                    controller,
                    XMLBrutosConstants.XML_BRUTOS_INTERCEPTOR_REF ),
            controllerBuilder );

        addBeans(
            parseUtil
                .getElements(
                    controller,
                    XMLBrutosConstants.XML_BRUTOS_BEAN ),
            controllerBuilder );

        addProperties(
            parseUtil
                .getElements(
                    controller,
                    XMLBrutosConstants.XML_BRUTOS_CONTROLLER_PROPERTY ),
            controllerBuilder );

        addActions(
            parseUtil
                .getElements(
                    controller,
                    XMLBrutosConstants.XML_BRUTOS_ACTION ),
            controllerBuilder );

        addThrowSafe(
            parseUtil.getElements(
                controller,
                XMLBrutosConstants.XML_BRUTOS_THROWS),
            controllerBuilder
            );

    }

    private void loadAliasController( NodeList aliasNode, 
            ControllerBuilder controllerBuilder ){

        for( int i=0;i<aliasNode.getLength();i++ ){
            Element c = (Element) aliasNode.item(i);
            controllerBuilder.addAlias( c.getTextContent() );
        }
        
    }

    private void addInterceptorController( NodeList interceptorList,
            ControllerBuilder controllerBuilder ){

        for( int j=0;j<interceptorList.getLength();j++ ){
            Element interceptorRefNode =
                (Element) interceptorList.item(j);

            String interceptorRefName =
                    parseUtil.getAttribute(interceptorRefNode,"name" );

            InterceptorBuilder interceptorBuilder = 
                    controllerBuilder.addInterceptor( interceptorRefName );

            NodeList listParam = parseUtil
                .getElements(
                    interceptorRefNode,
                    XMLBrutosConstants.XML_BRUTOS_PARAM );

            for( int k=0;k<listParam.getLength();k++ ){
                Element paramNode = (Element) listParam.item(k);

                String paramName  = parseUtil.getAttribute(paramNode,"name" );
                String paramValue = parseUtil.getAttribute(paramNode,"value" );

                paramValue =
                    paramValue == null?
                        paramNode.getTextContent() :
                        paramValue;

                interceptorBuilder.addParameter(paramName, paramValue);
            }
        }
    }

    private void addBeans( NodeList beanList,
            ControllerBuilder controllerBuilder ){

        for( int k=0;k<beanList.getLength();k++ ){
            Element beanNode = (Element) beanList.item(k);
            addBean(beanNode, controllerBuilder, null);
        }

    }

    private void addBean( Element beanNode,
            ControllerBuilder controllerBuilder, String propertyName ){

        String name          = parseUtil.getAttribute(beanNode,"name" );
        String separator     = parseUtil.getAttribute(beanNode,"separator" );
        String indexFormat   = parseUtil.getAttribute(beanNode,"index-format" );
        String factory       = parseUtil.getAttribute(beanNode,"factory" );
        String methodFactory = parseUtil.getAttribute(beanNode,"method-factory" );
        String target        = parseUtil.getAttribute(beanNode,"target" );

        Class clazz = null;
        try{
            clazz = Class.forName(
                        target,
                        true,
                        Thread.currentThread().getContextClassLoader() );
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        BeanBuilder beanBuilder =
            propertyName != null?
                controllerBuilder.buildProperty(propertyName, clazz) :
                controllerBuilder.buildMappingBean(name, clazz);

        beanBuilder.setFactory(factory);
        beanBuilder.setMethodfactory(methodFactory);
        beanBuilder.setSeparator(separator);
        beanBuilder.setIndexFormat(indexFormat);

        buildBean( beanNode, beanBuilder );

    }

    private void addBean( Element beanNode,
            BeanBuilder bean, String name, String propertyName, boolean key,
            boolean element ){

        //String name          = parseUtil.getAttribute(beanNode,"name" );
        String separator     = parseUtil.getAttribute(beanNode,"separator" );
        String indexFormat   = parseUtil.getAttribute(beanNode,"index-format" );
        String factory       = parseUtil.getAttribute(beanNode,"factory" );
        String methodFactory = parseUtil.getAttribute(beanNode,"method-factory" );
        String target        = parseUtil.getAttribute(beanNode,"target" );

        Class clazz = null;
        try{
            clazz = Class.forName(
                        target,
                        true,
                        Thread.currentThread().getContextClassLoader() );
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        BeanBuilder beanBuilder;

        if( key )
            beanBuilder = bean.buildKey( clazz );
        else
        if( element )
            beanBuilder = bean.buildElement( clazz );
        else
            beanBuilder =
                propertyName == null?
                    bean.buildConstructorArg(name, clazz) :
                    bean.buildProperty(name == null? propertyName : name, propertyName, clazz);

        beanBuilder.setFactory(factory);
        beanBuilder.setMethodfactory(methodFactory);
        beanBuilder.setSeparator(separator);
        beanBuilder.setIndexFormat(indexFormat);

        buildBean( beanNode, beanBuilder );

    }

    private void addBean( Element beanNode,
            ActionBuilder actionBuilder, Class paramType){

        String separator     = parseUtil.getAttribute(beanNode,"separator" );
        String indexFormat   = parseUtil.getAttribute(beanNode,"index-format" );
        String factory       = parseUtil.getAttribute(beanNode,"factory" );
        String methodFactory = parseUtil.getAttribute(beanNode,"method-factory" );
        String target        = parseUtil.getAttribute(beanNode,"target" );
        
        Class clazz = null;
        try{
            clazz = ClassUtil.get(target);/*Class.forName(
                        target,
                        true,
                        Thread.currentThread().getContextClassLoader() );*/
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        BeanBuilder beanBuilder = actionBuilder.buildParameter(paramType,clazz);

        beanBuilder.setFactory(factory);
        beanBuilder.setMethodfactory(methodFactory);
        beanBuilder.setSeparator(separator);
        beanBuilder.setIndexFormat(indexFormat);

        buildBean( beanNode, beanBuilder );

    }

    private void buildBean( Element beanNode, BeanBuilder beanBuilder ){
        buildConstructorBean(
            parseUtil
                .getElements(
                    beanNode,
                    XMLBrutosConstants.XML_BRUTOS_BEAN_CONSTRUCTOR_ARG ),
            beanBuilder
                );

        buildPropertiesBean(
            parseUtil
                .getElements(
                    beanNode,
                    XMLBrutosConstants.XML_BRUTOS_BEAN_PROPERY ),
            beanBuilder
                );


        Element keyNode =
            parseUtil
                .getElement(
                    beanNode,
                    XMLBrutosConstants.XML_BRUTOS_MAP_KEY );

        if( keyNode != null )
            buildKeyCollection(keyNode, beanBuilder);
            //addBean(keyNode, beanBuilder, null, null, true, false);
        else
        if( beanBuilder.isMap() )
            throw new BrutosException("key node is required in Map" );

        Element elementNode =
            parseUtil
                .getElement(
                    beanNode,
                    XMLBrutosConstants.XML_BRUTOS_COLLECTION_ELEMENT );
        
        if( elementNode != null )
            buildElementCollection(elementNode, beanBuilder);
            //addBean(elementNode, beanBuilder, null, null, false, true);
        else
        if( beanBuilder.isMap() )
            throw new BrutosException("element node is required in Collection");
        
    }

    private void buildConstructorBean( NodeList consList,
            BeanBuilder beanBuilder ){

        for( int k=0;k<consList.getLength();k++ ){
            Element conNode = (Element) consList.item(k);

            String enumPropertyName = parseUtil.getAttribute(conNode, "enum-property");
            EnumerationType enumProperty =
                EnumerationType.valueOf(enumPropertyName);
            String value = parseUtil.getAttribute(conNode, "value");
            String temporalProperty = parseUtil.getAttribute(conNode, "temporal-property");
            String bean = parseUtil.getAttribute(conNode, "bean");
            boolean mapping = Boolean
                .valueOf(parseUtil.getAttribute(conNode, "mapping")).booleanValue();

            String scopeName = parseUtil.getAttribute(conNode, "scope");
            ScopeType scope = ScopeType.valueOf(scopeName);
            String factoryName = parseUtil.getAttribute(conNode, "factory");
            String typeName = parseUtil.getAttribute(conNode, "type");
            Type factory = null;
            boolean nullable = false;
            Class type = null;

            Element mappingRef     = parseUtil.getElement(conNode,"ref");
            Element beanNode       = parseUtil.getElement(conNode,"bean");
            Element valueNode      = parseUtil.getElement(conNode,"value");
            Element validatorNode  = parseUtil.getElement(conNode,"validator");
            Element nullNode       = parseUtil.getElement(conNode, "null");
            if( mappingRef != null ){
                enumProperty =
                    EnumerationType.valueOf(
                        parseUtil.getAttribute(mappingRef, "enum-property"));
                
                value = parseUtil.getAttribute(mappingRef,  "value" );
                temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
                bean = parseUtil.getAttribute(mappingRef,  "bean" );
                mapping = Boolean
                    .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
                scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
                factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
                validatorNode = parseUtil.getElement( mappingRef, "validator");
            }
            else
            if( beanNode != null ){
                addBean( beanNode, beanBuilder, bean, null, false, false);
                continue;
            }
            else
            if( valueNode != null ){
                value = valueNode.getTextContent();
            }
            else
            if( nullNode != null )
                nullable = true;

            try{
                if( factoryName != null ){
                    factory = (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                    /*
                    factory = (Type)Class.forName(
                                factoryName,
                                true,
                                Thread.currentThread().getContextClassLoader() )
                                    .newInstance();
                    */
                }
                
                if(typeName != null)
                    type = ClassUtil.get(typeName);

            }
            catch( Exception ex ){
                throw new BrutosException( ex );
            }

            ConstructorBuilder constructorBuilder =
                    beanBuilder.addContructorArg(
                        bean,
                        enumProperty,
                        temporalProperty,
                        mapping? bean : null,
                        scope,
                        value,
                        nullable,
                        factory,
                        type);

            addValidator(validatorNode, constructorBuilder);
        }

    }

    private void buildPropertiesBean( NodeList consList,
            BeanBuilder beanBuilder ){

        for( int k=0;k<consList.getLength();k++ ){
            Element propNode = (Element) consList.item(k);

            String propertyName = parseUtil.getAttribute(propNode,  "name" );
            EnumerationType enumProperty =
                EnumerationType.valueOf( parseUtil.getAttribute(propNode,  "enum-property" ) );
            String value = parseUtil.getAttribute(propNode,  "value" );
            String temporalProperty = parseUtil.getAttribute(propNode,  "temporal-property" );
            String bean = parseUtil.getAttribute(propNode,  "bean" );
            boolean mapping = Boolean
                .valueOf(parseUtil.getAttribute(propNode,  "mapping" )).booleanValue();
            ScopeType scope = ScopeType.valueOf( parseUtil.getAttribute(propNode,  "scope" ) );
            String factoryName = parseUtil.getAttribute(propNode,  "factory" );
            Type factory = null;
            boolean nullable = false;
            
            Element mappingRef = parseUtil.getElement( propNode, "ref");
            Element beanNode   = parseUtil.getElement( propNode, "bean");
            Element valueNode  = parseUtil.getElement( propNode, "value");
            Element validatorNode = parseUtil.getElement( propNode, "validator" );
            Element nullNode      = parseUtil.getElement(propNode, "null");
            if( mappingRef != null ){
                enumProperty =
                    EnumerationType.valueOf( parseUtil.getAttribute(mappingRef,  "enum-property" ) );
                value = parseUtil.getAttribute(mappingRef,  "value" );
                temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
                bean = parseUtil.getAttribute(mappingRef,  "bean" );
                mapping = Boolean
                    .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
                scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
                factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
                validatorNode = parseUtil.getElement( mappingRef, "validator");
            }
            else
            if( beanNode != null ){
                addBean( beanNode, beanBuilder, bean, propertyName, false, false );
                continue;
            }
            else
            if( valueNode != null ){
                value = valueNode.getTextContent();
            }
        else
        if( nullNode != null )
            nullable = true;

            try{
                if( factoryName != null ){
                    factory = (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                    /*
                    factory = (Type)Class.forName(
                                factoryName,
                                true,
                                Thread.currentThread().getContextClassLoader() )
                                    .newInstance();
                    */
                }
            }
            catch( Exception ex ){
                throw new BrutosException( ex );
            }

            PropertyBuilder propertyBuilder =
                    beanBuilder.addProperty(
                        bean,
                        propertyName,
                        enumProperty,
                        temporalProperty,
                        mapping? bean : null,
                        scope,
                        value,
                        nullable,
                        factory);

            addValidator(validatorNode, propertyBuilder);
        }
    }

    private void addProperties(
        NodeList properrties,
        ControllerBuilder controllerBuilder ){

        for( int k=0;k<properrties.getLength();k++ ){
            Element propNode = (Element) properrties.item(k);
            buildPropertyController(propNode, controllerBuilder);
        }
    }

    private void buildPropertyController( Element propNode,
            ControllerBuilder controllerBuilder ){

        String propertyName = parseUtil.getAttribute(propNode,  "property-name" );
        EnumerationType enumProperty =
            EnumerationType.valueOf( parseUtil.getAttribute(propNode,  "enum-property" ) );
        String value = parseUtil.getAttribute(propNode,  "value" );
        String temporalProperty = parseUtil.getAttribute(propNode,  "temporal-property" );
        String bean = parseUtil.getAttribute(propNode,  "bean" );
        boolean mapping = Boolean
            .valueOf(parseUtil.getAttribute(propNode,  "mapping" )).booleanValue();
        ScopeType scope = ScopeType.valueOf( parseUtil.getAttribute(propNode,  "scope" ) );
        String factoryName = parseUtil.getAttribute(propNode,  "factory" );
        Type factory = null;
        boolean nullable = false;

        Element mappingRef = parseUtil.getElement( propNode, "ref");
        Element beanNode   = parseUtil.getElement( propNode, "bean");
        Element valueNode  = parseUtil.getElement( propNode, "value");
        Element validatorNode = parseUtil.getElement( propNode, "validator" );
        Element nullNode      = parseUtil.getElement(propNode, "null");
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf( parseUtil.getAttribute(mappingRef,  "enum-property" ) );
            value = parseUtil.getAttribute(mappingRef,  "value" );
            temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
            bean = parseUtil.getAttribute(mappingRef,  "bean" );
            mapping = Boolean
                .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
            scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
            factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
            validatorNode = parseUtil.getElement( mappingRef, "validator");
        }
        else
        if( beanNode != null ){
            addBean( beanNode, controllerBuilder, propertyName );
            return;
        }
        else
        if( valueNode != null ){
            value = valueNode.getTextContent();
        }
        else
        if( nullNode != null )
            nullable = true;

        try{
            if( factoryName != null ){
                factory = (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                /*
                factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();
                */
            }
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        PropertyBuilder propertyBuilder =
                controllerBuilder.addProperty(
                    propertyName,
                    bean,
                    scope,
                    enumProperty,
                    temporalProperty,
                    mapping? bean : null,
                    value,
                    nullable,
                    factory);

        addValidator(validatorNode, propertyBuilder);

    }

    private void buildKeyCollection( Element conNode,
            BeanBuilder beanBuilder ){

        String enumPropertyName = parseUtil.getAttribute(conNode, "enum-property");
        EnumerationType enumProperty =
            EnumerationType.valueOf(enumPropertyName);
        String value = parseUtil.getAttribute(conNode, "value");
        String temporalProperty = parseUtil.getAttribute(conNode, "temporal-property");
        String bean = parseUtil.getAttribute(conNode, "bean");
        boolean mapping = Boolean
            .valueOf(parseUtil.getAttribute(conNode, "mapping")).booleanValue();

        String scopeName = parseUtil.getAttribute(conNode, "scope");
        ScopeType scope = ScopeType.valueOf(scopeName);
        String factoryName = parseUtil.getAttribute(conNode, "factory");
        String typeName = parseUtil.getAttribute(conNode, "type");
        Type factory = null;
        boolean nullable = false;
        Class type = null;

        Element mappingRef     = parseUtil.getElement(conNode,"ref");
        Element beanNode       = parseUtil.getElement(conNode,"bean");
        Element valueNode      = parseUtil.getElement(conNode,"value");
        Element validatorNode  = parseUtil.getElement(conNode,"validator");
        Element nullNode       = parseUtil.getElement(conNode, "null");
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf(
                    parseUtil.getAttribute(mappingRef, "enum-property"));

            value = parseUtil.getAttribute(mappingRef,  "value" );
            temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
            bean = parseUtil.getAttribute(mappingRef,  "bean" );
            mapping = Boolean
                .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
            scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
            factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
            validatorNode = parseUtil.getElement( mappingRef, "validator");
        }
        else
        if( beanNode != null ){
            addBean( beanNode, beanBuilder, bean, null, true, false);
            return;
        }
        else
        if( valueNode != null ){
            value = valueNode.getTextContent();
        }
        else
        if( nullNode != null )
            nullable = true;

        try{
            if( factoryName != null ){
                factory = (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                /*
                factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();
                */
            }

            if(typeName != null)
                type = ClassUtil.get(typeName);

        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        RestrictionBuilder keyBuilder =
                beanBuilder
                .setKey(
                    bean, 
                    enumProperty, 
                    temporalProperty, 
                    mapping? bean : null, 
                    scope, 
                    value, 
                    factory, 
                    type);

        addValidator(validatorNode, keyBuilder);

    }

    private void buildElementCollection( Element conNode,
            BeanBuilder beanBuilder ){

        String enumPropertyName = parseUtil.getAttribute(conNode, "enum-property");
        EnumerationType enumProperty =
            EnumerationType.valueOf(enumPropertyName);
        String value = parseUtil.getAttribute(conNode, "value");
        String temporalProperty = parseUtil.getAttribute(conNode, "temporal-property");
        String bean = parseUtil.getAttribute(conNode, "bean");
        boolean mapping = Boolean
            .valueOf(parseUtil.getAttribute(conNode, "mapping")).booleanValue();

        String scopeName = parseUtil.getAttribute(conNode, "scope");
        ScopeType scope = ScopeType.valueOf(scopeName);
        String factoryName = parseUtil.getAttribute(conNode, "factory");
        String typeName = parseUtil.getAttribute(conNode, "type");
        Type factory = null;
        boolean nullable = false;
        Class type = null;

        Element mappingRef     = parseUtil.getElement(conNode,"ref");
        Element beanNode       = parseUtil.getElement(conNode,"bean");
        Element valueNode      = parseUtil.getElement(conNode,"value");
        Element validatorNode  = parseUtil.getElement(conNode,"validator");
        Element nullNode       = parseUtil.getElement(conNode, "null");
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf(
                    parseUtil.getAttribute(mappingRef, "enum-property"));

            value = parseUtil.getAttribute(mappingRef,  "value" );
            temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
            bean = parseUtil.getAttribute(mappingRef,  "bean" );
            mapping = Boolean
                .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
            scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
            factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
            validatorNode = parseUtil.getElement( mappingRef, "validator");
        }
        else
        if( beanNode != null ){
            addBean( beanNode, beanBuilder, bean, null, false, true);
            return;
        }
        else
        if( valueNode != null ){
            value = valueNode.getTextContent();
        }
        else
        if( nullNode != null )
            nullable = true;

        try{
            if( factoryName != null ){
                factory = 
                    (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                /*
                factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();
                */
            }

            if(typeName != null)
                type = ClassUtil.get(typeName);

        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        RestrictionBuilder elementBuilder =
                beanBuilder
                .setElement(
                    bean, 
                    enumProperty, 
                    temporalProperty, 
                    mapping? bean : null, 
                    scope, 
                    value, 
                    nullable,
                    factory, 
                    type);

        addValidator(validatorNode, elementBuilder);

    }
    
    private void addActions( NodeList actionList,
            ControllerBuilder controllerBuilder ){

        for( int k=0;k<actionList.getLength();k++ ){
            Element actionNodeNode = (Element) actionList.item(k);
            addAction( actionNodeNode, controllerBuilder );
        }
    }

    private void addAction( Element actionNode,
            ControllerBuilder controllerBuilder ){

        String id = parseUtil.getAttribute(actionNode,  "id" );
        String executor = parseUtil.getAttribute(actionNode,  "executor" );
        String result = parseUtil.getAttribute(actionNode,  "result" );
        String resultRendered = parseUtil.getAttribute(actionNode,  "result-rendered" );
        String view = parseUtil.getAttribute(actionNode,  "view" );
        DispatcherType dispatcher =
            DispatcherType.valueOf( parseUtil.getAttribute(actionNode,  "dispatcher" ) );

        ActionBuilder actionBuilder =
            controllerBuilder.addAction(id, result, 
                Boolean.parseBoolean(resultRendered), 
                view, dispatcher, executor);

        addParametersAction(
            parseUtil.getElements(
                actionNode,
                XMLBrutosConstants.XML_BRUTOS_PARAMETER),
            actionBuilder
            );

        addThrowSafe(
            parseUtil.getElements(
                actionNode,
                XMLBrutosConstants.XML_BRUTOS_THROWS),
            actionBuilder
            );

    }

    private void addParametersAction( NodeList params,
            ActionBuilder actionBuilder ){
        for( int k=0;k<params.getLength();k++ ){
            Element paramNode = (Element) params.item(k);
            addParameterAction( paramNode, actionBuilder );
        }

    }

    private void addParameterAction( Element paramNode,
            ActionBuilder actionBuilder ){

        EnumerationType enumProperty =
            EnumerationType.valueOf( parseUtil.getAttribute(paramNode,  "enum-property" ) );
        String value = parseUtil.getAttribute(paramNode,  "value" );
        String temporalProperty = parseUtil.getAttribute(paramNode,  "temporal-property" );
        String bean = parseUtil.getAttribute(paramNode,  "bean" );
        boolean mapping = Boolean
            .valueOf(parseUtil.getAttribute(paramNode,  "mapping" )).booleanValue();
        ScopeType scope = ScopeType.valueOf( parseUtil.getAttribute(paramNode,  "scope" ) );
        String factoryName = parseUtil.getAttribute(paramNode,  "factory" );
        String type = parseUtil.getAttribute(paramNode,  "type" );
        Type factory = null;
        Class typeClass = null;
        boolean nullable = false;

        try{
            if( type != null )
                typeClass = ClassUtil.get(type);
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        Element mappingRef = parseUtil.getElement( paramNode, "ref");
        Element beanNode   = parseUtil.getElement( paramNode, "bean");
        Element valueNode  = parseUtil.getElement( paramNode, "value");
        Element validatorNode = parseUtil.getElement( paramNode, "validator" );
        Element nullNode = parseUtil.getElement( paramNode, "null" );
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf( parseUtil.getAttribute(mappingRef,  "enum-property" ) );
            value = parseUtil.getAttribute(mappingRef,  "value" );
            temporalProperty = parseUtil.getAttribute(mappingRef,  "temporal-property" );
            bean = parseUtil.getAttribute(mappingRef,  "bean" );
            mapping = Boolean
                .valueOf(parseUtil.getAttribute(mappingRef,  "mapping" )).booleanValue();
            scope = ScopeType.valueOf( parseUtil.getAttribute(mappingRef,  "scope" ) );
            factoryName = parseUtil.getAttribute(mappingRef,  "factory" );
            validatorNode = parseUtil.getElement( mappingRef, "validator");
        }
        else
        if( beanNode != null ){
            addBean( beanNode, actionBuilder, typeClass );
            return;
        }
        else
        if( valueNode != null ){
            value = valueNode.getTextContent();
        }
        else
        if( nullNode != null )
            nullable = true;
        
        try{
            if( factoryName != null ){
                factory = (Type)ClassUtil.getInstance(ClassUtil.get(factoryName));
                /*factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();*/
            }

            //if( type == null )
            //    throw new BrutosException( "tag type is required in parameter" );
        }
        catch( BrutosException ex ){
            throw ex;
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        ParameterBuilder parameterBuilder =
                actionBuilder.addParameter(
                    bean,
                    scope,
                    enumProperty,
                    temporalProperty,
                    mapping? bean : null,
                    factory,
                    value,
                    nullable,
                    typeClass);

        addValidator(validatorNode, parameterBuilder);
        
    }

    private void addThrowSafe( NodeList throwSafeNodeList,
            ControllerBuilder controllerBuilder ){

        for( int i=0;i<throwSafeNodeList.getLength();i++ ){
            Element throwSafeNode = (Element) throwSafeNodeList.item(i);
            addThrowSafe( throwSafeNode,controllerBuilder );
        }
    }

    private void addThrowSafe( Element throwSafeNode,
            ControllerBuilder controllerBuilder ){

        String view = parseUtil.getAttribute(throwSafeNode, "view");
        String target = parseUtil.getAttribute(throwSafeNode, "target");
        String name = parseUtil.getAttribute(throwSafeNode, "name");
        DispatcherType dispatcher =
            DispatcherType.valueOf(parseUtil.getAttribute(throwSafeNode, "dispatcher"));
        Class targetClass;

        try{
            targetClass = Class.forName(
                        target,
                        true,
                        Thread.currentThread().getContextClassLoader() );
        }
        catch( BrutosException ex ){
            throw ex;
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        controllerBuilder.addThrowable(targetClass, view, name, dispatcher);
    }

    private void addThrowSafe( NodeList throwSafeNodeList,
            ActionBuilder actionBuilder ){

        for( int i=0;i<throwSafeNodeList.getLength();i++ ){
            Element throwSafeNode = (Element) throwSafeNodeList.item(i);
            addThrowSafe(throwSafeNode,actionBuilder);
        }
    }

    private void addThrowSafe( Element throwSafeNode,
            ActionBuilder actionBuilder ){

        String view = parseUtil.getAttribute(throwSafeNode, "view");
        String target = parseUtil.getAttribute(throwSafeNode, "target");
        String name = parseUtil.getAttribute(throwSafeNode, "name");
        DispatcherType dispatcher =
            DispatcherType.valueOf(parseUtil.getAttribute(throwSafeNode, "dispatcher"));
        Class targetClass;

        try{
            targetClass = Class.forName(
                        target,
                        true,
                        Thread.currentThread().getContextClassLoader() );
        }
        catch( BrutosException ex ){
            throw ex;
        }
        catch( Exception ex ){
            throw new BrutosException( ex );
        }

        actionBuilder.addThrowable(targetClass, view, name, dispatcher);
    }

    private void addValidator( Element validatorNode,
            RestrictionBuilder restrictionBuilder ){

        if( validatorNode == null )
            return;

        String msg = parseUtil.getAttribute(validatorNode,  "message" );

        restrictionBuilder.setMessage(msg);

        NodeList rules =
            parseUtil.getElements(
                validatorNode,
                XMLBrutosConstants.XML_BRUTOS_VALIDATOR_RULE );

        for( int i=0;i<rules.getLength();i++ ){
            Element rule = (Element) rules.item(i);
            String name = parseUtil.getAttribute(rule,  "name" );
            String value = parseUtil.getAttribute(rule,  "value" );

            value = value == null?
                rule.getTextContent() :
                value;

            RestrictionRules r = RestrictionRules.valueOf( name );

            if( r == null )
                throw new BrutosException("invalid restriction rule: " + name);
            
            restrictionBuilder
                .addRestriction(
                    r,
                    value);
            
        }

    }

    public void loadDefinitions(Resource resource) {
        Element document = this.validate(resource);
        this.build(document, resource);
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
