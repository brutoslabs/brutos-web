
package org.brandao.brutos;

import org.brandao.brutos.scope.Scope;


public interface ScopeRegistry {
    
    void registerScope(String name, Scope scope);
    
    Scope getRegistredScope(String name);
    
    Scope getRegistredScope(ScopeType scopeType);
    
}
