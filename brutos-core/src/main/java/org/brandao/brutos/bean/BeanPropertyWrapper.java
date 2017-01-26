

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BeanPropertyWrapper implements BeanProperty{
    
    protected BeanProperty beanProperty;
    
    public BeanPropertyWrapper(BeanProperty beanProperty){
        this.beanProperty = beanProperty;
    }

    public void set(Object o, Object value) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException {
        this.beanProperty.set(o, value);
    }

    public Object get(Object o) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException {
        return this.beanProperty.get(o);
    }

    public Object getGenericType(){
        return this.beanProperty.getGenericType();
    }

    public Class getType() {
        return this.beanProperty.getType();
    }

    public Field getField() {
        return this.beanProperty.getField();
    }

    public void setField(Field field) {
        this.beanProperty.setField(field);
    }

    public Method getSet() {
        return this.beanProperty.getSet();
    }

    public void setSet(Method set) {
        this.beanProperty.setSet(set);
    }

    public Method getGet() {
        return this.beanProperty.getGet();
    }

    public void setGet(Method get) {
        this.beanProperty.setGet(get);
    }

    public String getName() {
        return this.beanProperty.getName();
    }

    public void setName(String name) {
        this.beanProperty.setName(name);
    }

	public Object getDeclaredGenericType() {
		return this.beanProperty.getGenericType();
	}

	public Class getDeclaredType() {
		return this.beanProperty.getType();
	}
}
