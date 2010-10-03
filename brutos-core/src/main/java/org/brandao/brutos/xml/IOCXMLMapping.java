/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brand�o. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.xml;

import org.brandao.brutos.ClassType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.ContextFactory;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.ioc.IOCProviderFactory;
import org.brandao.brutos.ioc.RequestFactory;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.programatic.Bean;
import org.brandao.brutos.programatic.BeanNotFoundException;
import org.brandao.brutos.programatic.CollectionBean;
import org.brandao.brutos.programatic.ConstructorBean;
import org.brandao.brutos.programatic.IOCManager;
import org.brandao.brutos.programatic.MapBean;
import org.brandao.brutos.programatic.PropertiesBean;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brand�o
 */
public class IOCXMLMapping {
    
    private IOCManager iocManager;
    private Map<String, Map<String,Object>> beans;
    private int id;

    public IOCXMLMapping( IOCManager iocManager ) {
        this.iocManager = iocManager;
        this.id         = 1;
    }

    private String getNextId(){
        return "Bean" + (id++);
    }

    @Deprecated
    private void loadDefaultBeans(){
        iocManager.addBean( "servletContextFactory", ContextFactory.class, ScopeType.APPLICATION, false, null );
        iocManager.addBean( "servletContext", ServletContext.class, ScopeType.APPLICATION, false, "servletContextFactory" );
        
        iocManager.addBean( "iocManagerFactory", IOCProviderFactory.class, ScopeType.APPLICATION, false, null );
        iocManager.addBean( "iocManager", IOCManager.class, ScopeType.APPLICATION, false, "iocManagerFactory" );

        iocManager.addBean( "requestFactory", RequestFactory.class, ScopeType.REQUEST, false, null );
        iocManager.addBean( "request", ServletRequest.class, ScopeType.REQUEST, false, "requestFactory" );

    }
    
    public void setBeans( Map<String, Map<String,Object>> beans ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        this.beans = beans;
        //loadDefaultBeans();
        
        if( beans != null ){
            Iterator i = beans.values().iterator();
            while( i.hasNext() ){
                Object obj = i.next();
                if( obj instanceof Map ){
                    Map<String,Object> bean = (Map)obj;
                    addBean( bean );
                }
            }
        }
    }
    
    private void addBean( Map<String,Object> bean ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        //if( iocManager.getBean( (String)bean.get( "name" ) ) == null ){
            //if( "properties".equals( bean.get( "complex-type" ) ) )
            //    addProperties( bean );
            //else
                addBean0( bean );
        //}
    }
    
    public void addBean0( Map<String,Object> data ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        Bean bean = iocManager
            .addBean(
                (String)data.get( "name" ),
                data.get( "class" ) != null? 
                    (Class)Class.forName( (String) data.get( "class" ), true, Thread.currentThread().getContextClassLoader() ) :
                    null,
                ScopeType.valueOf( data.get( "scope" ).toString() ),
                Boolean.valueOf( (String)data.get( "singleton" ) ),
                (String)data.get( "factory-bean" )
            );

        bean.setFactoryMethod( (String)data.get( "factory-method" ) );
        List<Map<String,Object>> args = (List)data.get( "constructor-arg" );
        if( args != null ){
            /*
            List<Class> argsTypes = getArgTypes( args );

            Class<?>[] types = argsTypes.toArray( new Class[]{} );
            ConstructorBean cb = bean.getConstructor( types );
            Constructor constructor = bean.getInjectable().getTarget().getConstructor( types );
            */

            /*
            Constructor constructor =
                    getContructor(
                        bean.getInjectable().getTarget(), args );
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            
            ConstructorBean cb = bean.getConstructor( constructor.getParameterTypes() );
            for( int i=0;i<args.size();i++ ){
                Map<String,Object> arg = args.get( i );
                constructorBean( arg, cb, genericParameterTypes[i] );
            }
             */

            Type[] genericParameterTypes =
                    getConstructorArgs( bean, data );
            
            for( int i=0;i<args.size();i++ ){
                Map<String,Object> arg = args.get( i );
                constructorBean( arg, bean, genericParameterTypes[i] );
            }
        }
        
        List<Map<String,Object>> props = (List)data.get( "property" );
        if( props != null ){
            for( Map<String,Object> property: props ){
                addProperty( property, bean );
            }
        }
    }

