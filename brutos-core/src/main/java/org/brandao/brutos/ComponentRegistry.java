

package org.brandao.brutos;

import org.brandao.brutos.io.ResourceLoader;


public interface ComponentRegistry 
    extends ControllerRegistry, 
        PropertyRegistry, 
        InterceptorRegistry, ScopeRegistry, TypeRegistry, ResourceLoader{
    
}
