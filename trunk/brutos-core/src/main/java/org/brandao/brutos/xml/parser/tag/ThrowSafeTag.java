/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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
public class ThrowSafeTag implements Tag{

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
        Map<String,Object> object = (Map)stack.pop();

        List<Map<String,Object>> throwsData = (List)object.get( "throw-safe" );

        Map<String,Object> throwSafe = new HashMap<String,Object>();

        if( throwsData == null ){
            throwsData = new ArrayList<Map<String,Object>>();
            object.put( "throw-safe", throwsData );
        }
        
        throwSafe.put( "uri", atts.getValue( "uri" ) );
        throwSafe.put( "target", atts.getValue( "target" ) );
        throwSafe.put( "name", atts.getValue( "name" ) );
        throwSafe.put( "redirect", atts.getValue( "redirect" ) );

        throwsData.add( throwSafe );
        stack.push( object );
    }

    public void end(String localName) {
    }

}
