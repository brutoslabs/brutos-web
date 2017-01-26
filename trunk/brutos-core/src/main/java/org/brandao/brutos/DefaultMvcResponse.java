


package org.brandao.brutos;

import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;


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
