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
public class WebFrameTag implements Tag{

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
        List<Map<String,Object>> webFrames = (List)stack.pop();

        Map<String,Object> webFrame = new HashMap<String,Object>();
        webFrames.add( webFrame );

        webFrame.put( "@tag", "web-frame" );
        webFrame.put( "uri", atts.getValue( "uri" ) );
        webFrame.put( "redirect", atts.getValue( "redirect" ) );
        webFrame.put( "name", atts.getValue( "name" ) );
        webFrame.put( "page", atts.getValue( "page" ) );
        webFrame.put( "class-type", atts.getValue( "class" ) );
        webFrame.put( "class", atts.getValue( "class" ) );
        webFrame.put( "scope", atts.getValue( "scope" ) );
        webFrame.put( "method-parameter-name", atts.getValue( "method-parameter-name" ) );
        webFrame.put( "default-method-name", atts.getValue( "default-method-name" ) );

        //dados usados no ioc/di
        webFrame.put( "singleton", "false" );

        stack.push( webFrames );
        stack.push( webFrame );
    }

    public void end(String localName) {
        stack.pop();
    }

}
