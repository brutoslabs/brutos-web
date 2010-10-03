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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.xml.TestHelper.MyType;
import org.brandao.brutos.xml.parser.XMLBrutosConstants;

/**
 *
 * @author Afonso Brandao
 */
public class TypeXMLMappingTest  extends TestCase implements Test{

    public void testloadTypes() throws Exception{
        String xmlData = "" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "" +
        "<brutos-configuration  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'" +
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS http://brutos.sourceforge.net/brutos_1_0.xsd' >" +
        "" +
        "   <types>" +
        "       <type class-type=\"int\" factory=\"org.brandao.brutos.feature.MyType\"/>" +
        "   </types>" +
        "</brutos-configuration>";

        BrutosProcessor bp   = new BrutosProcessor();
        Map<String,Object> data = bp.processBrutosXML( new ByteArrayInputStream( xmlData.getBytes() ) );
        
        TypeXMLMapping xml =
                new TypeXMLMapping();

        xml.processData( (List<Map<String,String>>)data.get( XMLBrutosConstants.XML_BRUTOS_TYPES ) );
        Type myType = Types.getType(int.class);
        assertEquals( MyType.class, myType.getClass() );
    }
}
