

package org.brandao.brutos.mapping;


public class InnerBean extends DependencyBean{

    public InnerBean(Bean parent) {
        super(parent);
    }

    protected void validate(Object source, Object value) {
    }
    
}
