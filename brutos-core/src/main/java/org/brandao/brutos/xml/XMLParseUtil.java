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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Brandao
 */
public class XMLParseUtil {

    public Element getElement( Element e, String name ){
        return (Element)e.getElementsByTagName(name);
    }

    public NodeList getElements( Element e, String name ){
        return e.getElementsByTagName(name);
    }

}
