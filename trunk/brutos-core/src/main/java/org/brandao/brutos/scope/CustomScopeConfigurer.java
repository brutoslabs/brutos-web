

package org.brandao.brutos.scope;

import java.util.Map;


public class CustomScopeConfigurer {

    private Map customEditors;

    public Map getCustomScopes() {
        return customEditors;
    }

    public void setCustomScopes(Map customEditors) {
        this.customEditors = customEditors;
    }

    public Scope getScope( String name ){
        return (Scope) customEditors.get(name);
    }
}
