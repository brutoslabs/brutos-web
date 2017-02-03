package org.brandao.brutos.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.ResourceAction;


public abstract class AbstractInterceptor implements InterceptorController{
    
    protected Map<String,Object> props;
    
    private Set<String> excludeMethods;
    
    public AbstractInterceptor() {
    }


    public boolean accept(InterceptorHandler handler) {
        ResourceAction rm = handler.getResourceAction();
        if( rm != null && excludeMethods != null )
            return !excludeMethods.contains( rm.getMethod().getName() );
        else
            return true;
    }
    
    public void setProperties( Map<String,Object> props ){
        this.props = props;
        
        if( props != null ){
            if( props.containsKey( "excludeMethods" ) ){
                String em = (String)props.get( "excludeMethods" );
                String[] ems = em.split( "," );
                this.excludeMethods = new HashSet<String>(Arrays.asList( ems ));
            }
        }
    }

    public boolean isConfigured(){
        return props != null;
    }

}
