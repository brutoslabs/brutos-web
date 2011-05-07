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

import java.lang.reflect.Field;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.FieldForm;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;

/**
 *
 * @author Afonso Brandao
 */
public class DataOutput {

    private Scope scope;

    public DataOutput(Scope scope) {
        this.scope = scope;
    }

    public void write( Controller form, Object object ){
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
    
    public void writeFields( Controller form, Object object ){
        //Scope requestScope = Scopes.get(ScopeType.REQUEST.toString());
        try{
            Field[] fields = form.getClassType().getDeclaredFields();
            for( Field f: fields ){
                f.setAccessible( true );
                scope.put( f.getName(), f.get( object ) );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
}
