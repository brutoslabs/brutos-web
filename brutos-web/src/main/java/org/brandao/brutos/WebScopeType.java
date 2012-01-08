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

package org.brandao.brutos;

/**
 * Descreve os escopos.
 * 
 * @author Afonso Brandao
 */
public class WebScopeType extends ScopeType{

    public static final WebScopeType APPLICATION = new WebScopeType( "application" );
    public static final WebScopeType SESSION     = new WebScopeType( "session" );
    public static final WebScopeType FLASH       = new WebScopeType( "flash" );

    static{
        defaultScopes.put( APPLICATION.toString(),  APPLICATION );
        defaultScopes.put( SESSION.toString(),      SESSION );
        defaultScopes.put( FLASH.toString(),        FLASH );
    }

    public WebScopeType( String name ){
        super(name);
    }
            
}
