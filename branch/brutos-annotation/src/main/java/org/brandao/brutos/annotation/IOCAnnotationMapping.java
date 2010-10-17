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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.CheckSearch;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.ioc.FactoryBean;
import org.brandao.brutos.SearchClass;
import org.brandao.brutos.annotation.ioc.ConstructorInject;
import org.brandao.brutos.annotation.Frame;
import org.brandao.brutos.annotation.ioc.Inject;
import org.brandao.brutos.annotation.ioc.Injectable;
import org.brandao.brutos.annotation.ioc.CollectionInject;
import org.brandao.brutos.annotation.ioc.MapInject;
import org.brandao.brutos.annotation.ioc.Prop;
import org.brandao.brutos.annotation.ioc.PropertiesInject;
import org.brandao.brutos.annotation.ioc.PropertyInject;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.ioc.SetInject;
import org.brandao.brutos.mapping.*;
import org.brandao.brutos.programatic.*;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
@Deprecated
public class IOCAnnotationMapping {
    
    Map<String, EntryClass> beans;
    
    private IOCManager iocManager;

    public IOCAnnotationMapping( IOCManager iocManager ) {
        this.setIocManager(iocManager);
    }

    public void configure(/* Configuration config, ServletContextEvent sce*/ ){
        try{
            Set<String> keys = beans.keySet();
            for( String beanName: keys ){
            //if( getIocManager().getBean( beanName ) == null )
                addBean( beanName, null, "" );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public void setBeans( List<Class> classBeans ){
        
        beans = new HashMap();
        
        for( Class<?> classe: classBeans ){
            if( classe.isAnnotationPresent( org.brandao.brutos.annotation.Frame.class ) ){
                org.brandao.brutos.annotation.Frame data =
                        classe.getAnnotation( org.brandao.brutos.annotation.Frame.class );
                
                beans.put( data.uri(), new EntryClass( data, classe ) );
            }
            else
            if( classe.isAnnotationPresent( org.brandao.brutos.annotation.Intercepts.class ) ){
                org.brandao.brutos.annotation.Intercepts data =
                        classe.getAnnotation( org.brandao.brutos.annotation.Intercepts.class );
                
                beans.put( data.name(), new EntryClass( data, classe ) );
            }
            else
            if( classe.isAnnotationPresent( org.brandao.brutos.annotation.ioc.Injectable.class ) ){
                org.brandao.brutos.annotation.ioc.Injectable data =
                        classe.getAnnotation( org.brandao.brutos.annotation.ioc.Injectable.class );
                
                beans.put( data.name(), new EntryClass( data, classe ) );
                injectable( classe, data );
            }
        }
    }
    
    private void injectable( Class<?> classe, Injectable an ){
        //injectableArgs( classe, an.property() );
        //injectableArgs( classe, an.map() );
        //injectableArgs( classe, an.set() );
        //injectableArgs( classe, an.list() );
    }
    
    
    private void injectableArgs( Class<?> classe, PropertiesInject[] props ){
        for( PropertiesInject prop: props ){
            beans.put( prop.name(), new EntryClass( prop, classe ) );
        }
    }
    
    private void injectableArgs( Class<?> classe, MapInject[] list ){
        for( MapInject obj: list ){
            beans.put( obj.name(), new EntryClass( obj, classe ) );
        }
    }
    
    private void injectableArgs( Class<?> classe, SetInject[] list ){
        for( SetInject obj: list ){
            beans.put( obj.name(), new EntryClass( obj, classe ) );
        }
    }
    
    private void injectableArgs( Class<?> classe, CollectionInject[] list ){
        for( CollectionInject obj: list ){
            beans.put( obj.name(), new EntryClass( obj, classe ) );
        }
    }
    
    private Class geClassBean( Class<?> classEntity ){
        java.lang.reflect.Type[] sc = classEntity.getGenericInterfaces();
        for( java.lang.reflect.Type in: sc ){
            if( in instanceof ParameterizedType && ((ParameterizedType)in).getRawType() == FactoryBean.class ){
                ParameterizedType pc = ((ParameterizedType)in);
                return (Class)pc.getActualTypeArguments()[0];
            }
        }
        return null;
    }
    
    private void addBean( String beanName, java.lang.reflect.Type type, String path ) throws Exception{
        EntryClass entryClass = beans.get( beanName );
        if( entryClass == null )
            return;

        if( entryClass.getAnnotation() instanceof Injectable ){
            Injectable data = (Injectable) entryClass.getAnnotation();

            if( FactoryBean.class.isAssignableFrom( entryClass.getClassType() ) ){
                addBean( 
                        data.name(), 
                        null/*data.scope()*/,
                        data.singleton(), 
                        geClassBean( entryClass.getClassType() ),
                        "factory_"+data.name(),
                        path + "[" + entryClass.getClassType().getName() + "]"
                );
                
                addBean( 
                        "factory_"+data.name(), 
                        null/*data.scope()*/,
                        data.singleton(), 
                        entryClass.getClassType(),
                        null,
                        path + "[" + entryClass.getClassType().getName() + "]"
                );
            }
            else{
                addBean( 
                        data.name(), 
                        null/*data.scope()*/,
                        data.singleton(), 
                        entryClass.getClassType(), 
                        null,
                        path + "[" + entryClass.getClassType().getName() + "]"
                );
            }
        }
        else
        if( entryClass.getAnnotation() instanceof Frame ){
            Frame data = (Frame) entryClass.getAnnotation();

            addBean( 
                    data.name().length() == 0? entryClass.getClassType().getSimpleName() : data.name(), 
                    null,//data.scope(),
                    false, 
                    entryClass.getClassType(), 
                    null,
                    path + "[" + entryClass.getClassType().getName() + "]"
            );
        }
        else
        if( entryClass.getAnnotation() instanceof Intercepts ){
            Intercepts data = (Intercepts) entryClass.getAnnotation();

            addBean( 
                    data.name(), 
                    ScopeType.valueOf("singleton"),
                    false, 
                    entryClass.getClassType(), 
                    null,
                    path + "[" + entryClass.getClassType().getName() + "]"
            );
        }
        else
        if( type != null ){
            addExtendBean( entryClass, type, path );
        }
    }
    
    private void addExtendBean( EntryClass entryClass, java.lang.reflect.Type type, String path ){
        try{
            if( entryClass.getAnnotation() instanceof SetInject ){
                SetInject data = (SetInject) entryClass.getAnnotation();
                addCollectionBean( data.name(), ScopeType.APPLICATION, false, data.values(), type, data.type(), data.factory().equals( "" )? null : data.factory(), path );
            }
            else
            if( entryClass.getAnnotation() instanceof CollectionInject ){
                CollectionInject data = (CollectionInject) entryClass.getAnnotation();
                addCollectionBean( data.name(), ScopeType.APPLICATION, false, data.values(), type, data.type(), data.factory().equals( "" )? null : data.factory(), path );
            }
            else
            if( entryClass.getAnnotation() instanceof MapInject ){
                MapInject data = (MapInject) entryClass.getAnnotation();
                addMapBean( data.name(), ScopeType.APPLICATION, false, data.values(), type, data.type(), data.factory().equals( "" )? null : data.factory(), path );
            }
            else
            if( entryClass.getAnnotation() instanceof PropertiesInject ){
                PropertiesInject data = (PropertiesInject) entryClass.getAnnotation();
                addMapBean( data.name(), ScopeType.APPLICATION, false, data.values(), type, data.type(), data.factory().equals( "" )? null : data.factory(), path );
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new MappingException( e );
        }
    }
    
    private void addBean( String name, ScopeType scope, boolean singleton, Class classBean, String factoryBean, String path ) throws Exception{
        //if( getIocManager().getBean( name ) != null )
        //    return;
        
        Bean bean;
        
        bean = getIocManager().addBean( name, classBean, scope, singleton, factoryBean );
        if( factoryBean == null ){
            constructorBean( bean, path );
            setterBean( bean, path );
        }
    }
    
    private Constructor getConstructorBean( Class<?> classBean ) throws NoSuchMethodException{
        Constructor[] cons = classBean.getConstructors();
        for( Constructor con: cons ){
            if( con.isAnnotationPresent( ConstructorInject.class ) )
                return con;
        }
        return classBean.getConstructor();
    }
    
    private void constructorBean( Bean bean, String path ) throws Exception{
        Class<?> classBean = bean.getInjectable().getTarget();
        
        
        Constructor constructor = getConstructorBean( classBean );
        
        java.lang.reflect.Type[] parameters = constructor.getGenericParameterTypes();
        Inject[] injectParams = new Inject[]{};
        
        if( constructor.isAnnotationPresent( ConstructorInject.class ) ){
            ConstructorInject cin = (ConstructorInject) constructor.getAnnotation( ConstructorInject.class );
            injectParams = cin.value();
        }
        
        ConstructorBean cb = bean.getConstructor( constructor.getParameterTypes() );
        
        for( int i=0; i<parameters.length; i++){
            java.lang.reflect.Type paramType = parameters[i];
            if( i < injectParams.length ){
                Inject val = injectParams[i];
                
                if( val.value().length() != 0 ){
                    Type type = Types.getType( (Class)paramType );
                    if( type == null )
                        throw new MappingException( constructor.toGenericString() + ": invalid arg " + ((Class)paramType).getName() );
                    
                    cb.addValueArg( type.getValue( null, null, val.value() ) );
                }
                else
                if( val.ref().length() != 0 ){
                    String tmpPath = path + "$(" + i + ")";
                    String idPath  = "[$cb]$($i)"
                                        .replace( "$cb", classBean.getName() )
                                        .replace( "$i", String.valueOf( i ) );
                    
                    if( path.indexOf( idPath ) != -1 )
                        throw new MappingException( "circular reference not allowed: " + constructor.toGenericString() );
                    
                    if( getIocManager().getBean( val.ref() ) == null )
                        this.addBean( val.ref(), paramType, tmpPath );
                    
                    cb.addRefArg( val.ref() );
                }
                else
                    cb.addValueArg( null );
                
            }
            else
                cb.addValueArg( null );

        }
    }

    private void setterBean( Bean bean, String path ) throws Exception{
        Class<?> classBean = bean.getInjectable().getTarget();
        
        Method[] methods = classBean.getDeclaredMethods();
        
        for( int i=0; i<methods.length; i++){
            Method m = methods[i];
            
            if( m.isAnnotationPresent( PropertyInject.class ) ){
                PropertyInject pi = m.getAnnotation( PropertyInject.class );
                Inject val        = pi.value();
                Field f           = getFieldBySetter( m );
                
                if( val.value().length() != 0 ){
                    Type type = Types.getType( f.getType() );
                    if( type == null )
                        throw new MappingException( f.toGenericString() + ": invalid type " + f.getType().getName() );
                    
                    bean.addPropertyValue( f.getName(), type.getValue( null, null, val.value() ) );
                }
                else
                if( val.ref().length() != 0 ){
                    String tmpPath = path + "." + f.getName() + "";
                    String idPath  = "[$cb].$i"
                                        .replace( "$cb", classBean.getName() )
                                        .replace( "$i", f.getName() );
                    
                    if( path.indexOf( idPath ) != -1 )
                        throw new MappingException( "circular reference not allowed: " + f.toGenericString() );
                    
                    if( getIocManager().getBean( val.ref() ) == null )
                        this.addBean( val.ref(), f.getGenericType(), tmpPath );
                    
                    bean.addPropertyRef( f.getName(), val.ref() );
                }
                else
                    bean.addPropertyValue( f.getName(), null );
                
            }

        }
    }
    
    private Field getFieldBySetter( Method method ){
        String methodName = method.getName();
        if( !methodName.startsWith( "set" ) )
            throw new MappingException( "expected set<field_name>(): " + method.getName() );

        String fieldName = methodName.substring( 3 );
        Class<?> classe  = method.getDeclaringClass();
        Field f          = null;
        
        try{
            f = classe.getDeclaredField( fieldName );
        }
        catch( Exception e ){
            try{
                fieldName = String.valueOf( fieldName.charAt( 0 ) ).toLowerCase() + fieldName.substring( 1 );
                f = classe.getDeclaredField( fieldName );
            }
            catch( Exception k ){
            }
        }
        
        if( f == null )
            throw new MappingException( fieldName + " not found in the " + classe.getName() );
        
        return f;
    }
    
    private void addCollectionBean( String name, ScopeType scope, boolean singleton, Inject[] values,
            java.lang.reflect.Type type, Class<?> objectType, String factory, String path ) throws Exception{

        Class<?> valueType = getCollectionType( type );
        
        Class<?> classBean = type instanceof ParameterizedType?
            (Class)((ParameterizedType)type).getRawType() :
            (Class)type;
        
        
        CollectionBean collectionBean =
            getIocManager().addCollection( name, objectType, valueType, factory );
        
        for( Inject val: values ){
            if( val.value().length() != 0 ){
                collectionBean.addValue( 
                        Types.getType( (Class)valueType ).getValue( null, null, val.value() ) 
                );
            }
            else
            if( val.ref().length() != 0 ){
                    String tmpPath = path + "[" + classBean.getName() + "]";
                    String idPath  = "[$cb]"
                                        .replace( "$cb", classBean.getName() );
                    
                    if( path.indexOf( idPath ) != -1 )
                        throw new MappingException( "circular reference not allowed: " + classBean.getName() );
                    
                    if( getIocManager().getBean( val.ref() ) == null )
                        this.addBean( val.ref(), classBean, tmpPath );
                    
                    collectionBean.addBean( val.ref() );
            }

        }
    }
    
    private void addMapBean( String name, ScopeType scope, boolean singleton, Prop[] values,
            java.lang.reflect.Type type, Class<?> objectType, String factory, String path ) throws Exception{

        
        Class<?>[] valueType = 
                Properties.class.isAssignableFrom( objectType )?
                    new Class[]{ String.class, String.class } :
                    getMapType( type );
        
        Class<?> classBean = type instanceof ParameterizedType?
            (Class)((ParameterizedType)type).getRawType() :
            (Class)type;
        
        MapBean mapBean =
                Properties.class.isAssignableFrom( objectType )?
                    getIocManager().addProperties( 
                            name, 
                            (Class)objectType 
                    ) :
                    getIocManager().addMap( 
                            name, 
                            objectType, 
                            (Class)valueType[0], 
                            (Class)valueType[1],
                            factory
                );
        
        for( Prop val: values ){
            Type keyType = Types.getType( (Class)valueType[0] );
            if( val.value().value().length() != 0 ){
                Type valType = Types.getType( (Class)valueType[1] );
                mapBean.addValue( keyType.getValue( null, null, val.key() ), valType.getValue( null, null, val.value().value() ) );
            }
            else
            if( val.value().ref().length() != 0 ){
                    String tmpPath = path + "[" + classBean.getName() + "]";
                    String idPath  = "[$cb]"
                                        .replace( "$cb", classBean.getName() );
                    
                    if( path.indexOf( idPath ) != -1 )
                        throw new MappingException( "circular reference not allowed: " + classBean.getName() );
                    
                    if( getIocManager().getBean( val.value().ref() ) == null )
                        this.addBean( val.value().ref(), classBean, tmpPath );
                    
                    mapBean.addBean( keyType.getValue( null, null, val.key() ), val.value().ref() );
            }

        }
    }

    private Class getCollectionType( java.lang.reflect.Type type ){
        Class genericType = null;
        
        if( type instanceof ParameterizedType ){
            genericType = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
        }
        else
            throw new MappingException( "expected List<Type>" );
        
        return genericType;
    }
    
    private Class[] getMapType( java.lang.reflect.Type classe ){
        Class[] genericType = null;
        
        
        java.lang.reflect.Type classType = classe;
        if( classType instanceof ParameterizedType ){
            genericType = new Class[2];
            genericType[0] = (Class) ((ParameterizedType)classType).getActualTypeArguments()[0];
            genericType[1] = (Class) ((ParameterizedType)classType).getActualTypeArguments()[1];
        }
        else
            throw new MappingException( "expected List<Type>" );
        return genericType;
    }

    public IOCManager getIocManager() {
        return iocManager;
    }

    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }
}

class EntryClass{
    
    private Object annotation;
    
    private Class classType;

    public EntryClass( Object annotation, Class classType ){
        this.annotation = annotation;
        this.classType = classType;
    }
    
    public Object getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Object annotation) {
        this.annotation = annotation;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
}