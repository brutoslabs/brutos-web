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


package org.brandao.brutos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author Brandao
 */
public class XMLHelper {

    private static final String simpleXMLApplicationContext =
        "<controllers  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" +
        "   xmlns='http://brutos.sourceforge.net/targetNS'"+
        "   xsi:schemaLocation='http://brutos.sourceforge.net/targetNS brutos-controller-1.0.xsd'>"+
        "</controllers>";

    public static String getSimpleXMLApplicationContext(){
        return simpleXMLApplicationContext;
    }

    public static byte[] getByteArray( String xml ){
        return xml.getBytes();
    }

    public static InputStream getInputStream( String xml ){
        return new ByteArrayInputStream( getByteArray(xml) );
    }

}
