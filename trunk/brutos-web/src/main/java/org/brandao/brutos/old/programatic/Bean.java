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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.PropertyInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.ClassType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.GetterProperty;
import org.brandao.brutos.bean.SetterProperty;

/**
 *
 * @author Afonso Brandao
 */
public class Bean {
    
    Injectable bean;
    IOCManager manager;
    BeanInstance beanInstance;

    public Bean( Injectable inject, IOCManager manager ) {
        this.bean = inject;
        this.manager = manager;

        if( bean instanceof ComplexObjectInject )
            this.beanInstance = new BeanInstance( null, ((ComplexObjectInject)bean).getType() );
        else
            this.beanInstance = new BeanInstance( null, inject.getTarget() );
    }
    
    public Injectable getInjectable(){
        return this.bean;
    }

    public Bean addConstructiorArg( Object value, Class<?> type ){
        bean.getConstructor().addArg( new ValueInject( type, value ) );
        return this;
    }

    public Bean addConstructiorArg( Object value ){
        return addConstructiorArg( value, value == null? null : value.getClass() );
    }

    public Bean addConstructiorArg( boolean value ){
        bean.getConstructor().addArg( new ValueInject(boolean.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( byte value ){
        bean.getConstructor().addArg( new ValueInject( byte.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( char value ){
        bean.getConstructor().addArg( new ValueInject( char.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( double value ){
        bean.getConstructor().addArg( new ValueInject( double.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( float value ){
        bean.getConstructor().addArg( new ValueInject( float.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( int value ){
        bean.getConstructor().addArg( new ValueInject( int.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( long value ){
        bean.getConstructor().addArg( new ValueInject( long.class, value ) );
        return this;
    }

    public Bean addConstructiorArg( short value ){
        bean.getConstructor().addArg( new ValueInject( short.class, value ) );
        return this;
    }

    public void setFactoryMethod( String method ){
        bean.getConstructor().setMethodFactory(method);
    }

    public Bean addConstructiorRefArg( String ref ){
        Bean refBean = manager.getBean( ref );

        if( refBean == null )
            throw new BeanNotFoundException( ref );

        bean.getConstructor().addArg( refBean.getInjectable() );
        return this;
    }

    @Deprecated
    public ConstructorBean getConstructor( Class ... paramType ){
        return new ConstructorBean( this, manager, paramType );
        /*
        List<Injectable> inject = new ArrayList();
        for( String beanName: args ){
            Bean refBean = manager.getBean( beanName );
            
            if( refBean == null )
                throw new BeanNotFoundException( beanName );
            
            inject.add( refBean.getInjectable() );
        }
        
        bean.setConstructor(
                new ConstructorInject(
                    null,//construtor do bean
                    inject.toArray( new Injectable[]{} )
                )
        );
        
        return this;
        */
    }
    
    public Bean addPropertyRef( String name, String arg ){
        Bean refBean = manager.getBean( arg );

        if( refBean == null )
            throw new BeanNotFoundException( arg );

        /*
        Method method = null;
        Field field = null;
        try{
            String methodName = 
                    "set" + 
                    name.substring( 0, 1 ).toUpperCase() +
                    name.substring( 1 );
            
            Class<?> classType = null;
            if( bean instanceof ComplexObjectInject )
                classType = ((ComplexObjectInject)bean).getType();
            else
                classType = bean.getTarget();
            
            field = classType.getDeclaredField( name );
            method = classType.getMethod( methodName, field.getType() );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
        */

        SetterProperty set = beanInstance.getSetter(name);
        Method method = set == null? null : set.getMethod();

        if( method == null ){
            Class<?> classType = null;
            if( bean instanceof ComplexObjectInject )
                classType = ((ComplexObjectInject)bean).getType();
            else
                classType = bean.getTarget();

            throw new BrutosException( "can not set property: " + name + " in " + classType.getName() );
        }
        
        bean.getProperties().add(
            new PropertyInject(
                name,
                refBean.getInjectable(),
                method
            )
        );
        return this;
    }

    /*
    public Bean addPropertyValue( String name, Object arg ){
        Method method = null;
        Field field = null;
        try{
            String methodName = 
                    "set" + 
                    name.substring( 0, 1 ).toUpperCase() +
                    name.substring( 1 );
            
            Class<?> classType = null;
            
            if( bean instanceof ComplexObjectInject )
                classType = ((ComplexObjectInject)bean).getType();
            else
                classType = bean.getTarget();
            
            field = classType.getDeclaredField( name );
            method = classType.getMethod( methodName, field.getType() );
            
            if( arg != null && !ClassType.getWrapper( field.getType() ).isInstance( arg ) )
                throw new BrutosException( "invalid type" );
                
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
        
        bean.getProperties().add(
            new PropertyInject(
                name,
                new ValueInject( field.getType(), arg ),
                method
            )
        );
        return this;
    }
    */
    
    public Bean addPropertyValue( String name, Object arg ){
        return addPropertyValue( name,arg == null? Object.class : arg.getClass(), arg );
    }

    public Bean addPropertyValue( String name, boolean value ){
        return addPropertyValue( name, boolean.class, value );
    }

    public Bean addPropertyValue( String name, byte value ){
        return addPropertyValue( name, byte.class, value );
    }

    public Bean addPropertyValue( String name, char value ){
        return addPropertyValue( name, char.class, value );
    }

    public Bean addPropertyValue( String name, double value ){
        return addPropertyValue( name, double.class, value );
    }

    public Bean addPropertyValue( String name, float value ){
        return addPropertyValue( name, float.class, value );
    }

    public Bean addPropertyValue( String name, int value ){
        return addPropertyValue( name, int.class, value );
    }

    public Bean addPropertyValue( String name, long value ){
        return addPropertyValue( name, long.class, value );
    }

    public Bean addPropertyValue( String name, short value ){
        return addPropertyValue( name, short.class, value );
    }

    public Bean addPropertyValue( String name, Class argType, Object arg ){
        Method method = null;
        try{
            String methodName =
                    "set" +
                    name.substring( 0, 1 ).toUpperCase() +
                    name.substring( 1 );

            Class<?> classType = null;

            if( bean instanceof ComplexObjectInject )
                classType = ((ComplexObjectInject)bean).getType();
            else
                classType = bean.getTarget();

            for( Method m: classType.getMethods() ){
                if( m.getParameterTypes().length == 1 && methodName.equals( m.getName() ) && ClassType.getWrapper( m.getParameterTypes()[0] ).isAssignableFrom( ClassType.getWrapper(argType) ) ){
                    method = classType.getMethod( methodName, m.getParameterTypes()[0] );
                    break;
                }
            }
            
            if( method == null )
                throw new BrutosException( "not found: " + methodName + "(" + argType.getName()  + ")" );

        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

        bean.getProperties().add(
            new PropertyInject(
                name,
                new ValueInject( argType, arg ),
                method
            )
        );
        return this;
    }

}
