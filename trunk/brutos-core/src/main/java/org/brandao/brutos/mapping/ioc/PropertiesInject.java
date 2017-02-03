package org.brandao.brutos.mapping.ioc;

import java.util.List;


public class PropertiesInject extends ComplexObjectInject{
    
    private List props;
    
    public PropertiesInject( Class type, String name, String factory, Property[] props ) {
        //super( type, name, ScopeType.REQUEST, false );
        super( name, String.class, String.class, java.util.Properties.class, factory, props );
        setSingleton( true );
        setType( type == null? java.util.Properties.class : type );
        //setConstructor( new ConstructorInject( null, this ) );
    }
    
    public List getProps() {
        return props;
    }

    public void setProps(List props) {
        this.props = props;
    }
    
}
