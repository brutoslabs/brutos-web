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

package org.brandao.brutos.xml.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Afonso Brandao
 */
public class Stack {

    List<Object> data;

    public Stack(){
        this.data = new ArrayList<Object>();
    }

    public void push( Object obj ){
        data.add( obj );
    }

    public Object pop(){
        return data.remove( data.size() - 1 );
    }

}
