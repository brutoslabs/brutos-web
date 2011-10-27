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

package org.brandao.brutos.old.programatic;

import org.brandao.brutos.InterceptorManager;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class WebFrameManager {
    
    private Map forms;
    private Map revForms;
    
    private WebFrameBuilder current;
    
    private InterceptorManager interceptorManager;
    
    private IOCManager iocManager;
    
    public WebFrameManager( InterceptorManager interceptorManager, IOCManager iocManager ) {
        this.forms              = new HashMap();
        this.revForms           = new HashMap();
        this.interceptorManager = interceptorManager;
        this.iocManager         = iocManager;
    }

    public WebFrameBuilder addWebFrame( String uri, Class classtype ){
        return addWebFrame( uri, null, null, classtype, ScopeType.valueOf("prototype"), "invoke" );
    }
    
    public WebFrameBuilder addWebFrame( String uri, Class classtype, ScopeType scope ){
        return addWebFrame( uri, null, null, classtype, scope, "invoke" );
    }

    public WebFrameBuilder addWebFrame( String uri, String page, Class classtype ){
        return addWebFrame( uri, page, null, classtype, ScopeType.valueOf("prototype"), "invoke" );
    }
    
    public WebFrameBuilder addWebFrame( String uri, String page,
           String name, Class classType, ScopeType scope,
           String methodParameterName ){
        return addWebFrame( uri, page, false, name, classType, scope, methodParameterName );
    }

    public WebFrameBuilder addWebFrame( String uri, String page, boolean redirect, 
            String name, Class classType, ScopeType scope,
            String methodParameterName ){

        uri = uri == null || uri.replace( " ", "" ).length() == 0? null : uri;
        page = page == null || page.replace( " ", "" ).length() == 0 ? null : page;

        if( uri == null )
            throw new BrutosException( "uri is required!" );
        
        if( scope == null )
            throw new BrutosException( "scope is required!" );
            
        if( methodParameterName == null )
            methodParameterName = "invoke";
        
        if( name == null || name.length() == 0 )
            name = classType.getSimpleName();
        
        //IOC-Manager
        iocManager.addBean( name, classType, scope, false, null );
        
        Controller fr = new Controller();
        fr.setUri( uri );
        fr.setId( name );
        fr.setPage( page );
        fr.setClassType( classType );
        fr.setScope( scope );
        fr.setMethodId( methodParameterName );
        fr.setRedirect(redirect);
        fr.setDispatcherType(redirect? DispatcherType.REDIRECT : DispatcherType.INCLUDE );
        
        //Action
        Action ac = new Action();
        ac.setPreAction( getMethodAction( "preAction", fr.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", fr.getClassType() ) );
        fr.setAcion( ac );
        
        //forms.put( fr.getUri(), fr );
        //revForms.put( fr.getClassType(), fr );
        addForm( fr.getUri(), fr );
        this.current = new WebFrameBuilder( fr, this, interceptorManager );
        
        //for( Interceptor in: interceptorManager.getDefaultInterceptors() )
        //    current.addInterceptor( in.getName() );
        
        return this.getCurrent();
    }
    
    private Method getMethodAction( String methodName, Class classe ){
        try{
            //Method method = classe.getDeclaredMethod( methodName );
            //return method;
            return null;
        }
        catch( Exception e ){
            //throw new BrutosException( e );
            return null;
        }
    }
    
    public boolean contains( String uri ){
        return this.forms.containsKey( uri );
    }
    
    public Controller getForm( String uri ){
        return (Controller) forms.get( uri );
    }
    
    public Controller getForm( Class controllerClass ){
        return (Controller) revForms.get( controllerClass );
    }
    
    public Map getForms() {
        return Collections.unmodifiableMap( forms );
    }

    void addForm( String uri, Controller form ) {
        if( contains(uri) )
            throw new BrutosException( "duplicate uri: " + uri );

        forms.put(uri, form);
        revForms.put( form.getClassType(), form);
    }

    public WebFrameBuilder getCurrent() {
        return current;
    }
}
