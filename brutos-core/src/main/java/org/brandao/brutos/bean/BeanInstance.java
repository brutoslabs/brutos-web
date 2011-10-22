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

package org.brandao.brutos.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassType;


/**
 *
 * @author Afonso Brandao
 */
public class BeanInstance {

    private static Map<Class, BeanData> cache;

    static{
        cache = new HashMap<Class, BeanData>();
    }

    private Object object;
    private Class clazz;
    private BeanData data;
    
    public BeanInstance( Object object ){
        this( object, object.getClass() );
    }

    public BeanInstance( Object object, Class clazz ){
        this.object = object;
        this.clazz  = clazz;
        this.data   = getBeanData( this.clazz );
    }

    public SetterProperty getSetter( String property ){
        Method method = data.getSetter().get(property);
        if( method == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );
        return new SetterProperty( method, object );
        //return new SetterProperty( clazz.getDeclaredField( fieldName ), object );
    }

    private BeanData getBeanData( Class clazz ){
        if( cache.containsKey(clazz) )
            return cache.get(clazz);
        else{
            BeanData data = new BeanData();
            data.setClassType(clazz);
            for( Method method: clazz.getMethods() ){
                String methodName = method.getName();

                if( methodName.startsWith("set") && method.getParameterTypes().length == 1 ){
                    String id = methodName
                            .substring(3,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    data.getSetter().put(id, method);
                }
                else
                if( methodName.startsWith("get") &&
                    method.getParameterTypes().length == 0  &&
                    method.getReturnType() != void.class ){
                    String id = methodName
                            .substring(3,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    data.getGetter().put(id, method);
                }
                else
                if( methodName.startsWith("is") &&
                    method.getParameterTypes().length == 0  &&
                    ClassType.getWrapper(method.getReturnType()) == Boolean.class ){
                    String id = methodName
                            .substring(2,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    data.getGetter().put(id, method);
                }
            }
            cache.put( clazz, data );
            return data;
        }
    }

    public GetterProperty getGetter( String property ){
        Method method = data.getGetter().get(property);
        if( method == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );

        return new GetterProperty( method, object );
        //return new GetterProperty( clazz.getDeclaredField( fieldName ), object );
    }

    public boolean containProperty( String property ){
        return data.getGetter().get(property) != null;
    }
    
    public Class getType( String property ){
        Method method = data.getGetter().get(property);
        if( method == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );

        return method.getReturnType();
    }

    public Object getGenericType( String property ){
        
        Method method = data.getGetter().get(property);
        if( method == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );

        try{
            return getGenericReturnType( method );
        }
        catch( NoSuchMethodException ex ){
            return this.getType(property);
        }
        catch( Exception ex ){
            throw new BrutosException(ex);
        }

    }

    private Object getGenericReturnType( Method method ) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        Class methodClass = method.getClass();
        Method getGenericReturnType =
            methodClass.getMethod("getGenericReturnType");

        return getGenericReturnType.invoke(method);
    }

    public Class getClassType(){
        return this.clazz;
    }
}

class BeanData{

    private Class<?> classType;
    private Map<String,Method> setter;
    private Map<String,Method> getter;

    public BeanData(){
        this.setter = new HashMap<String, Method>();
        this.getter = new HashMap<String, Method>();
    }
    
    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public Map<String, Method> getSetter() {
        return setter;
    }

    public void setSetter(Map<String, Method> setter) {
        this.setter = setter;
    }

    public Map<String, Method> getGetter() {
        return getter;
    }

    public void setGetter(Map<String, Method> getter) {
        this.getter = getter;
    }

}