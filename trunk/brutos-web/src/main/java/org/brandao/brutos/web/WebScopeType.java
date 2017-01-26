

package org.brandao.brutos.web;

import org.brandao.brutos.ScopeType;


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
