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

package org.brandao.brutos.ioc.editors;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;

/**
 *
 * @author Afonso Brandao
 */
public class DateEditorSupport extends PropertyEditorSupport{

    private String text;
    private SimpleDateFormat pattern;

    public DateEditorSupport( SimpleDateFormat pattern, boolean lenient ){
        this.pattern = pattern;
        pattern.setLenient(lenient);
    }

    public void setAsText(String text) {
        this.text = text;
        try{
            setValue( pattern.parse(text) );
        }
        catch( Exception e ){
            setValue( null );
        }
    }

    public String getAsText() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss:sss" );
        return this.text;
    }

    public SimpleDateFormat getPattern() {
        return pattern;
    }

    public void setPattern(SimpleDateFormat pattern) {
        this.pattern = pattern;
    }
    
}