    private Type[] getConstructorArgs( Bean bean, Map<String,Object> data ) throws ClassNotFoundException{
        String factoryMethod = (String) data.get( "factory-method" );

        if( factoryMethod == null ){
            Constructor constructor =
                    getContructor(
                            bean.getInjectable().getTarget(), (List)data.get( "constructor-arg" ) );
            return constructor.getGenericParameterTypes();
        }
        else{
            Method method = null;
            if( bean.getInjectable().getFactory() == null ){
                method = getMethod(
                            bean.getInjectable().getTarget(),
                            factoryMethod,
                            (List)data.get( "constructor-arg" ) );
            }
            else{
                Map<String,Object> factoryBean = beans.get( bean.getInjectable().getFactory() );
                if( factoryBean == null )
                    throw new BrutosException( "factory not defined: " + bean.getInjectable().getFactory() );

                method = getMethod(
                            factoryBean.get( "class" ) != null?
                            (Class)Class.forName( (String) factoryBean.get( "class" ), true, Thread.currentThread().getContextClassLoader() ) :
                            null,
                            factoryMethod,
                            (List)data.get( "constructor-arg" ) );

            }

            if( method == null )
                throw new BrutosException( "method factory not found: " + bean.getInjectable().getFactory() );

            return method.getGenericParameterTypes();
        }
    }

    private Constructor getContructor( Class clazz, List<Map<String,Object>> args ) throws ClassNotFoundException{
        Class[] classArgs = new Class[ args.size() ];

        int i=0;
        for( Map<String,Object> arg: args ){
            if( arg.get( "type" ) != null )
                classArgs[ i ] = ClassType.get( (String) arg.get( "type" ) );
            i++;
        }

        for( Constructor con: clazz.getConstructors() ){
            if( isCompatible( con, classArgs ) )
                return con;
        }

        String msg = "not found: " + clazz.getName() + "( ";

        for( i=0;i<classArgs.length;i++ ){
            Class arg = classArgs[i];
            msg += i != 0? ", " : "";
            msg += arg == null? "?" : arg.getName();
        }
        msg += " )";

        throw new BrutosException( msg );
    }

    private Method getMethod( Class clazz, String name, List<Map<String,Object>> args ) throws ClassNotFoundException{
        Class[] classArgs = new Class[ args.size() ];

        int i=0;
        for( Map<String,Object> arg: args ){
            if( arg.get( "type" ) != null )
                classArgs[ i ] = ClassType.get( (String) arg.get( "type" ) );
            i++;
        }

        for( Method m: clazz.getDeclaredMethods() ){
            if( m.getName().equals(name) &&
                /*( inject.getFactory() != null || Modifier.isStatic( m.getModifiers() ) ) &&*/
                isCompatible( m, classArgs ) )
                return m;
        }

        String msg = "not found: " + clazz.getName() + "( ";

        for( i=0;i<classArgs.length;i++ ){
            Class arg = classArgs[i];
            msg += i != 0? ", " : "";
            msg += arg == null? "?" : arg.getName();
        }
        msg += " )";

        throw new BrutosException( msg );
    }

