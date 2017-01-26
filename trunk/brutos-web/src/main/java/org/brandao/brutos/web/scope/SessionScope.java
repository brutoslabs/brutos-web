

package org.brandao.brutos.web.scope;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.web.ContextLoaderListener;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.RequestInfo;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebMvcRequest;


public class SessionScope implements Scope{
    
    public SessionScope() {
    }

    public void put(String name, Object value) {
        ServletRequest request = getServletRequest();
        HttpSession session = ((HttpServletRequest)request).getSession();
        session.setAttribute( name, value );
    }

    public Object get(String name) {
        ServletRequest request = getServletRequest();
        HttpSession session = ((HttpServletRequest)request).getSession();
        return session.getAttribute( name );
    }

    public Object getCollection( String name ){
        return get( name );
    }

    public void remove( String name ){
        ServletRequest request = getServletRequest();
        HttpSession session = ((HttpServletRequest)request).getSession();
        session.removeAttribute( name );
    }

    private ServletRequest getServletRequest(){
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        return requestInfo.getRequest();
        
    }

}
