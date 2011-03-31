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

package org.brandao.brutos.view;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.web.http.BrutosRequestFilter;
import org.brandao.brutos.web.RequestInfo;

/**
 *
 * @author Afonso Brandao
 */
public class JSFViewProvider extends ViewProvider{

    public JSFViewProvider() {
    }

    public void configure(Properties properties) {
      /*
      properties
          .setProperty("org.brandao.brutos.controller.method_resolver",
                    "org.brandao.brutos.view.jsf.MethodResolverJSF" );
      */
    }

    @Deprecated
    public void show(String page, ServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        /*
        FacesContext facesContext = (FacesContext)request
                .getAttribute( BrutosConstants.JSF_CONTEXT );

        ViewHandler baseViewHandler = 
                (ViewHandler)request.getAttribute( BrutosConstants.JSF_HANDLER );
        
        baseViewHandler
            .renderView(
                facesContext,
                (UIViewRoot)request.getAttribute( BrutosConstants.JSF_UI_VIEW_ROOT )
            );
        */

        BrutosRequestFilter
                .getCurrentFilterChain().doFilter(request, response);
    }

    public void show(String page, boolean redirect,
            ServletRequest request, HttpServletResponse response,
            ServletContext context) throws ServletException, IOException {

        show(page, request, response, context);
    }

    public void show(String view, DispatcherType dispatcherType) throws IOException {
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();
        FilterChain filter = BrutosRequestFilter.getCurrentFilterChain();
        try {
            filter.doFilter(requestInfo.getRequest(), requestInfo.getResponse());
        }
        catch (ServletException ex) {
            throw new BrutosException( ex );
        }
    }

}
