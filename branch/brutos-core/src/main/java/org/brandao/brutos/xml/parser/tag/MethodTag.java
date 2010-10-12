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
public class MethodTag implements Tag{

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

        List<Map<String,Object>> methods = (List)webFrame.get( "method" );

        if( methods == null ){
            methods = new ArrayList<Map<String,Object>>();
            webFrame.put( "method", methods );
        }
        Map<String,Object> method = new HashMap<String,Object>();
        method.put( "name", atts.getValue( "name" ) );
        method.put( "method-name", atts.getValue( "method-name" ) );
        method.put( "return-in", atts.getValue( "return-in" ) );
        method.put( "page", atts.getValue( "page" ) );
        method.put( "redirect", atts.getValue( "redirect" ) );
        method.put( "parameter", new ArrayList<Map<String,Object>>() );
        methods.add( method );

        stack.push( webFrame );
        stack.push( method );
    }

    public void end(String localName) {
        stack.pop();
    }

}
