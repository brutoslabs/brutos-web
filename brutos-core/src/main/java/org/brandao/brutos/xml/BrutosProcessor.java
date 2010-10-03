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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.brandao.brutos.xml.parser.BrutosXMLError;
import org.brandao.brutos.xml.parser.Handler;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosProcessor {
    
    public BrutosProcessor() {
    }
    
    public Map<String,Object> processBrutosXML( InputStream input )
                throws ParserConfigurationException, SAXException, IOException{
        return processBrutosXML( input, null );
    }

    @Deprecated
    public Map<String,Object> processBrutosXML( InputStream input, XMLBrutosParse parse )
                throws ParserConfigurationException, SAXException, IOException{
        
        //BrutosHandler handler = new BrutosHandler( parse );
        //BrutosHandler handler = new BrutosHandler();
        Handler handler = new Handler();
        InputSource inSource  = new InputSource( input );
        URL schemaURL       = Thread.currentThread()
                    .getContextClassLoader()
                        .getResource( XMLBrutosConstants.XML_BRUTOS_SCHEMA );

        SAXParserFactory sf = SAXParserFactory.newInstance();
        sf.setNamespaceAware( true );
        sf.setValidating( true );
        SAXParser sp = sf.newSAXParser();

        sp.setProperty(
                XMLBrutosConstants.JAXP_SCHEMA_LANGUAGE, 
                XMLBrutosConstants.W3C_XML_SCHEMA
        );

        sp.setProperty(
                XMLBrutosConstants.JAXP_SCHEMA_SOURCE, 
                schemaURL.toString()
        );

        BrutosXMLError errorHandler = new BrutosXMLError();
        XMLReader  reader           = sp.getXMLReader();

        reader.setErrorHandler( errorHandler );
        reader.setContentHandler( handler );
        reader.parse( inSource );

        SAXParseException exception = errorHandler.getException();
        if( exception != null )
            throw exception;
        else
            return handler.getData();
    }
    
}
