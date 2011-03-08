/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.brandao.brutos.ActionBuilder;
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConstructorBuilder;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.HandlerApplicationContext;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.InterceptorStackBuilder;
import org.brandao.brutos.ParameterBuilder;
import org.brandao.brutos.PropertyBuilder;
import org.brandao.brutos.RestrictionBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;
import org.brandao.brutos.type.*;
import org.brandao.brutos.validator.RestrictionRules;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Brandao
 */
public class BuildApplication {

    private InputSource source;
    private HandlerApplicationContext handler;
    private XMLParseUtil parseUtil;
    private List importers;

    public BuildApplication( InputSource source, HandlerApplicationContext handler ){
        this.source = source;
        this.handler = handler;
        this.parseUtil = new XMLParseUtil();
    }

    public void build(){
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document xmlDocument = null;

        URL schemaURL = Thread.currentThread()
            .getContextClassLoader()
                .getResource( XMLBrutosConstants.XML_BRUTOS_SCHEMA );

        try{
            documentBuilderFactory.setNamespaceAware( true);
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
            
            xmlDocument = documentBuilder.parse(source);
        }
        catch (Exception e) {
            throw new BrutosException(e);
        }

        xmlDocument.getDocumentElement().normalize();

        Element document = xmlDocument.getDocumentElement();

        loadContextParams(
            parseUtil.getElement(
                document, 
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAMS ) );

        loadImporters( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_IMPORTERS ) );

