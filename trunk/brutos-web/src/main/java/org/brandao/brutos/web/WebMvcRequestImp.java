

package org.brandao.brutos.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.servlet.ServletRequest;
import org.brandao.brutos.web.http.BrutosRequest;


public class WebMvcRequestImp implements WebMvcRequest{

    private ServletRequest request;

    public WebMvcRequestImp( ServletRequest request ){
        this.request = request;
    }
    
    public Object getValue(String name) {
        return ((BrutosRequest)request).getObject(name);
    }

    public Object getProperty(String name) {
        return request.getAttribute(name);
    }

    public InputStream getStream() throws IOException{
        return request.getInputStream();
    }

    public String getType() {
        return request.getContentType();
    }

    public int getLength() {
        return request.getContentLength();
    }

    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    public Locale getLocale() {
        return request.getLocale();
    }

    public ServletRequest getServletRequest() {
        return request;
    }

}
