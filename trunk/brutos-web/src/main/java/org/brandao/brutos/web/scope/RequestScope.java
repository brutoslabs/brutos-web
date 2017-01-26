

package org.brandao.brutos.web.scope;

import java.util.Arrays;
import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.web.http.ParameterList;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;


public class RequestScope implements Scope{
    
    public RequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        ServletRequest request = getServletRequest();
        return request.getAttribute(name);
    }

    public Object getCollection( String name ){
        ServletRequest request = getServletRequest();
        return new ParameterList(
                Arrays.asList(request.getParameterValues(name)));
    }

    public void remove( String name ){
        ServletRequest request = getServletRequest();
        request.removeAttribute(name);
    }

    private ServletRequest getServletRequest(){
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return requestInfo.getRequest();
        
    }

}
