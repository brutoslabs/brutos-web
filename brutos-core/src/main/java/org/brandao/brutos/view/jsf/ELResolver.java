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

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELException;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.mapping.Form;

/**
 *
 * @author Afonso Brandao
 */
public class ELResolver extends javax.el.ELResolver{

    public ELResolver(){
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) throws ELException{
        Object result = null;

        if( base == null ){
            String propertyName = (String)property;
            BrutosContext brutosContext = BrutosContext.getCurrentInstance();
            if( propertyName.equals( BrutosConstants.WEBFRAME ) ){
                Form controller = brutosContext.getController();
                if( controller != null ){
                    result = resolveBrutosBean( controller.getId() );
                    context.setPropertyResolved( true );
                }
            }
        }
        return result;
    }
    
    protected Object resolveBrutosBean( String name ) {
        IOCProvider ioc = getIOCProvider();
        if( ioc == null )
            throw new BrutosException( "IOC provider not found!" );

        if (ioc.containsBeanDefinition(name)) {
            return ioc.getBean( name );
        }
        else{
            return null;
        }
    }

    protected IOCProvider getIOCProvider(){
        BrutosContext brutosContext = BrutosContext.getCurrentInstance();
        return (IOCProvider) brutosContext
                .getContext().getAttribute( BrutosConstants.IOC_PROVIDER );
    }

    @Override
    public Class<?> getType(ELContext arg0, Object arg1, Object arg2) {
        return null;
    }

    @Override
    public void setValue(ELContext arg0, Object arg1, Object arg2, Object arg3) {
        arg0.setPropertyResolved( true );
    }

    @Override
    public boolean isReadOnly(ELContext arg0, Object arg1, Object arg2) {
        return true;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext arg0, Object arg1) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext arg0, Object arg1) {
        return null;
    }

}
