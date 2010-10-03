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

import org.xml.sax.Attributes;

/**
 *
 * @author Afonso Brandao
 */
public interface Tag {

    public void setStack( Stack stack );
    
    public void setText( String txt );
    
    public boolean isRead();
    
    public void start( String localName, Attributes atts );

    public void end( String localName );

}
