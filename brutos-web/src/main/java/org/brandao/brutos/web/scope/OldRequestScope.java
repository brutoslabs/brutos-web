

package org.brandao.brutos.web.scope;

import java.util.Arrays;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.web.http.BrutosRequest;
import org.brandao.brutos.web.http.MutableRequest;
import org.brandao.brutos.web.http.ParameterList;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;


public class OldRequestScope implements Scope{
    
    public OldRequestScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        request.setAttribute(name, value);
    }

    public Object get(String name) {
        BrutosRequest request = (BrutosRequest)getServletRequest();
        Object value = request.getObject(name);
        value = value == null? ((HttpServletRequest)request).getAttribute(name) : value;
        return value;
    }

    public Object getCollection( String name ){
        BrutosRequest request = (BrutosRequest)getServletRequest();
        return request.getObjects(name);
    }

    public void remove( String name ){
        ServletRequest request = getServletRequest();
        request.removeAttribute(name);
    }

    private ServletRequest getServletRequest(){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();

        MvcRequest request = context.getMvcRequest();
        if( !(request instanceof WebMvcRequest) )
            throw new BrutosException( "invalid web request: " +
                    request.getClass() );

        return ((WebMvcRequest)request).getServletRequest();
    }

}
