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

import java.util.ArrayList;
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
public class BeanPropertyTag implements Tag{

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
        Map<String,Object> webFrame = (Map)stack.pop();

        //dados usados no ioc/di
        List<Map<String,Object>> properties = (List)webFrame.get( "property" );
        if( properties == null ){
            properties = new ArrayList<Map<String,Object>>();
            webFrame.put( "property", properties );
        }
        Map<String,Object> property = new HashMap<String,Object>();
        property.put( "name", atts.getValue( "name" ) );
        properties.add( property );

        stack.push( webFrame );
        stack.push( property );
    }

    public void end(String localName) {
        Map<String,Object> value = (Map)stack.pop();
        Map<String,Object> property = (Map)stack.pop();

        property.put( "value-type", value.get( "type" ) );
        if( "value".equals( value.get( "type" ) ) ){
            property.put( "value", value.get( "value" ) );
        }
        else
        if( "ref".equals( value.get( "type" ) )  ){
            property.put( "ref", value.get( "value" ) );
        }
        else
        if( "bean".equals( value.get( "type" ) )  ){
            property.put( "bean", value );
        }
        else{
            property.put( "complex-type", value.get( "complex-type" ) );
            property.put( "data", value.get( "data" ) );
        }
    }

}
