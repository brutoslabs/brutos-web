

package org.brandao.brutos.mapping;

import java.lang.reflect.Method;


public class ReverseActionKey {

    private String methodName;

    private Class<?>[] parametersType;

    private final int hashCode;

    public ReverseActionKey(Method method){
        this.methodName = method.getName();
        this.parametersType = method.getParameterTypes();
        this.hashCode = createHashCode();
    }

    private int createHashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.methodName.hashCode();

        int len = this.parametersType.length;
        for( int i=0;i<len;i++ )
            result = prime * result + this.parametersType[i].hashCode();
        
        return result;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParametersType() {
        return parametersType;
    }

    public int hashCode(){
        return this.hashCode;
    }

    public boolean equals(Object o){
        
        if( !(o instanceof ReverseActionKey) )
            return false;

        ReverseActionKey key = ((ReverseActionKey)o);

        if( !this.methodName.equals(key.getMethodName()) )
            return false;

        Class<?>[] keyTypes = key.getParametersType();
        if( this.parametersType.length != keyTypes.length )
            return false;

        int len = this.parametersType.length;
        for( int i=0;i<len;i++ ){
            if( !this.parametersType[i].equals(keyTypes[i]) )
                return false;
        }

        return true;
    }
}
