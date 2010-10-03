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

package org.brandao.brutos.xml.parser.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.xml.parser.Stack;
import org.brandao.brutos.xml.parser.Tag;
import org.xml.sax.Attributes;

/**
 *
 * @author Afonso Brandao
 */
public class PropertyMappingTag implements Tag{

    private Stack stack;

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void setText(String txt) {
    }

    public boolean isRead() {
        return false;
    }

    public void start(String localName, Attributes atts) {
        Map<String,Object> mapping = (Map)stack.pop();

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

        stack.push( mapping );
    }

    public void end(String localName) {
    }

}
