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


package org.brandao.brutos.interceptor;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Afonso Brandao
 */
public class DataInput {
    
    private Scope scope;

    public DataInput( Scope requestScope ) {
        this.scope = requestScope;
    }
    
    public void read( Controller form, Object object ){
        try{
            for( FieldForm ff: form.getFields() ){
                //Object val = ff.getBean().getValue( context, request );
                Object val = ff.getBean().getValue();
                if( val != null )
                    ff.setValue( object, val );
            }
            setDataWebFrame( form, object );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    private void setDataWebFrame( Controller form, Object wf ){
        /*
        if( wf instanceof WebFrame ){
            WebFrame w = (WebFrame)wf;
            w.setName( form.getId() );
            w.setPage( form.getPage() );
            w.setDefaultMethodName( form.getDefaultMethodName() );
            w.setMethodParameterName( form.getMethodId() );
            w.setRequest( request );
            w.setResponse( response );
            w.setSession( request.getSession() );
            w.setServletContext( context );
            w.setUpdatable( true );
        }
        */
        //request.setAttribute( BrutosConstants.WEBFRAME, wf );
        scope.put( BrutosConstants.WEBFRAME, wf );
        scope.put( BrutosConstants.CONTROLLER, wf );
    }
}
