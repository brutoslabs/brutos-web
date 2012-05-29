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

package org.brandao.brutos.view.jsf;

import java.io.IOException;
import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.interceptor.DataInput;
import org.brandao.brutos.interceptor.DataOutput;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.interceptor.ImpInterceptorHandler;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.WebScopeType;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @author Afonso Brandao
 */
public class ViewHandler extends javax.faces.application.ViewHandler {

    protected javax.faces.application.ViewHandler baseViewHandler;

    public ViewHandler(javax.faces.application.ViewHandler viewHandler) {
        super();
        this.baseViewHandler = viewHandler;
    }

    @Override
    public Locale calculateLocale(FacesContext context) {
        return baseViewHandler.calculateLocale(context);
    }

    @Override
    public String calculateRenderKitId(FacesContext context) {
        return baseViewHandler.calculateRenderKitId(context);
    }

    @Override
    public UIViewRoot createView(FacesContext context, String viewName) {
        WebApplicationContext brutosContext = ContextLoader
                    .getCurrentWebApplicationContext();
        Controller controller = null;//brutosContext.getController();

        if( controller != null )
            loadController( controller, context );
        
        return baseViewHandler.createView(context, viewName);
    }

    @Override
    public String getActionURL(FacesContext context, String arg1) {
        String s = baseViewHandler.getActionURL(context, arg1);
        return s;
    }

    @Override
    public String getResourceURL(FacesContext context, String arg1) {
        return baseViewHandler.getResourceURL(context, arg1);
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot viewRoot)
        throws IOException, FacesException {

        WebApplicationContext brutosContext = ContextLoader
                    .getCurrentWebApplicationContext();
        Controller controller = null;//brutosContext.getController();
        

        if( controller != null )
            invokeController( controller, context, viewRoot );
        else
            baseViewHandler.renderView(context, viewRoot);
    }

    private void invokeController( Controller controller, FacesContext context, UIViewRoot viewRoot ){
        AbstractWebApplicationContext brutosContext     =
                (AbstractWebApplicationContext) ContextLoader
                    .getCurrentWebApplicationContext();
        HttpServletRequest request   = (HttpServletRequest)context
                    .getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse)context
                    .getExternalContext().getResponse();
        
        request.setAttribute( BrutosConstants.JSF_CONTEXT , context );
        request.setAttribute( BrutosConstants.JSF_HANDLER , baseViewHandler );
        request.setAttribute( BrutosConstants.JSF_UI_VIEW_ROOT , viewRoot );

        IOCManager iocManager =
                (IOCManager)brutosContext.getContext()
                    .getAttribute( BrutosConstants.IOC_MANAGER );

        ImpInterceptorHandler ih = new ImpInterceptorHandler();
        //ih.setContext( brutosContext.getContext() );
        //ih.setRequest( brutosContext.getRequest() );
        ih.setResource( iocManager.getInstance( controller.getName() ) );
        //ih.setResponse( response );
        //ih.setURI( ih.getRequest().getRequestURI() );

        if( brutosContext instanceof BrutosContext ){
            ih.setResourceAction(
                ((BrutosContext)brutosContext
                        ).getMethodResolver()
                            .getResourceMethod( null ) );
        }
        else{
            ih.setResourceAction(
                brutosContext
                        .getActionResolver()
                            .getResourceAction( controller,
                                    ih));
        }

            controller.proccessBrutosAction( ih );
    }

    @Override
    public UIViewRoot restoreView(FacesContext context, String arg1) {
        
        WebApplicationContext brutosContext = ContextLoader
                    .getCurrentWebApplicationContext();
        Controller controller = null;//brutosContext.getController();

        if( controller != null )
            loadController( controller, context );
        
        return baseViewHandler.restoreView(context, arg1);
    }

    private void loadController( Controller controller, FacesContext context ){
        WebApplicationContext brutosContext = ContextLoader
                    .getCurrentWebApplicationContext();
        IOCManager iocManager = ((BrutosContext)brutosContext).getIocManager();
        Object instance = iocManager.getInstance( controller.getName() );

        /*
        DataInput input = new DataInput( brutosContext.getRequest(),
                                       (HttpServletResponse)context.getExternalContext().getResponse(),
                                       brutosContext.getContext() );
        DataOutput output = new DataOutput( brutosContext.getRequest(),
                                       brutosContext.getContext() );
        */
            WebApplicationContext app = ContextLoader
                    .getCurrentWebApplicationContext();
        DataInput input = new DataInput( app.getScopes().get(WebScopeType.PARAM) );
        DataOutput output = new DataOutput(app.getScopes().get(WebScopeType.REQUEST));
        
        //brutosContext.getRequest().setAttribute( BrutosConstants.WEBFRAME, instance );
        input.read( controller , instance);
        output.writeFields( controller , instance );
    }

    @Override
    public void writeState(FacesContext context) throws IOException {
        baseViewHandler.writeState(context);
    }
} 
