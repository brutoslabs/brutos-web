package org.brandao.webchat.model;

import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeFactory;

/**
 *
 * @author Brandao
 */
public class RoomServiceTypeFactory 
    implements TypeFactory{

    @Override
    public Type getInstance() {
        return new RoomServiceType();
    }

    @Override
    public Class getClassType() {
        return RoomService.class;
    }
    
}
