

package org.brandao.brutos.helper.controller;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.AbstractType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;


public class TypeTest 
    extends AbstractType implements TypeFactory{

    public Object convert(Object value) {
        return new SimpleBean();
    }

    public void show(MvcResponse response, Object value) throws IOException {
    }

    public Class getClassType() {
        return SimpleBean.class;
    }

    public Type getInstance() {
        return this;
    }

    public boolean matches(Class type) {
        return type == SimpleBean.class;
    }

}
