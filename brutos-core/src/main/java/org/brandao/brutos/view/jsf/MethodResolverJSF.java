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

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.DefaultMethodResolver;
import org.brandao.brutos.ResourceMethod;
import org.brandao.brutos.http.MutableRequest;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.MethodForm;


/**
 *
 * @author Afonso Brandao
 */
public class MethodResolverJSF extends DefaultMethodResolver{
    
    public MethodResolverJSF() {
    }

    @Override
    public ResourceMethod getResourceMethod(HttpServletRequest request) {
        WebApplicationContext brutosContext = (WebApplicationContext) ApplicationContext.getCurrentApplicationContext();
        FacesContext context = FacesContext.getCurrentInstance();

        ActionEvent event = (ActionEvent) request.getAttribute(
                    BrutosConstants.JSF_ACTION_LISTENER );

        if( event != null )
            return getResourceMethod( context, brutosContext, event );
        else
            return super.getResourceMethod( request );
    }

    private ResourceMethod getResourceMethod( FacesContext context, WebApplicationContext brutosContext, ActionEvent event ){
        Form controller = brutosContext.getController();
        MethodForm methodController;
        
        UIComponent component = event.getComponent();
        UICommand command = (UICommand)component;

        MethodExpression method = command.getActionExpression();
        if( method != null ){
            String expression = method.getExpressionString();
            expression = expression.substring( 2, expression.length()-1 );
            String[] parts = expression.split( "\\." );

            if( BrutosConstants.WEBFRAME.equals( parts[0] ) ){
                methodController = controller.getMethodByName( parts[1] );

                if( methodController != null ){
                    ((MutableRequest)brutosContext.getRequest())
                            .setParameter( controller.getMethodId(), parts[1] );
                    
                    return super.getResourceMethod( brutosContext.getRequest() );
                }
            }
        }

        return getJSFResourceMethod( command, context.getELContext(), method );
        /*
        if( methodController == null )
            return getJSFResourceMethod( command, context.getELContext(), method );
        else{
            ((MutableRequest)brutosContext.getRequest())
                    .setParameter( controller.getMethodId(), methodInfo.getName());
            return super.getResourceMethod( brutosContext.getRequest() );
        }
        */
    }

    private ResourceMethod getJSFResourceMethod( UIComponent command, ELContext context, MethodExpression methodExpression ){
        return new JSFResourceMethod( methodExpression, context, command );
    }
}
