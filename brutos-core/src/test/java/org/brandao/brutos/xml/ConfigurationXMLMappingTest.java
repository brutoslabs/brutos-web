/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
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

import java.io.ByteArrayInputStream;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;

/**
 *
 * @author Afonso Brandao
 */
public class ConfigurationXMLMappingTest  extends TestCase implements Test{

    public void testLoadConfiguration() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <context-params>" +
        "       <context-param name=\"param1\" value=\"param1\"/>" +
        "       <context-param name=\"param2\" value=\"param\"/>" +
        "       <context-param name=\"param.value\" value=\"value\"/>" +
        "   </context-params>" +
        "" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data =
            bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );
        
        Configuration config = new Configuration();

        ConfigurationXMLMapping xmlConfig =
                new ConfigurationXMLMapping( config );

        xmlConfig.setData( (Map<String,Object>)data.get( XMLBrutosConstants.XML_BRUTOS_CONTEXT_PARAMS ) );
        assertEquals( "param1", config.getProperty("param1") );
        assertEquals( "param", config.getProperty("param2") );
        assertEquals( "value", config.getProperty("param.value") );
    }
}
