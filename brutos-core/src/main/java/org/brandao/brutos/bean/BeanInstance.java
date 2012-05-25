/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;


/**
 *
 * @author Afonso Brandao
 */
public class BeanInstance {

    private static Map cache;

    static{
        cache = new HashMap();
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

    public void set( String property, Object value ) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        
        BeanProperty prop = data.getProperty(property);
        if(prop == null)
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );
        prop.set(object, value);
    }

    public Object get( String property ) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        
        BeanProperty prop = data.getProperty(property);
        if(prop == null)
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );
        
        return prop.get(object);
    }
    
    /*
    public SetterProperty getSetter( String property ){
        Object access = data.getSetter().get(property);
        
        if( access instanceof Method )
            return new SetterProperty( (Method)access, object );
        else
        if( access instanceof Field )
            return new SetterProperty( (Field)access, object );
        else
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );
    }
    */
    /*
    public GetterProperty getGetter( String property ){
        Object access = data.getGetter().get(property);
        
        if( access instanceof Method )
            return new GetterProperty( (Method)access, object );
        else
        if( access instanceof Field )
            return new GetterProperty( (Field)access, object );
        else
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );
    }
    */
    
    private BeanData getBeanData( Class clazz ){
        if( cache.containsKey(clazz) )
            return (BeanData) cache.get(clazz);
        else{
            BeanData data = new BeanData();
            data.setClassType(clazz);
            
            Field[] fields = clazz.getFields();

            for( int i=0;i<fields.length;i++ ){
                Field f = fields[i];
                data.addProperty(f.getName(), new BeanProperty(f,null,null,f.getName()));
                data.getSetter().put(f.getName(), f);
                data.getGetter().put(f.getName(), f);
            }
            
            Method[] methods = clazz.getMethods();
            
            for( int i=0;i<methods.length;i++ ){
                Method method = methods[i];
                String methodName = method.getName();

                if( methodName.startsWith("set") && method.getParameterTypes().length == 1 ){
                    String id = methodName
                            .substring(3,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    
                    if(data.getProperty(id) != null)
                        data.getProperty(id).setSet(method);
                    else
                        data.addProperty(id, new BeanProperty(null,method,null,id));
                    
                    data.getSetter().put(id, method);
                }
                else
                if( methodName.startsWith("get") &&
                    method.getParameterTypes().length == 0  &&
                    method.getReturnType() != void.class ){
                    String id = methodName
                            .substring(3,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    
                    if(data.getProperty(id) != null)
                        data.getProperty(id).setGet(method);
                    else
                        data.addProperty(id, new BeanProperty(null,null,method,id));
                    
                    data.getGetter().put(id, method);
                }
                else
                if( methodName.startsWith("is") &&
                    method.getParameterTypes().length == 0  &&
                    ClassUtil.getWrapper(method.getReturnType()) == Boolean.class ){
                    String id = methodName
                            .substring(2,methodName.length());

                    id = Character.toLowerCase( id.charAt(0) )+ id.substring(1, id.length());
                    
                    if(data.getProperty(id) != null)
                        data.getProperty(id).setGet(method);
                    else
                        data.addProperty(id, new BeanProperty(null,null,method,id));
                    
                    data.getGetter().put(id, method);
                }
            }
            cache.put( clazz, data );
            return data;
        }
    }

    public boolean containProperty( String property ){
        return data.getProperties().containsKey(property);
    }
    
    public Class getType( String property ){
        //Method method = (Method) data.getGetter().get(property);
        
        BeanProperty prop = data.getProperty(property);
        
        if( prop == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );

        //return method.getReturnType();

        return prop.getType();
    }

    public Object getGenericType( String property ){
        
        BeanProperty prop = data.getProperty(property);
        //Method method = (Method) data.getGetter().get(property);
        
        if( prop == null )
            throw new BrutosException( "not found: " + clazz.getName() + "." + property );

        try{
            //return getGenericReturnType( method );
            return prop.getGenericType();
        }
        catch( NoSuchMethodException ex ){
            return this.getType(property);
        }
        catch( Exception ex ){
            throw new BrutosException(ex);
        }

    }

    /*
    private Object getGenericReturnType( Method method ) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        Class methodClass = method.getClass();
        Method getGenericReturnType =
            methodClass.getMethod("getGenericReturnType", new Class[]{});

        return getGenericReturnType.invoke(method, new Object[]{});
    }
    */
    
    public Class getClassType(){
        return this.clazz;
    }
    
    public List getProperties(){
        return new LinkedList(this.data.getProperties().values());
    }
    
}

class BeanData{

    private Class classType;
    private Map setter;
    private Map getter;
    private Map properties;
    
    public BeanData(){
        this.setter = new HashMap();
        this.getter = new HashMap();
        this.properties = new HashMap();
    }
    
    public void addProperty(String name, BeanProperty property){
        this.properties.put(name, property);
    }

    public BeanProperty getProperty(String name){
        return (BeanProperty)this.properties.get(name);
    }
    
    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
    public Map getSetter() {
        return setter;
    }

    public void setSetter(Map setter) {
        this.setter = setter;
    }

    public Map getGetter() {
        return getter;
    }

    public void setGetter(Map getter) {
        this.getter = getter;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}