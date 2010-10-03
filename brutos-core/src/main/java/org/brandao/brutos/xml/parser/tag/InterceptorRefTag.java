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
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.xml.parser.Stack;
import org.brandao.brutos.xml.parser.Tag;
import org.xml.sax.Attributes;

/**
 *
 * @author Afonso Brandao
 */
public class InterceptorRefTag implements Tag{

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
        Map<String,Object> interceptor = (Map)stack.pop();

        List<Map<String,Object>> interceptors = (List)interceptor.get( "interceptors" );

        if( interceptors == null ){
            interceptors = new ArrayList();
            interceptor.put( "interceptors", interceptors );
        }

        Map<String,Object> interceptorRef = new HashMap();
        interceptors.add( interceptorRef );

        interceptorRef.put( "ref", atts.getValue( "name" ) );

        stack.push( interceptor );
        stack.push( interceptorRef );
    }

    public void end(String localName) {
        Map<String,Object> value = (Map)stack.pop();

        List<Map<String,Object>> values = new ArrayList();
        while( value.get( "value" ) != null ){
            values.add( value );
            value = (Map)stack.pop();
        }

        value.put( "params", values );
    }

}
