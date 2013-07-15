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

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.servlet.ServletContext;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.IOCProvider;

/**
 *
 * @author Afonso Brandao
 */
public class VariableResolver extends javax.faces.el.VariableResolver{
    
    protected final javax.faces.el.VariableResolver originalVariableResolver;

    public VariableResolver(javax.faces.el.VariableResolver originalVariableResolver) {
        this.originalVariableResolver = originalVariableResolver;
    }

    protected final javax.faces.el.VariableResolver getOriginalVariableResolver() {
        return this.originalVariableResolver;
    }
    
    @Override
    public Object resolveVariable(FacesContext facesContext, String name) throws EvaluationException {
        Object bean = resolveBrutosBean(facesContext, name);
        if (bean != null) 
            return bean;
        else
            return resolveJSFBean( facesContext, name);
    }
    
    protected Object resolveOriginal(FacesContext facesContext, String name) {
        return getOriginalVariableResolver().resolveVariable(facesContext, name);
    }
    
    protected Object resolveBrutosBean(FacesContext facesContext, String name) {
        IOCProvider ioc = getIOCProvider(facesContext);
        if( ioc == null )
            throw new BrutosException( "IOC provider not found!" );
        
        /*
        if (ioc.containsBeanDefinition(name)) {
            return ioc.getBean( name );
        }
        else{
            return null;
        }
        * 
        */
        return null;
    }
    
    protected Object resolveJSFBean(FacesContext facesContext, String name) {
        return originalVariableResolver.resolveVariable( facesContext , name);
    }

    protected IOCProvider getIOCProvider( FacesContext facesContext ){
        Object context = facesContext.getExternalContext().getContext();
        
        if( !(context instanceof ServletContext) )
            throw new java.lang.UnsupportedOperationException();
        
        return (IOCProvider) ((ServletContext)context).getAttribute( BrutosConstants.IOC_PROVIDER );
    }
}
