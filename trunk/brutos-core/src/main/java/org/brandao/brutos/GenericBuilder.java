package org.brandao.brutos;

import org.brandao.brutos.type.Type;

public interface GenericBuilder {

    MetaBeanBuilder buildMetaBean(String name, Class<?> classType);

    MetaBeanBuilder buildMetaBean(String name, 
            EnumerationType enumProperty, String temporalProperty, 
            Class<?> classType);

    MetaBeanBuilder buildMetaBean(String name, Type type );

    MetaBeanBuilder buildMetaBean(String name, ScopeType scope, Class<?> classType);

    MetaBeanBuilder buildMetaBean(String name, ScopeType scope, Type type);
    
    MetaBeanBuilder buildMetaBean(String name, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            Class<?> classType, Type type );
    
}
