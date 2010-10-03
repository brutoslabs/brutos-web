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
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.xml.parser.Stack;
import org.brandao.brutos.xml.parser.Tag;
import org.xml.sax.Attributes;

/**
 *
 * @author Afonso Brandao
 */
public class PropsPropTag implements Tag{

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
        Map<String,String> prop = new HashMap<String,String>();

        prop.put( "name", atts.getValue( "name" ) );
        prop.put( "value", atts.getValue( "value" ) );
        //prop.put( "type", "value" );
        stack.push( prop );
    }

    public void end(String localName) {
        Map<String,Object> obj = (Map<String,Object>)stack.pop();

        if( "value".equals( obj.get( "type" ) ) ){
            Object newValue = obj.get( "value" );
            Object type     = obj.get( "type" );
            obj = (Map<String,Object>)stack.pop();
            obj.put( "value" , newValue );
            obj.put( "type" , type);
        }

        stack.push( obj );
    }

}
