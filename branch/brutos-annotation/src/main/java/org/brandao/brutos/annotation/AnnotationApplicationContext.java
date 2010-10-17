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

package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.CheckSearch;
import org.brandao.brutos.SearchClass;
import org.brandao.brutos.annotation.ioc.Injectable;
import org.brandao.brutos.programatic.*;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.interceptor.Interceptor;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends ApplicationContext{
    
    private SearchClass sf;
    private List<Class> types;
    private List<Class> controllers;
    private List<Class> beans;
    private List<Class> interceptors;

    public AnnotationApplicationContext() {
    }

    public void configure( Configuration config, ServletContextEvent sce ) {
        super.configure( config, sce );
        loadClass();
        loadTypes();
    }

    private void loadTypes(){
        TypeConfiguration tc = new TypeConfiguration();
        tc.setResource(new Types());
        tc.setSource(types);
        tc.configure();
    }

    public void destroy(){
        this.sf           = null;
        this.controllers  = null;
        this.interceptors = null;
        this.types        = null;
        setIocManager( null );
        setWebFrameManager( null );
    }
    
    private void loadClass(){
        sf  = new SearchClass();
        
        sf.setCheck( new CheckSearch() {

                public boolean checkClass(Class<?> classe) {
                    boolean isController = classe.isAnnotationPresent( Controller.class );
                    boolean isType = classe.isAnnotationPresent( TypeDef.class ) &&
                            Type.class.isAssignableFrom(classe);
                    boolean isInjectable  = classe.isAnnotationPresent( Injectable.class );
                    boolean isInterceptor = classe.isAnnotationPresent( Intercepts.class )&&
                            Interceptor.class.isAssignableFrom(classe);
                    if( isController || isType || isInjectable || isInterceptor )
                        return true;
                    else
                        return false;
                }
            } 
        );
        
        sf.load( Thread.currentThread().getContextClassLoader() );
        sf.loadDirs( Thread.currentThread().getContextClassLoader() );

        this.beans = new ArrayList<Class>();
        this.controllers = new ArrayList<Class>();
        this.interceptors = new ArrayList<Class>();
        this.types = new ArrayList<Class>();

        for( Class classe: sf.getClasses() ){
            
            if( classe.isAnnotationPresent( Controller.class ) )
                this.controllers.add(classe);

            if( classe.isAnnotationPresent( Intercepts.class ) )
                this.types.add(classe);

            if( classe.isAnnotationPresent( Injectable.class ) )
                this.beans.add(classe);
            
            if( classe.isAnnotationPresent( TypeDef.class ) )
                this.types.add(classe);

        }
    }


    public void loadIOCManager(IOCManager iocManager) {
        IOCConfiguration tc = new IOCConfiguration();
        tc.setResource(iocManager);
        tc.setSource(beans);
        tc.configure();
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager) {
        ControllerConfiguration tc = new ControllerConfiguration();
        tc.setResource(webFrameManager);
        tc.setSource(controllers);
        tc.configure();
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager) {
        InterceptorConfiguration tc = new InterceptorConfiguration();
        tc.setResource(interceptorManager);
        tc.setSource(interceptors);
        tc.configure();
    }
        
}
