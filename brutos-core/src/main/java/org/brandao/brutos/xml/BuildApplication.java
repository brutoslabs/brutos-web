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
import org.brandao.brutos.BeanBuilder;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.HandlerApplicationContext;
import org.brandao.brutos.InterceptorBuilder;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.InterceptorStackBuilder;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;
import org.brandao.brutos.type.*;
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

    private  void loadControllers( Element controllersNode ){

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
        ScopeType scope = 
            ScopeType.valueOf( controller.getAttribute( "scope" ) );
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
            addBean(beanNode, controllerBuilder);
        }

    }

    private void addBean( Element beanNode,
            ControllerBuilder controllerBuilder ){

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
            controllerBuilder.buildMappingBean(name, clazz);

        beanBuilder.setFactory(factory);
        beanBuilder.setMethodfactory(methodFactory);
        beanBuilder.setSeparator(separator);
        beanBuilder.setIndexFormat(indexFormat);
    }
}
