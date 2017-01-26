

package org.brandao.brutos.web.scope;

import javax.servlet.ServletContext;
import org.brandao.brutos.scope.Scope;


public class ApplicationScope implements Scope{
    
    private ServletContext context;
    
    public ApplicationScope( ServletContext context ) {
        this.context = context;
    }

    public void put(String name, Object value) {
        context.setAttribute( name, value );
    }

    public Object get(String name) {
        return context.getAttribute( name );
    }

    public Object getCollection(String name) {
        return context.getAttribute( name );
    }

    public void remove( String name ){
        context.removeAttribute(name);
    }

}
