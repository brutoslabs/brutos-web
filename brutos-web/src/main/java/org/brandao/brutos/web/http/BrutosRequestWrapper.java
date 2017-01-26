

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class BrutosRequestWrapper extends HttpServletRequestWrapper implements BrutosRequest{

    private BrutosRequest request;

    public BrutosRequestWrapper( BrutosRequest request ){
        super( (HttpServletRequest)request.getServletRequest() );
        this.request = request;
    }

    public Object getObject(String name) {
        return request.getObject(name);
    }

    public List<Object> getObjects(String name) {
        return request.getObjects(name);
    }

    public UploadListener getUploadListener() {
        return request.getUploadListener();
    }

    public void parseRequest() throws IOException {
        request.parseRequest();
    }

    public void setServletRequest(ServletRequest request0){
        request.setServletRequest(request0);
    }
    
    public ServletRequest getServletRequest() {
        return request.getServletRequest();
    }

    public void setParameter(String name, String value) {
        request.setParameter(name, value);
    }

    public void setParameters(String name, String[] values) {
        request.setParameters(name, values);
    }

    public String[] getParameterValues(String name){
        return request.getParameterValues(name);
    }

    public String getParameter(String name){
        return request.getParameter(name);
    }
    
    public void setObject(String name, Object value) {
        request.setObject(name, value);
    }

    public void setObjects(String name, Object[] value) {
        request.setObjects(name, value);
    }

    public String getRequestId() {
        return request.getRequestId();
    }

}
