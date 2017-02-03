package org.brandao.brutos.mapping.ioc;


public class MapInject extends ComplexObjectInject{
    
    public MapInject( String name, Class keyType, Class valueType, Class type, String factory, Property[] props ) {
        //super( type == null? java.util.HashMap.class : type, name, props );
        super( name, keyType == null? String.class : keyType, valueType == null? String.class : valueType, java.util.Map.class, factory, props );
        setType( type == null? java.util.HashMap.class : type );
    }
    
}
