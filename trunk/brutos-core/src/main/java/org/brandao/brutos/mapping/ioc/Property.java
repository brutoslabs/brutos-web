package org.brandao.brutos.mapping.ioc;


public class Property {
    
    private Injectable key;
    
    private Injectable value;
    
    public Property() {
    }

    public Property( Injectable key, Injectable value ) {
        this.setKey(key);
        this.setValue(value);
    }

    public Injectable getKey() {
        return key;
    }

    public void setKey(Injectable key) {
        this.key = key;
    }

    public Injectable getValue() {
        return value;
    }

    public void setValue(Injectable value) {
        this.value = value;
    }
    
}
