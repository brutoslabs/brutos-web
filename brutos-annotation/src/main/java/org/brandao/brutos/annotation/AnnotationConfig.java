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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.CheckSearch;
import org.brandao.brutos.SearchClass;
import org.brandao.brutos.WebFrame;
import org.brandao.brutos.annotation.Frame;
import org.brandao.brutos.annotation.ioc.Injectable;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.MappingUseBean;
import org.brandao.brutos.annotation.MappingUseBeans;
import org.brandao.brutos.annotation.MethodMapping;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.annotation.PropertyBean;
import org.brandao.brutos.annotation.Request;
import org.brandao.brutos.annotation.Response;
import org.brandao.brutos.annotation.ThrowableSafe;
import org.brandao.brutos.annotation.UseBean;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.programatic.*;
import org.brandao.brutos.mapping.Mapping;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.view.ViewProvider;

/**
 *
 * @author Afonso Brandao
 */
@Deprecated
public class AnnotationConfig extends Mapping{
    
    SearchClass sf;
    
    public AnnotationConfig() {
        loadClass();
    }

    public void configure( Configuration config, ServletContextEvent sce ) {
        super.configure( config, sce );
        //webFrameMapping();
        //iocAnnotationMapping( config, sce );
    }
    
    public void destroy(){
        if( getIocManager().getProvider() != null ){
            getIocManager().getProvider().destroy();
        }
            
        sf = null;
        setIocManager( null );
        setWebFrameManager( null );
    }
    
    private void loadClass(){
        sf  = new SearchClass();
        
        sf.setCheck( new CheckSearch() {

                public boolean checkClass(Class<?> classe) {
                    //Class[] types = classe.getInterfaces();
                    //if( WebFrame.class.isAssignableFrom( classe ) ){
                    
                    if( classe.isAnnotationPresent( Frame.class ) || 
                        classe.isAnnotationPresent( Injectable.class ) ||
                        classe.isAnnotationPresent( Intercepts.class ) )
                        return true;
                    else
                        return false;
                       
                }
            } 
        
        );
        
        sf.load( Thread.currentThread().getContextClassLoader() );
        sf.loadDirs( Thread.currentThread().getContextClassLoader() );
    }

    public void loadIOCManager(IOCManager iocManager) {
        if( iocManager.getProvider() != null ){
            IOCAnnotationMapping aiom = new IOCAnnotationMapping( iocManager );
            aiom.setBeans( sf.getClasses() );
            aiom.configure();
        }
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager) {
        WebFrameAnnotationMapping wfam = new WebFrameAnnotationMapping( webFrameManager );
        wfam.setClassBeans( sf.getClasses() );
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager) {
        InterceptorAnnotationMapping aiom = new InterceptorAnnotationMapping( interceptorManager );
        aiom.setBeans( sf.getClasses() );
    }
        
}
