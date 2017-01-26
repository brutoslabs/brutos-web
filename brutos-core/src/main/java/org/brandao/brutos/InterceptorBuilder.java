

package org.brandao.brutos;

import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.InterceptorStack;
import org.brandao.brutos.mapping.MappingException;


public class InterceptorBuilder {

    private Interceptor interceptor;
    
    private InterceptorManager manager;

    public InterceptorBuilder( Interceptor interceptor, InterceptorManager manager ) {
        this.interceptor = interceptor;
        this.manager = manager;
    }

    
    public InterceptorBuilder addParameter( String name, String value ){
        interceptor.setProperty(name, value);
        return this;
    }
}