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
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.HandlerApplicationContext;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;
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
            documentBuilderFactory.setValidating( true);
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
            config.setProperty(name, value);
        }
        
    }
}
