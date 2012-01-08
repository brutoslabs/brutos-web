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
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Implementa��o padr�o do MvcResponse.
 * 
 * @author Afonso Brandao
 */
public class DefaultMvcResponse implements MvcResponse{

    public void process( Object object ){
        JOptionPane.showMessageDialog(null, String.valueOf( object ) );
    }

    public OutputStream processStream() {
        return null;
    }

    public void process(Object object, Map config, Map info) {
    }

    public OutputStream processStream(Map config, Map info) {
        return null;
    }

    public void setInfo(String name, String value) {
    }

    public String getType() {
        return null;
    }

    public int getLength() {
        return -1;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public void setLocale(Locale value) {
    }

    public void setType(String value) {
    }

    public void setLength(int value) {
    }

    public void setCharacterEncoding(String value) {
    }
    
}
