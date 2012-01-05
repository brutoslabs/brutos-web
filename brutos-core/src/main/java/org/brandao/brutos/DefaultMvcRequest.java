/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 *
 * @author Brandao
 */
public class DefaultMvcRequest implements MvcRequest{

    public Object getValue(String name) {
        return null;
    }

    public Object getProperty(String name) {
        return null;
    }

    public InputStream getStream() throws IOException {
        return null;
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

}