    private boolean isCompatible( Constructor con, Class[] classArgs ){
        Class[] params = con.getParameterTypes();
        if( params.length == classArgs.length ){
            for( int i=0;i<params.length;i++ ){
                if( classArgs[i] != null && !params[i].isAssignableFrom( classArgs[i] ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

    private boolean isCompatible( Method con, Class[] classArgs ){
        Class[] params = con.getParameterTypes();
        if( params.length == classArgs.length ){
            for( int i=0;i<params.length;i++ ){
                if( classArgs[i] != null && !params[i].isAssignableFrom( classArgs[i] ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

    private List<Class> getArgTypes( List<Map<String,Object>> args ) throws ClassNotFoundException{
        List<Class> list = new ArrayList<Class>();
        for( Map<String,Object> arg: args )
            list.add( ClassType.get( (String) arg.get( "type" ) ) );
        
        return list;
    }

    @Deprecated
    private void constructorBean( Map<String,Object> arg, ConstructorBean cb, Type argType ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        if( "ref".equals( arg.get( "value-type" ) ) ){
            cb.addRefArg( getBean( (String)arg.get( "ref" ) ) );
        }
        else
        if( "value".equals( arg.get( "value-type" ) ) ){
            org.brandao.brutos.type.Type type = Types.getType( (Class)argType, EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" );
            if( type == null )
                throw new BrutosException( "invalid type" );
            cb.addValueArg( type.getValue( null, null, arg.get( "value" ) ) );
        }
        else
        if( "null-type".equals( arg.get( "value-type" ) ) ){
            cb.addValueArg( null );
        }
        else
        if( "properties".equals( arg.get( "complex-type" ) ) ){
            PropertiesBean mb = addProperties( arg );
            cb.addRefArg( mb.getInjectable().getName() );
        }
        else
        if( "map".equals( arg.get( "complex-type" ) ) ){
            MapBean mb = addMap( arg, argType );
            cb.addRefArg( mb.getInjectable().getName() );
        }
        else
        if( "set".equals( arg.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( arg, argType, java.util.HashSet.class );
            cb.addRefArg( mb.getInjectable().getName() );
        }
        else
        if( "list".equals( arg.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( arg, argType, java.util.LinkedList.class );
            cb.addRefArg( mb.getInjectable().getName() );
        }
        else
        if( "inner-bean".equals( arg.get( "complex-type" ) ) ){
            Bean mb = addInnerBean( arg );
            cb.addRefArg( mb.getInjectable().getName() );
        }
        
    }

    private void constructorBean( Map<String,Object> arg, Bean bean, Type argType ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        if( "ref".equals( arg.get( "value-type" ) ) ){
            bean.addConstructiorRefArg( getBean( (String)arg.get( "ref" ) ) );
        }
        else
        if( "value".equals( arg.get( "value-type" ) ) ){
            //org.brandao.brutos.type.Type type = Types.getType( getClass( argType ), EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" );
            //if( type == null )
            //    throw new BrutosException( "invalid type" );
            //bean.addConstructiorArg( type.getValue( null, null, arg.get( "value" ) ), getClass( argType ) );
            /*
            PropertyEditor editor = EditorConfigurer.getPropertyEditor(getClass( argType ));
            if( editor == null )
                throw new BrutosException( "invalid type" );

            editor.setAsText( (String)arg.get( "value" ) );
            bean.addConstructiorArg( editor.getValue(), getClass( argType ) );
            */
            bean.addConstructiorArg( (String)arg.get( "value" ), getClass( argType ) );
        }
        else
        if( "null-type".equals( arg.get( "value-type" ) ) ){
            bean.addConstructiorArg(null);
        }
        else
        if( "bean".equals( arg.get( "value-type" ) ) ){
            Bean mb = addInnerBean( (Map<String, Object>) arg.get( "bean" ));
            bean.addConstructiorRefArg( mb.getInjectable().getName() );
        }
        else
        if( "properties".equals( arg.get( "complex-type" ) ) ){
            PropertiesBean mb = addProperties( arg );
            bean.addConstructiorRefArg( mb.getInjectable().getName() );
        }
        else
        if( "map".equals( arg.get( "complex-type" ) ) ){
            MapBean mb = addMap( arg, argType );
            bean.addConstructiorRefArg( mb.getInjectable().getName() );
        }
        else
        if( "set".equals( arg.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( arg, argType, java.util.HashSet.class );
            bean.addConstructiorRefArg( mb.getInjectable().getName() );
        }
        else
        if( "list".equals( arg.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( arg, argType, java.util.LinkedList.class );
            bean.addConstructiorRefArg( mb.getInjectable().getName() );
        }

    }

    private Bean addInnerBean( Map<String,Object> data ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        //Map<String,Object> data = (Map<String, Object>)innerBean.get( "bean" );
        String beanId = getNextId();
        data.put( "name" , beanId );
        addBean( data );
        return iocManager.getBean( beanId );
    }
    
    private void addProperty( Map<String,Object> property, Bean bean ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        BeanInstance beanInstance = new BeanInstance( null, bean.getInjectable().getTarget() );
        //Type argType = getType( bean.getInjectable().getTarget(), (String)property.get( "name" ) );
        Type argType = beanInstance.getGenericType((String)property.get( "name" ));
        if( "ref".equals( property.get( "value-type" ) ) ){
            bean.addPropertyRef( 
                    (String)property.get( "name" ), 
                    getBean( (String)property.get( "ref" ) ) 
            );
        }
        else
        if( "value".equals( property.get( "value-type" ) ) ){
            //String type = (String)property.get( "type" );
            //Class classType = ClassType.get( type );
            
            //bean.addPropertyValue(
            //        (String)property.get( "name" ),
            //        Types.getType( argType, EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" ).getValue( null, null, (String)property.get( "value" ) )
            //);
            /*
            PropertyEditor editor = EditorConfigurer.getPropertyEditor( getClass( argType ) );
            if( editor == null )
                throw new BrutosException( "invalid type" );

            editor.setAsText( (String)property.get( "value" ) );
            bean.addPropertyValue( (String)property.get( "name" ), editor.getValue() );
            */
            bean.addPropertyValue( (String)property.get( "name" ), getClass( argType ), (String)property.get( "value" ) );
        }
        else
        if( "null-type".equals( property.get( "value-type" ) ) ){
            bean.addPropertyValue( (String)property.get( "name" ), null );
        }
        else
        if( "bean".equals( property.get( "value-type" ) ) ){
            Bean mb = addInnerBean( (Map<String, Object>) property.get( "bean" ));
            bean.addPropertyRef( (String)property.get( "name" ), mb.getInjectable().getName() );
        }
        else
        if( "properties".equals( property.get( "complex-type" ) ) ){
            PropertiesBean mb = addProperties( property );
            bean.addPropertyRef( (String)property.get( "name" ), mb.getInjectable().getName() );
        }
        else
        if( "map".equals( property.get( "complex-type" ) ) ){
            MapBean mb = addMap( property, argType );
            bean.addPropertyRef( (String)property.get( "name" ), mb.getInjectable().getName() );
        }
        else
        if( "set".equals( property.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( property, argType, java.util.HashSet.class );
            bean.addPropertyRef( (String)property.get( "name" ), mb.getInjectable().getName() );
        }
        else
        if( "list".equals( property.get( "complex-type" ) ) ){
            CollectionBean mb = addCollection( property, argType, java.util.LinkedList.class );
            bean.addPropertyRef( (String)property.get( "name" ), mb.getInjectable().getName() );
        }
            
    }

    @Deprecated
    private Type getType( Class<?> classz, String field ){
        try{
            return classz.getDeclaredField( field ).getGenericType();
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    private PropertiesBean addProperties( Map<String,Object> data ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        /*
        PropertiesBean pb = iocManager
            .addProperties(
                (String)data.get( "name" ),
                (Class)Class.forName( (String) data.get( "class" ), true, Thread.currentThread().getContextClassLoader() ),
                (String)data.get( "factory-bean" )
            );
        */
        PropertiesBean pb = iocManager
            .addProperties(
                getNextId(),
                java.util.Properties.class,
                null
            );

        List<Map<String,String>> props = (List)data.get( "data" );
        for( Map<String,String> prop: props )
            addItem( pb, (String)prop.get( "name" ), (String)prop.get( "value" ), null );
        
        return pb;
    }
    
    private MapBean addMap( Map<String,Object> arg, Type argType ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        Type[] types = getMapType( argType );
        List<Map<String,Object>> data = (List)arg.get( "data" );

        //MapBean mb = iocManager.addMap( argType.toString(), java.util.HashMap.class, types[0], types[1], null );
        MapBean mb = iocManager.addMap( getNextId(), java.util.HashMap.class, getClass(types[0]), getClass(types[1]), null );
        for( Map<String,Object> item: data ){
            String keyRef   = null;
            Object keyValue = null;
            Object value    = null; 
            String valueRef = null;

            Map<String,Object> key = (Map<String,Object>)item.get("key");
            if( "value".equals( key.get( "type" ) ) ){
                //org.brandao.brutos.type.Type type = org.brandao.brutos.type.Types.getType( types[0], EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" );
                //if( type == null )
                //    throw new MappingException( "use <ref bean=''/>" );
                
                //keyValue = type.getValue( null, null, key.get( "value" ) );

                /*
                PropertyEditor editor = EditorConfigurer.getPropertyEditor(getClass( types[0] ));
                if( editor == null )
                    throw new MappingException( "use <ref bean=''/>" );

                editor.setAsText( (String)key.get( "value" ) );
                keyValue = editor.getValue();
                */
                
                keyValue = (String)key.get( "value" );
            }
            else
            if( "ref".equals( key.get( "type" ) ) ){
                keyRef = getBean( (String)key.get( "value" ) );
            }
            else
            if( "bean".equals( key.get( "type" ) ) ){
                Bean bean = addInnerBean( (Map<String,Object>)key );
                keyRef = bean.getInjectable().getName();
            }
            else
            if( "null-type".equals( key.get( "type" ) ) ){
                keyValue = null;
            }
            else
            if( "properties".equals( key.get( "complex-type" ) ) ){
                PropertiesBean bean = addProperties( key );
                keyRef = bean.getInjectable().getName();
            }
            else
            if( "map".equals( key.get( "complex-type" ) ) ){
                MapBean bean = addMap( key, types[0] );
                keyRef = bean.getInjectable().getName();
            }
            else
            if( "set".equals( key.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( key, types[0], java.util.HashSet.class );
                keyRef = bean.getInjectable().getName();
            }
            else
            if( "list".equals( key.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( key, types[0], java.util.LinkedList.class );
                keyRef = bean.getInjectable().getName();
            }

            Map<String,Object> val = (Map<String,Object>)item.get("value");
            
            if( "value".equals( val.get( "type" ) ) ){
                //org.brandao.brutos.type.Type type = org.brandao.brutos.type.Types.getType( types[1], EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" );
                //if( type == null )
                //    throw new MappingException( "use <ref bean=''/>" );
                
                //value = type.getValue( null, null, val.get( "value" ) );

                //PropertyEditor editor = EditorConfigurer.getPropertyEditor(getClass( types[1] ));
                //if( editor == null )
                //    throw new MappingException( "use <ref bean=''/>" );

                //editor.setAsText( (String)val.get( "value" ) );
                //value = editor.getValue();
                value = (String)val.get( "value" );
            }
            else
            if( "ref".equals( val.get( "type" ) ) ){
                valueRef = getBean( (String)val.get( "value" ) );
            }
            else
            if( "bean".equals( val.get( "type" ) ) ){
                Bean bean = addInnerBean( (Map<String,Object>)val );
                valueRef = bean.getInjectable().getName();
            }
            else
            if( "null-type".equals( val.get( "type" ) ) ){
                value = null;
            }
            else
            if( "properties".equals( val.get( "complex-type" ) ) ){
                PropertiesBean bean = addProperties( val );
                valueRef = bean.getInjectable().getName();
            }
            else
            if( "map".equals( val.get( "complex-type" ) ) ){
                MapBean bean = addMap( val, types[1] );
                valueRef = bean.getInjectable().getName();
            }
            else
            if( "set".equals( val.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( val, types[1], java.util.HashSet.class );
                valueRef = bean.getInjectable().getName();
            }
            else
            if( "list".equals( val.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( val, types[1], java.util.LinkedList.class );
                valueRef = bean.getInjectable().getName();
            }

            mb.add( keyValue, keyRef, value, valueRef );
        }
        return mb;
    }


    private void addItem( MapBean mb, Object key, Object value, String ref ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        if( value != null )
            mb.addValue( key, value );
        else
            mb.addBean( key, getBean( ref ) );
    }

    private CollectionBean addCollection( Map<String,Object> arg, Type argType, Class<?> classType ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        Type genericType = getCollectionType( argType );
        List<Map<String,Object>> data = (List)arg.get( "data" );

        CollectionBean mb = iocManager.addCollection( getNextId(), classType, getClass(genericType), null );
        for( Map<String,Object> item: data ){
            
            if( "value".equals( item.get( "type" ) ) ){
                org.brandao.brutos.type.Type type = org.brandao.brutos.type.Types.getType( genericType, EnumerationType.ORDINAL, "yyyy-MM-dd hh:mm:ss" );
                if( type == null )
                    throw new MappingException( "use <ref bean=''/>" );
                
                mb.addValue( type.getValue( null, null, item.get( "value" ) ) );
            }
            else
            if( "ref".equals( item.get( "type" ) ) ){
                mb.addBean( getBean( (String)item.get( "ref" ) ) );
            }
            else
            if( "bean".equals( item.get( "type" ) ) ){
                Bean bean = addInnerBean( (Map<String,Object>)item );
                mb.addBean( bean.getInjectable().getName() );
            }
            else
            if( "null-type".equals( item.get( "type" ) ) ){
                mb.addValue(null);
            }
            else
            if( "properties".equals( item.get( "complex-type" ) ) ){
                PropertiesBean bean = addProperties( item );
                mb.addBean( bean.getInjectable().getName() );
            }
            else
            if( "map".equals( item.get( "complex-type" ) ) ){
                MapBean bean = addMap( item, genericType );
                mb.addBean( bean.getInjectable().getName() );
            }
            else
            if( "set".equals( item.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( item, genericType, java.util.HashSet.class );
                mb.addBean( bean.getInjectable().getName() );
            }
            else
            if( "list".equals( item.get( "complex-type" ) ) ){
                CollectionBean bean = addCollection( item, genericType, java.util.LinkedList.class );
                mb.addBean( bean.getInjectable().getName() );
            }

        }
        return mb;
    }
    
    private Type[] getMapType( java.lang.reflect.Type classe ){
        Type[] genericType = null;
        
        
        java.lang.reflect.Type classType = classe;
        if( classType instanceof ParameterizedType ){
            genericType = new Type[2];
            genericType[0] = ((ParameterizedType)classType).getActualTypeArguments()[0];
            genericType[1] = ((ParameterizedType)classType).getActualTypeArguments()[1];
        }
        else
            //throw new MappingException( "key type and collection type should be specified" );
            genericType = new Type[]{ Object.class, Object.class };
        return genericType;
    }
    
    private Type getCollectionType( java.lang.reflect.Type classe ){
        Type genericType = null;
        
        
        if( classe instanceof ParameterizedType ){
            genericType = ((ParameterizedType)classe).getActualTypeArguments()[0];
        }
        else
            throw new MappingException( "collection type should be specified" );
        
        return genericType;
    }
    
    private String getBean( String ref ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        Bean bean = iocManager.getBean( ref );
        if( bean == null ){
            Map<String,Object> tmp = beans.get( ref );
            if( tmp == null )
                throw new BeanNotFoundException( ref );
            else
                addBean( tmp );
        }
        return ref;
    }

    private Class getClass( Type type ){
        if( type instanceof ParameterizedType ){
            return (Class)((ParameterizedType)type).getRawType();
        }
        else
            return (Class)type;

    }
    
    public IOCManager getIocManager() {
        return iocManager;
    }

    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }
    
}
