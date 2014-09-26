/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        //NodeList elements = e.getElementsByTagNameNS("*", name);
        NodeList elements = e.getElementsByTagName(name);
        
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
        /*CustomNodeList list = new CustomNodeList();

        NodeList es = e.getElementsByTagNameNS("*", name);
        for( int i=0;i<es.getLength();i++ ){
            Element c = (Element) es.item(i);
            if( c.getParentNode().equals(e) )
                list.add(c);
        }
        
        return list;*/
        return e.getElementsByTagName(name);
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
