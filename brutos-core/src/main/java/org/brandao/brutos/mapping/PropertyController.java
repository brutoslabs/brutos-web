package org.brandao.brutos.mapping;

import java.lang.reflect.InvocationTargetException;
import org.brandao.brutos.bean.BeanProperty;


public class PropertyController extends UseBeanData{

    private boolean request;
    
    private boolean response;
    
    private boolean persistenceContext;

    private String propertyName;
    
    private BeanProperty beanProperty;
    
    private Controller controller;
    
    public PropertyController() {
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(boolean persistenceContext) {
        this.persistenceContext = persistenceContext;
    }
    
    public boolean equals( Object o ){
        return o instanceof PropertyController? 
            ((PropertyController)o).propertyName.equals( propertyName ) :
            false;
    }

    protected void validate(Object source, Object value) {
        this.validate.validate(this, source, value);
    }

    public BeanProperty getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(BeanProperty beanProperty) {
        this.beanProperty = beanProperty;
    }
    
    public Object getValueFromSource(Object source) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException{
        return this.beanProperty.get(source);
    }
    
    public void setValueInSource(Object source, Object value) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException{
        this.beanProperty.set(source, value);
    }

    public void setValue(Object source) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException {
        Object value = super.getValue(source);
        this.setValueInSource(source, value);
    }
    
    
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
}
