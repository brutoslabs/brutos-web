

package org.brandao.brutos.mapping;


public class ConstructorArgBean extends DependencyBean{

    public ConstructorArgBean(Bean mappingBean){
        super(mappingBean);
    }

    protected void validate(Object source, Object value) {
        if(this.validator != null)
            this.validator.validate(this, value);
    }

}
