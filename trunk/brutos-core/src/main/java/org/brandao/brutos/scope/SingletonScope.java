

package org.brandao.brutos.scope;

import java.util.HashMap;
import java.util.Map;


public class SingletonScope implements Scope{

    private static final Map values;

    static{
        values = new HashMap();
    }

    public void put(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }

    public Object getCollection(String name) {
        return get(name);
    }

    public void remove(String name) {
        values.remove(name);
    }
    
}
