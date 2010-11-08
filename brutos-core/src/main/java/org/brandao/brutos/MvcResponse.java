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

package org.brandao.brutos;

import java.io.OutputStream;
import java.util.Locale;

/**
 *
 * @author Afonso Brandao
 */
public interface MvcResponse {

    public void process( Object object );

    public OutputStream processStream();

    public void setInfo( String name, String value );

    public String getType();

    public int getLength();

    public String getCharacterEncoding();

    public Locale getLocale();
    
    public void setLocale( Locale value );

    public void setType( String value );

    public void setLength( int value );

    public void setCharacterEncoding( String value );


}
