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
public class AliasTag implements Tag{

    private Stack stack;
    private String text;

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void setText(String txt) {
        this.text = txt;
    }

    public boolean isRead() {
        return true;
    }

    public void start(String localName, Attributes atts) {
    }

    public void end(String localName) {
        Map<String,Object> object = (Map)stack.pop();
        List<String> list = (List)object.get( "alias" );

        if( list == null ){
            list = new ArrayList<String>();
            object.put( "alias", list );
        }

        list.add( text );
        stack.push( object );
    }

}