        loadTypes( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_TYPES ) );

        loadInterceptors( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_INTERCEPTORS ) );

        loadControllers( parseUtil.getElement(
                document,
                XMLBrutosConstants.XML_BRUTOS_CONTROLLERS ) );

    }

    private void loadContextParams( Element cp ){
        Properties config = handler.getConfiguration();

        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAM );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String name  = c.getAttribute( "name" );
            String value = c.getAttribute("value");

            value = value == null? c.getTextContent() : value;
            
            config.setProperty(name, value);
        }
        
    }
    
    private void loadImporters( Element e ){
        this.importers = new ArrayList();

        NodeList list = parseUtil
            .getElements(
                e,
                XMLBrutosConstants.XML_BRUTOS_IMPORTER );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String resource = c.getAttribute( "resource" );

            if( resource != null && resource.length() != 0 )
                this.importers.add(resource);
            
        }
    }

    private void loadTypes( Element cp ){
        NodeList list = parseUtil
            .getElements(
                cp,
                XMLBrutosConstants.XML_BRUTOS_TYPE );

        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);
            String name  = c.getAttribute( "class-type" );
            String value = c.getAttribute("factory");

            value = value == null? c.getTextContent() : value;

            Class type = null;
            Class factory = null;

            try{
                type = Class.forName(
                            name,
                            true,
                            Thread.currentThread().getContextClassLoader() );
                factory = Class.forName(
                            value,
                            true,
                            Thread.currentThread().getContextClassLoader() );
            }
            catch( Exception e ){
                throw new BrutosException( e );
            }

            Types.setType(type, factory);
        }

    }

    private void loadInterceptors( Element e ){
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
        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);

            String name       = c.getAttribute( "name" );
            String clazzName  = c.getAttribute( "class" );
            Boolean isDefault = Boolean.valueOf(c.getAttribute( "default" ));
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

                String paramName  = paramNode.getAttribute( "name" );
                String paramValue = paramNode.getAttribute( "value" );

                paramValue =
                    paramValue == null?
                        paramNode.getTextContent() :
                        paramValue;

                interceptorBuilder.addParameter(paramName, paramValue);
            }

        }
    }

    private void loadInterceptorStack( NodeList list ){
        for( int i=0;i<list.getLength();i++ ){
            Element c = (Element) list.item(i);

            String name       = c.getAttribute( "name" );
            Boolean isDefault = Boolean.valueOf(c.getAttribute( "default" ));

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
                        interceptorRefNode.getAttribute( "name" );

                interceptorStackBuilder.addInterceptor( interceptorRefName );

                NodeList listParam = parseUtil
                    .getElements(
                        interceptorRefNode,
                        XMLBrutosConstants.XML_BRUTOS_PARAM );

                for( int k=0;k<listParam.getLength();k++ ){
                    Element paramNode = (Element) listParam.item(k);

                    String paramName  = paramNode.getAttribute( "name" );
                    String paramValue = paramNode.getAttribute( "value" );

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

        NodeList controllers = parseUtil
            .getElements(
                controllersNode,
                XMLBrutosConstants.XML_BRUTOS_CONTROLLER );


        for( int i=0;i<controllers.getLength();i++ ){
            Element controllerNode = (Element) controllers.item(i);
            loadController( controllerNode );
        }
    }

    private void loadController( Element controller ){
        String id = controller.getAttribute( "id" );
        DispatcherType dispatcher =
            DispatcherType.valueOf( controller.getAttribute( "dispatcher" ) );
        String view = controller.getAttribute( "view" );
        String name = controller.getAttribute( "name" );
        String clazzName = controller.getAttribute( "class" );
        //ScopeType scope =
        //    ScopeType.valueOf( controller.getAttribute( "scope" ) );
        String actionId = controller.getAttribute( "action-id" );
        String defaultAction = controller.getAttribute( "default-action" );

        Class clazz = null;
        try{
            clazz = Class.forName(
                        clazzName,
                        true,
                        Thread.currentThread().getContextClassLoader() );
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
                actionId);

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
                    interceptorRefNode.getAttribute( "name" );

            InterceptorBuilder interceptorBuilder = 
                    controllerBuilder.addInterceptor( interceptorRefName );

            NodeList listParam = parseUtil
                .getElements(
                    interceptorRefNode,
                    XMLBrutosConstants.XML_BRUTOS_PARAM );

            for( int k=0;k<listParam.getLength();k++ ){
                Element paramNode = (Element) listParam.item(k);

                String paramName  = paramNode.getAttribute( "name" );
                String paramValue = paramNode.getAttribute( "value" );

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

        String name          = beanNode.getAttribute( "name" );
        String separator     = beanNode.getAttribute( "separator" );
        String indexFormat   = beanNode.getAttribute( "index-format" );
        String factory       = beanNode.getAttribute( "factory" );
        String methodFactory = beanNode.getAttribute( "method-factory" );
        String target        = beanNode.getAttribute( "target" );

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
            BeanBuilder bean, String propertyName, boolean key,
            boolean element ){

        String name          = beanNode.getAttribute( "name" );
        String separator     = beanNode.getAttribute( "separator" );
        String indexFormat   = beanNode.getAttribute( "index-format" );
        String factory       = beanNode.getAttribute( "factory" );
        String methodFactory = beanNode.getAttribute( "method-factory" );
        String target        = beanNode.getAttribute( "target" );

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
                    bean.buildProperty(name, propertyName, clazz);

        beanBuilder.setFactory(factory);
        beanBuilder.setMethodfactory(methodFactory);
        beanBuilder.setSeparator(separator);
        beanBuilder.setIndexFormat(indexFormat);

        buildBean( beanNode, beanBuilder );

    }

    private void addBean( Element beanNode,
            ActionBuilder actionBuilder ){

        String separator     = beanNode.getAttribute( "separator" );
        String indexFormat   = beanNode.getAttribute( "index-format" );
        String factory       = beanNode.getAttribute( "factory" );
        String methodFactory = beanNode.getAttribute( "method-factory" );
        String target        = beanNode.getAttribute( "target" );

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

        BeanBuilder beanBuilder = actionBuilder.buildParameter(clazz);

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
            addBean(keyNode, beanBuilder, null, true, false);
        else
        if( beanBuilder.isMap() )
            throw new BrutosException("key node is required in Map" );

        Element elementNode =
            parseUtil
                .getElement(
                    beanNode,
                    XMLBrutosConstants.XML_BRUTOS_COLLECTION_ELEMENT );
        
        if( elementNode != null )
            addBean(elementNode, beanBuilder, null, false, true);
        else
        if( beanBuilder.isMap() )
            throw new BrutosException("element node is required in Collection");
        
    }

    private void buildConstructorBean( NodeList consList,
            BeanBuilder beanBuilder ){

        for( int k=0;k<consList.getLength();k++ ){
            Element conNode = (Element) consList.item(k);

            EnumerationType enumProperty =
                EnumerationType.valueOf(conNode.getAttribute("enum-property"));
            String value = conNode.getAttribute("value");
            String temporalProperty = conNode.getAttribute("temporal-property");
            String bean = conNode.getAttribute("bean");
            boolean mapping = Boolean
                .valueOf(conNode.getAttribute("mapping")).booleanValue();
            ScopeType scope = ScopeType.valueOf(conNode.getAttribute("scope"));
            String factoryName = conNode.getAttribute("factory");
            Type factory = null;

            Element mappingRef     = parseUtil.getElement(conNode,"ref");
            Element beanNode       = parseUtil.getElement(conNode,"bean");
            Element valueNode      = parseUtil.getElement(conNode,"value");
            Element validatorNode  = parseUtil.getElement(conNode,"validator");
            
            if( mappingRef != null ){
                enumProperty =
                    EnumerationType.valueOf(
                        mappingRef.getAttribute("enum-property"));
                
                value = mappingRef.getAttribute( "value" );
                temporalProperty = mappingRef.getAttribute( "temporal-property" );
                bean = mappingRef.getAttribute( "bean" );
                mapping = Boolean
                    .valueOf(mappingRef.getAttribute( "mapping" )).booleanValue();
                scope = ScopeType.valueOf( mappingRef.getAttribute( "scope" ) );
                factoryName = mappingRef.getAttribute( "factory" );
                validatorNode = parseUtil.getElement( mappingRef, "validator");
            }
            else
            if( beanNode != null ){
                addBean( beanNode, beanBuilder, null, false, false );
                continue;
            }
            else
            if( valueNode != null ){
                value = valueNode.getTextContent();
            }

            try{
                if( factoryName != null ){
                    factory = (Type)Class.forName(
                                factoryName,
                                true,
                                Thread.currentThread().getContextClassLoader() )
                                    .newInstance();
                }
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
                        factory);

            addValidator(validatorNode, constructorBuilder);
        }

    }

    private void buildPropertiesBean( NodeList consList,
            BeanBuilder beanBuilder ){

        for( int k=0;k<consList.getLength();k++ ){
            Element propNode = (Element) consList.item(k);

            String propertyName = propNode.getAttribute( "name" );
            EnumerationType enumProperty =
                EnumerationType.valueOf( propNode.getAttribute( "enum-property" ) );
            String value = propNode.getAttribute( "value" );
            String temporalProperty = propNode.getAttribute( "temporal-property" );
            String bean = propNode.getAttribute( "bean" );
            boolean mapping = Boolean
                .valueOf(propNode.getAttribute( "mapping" )).booleanValue();
            ScopeType scope = ScopeType.valueOf( propNode.getAttribute( "scope" ) );
            String factoryName = propNode.getAttribute( "factory" );
            Type factory = null;

            Element mappingRef = parseUtil.getElement( propNode, "ref");
            Element beanNode   = parseUtil.getElement( propNode, "bean");
            Element valueNode  = parseUtil.getElement( propNode, "value");
            Element validatorNode = parseUtil.getElement( propNode, "validator" );
            if( mappingRef != null ){
                enumProperty =
                    EnumerationType.valueOf( propNode.getAttribute( "enum-property" ) );
                value = propNode.getAttribute( "value" );
                temporalProperty = propNode.getAttribute( "temporal-property" );
                bean = propNode.getAttribute( "bean" );
                mapping = Boolean
                    .valueOf(propNode.getAttribute( "mapping" )).booleanValue();
                scope = ScopeType.valueOf( propNode.getAttribute( "scope" ) );
                factoryName = propNode.getAttribute( "factory" );
                validatorNode = parseUtil.getElement( mappingRef, "validator");
            }
            else
            if( beanNode != null ){
                addBean( beanNode, beanBuilder, propertyName, false, false );
                continue;
            }
            else
            if( valueNode != null ){
                value = valueNode.getTextContent();
            }

            try{
                if( factoryName != null ){
                    factory = (Type)Class.forName(
                                factoryName,
                                true,
                                Thread.currentThread().getContextClassLoader() )
                                    .newInstance();
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

        String propertyName = propNode.getAttribute( "name" );
        EnumerationType enumProperty =
            EnumerationType.valueOf( propNode.getAttribute( "enum-property" ) );
        String value = propNode.getAttribute( "value" );
        String temporalProperty = propNode.getAttribute( "temporal-property" );
        String bean = propNode.getAttribute( "bean" );
        boolean mapping = Boolean
            .valueOf(propNode.getAttribute( "mapping" )).booleanValue();
        ScopeType scope = ScopeType.valueOf( propNode.getAttribute( "scope" ) );
        String factoryName = propNode.getAttribute( "factory" );
        Type factory = null;

        Element mappingRef = parseUtil.getElement( propNode, "ref");
        Element beanNode   = parseUtil.getElement( propNode, "bean");
        Element valueNode  = parseUtil.getElement( propNode, "value");
        Element validatorNode = parseUtil.getElement( propNode, "validator" );
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf( propNode.getAttribute( "enum-property" ) );
            value = propNode.getAttribute( "value" );
            temporalProperty = propNode.getAttribute( "temporal-property" );
            bean = propNode.getAttribute( "bean" );
            mapping = Boolean
                .valueOf(propNode.getAttribute( "mapping" )).booleanValue();
            scope = ScopeType.valueOf( propNode.getAttribute( "scope" ) );
            factoryName = propNode.getAttribute( "factory" );
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

        try{
            if( factoryName != null ){
                factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();
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
                    factory);

        addValidator(validatorNode, propertyBuilder);

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

        String id = actionNode.getAttribute( "id" );
        String executor = actionNode.getAttribute( "executor" );
        String result = actionNode.getAttribute( "result" );
        String view = actionNode.getAttribute( "view" );
        DispatcherType dispatcher =
            DispatcherType.valueOf( actionNode.getAttribute( "result" ) );

        ActionBuilder actionBuilder =
            controllerBuilder.addAction(id, result, view, dispatcher, executor);

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
            EnumerationType.valueOf( paramNode.getAttribute( "enum-property" ) );
        String value = paramNode.getAttribute( "value" );
        String temporalProperty = paramNode.getAttribute( "temporal-property" );
        String bean = paramNode.getAttribute( "bean" );
        boolean mapping = Boolean
            .valueOf(paramNode.getAttribute( "mapping" )).booleanValue();
        ScopeType scope = ScopeType.valueOf( paramNode.getAttribute( "scope" ) );
        String factoryName = paramNode.getAttribute( "factory" );
        String type = paramNode.getAttribute( "type" );
        Type factory = null;
        Class typeClass = null;
        
        Element mappingRef = parseUtil.getElement( paramNode, "ref");
        Element beanNode   = parseUtil.getElement( paramNode, "bean");
        Element valueNode  = parseUtil.getElement( paramNode, "value");
        Element validatorNode = parseUtil.getElement( paramNode, "validator" );
        if( mappingRef != null ){
            enumProperty =
                EnumerationType.valueOf( paramNode.getAttribute( "enum-property" ) );
            value = paramNode.getAttribute( "value" );
            temporalProperty = paramNode.getAttribute( "temporal-property" );
            bean = paramNode.getAttribute( "bean" );
            mapping = Boolean
                .valueOf(paramNode.getAttribute( "mapping" )).booleanValue();
            scope = ScopeType.valueOf( paramNode.getAttribute( "scope" ) );
            factoryName = paramNode.getAttribute( "factory" );
            validatorNode = parseUtil.getElement( mappingRef, "validator");
        }
        else
        if( beanNode != null ){
            addBean( beanNode, actionBuilder );
            return;
        }
        else
        if( valueNode != null ){
            value = valueNode.getTextContent();
        }

        try{
            if( factoryName != null ){
                factory = (Type)Class.forName(
                            factoryName,
                            true,
                            Thread.currentThread().getContextClassLoader() )
                                .newInstance();
            }

            if( type == null )
                throw new BrutosException( "tag type is required in parameter" );

            typeClass = Class.forName(
                        factoryName,
                        true,
                        Thread.currentThread().getContextClassLoader() );


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

        String view = throwSafeNode.getAttribute("view");
        String target = throwSafeNode.getAttribute("target");
        String name = throwSafeNode.getAttribute("name");
        DispatcherType dispatcher =
            DispatcherType.valueOf(throwSafeNode.getAttribute("dispatcher"));
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

        String view = throwSafeNode.getAttribute("view");
        String target = throwSafeNode.getAttribute("target");
        String name = throwSafeNode.getAttribute("name");
        DispatcherType dispatcher =
            DispatcherType.valueOf(throwSafeNode.getAttribute("dispatcher"));
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

        String msg = validatorNode.getAttribute( "message" );

        restrictionBuilder.setMessage(msg);

        NodeList rules =
            parseUtil.getElements(
                validatorNode,
                XMLBrutosConstants.XML_BRUTOS_VALIDATOR_RULE );

        for( int i=0;i<rules.getLength();i++ ){
            Element rule = (Element) rules.item(i);
            String name = rule.getAttribute( "name" );
            String value = rule.getAttribute( "value" );

            value = value == null?
                rule.getTextContent() :
                value;

            restrictionBuilder
                .addRestriction(
                    RestrictionRules.valueOf( name ),
                    value);
            
        }

    }
}
