package org.brandao.brutos.mapping.ioc;


public class ListInject extends ComplexObjectInject{
    
    public ListInject( String name, Class valueType, Class type, String factory, Property[] props ) {
        //super( type == null? java.util.ArrayList.class : type, name, props );
        super( name, null, valueType, java.util.List.class, factory, props );
        setType( type == null || type == java.util.List.class? java.util.ArrayList.class : type );
    }
    
}
