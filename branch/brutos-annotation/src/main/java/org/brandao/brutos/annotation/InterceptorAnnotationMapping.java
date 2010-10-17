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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.interceptor.Interceptors;
import org.brandao.brutos.programatic.InterceptorBuilder;
import org.brandao.brutos.programatic.InterceptorManager;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.Param;
import org.brandao.brutos.programatic.InterceptorStackBuilder;

/**
 *
 * @author Afonso Brandao
 */
@Deprecated
public class InterceptorAnnotationMapping {
    
    private InterceptorManager interceptorManager;
    
    public InterceptorAnnotationMapping( InterceptorManager interceptorManager ) {
        this.interceptorManager = interceptorManager;
    }
    
    public void setBeans( List<Class> classBeans ){
        List<Class> stacks = new ArrayList();
        
        for( Class clazz: classBeans ){
            if( clazz.isAnnotationPresent( Intercepts.class ) && Interceptors.class.isAssignableFrom( clazz ) )
                stacks.add( clazz );
            else
            if( clazz.isAnnotationPresent( Intercepts.class ) )
                addInterceptor( clazz, (Intercepts) clazz.getAnnotation( Intercepts.class ) );
        }
        
        //stacks
        for( Class clazz: stacks ){
            addInterceptorStack( clazz, (Intercepts) clazz.getAnnotation( Intercepts.class ) );
        }
    }
    
    private void addInterceptorStack( Class classInterceptor, Intercepts ann ){
        try{
            if( !Interceptors.class.isAssignableFrom( classInterceptor ) )
                throw new BrutosException( "the class does not implement interceptors: " + classInterceptor.getName() );
            
            Constructor c = classInterceptor.getConstructor();
            Interceptors i = (Interceptors)c.newInstance();
            Class[] interceptors = i.getInterceptors();
            
            InterceptorStackBuilder isb = 
                    interceptorManager.addInterceptorStack(  
                        ann.name().length() == 0? 
                            classInterceptor.getName() : 
                            ann.name(), 
                        ann.isDefault() 
                    );
            
            addParams( isb, ann.params() );
            
            for( Class interceptor: interceptors ){
                if( !interceptor.isAnnotationPresent( Intercepts.class ) )
                    throw new BrutosException( "is not a interceptor: " + interceptor.getName() );
                
                Intercepts in = (Intercepts) interceptor.getAnnotation( Intercepts.class );
                String name = in.name().length() == 0? 
                                interceptor.getName() : 
                                in.name();

                isb.addInterceptor( name );
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    private void addInterceptor( Class classInterceptor, Intercepts ann ){
        InterceptorBuilder ib = interceptorManager.addInterceptor( 
                        ann.name().length() == 0? 
                            classInterceptor.getName() : 
                            ann.name(), 
                        classInterceptor, 
                        ann.isDefault() 
                    );
        
        addParams( ib, ann.params() );
    }
    
    private void addParams( InterceptorStackBuilder ib, Param[] params ){
        for( Param param: params )
            ib.addParameter( param.name(), param.value() );
    }
    
    private void addParams( InterceptorBuilder ib, Param[] params ){
        for( Param param: params )
            ib.addParameter( param.name(), param.value() );
    }
}
