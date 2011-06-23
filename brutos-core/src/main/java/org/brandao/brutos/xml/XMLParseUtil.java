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

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author Brandao
 */
public class XMLParseUtil {

    public Element getElement( Element e, String name ){
        NodeList elements = e.getElementsByTagNameNS("*", name);

        for(int i=0;i<elements.getLength();i++){
            Element c = (Element) elements.item(i);
            if( c.getParentNode().equals(e) )
                return (Element)elements.item(0);
        }

        return null;

        /*
        if( elements.getLength() > 0 )
            return (Element)elements.item(0);
        else
            return null;
        */
    }

    public NodeList getElements( Element e, String name ){
        CustomNodeList list = new CustomNodeList();

        NodeList es = e.getElementsByTagNameNS("*", name);
        for( int i=0;i<es.getLength();i++ ){
            Element c = (Element) es.item(i);
            if( c.getParentNode().equals(e) )
                list.add(c);
        }
        
        return list;
    }

    public String getAttribute( Element e, String name ){
        String value = e.getAttribute(name);
        if( "".equals(value))
            return null;
        else
            return value;
    }

    private static class CustomNodeList
            extends ArrayList implements NodeList{

        public Node item(int index) {
            return (Node) get(index);
        }

        public int getLength() {
            return size();
        }

    }
}
