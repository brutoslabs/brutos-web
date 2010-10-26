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


package org.brandao.brutos.http;

import java.lang.reflect.Field;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.WebFrame;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Form;

/**
 *
 * @author Afonso Brandao
 */
public class DataInput {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    
    public DataInput( HttpServletRequest request, HttpServletResponse response, ServletContext context ) {
        this.request  = request;
        this.response = response;
        this.context  = context;
    }

    public DataInput() {
    }
    
    public void read( Form form, Object object ){
        try{
            for( FieldForm ff: form.getFields() ){
                //Object val = ff.getBean().getValue( context, request );
                Object val = ff.getBean().getValue();
                if( val != null )
                    ff.setValue( object, val );
            }
            setDataWebFrame( form, object, context, request, response );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
 /*
        try{
            for( FieldForm ff: form.getFields() ){
                Object val = ff.getBean().getValue( context, request );
                if( val != null )
                    ff.setValue( object, val );
            }
            setDataWebFrame( form, object, context, request, response );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
*/
    }
    
    private void setDataWebFrame( Form form, Object wf, ServletContext context, HttpServletRequest request, HttpServletResponse response ){
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
        request.setAttribute( BrutosConstants.WEBFRAME, wf );
    }
}
