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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 *
 * @author Afonso Brandao
 */
public interface MvcRequest {

    public Object getValue( String name );

    public Object getProperty( String name );
    
    public InputStream getStream() throws IOException;

    public String getType();

    public int getLength();

    public String getCharacterEncoding();

    public Locale getLocale();

}
