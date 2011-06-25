/*
  Brutos Web MVC http://brutos.sourceforge.net/
  Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
*/

package br.brandao.interceptors;

import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ResourceMethod;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;

/**
 *
 * @author Afonso Brandao
 */
public class TestInterceptor extends AbstractInterceptor{

    public void intercepted(InterceptorStack stack, InterceptorHandler handler) 
        throws InterceptedException {

        ResourceAction action = handler.getResourceAction();
        
        if( action != null )
            handler.getContext().getScopes().get(ScopeType.REQUEST)
                    .put( "invoked" , action.getMethod().getName() );

        stack.next( handler );
    }

}
