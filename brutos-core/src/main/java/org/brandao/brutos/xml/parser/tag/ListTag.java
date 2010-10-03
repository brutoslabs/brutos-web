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
import java.util.Collections;
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
public class ListTag implements Tag{

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
        Map<String,Object> val = new HashMap<String,Object>();
        val.put( "complex-type", "list" );
        stack.push( val );
    }

    public void end(String localName) {
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

        Map<String,Object> dta = (Map)stack.pop();

        while( dta.get( "complex-type" ) == null ){
            data.add( dta );
            dta = (Map)stack.pop();
        }
        Collections.reverse(data);
        dta.put( "data", data );

        stack.push( dta );
    }

}
