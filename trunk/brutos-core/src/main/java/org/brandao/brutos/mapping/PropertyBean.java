package org.brandao.brutos.mapping;

import java.lang.reflect.InvocationTargetException;
import org.brandao.brutos.bean.BeanProperty;


public class PropertyBean extends DependencyBean{

    private String name;

    private BeanProperty beanProperty;
    
    public PropertyBean(Bean mappingBean) {
        super(mappingBean);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getParameterName(){
        return super.parameterName == null? 
                this.name : 
                super.parameterName;
    }

    protected void validate(Object source, Object value) {
        if(this.validator != null)
            this.validator.validate(this, source, value);
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
    
}
