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

package org.brandao.brutos.programatic;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 *
 * @author Afonso Brandao
 */
public class ControllerManager {

    private static Logger logger = LoggerProvider.getCurrentLoggerProvider().getLogger(ControllerManager.class.getName());
    private Map forms;
    private Map revForms;
    private ValidatorProvider validatorProvider;
    private ControllerBuilder current;
    
    private InterceptorManager interceptorManager;
    
    public ControllerManager( InterceptorManager interceptorManager, ValidatorProvider validatorProvider) {
        this.forms              = new HashMap();
        this.revForms           = new HashMap();
        this.interceptorManager = interceptorManager;
        this.validatorProvider  = validatorProvider;
    }

    public ControllerBuilder addController( String id, Class classtype ){
        return addController( id, null, null, classtype, ScopeType.valueOf("prototype"), "invoke" );
    }
    
    public ControllerBuilder addController( String id, Class classtype, ScopeType scope ){
        return addController( id, null, null, classtype, scope, "invoke" );
    }

    public ControllerBuilder addController( String id, String view, Class classtype ){
        return addController( id, view, null, classtype, ScopeType.valueOf("prototype"), "invoke" );
    }
    
    public ControllerBuilder addController( String id, String view,
           String name, Class classType, ScopeType scope,
           String actionId ){
        return addController( id, view, DispatcherType.FORWARD, name, classType, scope, actionId );
    }

    public ControllerBuilder addController( String id, String view, DispatcherType dispatcherType,
            String name, Class classType, ScopeType scope,
            String actionId ){

        id = id == null || id.replace( " ", "" ).length() == 0? null : id;
        view = view == null || view.replace( " ", "" ).length() == 0 ? null : view;

        if( scope == null )
            throw new BrutosException( "scope is required!" );
            
        if( actionId == null )
            actionId = "invoke";
        
        if( name == null || name.length() == 0 )
            name = classType.getSimpleName();
        
        Form fr = new Form();
        fr.setUri( id );
        fr.setId( name );
        fr.setPage( view );
        fr.setClassType( classType );
        fr.setScope( scope );
        fr.setMethodId( actionId );
        fr.setRedirect(false);
        fr.setDispatcherType(dispatcherType);
        
        //Action
        Action ac = new Action();
        ac.setPreAction( getMethodAction( "preAction", fr.getClassType() ) );
        ac.setPostAction( getMethodAction( "postAction", fr.getClassType() ) );
        fr.setAcion( ac );
        
        //forms.put( fr.getUri(), fr );
        //revForms.put( fr.getClassType(), fr );
        addForm( fr.getUri(), fr );
        this.current = new ControllerBuilder( fr, this, interceptorManager, validatorProvider );
        
        for( Interceptor in: interceptorManager.getDefaultInterceptors() )
            current.addInterceptor( in.getName() );
        
        return this.getCurrent();
    }
    
    private Method getMethodAction( String methodName, Class classe ){
        try{
            Method method = classe.getDeclaredMethod( methodName );
            return method;
        }
        catch( Exception e ){
            //throw new BrutosException( e );
            return null;
        }
    }
    
    public boolean contains( String uri ){
        return this.forms.containsKey( uri );
    }
    
    public Form getForm( String uri ){
        return (Form)forms.get( uri );
    }
    
    public Form getForm( Class controllerClass ){
        return (Form)revForms.get( controllerClass );
    }
    
    public Map<String, Form> getForms() {
        return Collections.unmodifiableMap( forms );
    }

    void addForm( String uri, Form form ) {
        if( contains(uri) )
            throw new BrutosException( "duplicate id: " + uri );

        forms.put(uri, form);
        revForms.put( form.getClassType(), form);
    }

    public ControllerBuilder getCurrent() {
        return current;
    }
}
