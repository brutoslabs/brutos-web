package org.brandao.brutos.test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.brandao.brutos.scope.Scope;


public class MockScope implements Scope{

    private final ConcurrentMap values;
    
    public MockScope(){
        this.values = new ConcurrentHashMap();
    }
    
    public void put(String name, Object value) {
        this.values.put(name, value);
    }

    public Object get(String name) {
        return this.values.get(name);
    }

    public Object getCollection(String name) {
        return this.values.get(name);
    }

    public void remove(String name) {
        this.values.remove(name);
    }
    
}
