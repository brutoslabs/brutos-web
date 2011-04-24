/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.scope;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Brandao
 */
public class ApplicationScope implements Scope{

    private Map values = new HashMap();

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
