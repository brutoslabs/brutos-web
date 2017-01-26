


package org.brandao.brutos.type;

import java.io.IOException;
import org.brandao.brutos.MvcResponse;


public class ObjectType 
    extends AbstractType {

    private Type serializableType;

    public ObjectType() {
        this(null);
    }
    
    public ObjectType(Class classType) {
        this.classType = classType;
    }

    public Object convert(Object value) {
        return value;
    }

    public void show(MvcResponse response, Object value) throws IOException {
        serializableType.show(response, value);
    }

}
