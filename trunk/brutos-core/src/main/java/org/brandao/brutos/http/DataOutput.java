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
import javax.servlet.http.HttpSession;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;

/**
 *
 * @author Afonso Brandao
 */
public class DataOutput {
    
    private HttpServletRequest request;
    @Deprecated
    private ServletContext context;
    
    public DataOutput( HttpServletRequest request, ServletContext context ) {
        this.request = request;
        this.context = context;
    }
    
    public DataOutput() {
    }

    public void write( Form form, Object object ){
       try{
           //HttpSession session = request.getSession();
           
            for( FieldForm ff: form.getFields() ){
                if( ff.getBean() != null ){
                    UseBeanData ubd = ff.getBean();
                    Object value = ff.getValue( object );
                    if( value == null )
                        ubd.getScope().remove(ubd.getNome());
                    else
                        ubd.getScope().put(ubd.getNome(), value);
                    /*
                    if( ubd.getScope() == ScopeType.SESSION && !ubd.getNome().equals( "" ) ){
                        Object value = ff.getValue( object );
                        if( value == null )
                            session.removeAttribute( ubd.getNome() );
                        else
                            session.setAttribute( ubd.getNome(), value );
                    }
                     */
                }
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public void writeFields( Form form, Object object ){
        Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
        try{
            Field[] fields = form.getClassType().getDeclaredFields();
            for( Field f: fields ){
                f.setAccessible( true );
                requestScope.put( f.getName(), f.get( object ) );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
}
