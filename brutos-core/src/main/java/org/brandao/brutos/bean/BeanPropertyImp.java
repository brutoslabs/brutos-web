package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BeanPropertyImp implements BeanProperty{
    
    private Field field;
    private Method set;
    private Method get;
    private String name;
    
    public BeanPropertyImp(Field field, Method set, Method get, String name){
        this.field = field;
        this.set = set;
        this.get = get;
        this.name = name;
    }
    
    public void set( Object o, Object value ) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        
        if(set != null)
            set.invoke( o, new Object[]{value} );
        else{
            field.setAccessible(true);
            field.set(o, value);
        }
    }
    
    public Object get(Object o) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException {
        
        if(get != null)
            return get.invoke( o, new Object[]{} );
        else{
            field.setAccessible(true);
            return field.get(o);
        }
    }

    public Object getGenericType(){
        try{
            if(get != null)
                return getGenericReturnType( get );
            else
                return getGenericType( field );
        }
        catch(NoSuchMethodException e){
            return this.getType();
        }
        catch(Exception e){
            throw new BeanException(e);
        }
            
    }
    
    private Object getGenericType( Field field ) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        Class fieldClass = field.getClass();
        Method getGenericReturnType =
            fieldClass.getMethod("getGenericType", new Class[]{});

        return getGenericReturnType.invoke(field, new Object[]{});
    }
    
    private Object getGenericReturnType( Method method ) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
        Class methodClass = method.getClass();
        Method getGenericReturnType =
            methodClass.getMethod("getGenericReturnType", new Class[]{});

        return getGenericReturnType.invoke(method, new Object[]{});
    }

    public Class getType(){
        
        if(get != null)
            return getReturnType( get );
        else
            return getType( field );
            
    }
    
    private Class getType( Field field ){
        return field.getType();
    }
    
    private Class getReturnType( Method method ){
        return method.getReturnType();
    }
    
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getSet() {
        return set;
    }

    public void setSet(Method set) {
        this.set = set;
    }

    public Method getGet() {
        return get;
    }

    public void setGet(Method get) {
        this.get = get;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Object getDeclaredGenericType() {
		return this.getGenericType();
	}

	public Class getDeclaredType() {
		return this.getType();
	}
    
}
