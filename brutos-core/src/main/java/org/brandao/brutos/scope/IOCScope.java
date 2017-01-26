

package org.brandao.brutos.scope;

import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Invoker;


public class IOCScope implements Scope{

    public IOCScope() {
    }

    public void put(String name, Object value){
    }

    public Object get(String name) {
        ApplicationContext app = Invoker.getCurrentApplicationContext();
        return app.getBean(name);
    }

    public Object getCollection( String name ){
        return get( name );
    }

    public void remove( String name ){
    }
    
}
