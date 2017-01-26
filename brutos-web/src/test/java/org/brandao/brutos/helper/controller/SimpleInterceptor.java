


package org.brandao.brutos.helper.controller;

import org.brandao.brutos.Scopes;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebScopeType;


public class SimpleInterceptor extends AbstractInterceptor{

    public void intercepted(InterceptorStack stack, InterceptorHandler handler)
            throws InterceptedException {

        Scopes scopes = ContextLoader.getCurrentWebApplicationContext().getScopes();
        Scope scope = scopes.get(WebScopeType.REQUEST);
        for(Object key: this.props.keySet()){
            scope.put((String)key, this.props.get(key));
        }

        stack.next(handler);
    }

}
