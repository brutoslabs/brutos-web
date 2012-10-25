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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.mapping.ioc.PropertyInject;
import org.brandao.brutos.mapping.ioc.ValueInject;
import org.brandao.brutos.ClassUtil;
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

    public Bean addConstructiorArg( Object value, Class type ){
        bean.getConstructor().addArg( new ValueInject( type, value ) );
        return this;
    }

    public Bean addConstructiorArg( Object value ){
        return addConstructiorArg( value, value == null? null : value.getClass() );
    }

    public Bean addConstructiorArg( boolean value ){
        bean.getConstructor().addArg( new ValueInject(boolean.class, new Boolean(value) ) );
        return this;
    }

    public Bean addConstructiorArg( byte value ){
        bean.getConstructor().addArg( new ValueInject( byte.class, new Byte(value) ) );
        return this;
    }

    public Bean addConstructiorArg( char value ){
        bean.getConstructor().addArg( new ValueInject( char.class, new Character(value) ) );
        return this;
    }

    public Bean addConstructiorArg( double value ){
        bean.getConstructor().addArg( new ValueInject( double.class, new Double(value) ) );
        return this;
    }

    public Bean addConstructiorArg( float value ){
        bean.getConstructor().addArg( new ValueInject( float.class, new Float(value) ) );
        return this;
    }

    public Bean addConstructiorArg( int value ){
        bean.getConstructor().addArg( new ValueInject( int.class, new Integer(value) ) );
        return this;
    }

    public Bean addConstructiorArg( long value ){
        bean.getConstructor().addArg( new ValueInject( long.class, new Long(value) ) );
        return this;
    }

    public Bean addConstructiorArg( short value ){
        bean.getConstructor().addArg( new ValueInject( short.class, new Short(value) ) );
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

    /**
     * @deprecated
     * @param paramType
     * @return
     */
    public ConstructorBean getConstructor( Class[] paramType ){
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

        SetterProperty set = null;//beanInstance.getSetter(name);
        Method method = set == null? null : set.getMethod();

        if( method == null ){
            Class classType = null;
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
        return addPropertyValue( name, boolean.class, Boolean.valueOf(value));
    }

    public Bean addPropertyValue( String name, byte value ){
        return addPropertyValue( name, byte.class, new Byte(value) );
    }

    public Bean addPropertyValue( String name, char value ){
        return addPropertyValue( name, char.class, new Character(value) );
    }

    public Bean addPropertyValue( String name, double value ){
        return addPropertyValue( name, double.class, new Double(value) );
    }

    public Bean addPropertyValue( String name, float value ){
        return addPropertyValue( name, float.class, new Float(value) );
    }

    public Bean addPropertyValue( String name, int value ){
        return addPropertyValue( name, int.class, new Integer(value) );
    }

    public Bean addPropertyValue( String name, long value ){
        return addPropertyValue( name, long.class, new Long(value) );
    }

    public Bean addPropertyValue( String name, short value ){
        return addPropertyValue( name, short.class, new Short(value) );
    }

    public Bean addPropertyValue( String name, Class argType, Object arg ){
        Method method = null;
        try{
            String methodName =
                    "set" +
                    name.substring( 0, 1 ).toUpperCase() +
                    name.substring( 1 );

            Class classType = null;

            if( bean instanceof ComplexObjectInject )
                classType = ((ComplexObjectInject)bean).getType();
            else
                classType = bean.getTarget();

            //for( Method m: classType.getMethods() ){
            Method[] methods = classType.getMethods();
            for( int i=0;i<methods.length;i++ ){
                Method m = methods[i];
                if( m.getParameterTypes().length == 1 && methodName.equals( m.getName() ) && ClassUtil.getWrapper( m.getParameterTypes()[0] ).isAssignableFrom( ClassUtil.getWrapper(argType) ) ){
                    method = classType.getMethod( methodName, new Class[]{m.getParameterTypes()[0]} );
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
