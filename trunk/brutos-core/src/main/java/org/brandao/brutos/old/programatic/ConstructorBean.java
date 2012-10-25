/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.old.programatic;

import java.lang.reflect.Constructor;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.ConstructorInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.ClassUtil;

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
            
            
            //bean.getInjectable().setConstructor( new ConstructorInject( cons ) );
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
        Class typeArg = con.getParameterTypes()[ bean.getInjectable().getConstructor().getArgs().size() ];
        
        if( value != null && !ClassUtil.getWrapper( typeArg ).isAssignableFrom( value.getClass() ) )
            throw new BrutosException( "invalid type" );
        
        bean.getInjectable().getConstructor().getArgs().add( new ValueInject( typeArg, value ) );
        
        return this;
    }
    
    public ConstructorBean addRefArg( String ref ){
        Bean refBean = manager.getBean( ref );

        if( refBean == null )
            throw new BeanNotFoundException( ref );
        
        Constructor con  = bean.getInjectable().getConstructor().getContructor();
        Class typeArg = con.getParameterTypes()[ bean.getInjectable().getConstructor().getArgs().size() ];
        
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
