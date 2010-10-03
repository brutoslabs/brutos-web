/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.xml.XMLBrutosParse;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 *
 * @author Afonso Brand�o
 */
@Deprecated
public class BrutosHandler implements ContentHandler, XMLBrutosConstants{

    @Deprecated
    private XMLBrutosParse parse;
    
    private boolean readData;
    
    private StringBuffer string;
    
    private Pilha pilha;

    private Map<String,Object> data;

    @Deprecated
    public BrutosHandler( XMLBrutosParse parse ) {
        this.readData = false;
        this.parse    = parse;
        this.pilha    = new Pilha();
    }

    public BrutosHandler() {
        this.readData = false;
        this.pilha    = new Pilha();
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        
        if( XML_BRUTOS.equals( localName ) ){
            Map<String,Object> config = new HashMap<String,Object>();
            config.put( "version", atts.getValue( "version" ) );
            config.put( "encoding", atts.getValue( "encoding" ) );
            pilha.push( config );
        }
        else
        if( XML_BRUTOS_CONTEXT_PARAMS.equals( localName ) ){
            Map<String,Object> config = (Map)pilha.pop();
            Map<String,String> contextParams = new HashMap<String,String>();
            config.put( "context-params", contextParams );
            pilha.push( config );
            pilha.push( contextParams );
        }
        else
        if( XML_BRUTOS_CONTEXT_PARAM.equals( localName ) ){
            Map<String,String> contextParams = (Map)pilha.pop();
            
            contextParams.put( atts.getValue( "name" ), atts.getValue( "value" ) );
            
            pilha.push( contextParams );
        }
        else
        if( XML_BRUTOS_EXTEND_CONFIGURATION.equals( localName ) ){
            Map<String,Object> config = (Map)pilha.pop();
            List<String> providers = new ArrayList();
            
            config.put( "extend-configuration", providers );
            
            pilha.push( config );
            pilha.push( providers );
        }
        else
        if( XML_BRUTOS_PROVIDER.equals( localName ) ){
            List<String> providers = (List)pilha.pop();
            
            providers.add( atts.getValue( "class" ) );
            
            pilha.push( providers );
        }
        else
        if( XML_BRUTOS_WEB_FRAMES.equals( localName ) ){
            Map<String,Object> config = (Map)pilha.pop();
            
            List<Map<String,Object>> webFrames = new ArrayList();
            config.put( "web-frames", webFrames );
            
            pilha.push( config );
            pilha.push( webFrames );
        }
        else
        if( XML_BRUTOS_WEB_FRAME.equals( localName ) ){
            List<Map<String,Object>> webFrames = (List)pilha.pop();
            
            Map<String,Object> webFrame = new HashMap<String,Object>();
            webFrames.add( webFrame );
            
            webFrame.put( "uri", atts.getValue( "uri" ) );
            webFrame.put( "name", atts.getValue( "name" ) );
            webFrame.put( "page", atts.getValue( "page" ) );
            webFrame.put( "class-type", atts.getValue( "class" ) );
            webFrame.put( "class", atts.getValue( "class" ) );
            webFrame.put( "scope", atts.getValue( "scope" ) );
            webFrame.put( "method-parameter-name", atts.getValue( "method-parameter-name" ) );
            webFrame.put( "default-method-name", atts.getValue( "default-method-name" ) );

            //dados usados no ioc/di
            webFrame.put( "singleton", "false" );
            
            pilha.push( webFrames );
            pilha.push( webFrame );
        }
        else
        if( XML_BRUTOS_MAPPING.equals( localName ) ){
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            List<Map<String,Object>> mappings = (List)webFrame.get( "mapping" );
            if( mappings == null ){
                mappings = new ArrayList();
                webFrame.put( "mapping", mappings );
            }
            
            Map<String,Object> mapping = new HashMap();
            mappings.add( mapping );
            
            mapping.put( "name", atts.getValue( "name" ) );
            mapping.put( "target", atts.getValue( "target" ) );
            mapping.put( "properties", new ArrayList<Map<String,Object>>() );
            
            pilha.push( webFrame );
            pilha.push( mapping );
        }
        else
        if( XML_BRUTOS_PROPERTY_MAPPING.equals( localName ) ){
            Map<String,Object> mapping = (Map)pilha.pop();
            
            List<Map<String,Object>> props = (List)mapping.get( "properties" );
            
            Map<String,Object> prop = new HashMap<String,Object>();
            props.add( prop );
            
            prop.put( "name", atts.getValue( "name" ) );
            prop.put( "property-name", atts.getValue( "property-name" ) );
            prop.put( "enum-property", atts.getValue( "enum-property" ) );
            prop.put( "temporal-property", atts.getValue( "temporal-property" ) );
            prop.put( "mapping-name", atts.getValue( "mapping-name" ) );
            prop.put( "scope", atts.getValue( "scope" ) );
            prop.put( "factory", atts.getValue( "factory" ) );

            pilha.push( mapping );
        }
        else/*
        if( XML_BRUTOS_BEAN_CONSTRUCTOR.equals( localName ) ){
            
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            //dados usados no ioc/di
            List<Map<String,Object>> constructorArgs = new ArrayList<Map<String,Object>>();
            webFrame.put( "constructor-arg", constructorArgs );
            
            pilha.push( webFrame );
            pilha.push( constructorArgs );
            
        }
        else*/
        if( XML_BRUTOS_PROPERTY.equals( localName ) ){
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            List<Map<String,Object>> properties = (List)webFrame.get( "property-webframe" );
            
            if( properties == null ){
                properties = new ArrayList<Map<String,Object>>();                
                webFrame.put( "property-webframe", properties );
            }
            
            Map<String,Object> prop = new HashMap<String,Object>();
            properties.add( prop );
            
            prop.put( "name", atts.getValue( "name" ) );
            prop.put( "property-name", atts.getValue( "property-name" ) );
            prop.put( "enum-property", atts.getValue( "enum-property" ) );
            prop.put( "temporal-property", atts.getValue( "temporal-property" ) );
            prop.put( "mapping-name", atts.getValue( "mapping-name" ) );
            prop.put( "scope", atts.getValue( "scope" ) );
            prop.put( "factory", atts.getValue( "factory" ) );
            pilha.push( webFrame );
        }
        else
        if( XML_BRUTOS_BEAN_PROPERY.equals( localName ) ){
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            //dados usados no ioc/di
            List<Map<String,Object>> properties = (List)webFrame.get( "property" );
            if( properties == null ){
                properties = new ArrayList<Map<String,Object>>();                
                webFrame.put( "property", properties );
            }
            Map<String,Object> property = new HashMap<String,Object>();
            property.put( "name", atts.getValue( "name" ) );
            properties.add( property );
            
            pilha.push( webFrame );
            pilha.push( property );
        }
        else
        if( XML_BRUTOS_BEAN_CONSTRUCTOR_ARG.equals( localName ) ){
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            //dados usados no ioc/di
            List<Map<String,Object>> constructorArgs = (List)webFrame.get( "constructor-arg");
            
            if( constructorArgs == null){
                constructorArgs = new ArrayList<Map<String,Object>>();
                webFrame.put( "constructor-arg", constructorArgs );
            }
            
            Map<String,Object> arg = new HashMap<String,Object>();
            constructorArgs.add( arg );
            
            arg.put( "type", atts.getValue( "type" ) );
            
            pilha.push( webFrame );
            pilha.push( arg );
        }
        else
        if( XML_BRUTOS_BEAN_VALUE_NULL.equals( localName ) ){
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "type", "null-type" );
            pilha.push( val );
        }
        else
        if( XML_BRUTOS_BEAN_VALUE.equals( localName ) ){
            string = new StringBuffer( "" );
            readData = true;
        }
        else
        if( XML_BRUTOS_BEAN_REF.equals( localName ) ){
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "type", "ref" );
            val.put( "value", atts.getValue( "bean" ) );
            pilha.push( val );
        }
        if( XML_BRUTOS_MAP.equals( localName ) ){
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "complex-type", "map" );
            val.put( "data", new ArrayList<Map<String,Object>>() );
            pilha.push( val );
        }
        else
        if( XML_BRUTOS_MAP_ENTRY.equals( localName ) ){
            Map<String,Object> entry = new HashMap<String,Object>();
            pilha.push( entry );
        }
        else
        if( XML_BRUTOS_MAP_KEY.equals( localName ) ){
            //not-implemented
        }
        else
        if( XML_BRUTOS_SET.equals( localName ) ){
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "complex-type", "set" );
            pilha.push( val );
        }
        else
        if( XML_BRUTOS_LIST.equals( localName ) ){
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "complex-type", "list" );
            pilha.push( val );
        }
        else
        if( XML_BRUTOS_METHOD.equals( localName ) ){
            Map<String,Object> webFrame = (Map)pilha.pop();
            
            List<Map<String,Object>> methods = (List)webFrame.get( "method" );
            
            if( methods == null ){
                methods = new ArrayList<Map<String,Object>>();                
                webFrame.put( "method", methods );
            }
            Map<String,Object> method = new HashMap<String,Object>();
            method.put( "name", atts.getValue( "name" ) );
            method.put( "method-name", atts.getValue( "method-name" ) );
            method.put( "return-in", atts.getValue( "return-in" ) );
            method.put( "page", atts.getValue( "page" ) );
            method.put( "parameter", new ArrayList<Map<String,Object>>() );
            methods.add( method );
            
            pilha.push( webFrame );
            pilha.push( method );
        }
        else
        if( XML_BRUTOS_METHOD_PARAM.equals( localName ) ){
            Map<String,Object> method = (Map)pilha.pop();
            
            List<Map<String,Object>> params = (List)method.get( "parameter" );
            
            Map<String,Object> param = new HashMap<String,Object>();
            
            param.put( "name", atts.getValue( "name" ) );
            param.put( "class-type", atts.getValue( "type" ) );
            param.put( "enum-property", atts.getValue( "enum-property" ) );
            param.put( "temporal-property", atts.getValue( "temporal-property" ) );
            param.put( "mapping-name", atts.getValue( "mapping-name" ) );
            param.put( "scope", atts.getValue( "scope" ) );
            param.put( "factory", atts.getValue( "factory" ) );
            
            params.add( param );
            pilha.push( method );
        }
        else
        if( XML_BRUTOS_BEANS.equals( localName ) ){
            Map<String,Object> config = (Map)pilha.pop();
            
            Map<String,Map<String,Object>> beans = new HashMap<String,Map<String,Object>>();
            config.put( "beans", beans );
            
            pilha.push( config );
            pilha.push( beans );
        }
        else
        if( XML_BRUTOS_BEAN.equals( localName ) ){
            Map<String,Object> bean = new HashMap<String,Object>();
            bean.put( "name", atts.getValue( "name" ) );
            bean.put( "class", atts.getValue( "class" ) );
            bean.put( "scope", atts.getValue( "scope" ) );
            bean.put( "singleton", atts.getValue( "singleton" ) );
            bean.put( "factory-bean", atts.getValue( "factory-bean" ) );

            if( atts.getValue( "name" ) == null ){
                bean.put( "type", "inner-bean" );
                //inner-bean
                bean.put( "complex-type", "inner-bean" );
                //necessario para manter a compatibilidade
                bean.put( "data", bean );
            }
            else{
                bean.put( "type", "bean" );
                Map<String,Map<String,Object>> beans = (Map)pilha.pop();
                if( beans.put( atts.getValue( "name" ), bean ) != null )
                    throw new MappingException( "conflict bean name: " + atts.getValue( "name" ) );

                pilha.push( beans );
            }
            
            pilha.push( bean );
        }
        else
        if( XML_BRUTOS_PROPERTIES.equals( localName ) ){
            Map<String,Map<String,Object>> beans = (Map)pilha.pop();
            
            Map<String,Object> bean = new HashMap<String,Object>();
            if( beans.put( atts.getValue( "name" ), bean ) != null )
                throw new MappingException( "conflict bean name: " + atts.getValue( "name" ) );
            
            bean.put( "name", atts.getValue( "name" ) );
            bean.put( "class", atts.getValue( "class" ) );
            bean.put( "factory-bean", atts.getValue( "factory-bean" ) );
            bean.put( "complex-type", "properties" );
            bean.put( "data", new ArrayList<Map<String,String>>() );
            
            pilha.push( beans );
            pilha.push( bean );
        }
        else
        if( XML_BRUTOS_PROPS.equals( localName ) ){
            Map<String,Object> props = new HashMap<String,Object>();
            props.put( "complex-type", "properties" );
            pilha.push( props );
        }
        else
        if( XML_BRUTOS_PROPS_PROP.equals( localName ) ){
            Map<String,String> prop = new HashMap<String,String>();

            prop.put( "name", atts.getValue( "name" ) );
            prop.put( "value", atts.getValue( "value" ) );
            //prop.put( "type", "value" );
            pilha.push( prop );
        }
        else
        if( XML_BRUTOS_PROPERTIES_PROP.equals( localName ) ){
            Map<String,Object> bean = (Map)pilha.pop();
            
            List<Map<String,String>> data = (List)bean.get( "data" );
            
            Map<String,String> prop = new HashMap<String,String>();
            data.add( prop );
            
            prop.put( "name", atts.getValue( "name" ) );
            prop.put( "value", atts.getValue( "value" ) );
            pilha.push( bean );
        }
        else
        if( XML_BRUTOS_INTERCEPTORS.equals( localName ) ){
            Map<String,Object> config = (Map)pilha.pop();
            List<Map<String,Object>> interceptors = new ArrayList();
            config.put( "interceptors", interceptors );
            
            pilha.push( config );
            pilha.push( interceptors );
        }
        else
        if( XML_BRUTOS_INTERCEPTOR.equals( localName ) ){
            List<Map<String,Object>> interceptors = (List)pilha.pop();
            
            Map<String,Object> interceptor = new HashMap();
            interceptors.add( interceptor );
            
            interceptor.put( "name", atts.getValue( "name" ) );
            interceptor.put( "class", atts.getValue( "class" ) );
            interceptor.put( "default", atts.getValue( "default" ) );
            
            pilha.push( interceptors );
            pilha.push( interceptor );
        }
        else
        if( XML_BRUTOS_PARAM.equals( localName ) ){
            Map<String,Object> value = new HashMap();
            value.put( "name", atts.getValue( "name" ) );
            readData = true;
            string = new StringBuffer("");
            
            pilha.push( value );
        }
        else
        if( XML_BRUTOS_INTERCEPTOR_STACK.equals( localName ) ){
            List<Map<String,Object>> interceptors = (List)pilha.pop();
            
            Map<String,Object> interceptor = new HashMap();
            interceptors.add( interceptor );
            
            interceptor.put( "name", atts.getValue( "name" ) );
            interceptor.put( "type", "stack" );
            interceptor.put( "default", atts.getValue( "default" ) );
            
            pilha.push( interceptors );
            pilha.push( interceptor );
        }
        else
        if( XML_BRUTOS_INTERCEPTOR_REF.equals( localName ) ){
            Map<String,Object> interceptor = (Map)pilha.pop();
            
            List<Map<String,Object>> interceptors = (List)interceptor.get( "interceptors" );
            
            if( interceptors == null ){
                interceptors = new ArrayList();
                interceptor.put( "interceptors", interceptors );
            }
            
            Map<String,Object> interceptorRef = new HashMap();
            interceptors.add( interceptorRef );
            
            interceptorRef.put( "ref", atts.getValue( "name" ) );
            
            pilha.push( interceptor );
            pilha.push( interceptorRef );
        }
        else
        if( XML_BRUTOS_TYPES.equals( localName ) ){
            Map<String,Object> root = (Map<String,Object>)pilha.pop();

            List<Map<String,String>> types = new ArrayList();
            root.put( "types" , types );

            pilha.push( root );
            pilha.push( types );
        }
        else
        if( XML_BRUTOS_TYPE.equals( localName ) ){
            List<Map<String,String>> types = (List)pilha.pop();

            Map<String,String> type = new HashMap();
            type.put( "class-type" , atts.getValue( "class-type" ) );
            type.put( "factory" , atts.getValue( "factory" ) );

            types.add( type );

            pilha.push( types );
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if( XML_BRUTOS.equals( localName ) ){
            //parse.processData( (Map<String,Object>)pilha.pop() );
            this.data = (Map<String,Object>)pilha.pop();
        }
        else
        if( XML_BRUTOS_CONTEXT_PARAMS.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_EXTEND_CONFIGURATION.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_WEB_FRAMES.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_WEB_FRAME.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_MAPPING.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_BEAN_CONSTRUCTOR_ARG.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            Map<String,Object> arg = (Map)pilha.pop();
            
            arg.put( "value-type", value.get( "type" ) );
            if( "value".equals( value.get( "type" ) ) ){
                arg.put( "value", value.get( "value" ) );
            }
            else
            if( "ref".equals( value.get( "type" ) )  ){
                arg.put( "ref", value.get( "value" ) );
            }
            else{
                arg.put( "complex-type", value.get( "complex-type" ) );
                arg.put( "data", value.get( "data" ) );
            }
            
        }
        else
        if( XML_BRUTOS_BEAN_PROPERY.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            Map<String,Object> property = (Map)pilha.pop();
            
            property.put( "value-type", value.get( "type" ) );
            if( "value".equals( value.get( "type" ) ) ){
                property.put( "value", value.get( "value" ) );
            }
            else
            if( "ref".equals( value.get( "type" ) )  ){
                property.put( "ref", value.get( "value" ) );
            }
            else{
                property.put( "complex-type", value.get( "complex-type" ) );
                property.put( "data", value.get( "data" ) );
            }
        }
        else/*
        if( XML_BRUTOS_BEAN_CONSTRUCTOR.equals( localName ) ){
            //pilha.pop();
        }
        else*/
        if( XML_BRUTOS_BEAN_VALUE.equals( localName ) ){
            readData = false;
            Map<String,Object> val = new HashMap<String,Object>();
            val.put( "type", "value" );
            val.put( "value", string.toString() );
            pilha.push( val );
        }
        else
        if( XML_BRUTOS_MAP_KEY.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            Map<String,Object> entry = (Map)pilha.pop();
            
            if( "value".equals( value.get( "type" ) ) )
                entry.put( "key-value", value.get( "value" ) );
            else
            if( "ref".equals( value.get( "type" ) ) )
                entry.put( "key-ref", value.get( "value" ) );
            
            pilha.push( entry );
        }
        else
        if( XML_BRUTOS_MAP_ENTRY.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            Map<String,Object> entry = (Map)pilha.pop();
            Map<String,Object> map   = (Map)pilha.pop();
            
            if( "value".equals( value.get( "type" ) ) )
                entry.put( "value", value.get( "value" ) );
            else
            if( "ref".equals( value.get( "type" ) ) )
                entry.put( "ref", value.get( "value" ) );
            
            List<Map<String,Object>> data = (List)map.get( "data" );
            data.add( entry );
            
            pilha.push( map );
        }
        else
        if( XML_BRUTOS_SET.equals( localName ) ){
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
            
            Map<String,Object> dta = (Map)pilha.pop();
            
            while( dta.get( "complex-type" ) == null ){
                data.add( dta );
                dta = (Map)pilha.pop();
            }
            
            dta.put( "data", data );
            
            pilha.push( dta );
        }
        else
        if( XML_BRUTOS_LIST.equals( localName ) ){
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
            
            Map<String,Object> dta = (Map)pilha.pop();
            
            while( dta.get( "complex-type" ) == null ){
                data.add( dta );
                dta = (Map)pilha.pop();
            }
            
            dta.put( "data", data );
            
            pilha.push( dta );
        }
        else
        if( XML_BRUTOS_METHOD.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_BEANS.equals( localName ) ){
            Map<String,Map<String,Object>> beans = (Map)pilha.pop();
            /*
            Map<String,Object> config = (Map)pilha.pop();
            List<Map<String,Object>> webFrames = (List)config.get( "web-frames" );
            
            for( Map<String,Object> wf: webFrames ){
                if( beans.put( (String)wf.get( "name" ), wf ) != null )
                    throw new MappingException( "conflict bean name: " + (String)wf.get( "name" ) );
            }
            pilha.push( config );
            */
        }
        else
        if( XML_BRUTOS_BEAN.equals( localName ) ){
            Map<String,Object> data = (Map<String,Object>)pilha.pop();

            //se o objeto removido da pilha n�o for um bean, significa
            //que o objeto j� foi removido por se tratar de um inner-bean.
            if( !"bean".equals( data.get( "type" ) ) )
                pilha.push( data );
        }
        else
        if( XML_BRUTOS_PROPERTIES.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_PROPS_PROP.equals( localName ) ){
            Map<String,Object> obj = (Map<String,Object>)pilha.pop();

            if( "value".equals( obj.get( "type" ) ) ){
                Object newValue = obj.get( "value" );
                Object type     = obj.get( "type" );
                obj = (Map<String,Object>)pilha.pop();
                obj.put( "value" , newValue );
                obj.put( "type" , type);
            }

            pilha.push( obj );
        }
        else
        if( XML_BRUTOS_PROPS.equals( localName ) ){
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

            Map<String,Object> dta = (Map)pilha.pop();

            while( dta.get( "complex-type" ) == null ){
                data.add( dta );
                dta = (Map)pilha.pop();
            }

            dta.put( "data", data );

            pilha.push( dta );
        }
        else
        if( XML_BRUTOS_INTERCEPTORS.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_INTERCEPTOR.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            
            List<Map<String,Object>> values = new ArrayList();
            while( value.get( "value" ) != null ){
                values.add( value );
                value = (Map)pilha.pop();
            }
            
            value.put( "params", values );
        }
        else
        if( XML_BRUTOS_INTERCEPTOR_REF.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            
            List<Map<String,Object>> values = new ArrayList();
            while( value.get( "value" ) != null ){
                values.add( value );
                value = (Map)pilha.pop();
            }
            
            value.put( "params", values );
        }
        else
        if( XML_BRUTOS_INTERCEPTOR_STACK.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_PARAM.equals( localName ) ){
            Map<String,Object> value = (Map)pilha.pop();
            value.put( "value", string.toString() );
            readData = false;
            string = null;
            pilha.push( value );
        }
        else
        if( XML_BRUTOS_TYPES.equals( localName ) ){
            pilha.pop();
        }
        else
        if( XML_BRUTOS_TYPE.equals( localName ) ){
            
        }
       
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if( readData ){
            string.append( ch, start, length );
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

}
class Pilha{
    
    List<Object> data;
    
    public Pilha(){
        this.data = new ArrayList<Object>();
    }
    
    public void push( Object obj ){
        data.add( obj );
    }
    
    public Object pop(){
        return data.remove( data.size() - 1 );
    }
}