/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.web;

import org.brandao.brutos.ScopeType;

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
