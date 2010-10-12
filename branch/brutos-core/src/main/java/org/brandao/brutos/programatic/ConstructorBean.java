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

import java.lang.reflect.Constructor;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.ConstructorInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.ClassType;

/**
 *
 * @author Afonso Brandao
 */
public class ConstructorBean {
    
    Bean bean;
    IOCManager manager;
    
    public ConstructorBean( Bean bean, IOCManager manager, Class[] params ) {
        this.bean = bean;
        this.manager = manager;
        
        try{
            Constructor cons = null;
            
            if( bean.getInjectable() instanceof ComplexObjectInject )
                cons = ((ComplexObjectInject)bean.getInjectable()).getType().getConstructor( params );
            else
                cons = bean.getInjectable().getTarget().getConstructor( params );
            
            
            bean.getInjectable().setConstructor( new ConstructorInject( cons ) );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public ConstructorBean addValueArg( Object value ){
        Constructor con  = bean.getInjectable().getConstructor().getContructor();
        Class<?> typeArg = con.getParameterTypes()[ bean.getInjectable().getConstructor().getArgs().size() ];
        
        if( value != null && !ClassType.getWrapper( typeArg ).isAssignableFrom( value.getClass() ) )
            throw new BrutosException( "invalid type" );
        
        bean.getInjectable().getConstructor().getArgs().add( new ValueInject( typeArg, value ) );
        
        return this;
    }
    
    public ConstructorBean addRefArg( String ref ){
        Bean refBean = manager.getBean( ref );

        if( refBean == null )
            throw new BeanNotFoundException( ref );
        
        Constructor con  = bean.getInjectable().getConstructor().getContructor();
        Class<?> typeArg = con.getParameterTypes()[ bean.getInjectable().getConstructor().getArgs().size() ];
        
        if( !typeArg.isAssignableFrom( refBean.getInjectable().getTarget() ) )
            throw new BrutosException( 
                    bean.getInjectable().getConstructor().getContructor().toGenericString() + 
                    " index " + bean.getInjectable().getConstructor().getArgs().size() + 
                    ": expected " + refBean.getInjectable().getTarget().getName() );
        
        bean.getInjectable().getConstructor().getArgs().add( refBean.getInjectable() );
        
        return this;
    }
    
    public Bean addPropertyRef( String name, String arg ){
        return bean.addPropertyRef( name, arg );
    }
    
    public Bean addPropertyValue( String name, Object arg ){
        return bean.addPropertyValue( name, arg );
    }
    
    public Bean getBean(){
        return bean;
    }
    
}
