
package org.brandao.webchat.controller.type;

import java.io.Serializable;
import org.brandao.brutos.annotation.TypeDef;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;

@TypeDef
public class SerializableObjectTypeFactory implements
    TypeFactory{

    @Override
    public Class getClassType() {
        return Serializable.class;
    }

    @Override
    public Type getInstance() {
        return new SerializableObjectType();
    }

}
